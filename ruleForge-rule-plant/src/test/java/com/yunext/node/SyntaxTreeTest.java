package com.yunext.node;

import cn.hutool.core.lang.Pair;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.yunext.api.service.core.ChainExecuteService;
import com.yunext.api.service.plant.ChainNodeService;
import com.yunext.api.service.reload.RuleReloadService;
import com.yunext.common.base.BasicNode;
import com.yunext.common.base.ComponentContextData;
import com.yunext.common.enums.ComponentEnum;
import com.yunext.common.enums.NodeTypeEnum;
import com.yunext.common.utils.StringUtil;
import com.yunext.node.struct.graph.ChainNodeDirectedGraph;
import com.yunext.node.struct.graph.ChainNodeUndirectedGraph;
import com.yunext.node.struct.tree.RuleSyntaxTree;
import org.apache.dubbo.config.annotation.DubboReference;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author ：qianjb [qianjb@hadlinks.com]
 * @description ：语法树测试
 * @date ：Created in 2024/7/4 15:07
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = RuleForgePlantApplication.class)
@ActiveProfiles("local")
public class SyntaxTreeTest {

    @DubboReference
    private RuleReloadService ruleReloadService;

    @DubboReference
    private ChainExecuteService chainExecuteService;

    private final String TEST_DATA = """
            [
                {
                 "vertices": ["A1", "B1", "C1", "D1", "E1", "F1", "G1", "H1", "I1"],
                 "edges": [["A1", "D1"], ["B1", "D1"], ["C1", "E1"], ["D1", "F1"], ["E1", "F1"], ["F1", "G1"], ["F1", "H1"], ["G1", "I1"]]
               }
            ]
            """;

    @Test
    public void testTreeCreate() {
        this.createAndExecute(graph -> {
            List<ChainNodeUndirectedGraph<BasicNode<?, ?>>> subGraphs = graph.connectedSubGraphByDfs();
            for (ChainNodeUndirectedGraph<BasicNode<?, ?>> subGraph : subGraphs) {
                List<BasicNode<?, ?>> allVertices = subGraph.getAllVertices();
                ChainNodeDirectedGraph<BasicNode<?, ?>> directedGraph = new ChainNodeDirectedGraph<>(allVertices, this.createEdges(allVertices));
                List<RuleSyntaxTree> trees = RuleSyntaxTree.createTrees(directedGraph);
                trees.forEach(tree -> System.out.println(tree.getRuleDsl()));
            }
            return null;
        });
    }

    @Test
    public void testWorkCore() {
        this.createAndExecute(graph -> {
            List<ChainNodeUndirectedGraph<BasicNode<?, ?>>> subGraphs = graph.connectedSubGraphByDfs();
            for (ChainNodeUndirectedGraph<BasicNode<?, ?>> subGraph : subGraphs) {
                List<BasicNode<?, ?>> allVertices = subGraph.getAllVertices();
                ChainNodeDirectedGraph<BasicNode<?, ?>> directedGraph = new ChainNodeDirectedGraph<>(allVertices, this.createEdges(allVertices));
                List<RuleSyntaxTree> trees = RuleSyntaxTree.createTrees(directedGraph);
                trees.forEach(tree -> {
                    ruleReloadService.reload(tree.getChainId(), tree.getRuleDsl());
                    chainExecuteService.executeOnce(tree.getChainId());
                });
            }
            return null;
        });
    }


    private <T> void createAndExecute(Function<ChainNodeUndirectedGraph<BasicNode<?, ?>>, Void> fuc) {
        int count = 0;
        JSONArray testData = JSONArray.parseArray(TEST_DATA);
        for (Object data : testData) {
            if (data instanceof JSONObject obj) {
                System.out.printf("------------------>测试块[%d]<------------------%n", (++count));
                JSONArray vertices = obj.getJSONArray("vertices");
                JSONArray edges = obj.getJSONArray("edges");

                List<BasicNode<?, ?>> basicNodeList = new ArrayList<>();
                for (String vertex : vertices.toList(String.class)) {
                    BasicNode<?, ?> basicNode = new BasicNode<>();
                    basicNode.setId(vertex);
                    basicNode.setTags(new ArrayList<>());
                    basicNode.setRules(new ArrayList<>());
                    basicNode.setProps(new ArrayList<>());
                    basicNode.setType("F1".equals(vertex) ? NodeTypeEnum.select : NodeTypeEnum.ordinary);
                    basicNode.setComponent(ComponentEnum.getByName(vertex));
                    basicNodeList.add(basicNode);
                }
                Map<String, BasicNode<?, ?>> nodeMap = basicNodeList.stream().collect(Collectors.toMap(BasicNode::getId, v -> v));
                List<Pair<BasicNode<?, ?>, BasicNode<?, ?>>> edgeList = new ArrayList<>();
                Map<String, List<List<String>>> wiresMap = new HashMap<>();
                for (Object edge : edges) {
                    if (edge instanceof JSONArray e) {
                        edgeList.add(new Pair<>(nodeMap.get(e.getString(0)), nodeMap.get(e.getString(1))));
                        BasicNode<?, ?> start = nodeMap.get(e.getString(0));
                        List<List<String>> wireList = Optional.ofNullable(wiresMap.get(start.getId())).orElse(new ArrayList<>());
                        wireList.add(Lists.newArrayList(e.getString(1)));
                        wiresMap.put(start.getId(), wireList);
                    }
                }
                //设置每个顶点的导线
                for (BasicNode<?, ?> basicNode : basicNodeList) {
                    List<List<String>> wires = Optional.ofNullable(wiresMap.get(basicNode.getId())).orElse(new ArrayList<>());
                    //String[][] wiresArray = wires.stream().map(subList -> subList.toArray(new String[0])).toArray(String[][]::new);
                    basicNode.setWires(wires);
                    String tag = StringUtil.randomString(5);
                    //设置节点的上下文数据
                    for (int i = 0; i < wires.size(); i++) {
                        if (NodeTypeEnum.isSelect(basicNode.getType().name())) {
                            basicNode.getTags().add(tag + "-" + i);
                            basicNode.getRules().add(null);
                        }
                        basicNode.getProps().add(null);
                    }
                }
                ChainNodeUndirectedGraph<BasicNode<?, ?>> adjTable = new ChainNodeUndirectedGraph<>(basicNodeList, edgeList);
                fuc.apply(adjTable);
            }
        }
    }

    private List<Pair<BasicNode<?, ?>, BasicNode<?, ?>>> createEdges(List<BasicNode<?, ?>> basicNodeList) {
        List<Pair<BasicNode<?, ?>, BasicNode<?, ?>>> edges = new ArrayList<>();
        Map<String, BasicNode<?, ?>> nodeMap = basicNodeList.stream().collect(Collectors.toMap(BasicNode::getId, v -> v));
        basicNodeList.forEach(node -> {
            List<List<String>> wires = node.getWires();
            if(wires != null) {
                List<String> wireList = wires.stream().flatMap(List::stream).toList();
                for (String wire : wireList) {
                    if (wire.equals(node.getId())) {
                        throw new RuntimeException("nodes cannot connect themselves！");
                    }
                    BasicNode<?, ?> subNode = nodeMap.get(wire);
                    if (subNode == null) {
                        throw new RuntimeException("unknown nodes id :" + wire);
                    }
                    edges.add(new Pair<>(node, subNode));
                }
            }
        });
        return edges;
    }
}
