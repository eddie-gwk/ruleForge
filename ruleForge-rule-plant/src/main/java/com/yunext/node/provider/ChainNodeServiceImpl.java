package com.yunext.node.provider;

import cn.hutool.core.lang.Pair;
import com.alibaba.fastjson2.JSONObject;
import com.yunext.api.dto.RuleSyntaxTreeDto;
import com.yunext.api.service.plant.ChainNodeService;
import com.yunext.common.base.BasicNode;
import com.yunext.api.dto.ResultDto;
import com.yunext.node.struct.graph.ChainNodeDirectedGraph;
import com.yunext.node.struct.graph.ChainNodeUndirectedGraph;
import com.yunext.node.struct.tree.RuleSyntaxTree;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.dubbo.config.annotation.DubboService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author ：qianjb [qianjb@hadlinks.com]
 * @description ：
 * @date ：Created in 2024/7/3 17:51
 */
@DubboService
public class ChainNodeServiceImpl implements ChainNodeService {

    @Override
    public ResultDto<List<RuleSyntaxTreeDto>> createChain(List<BasicNode<?, ?>> basicNodeList) {
        if (CollectionUtils.isEmpty(basicNodeList)) {
            return ResultDto.success(new ArrayList<>());
        }
        ChainNodeUndirectedGraph<BasicNode<?, ?>> graph = new ChainNodeUndirectedGraph<>(basicNodeList, this.createEdges(basicNodeList));
        //1.获取所有连通子图
        List<ChainNodeUndirectedGraph<BasicNode<?, ?>>> connectedSubGraph = graph.connectedSubGraphByDfs();
        //2.根据顶点数据生成有向图
        List<ChainNodeDirectedGraph<BasicNode<?, ?>>> directedGraphList = new ArrayList<>();
        connectedSubGraph.forEach(subGraph -> {
            List<BasicNode<?, ?>> allVertices = subGraph.getAllVertices();
            ChainNodeDirectedGraph<BasicNode<?, ?>> directedGraph = new ChainNodeDirectedGraph<>(allVertices, this.createEdges(allVertices));
            directedGraphList.add(directedGraph);
        });
        //3.为有向图生成语法树，根据语法树得到规则链
        List<RuleSyntaxTreeDto> ruleSyntaxTreeDtoList = new ArrayList<>();
        directedGraphList.forEach(directedGraph -> {
            List<RuleSyntaxTree> syntaxTreeList = RuleSyntaxTree.createTrees(directedGraph);
            syntaxTreeList.forEach(syntaxTree -> ruleSyntaxTreeDtoList.add(syntaxTree.toDto()));
        });
        return ResultDto.success(ruleSyntaxTreeDtoList);
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
