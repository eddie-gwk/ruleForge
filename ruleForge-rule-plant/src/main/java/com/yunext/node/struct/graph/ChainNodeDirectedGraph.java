package com.yunext.node.struct.graph;


import cn.hutool.core.lang.Pair;
import com.yunext.common.base.BasicNode;

import java.util.*;

/**
 * @author ：qianjb [qianjb@hadlinks.com]
 * @description ：邻接表实现的有向图
 * @date ：Created in 2024/7/4 10:00
 */
public class ChainNodeDirectedGraph<N> extends AbstractChainNodeGraph <N>{

    private List<N> roots;

    public ChainNodeDirectedGraph() {
        super();
        roots = new ArrayList<>();
    }

    public ChainNodeDirectedGraph(List<N> vertices, List<Pair<N, N>> edges) {
        super(vertices, edges);
        roots = new ArrayList<>();
    }

    public List<N> getRoots() {
        return roots;
    }

    /**
     * 有向图
     * 新增 vertex1->vertex2 的边
     * @param vertex1
     * @param vertex2
     */
    @Override
    public void addEdge(N vertex1, N vertex2) {
        if (!adjList.containsKey(vertex1) || !adjList.containsKey(vertex2) || Objects.equals(vertex1, vertex2)) {
            throw new IllegalArgumentException("[Add Edge Failed] Vertices must exist and cannot be equal!");
        }
        adjList.get(vertex1).add(vertex2);
    }

    @Override
    public void removeEdge(N vertex1, N vertex2) {
        if (!adjList.containsKey(vertex1) || !adjList.containsKey(vertex2) || Objects.equals(vertex1, vertex2)) {
            throw new IllegalArgumentException("[Remove Edge Failed] Vertices must exist and cannot be equal!");
        }
        adjList.get(vertex1).remove(vertex2);
    }

    /**
     * 计算根节点
     * 不被其他节点指向的点就是根节点
     */
    public void calcRoot() {
        this.roots.clear();
        List<N> allVertices = this.getAllVertices();
        List<N> tailVertices = this.getsAllTailVertices();
        for (N vertex : allVertices) {
            if (!tailVertices.contains(vertex)) {
                this.roots.add(vertex);
            }
        }
    }
}
