package com.yunext.node.struct.graph;

import cn.hutool.core.lang.Pair;

import java.util.*;

/**
 * @author ：qianjb [qianjb@hadlinks.com]
 * @description ：邻接表实现的无向图
 * @date ：Created in 2024/7/3 15:23
 */
public class ChainNodeUndirectedGraph<N> extends AbstractChainNodeGraph<N> {

    public ChainNodeUndirectedGraph() {
        super();
    }

    public ChainNodeUndirectedGraph(List<N> vertices, List<Pair<N, N>> edges) {
        super(vertices, edges);
    }

    /**
     * 新增边
     * 无向图，两个顶点的链表都需要加入一条边
     * @param vertex1
     * @param vertex2
     */
    @Override
    public void addEdge(N vertex1, N vertex2) {
        if (!adjList.containsKey(vertex1) || !adjList.containsKey(vertex2) || Objects.equals(vertex1, vertex2)) {
            throw new IllegalArgumentException("[Add Edge Failed] Vertices must exist and cannot be equal!");
        }
        adjList.get(vertex1).add(vertex2);
        adjList.get(vertex2).add(vertex1);
    }

    @Override
    public void removeEdge(N vertex1, N vertex2) {
        if (!adjList.containsKey(vertex1) || !adjList.containsKey(vertex2) || Objects.equals(vertex1, vertex2)) {
            throw new IllegalArgumentException("[Remove Edge Failed] Vertices must exist and cannot be equal!");
        }
        adjList.get(vertex1).remove(vertex2);
        adjList.get(vertex2).remove(vertex1);
    }


    /**
     * 获取图的所有连通分量
     *
     * @notice 会忽略多余的边，作用是知道哪些顶点是相连的
     * @return
     */
    public List<ChainNodeUndirectedGraph<N>> connectedSubGraphByDfs() {
        Map<N, Boolean> visited = this.createVisited();
        List<ChainNodeUndirectedGraph<N>> connectedGraph = new ArrayList<>();
        for (N start : this.adjList.keySet()) {
            if(!visited.get(start)) {
                ChainNodeUndirectedGraph<N> subGraph = new ChainNodeUndirectedGraph<>();
                this.dfs(start, visited, subGraph);
                connectedGraph.add(subGraph);
            }
        }
        return connectedGraph;
    }

    /**
     * 深度优先遍历
     * @param start 起点
     * @param visited 访问标记
     * @param subGraph 连通图
     */
    public void dfs(N start, Map<N, Boolean> visited, ChainNodeUndirectedGraph<N> subGraph) {
        visited.put(start, true);
        subGraph.addVertex(start);

        for (N adjVertex : this.getAdjVertices(start)) {
            if (!Optional.ofNullable(visited.get(adjVertex)).orElse(false)) {
                subGraph.addVertex(adjVertex);
                subGraph.addEdge(start, adjVertex);
                this.dfs(adjVertex, visited, subGraph);
            }
        }
    }

    /**
     * 获取目标顶点的所有邻接顶点
     * @param vertex 顶点
     * @return
     */
    public List<N> getAdjVertices(N vertex) {
        return Optional.ofNullable(this.adjList.get(vertex)).orElse(new LinkedList<>());
    }

}
