package com.yunext.node;

import cn.hutool.core.lang.Pair;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.yunext.api.service.plant.ChainNodeService;
import com.yunext.common.base.BasicNode;
import com.yunext.common.enums.ComponentEnum;
import com.yunext.common.enums.NodeTypeEnum;
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

import javax.annotation.Resource;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author ：qianjb [qianjb@hadlinks.com]
 * @description ：语法树测试
 * @date ：Created in 2024/7/4 15:07
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = SpringBootApplication.class)
@ActiveProfiles("local")
public class SyntaxTreeTest {

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
            List<ChainNodeUndirectedGraph<BasicNode>> subGraphs = graph.connectedSubGraphByDfs();
            for (ChainNodeUndirectedGraph<BasicNode> subGraph : subGraphs) {
                List<BasicNode> allVertices = subGraph.getAllVertices();
                ChainNodeDirectedGraph<BasicNode> directedGraph = new ChainNodeDirectedGraph<>(allVertices, this.createEdges(allVertices));
                RuleSyntaxTree tree = new RuleSyntaxTree(directedGraph);
                System.out.println(tree.getRule());
            }
            return null;
        });
    }


    private <T> void createAndExecute(Function<ChainNodeUndirectedGraph<BasicNode>, Void> fuc) {
        int count = 0;
        JSONArray testData = JSONArray.parseArray(TEST_DATA);
        for (Object data : testData) {
            if (data instanceof JSONObject obj) {
                System.out.printf("------------------>测试块[%d]<------------------%n", (++count));
                JSONArray vertices = obj.getJSONArray("vertices");
                JSONArray edges = obj.getJSONArray("edges");

                List<BasicNode> basicNodeList = new ArrayList<>();
                for (String vertex : vertices.toList(String.class)) {
                    BasicNode basicNode = new BasicNode();
                    basicNode.setId(vertex);
                    basicNode.setType("F1".equals(vertex) ? NodeTypeEnum.select : NodeTypeEnum.ordinary);
                    basicNode.setComponent(ComponentEnum.getByName(vertex));
                    basicNodeList.add(basicNode);
                }
                Map<String, BasicNode> nodeMap = basicNodeList.stream().collect(Collectors.toMap(BasicNode::getId, v -> v));
                List<Pair<BasicNode, BasicNode>> edgeList = new ArrayList<>();
                Map<String, List<List<String>>> wiresMap = new HashMap<>();
                for (Object edge : edges) {
                    if (edge instanceof JSONArray e) {
                        edgeList.add(new Pair<>(nodeMap.get(e.getString(0)), nodeMap.get(e.getString(1))));
                        BasicNode start = nodeMap.get(e.getString(0));
                        List<List<String>> wireList = Optional.ofNullable(wiresMap.get(start.getId())).orElse(new ArrayList<>());
                        //默认一条边一个单独的输出，实际情况可能多条边对应一个输出
                        wireList.add(Lists.newArrayList(e.getString(1)));
                        wiresMap.put(start.getId(), wireList);
                    }
                }
                //设置每个顶点的导线
                for (BasicNode basicNode : basicNodeList) {
                    List<List<String>> wires = Optional.ofNullable(wiresMap.get(basicNode.getId())).orElse(new ArrayList<>());
                    String[][] wiresArray = wires.stream().map(subList -> subList.toArray(new String[subList.size()])).toArray(String[][]::new);
                    basicNode.setWires(wiresArray);
                }
                ChainNodeUndirectedGraph<BasicNode> adjTable = new ChainNodeUndirectedGraph<>(basicNodeList, edgeList);
                fuc.apply(adjTable);
            }
        }
    }

    private List<Pair<BasicNode, BasicNode>> createEdges(List<BasicNode> basicNodeList) {
        List<Pair<BasicNode, BasicNode>> edges = new ArrayList<>();
        Map<String, BasicNode> nodeMap = basicNodeList.stream().collect(Collectors.toMap(BasicNode::getId, v -> v));
        basicNodeList.forEach(node -> {
            String[][] wires = node.getWires();
            if(wires != null) {
                List<String> wireList = Arrays.stream(wires).flatMap(Arrays::stream).toList();
                for (String wire : wireList) {
                    if (wire.equals(node.getId())) {
                        throw new RuntimeException("nodes cannot connect themselves！");
                    }
                    BasicNode subNode = nodeMap.get(wire);
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
