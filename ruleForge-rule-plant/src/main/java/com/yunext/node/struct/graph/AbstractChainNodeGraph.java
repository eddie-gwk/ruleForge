package com.yunext.node.struct.graph;

import cn.hutool.core.lang.Pair;
import org.checkerframework.checker.units.qual.N;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author ：qianjb [qianjb@hadlinks.com]
 * @description ：
 * @date ：Created in 2024/7/4 9:33
 */
public abstract class AbstractChainNodeGraph<N> implements ChainNodeGraph<N> {

    /**
     * 邻接表存储顶点和边
     * [a1] -> [b1, c1]
     * [b1] -> [c1]
     * [c1] -> [d1]
     * [d1] -> []
     */
    protected final Map<N, List<N>> adjList;

    public AbstractChainNodeGraph() {
        this.adjList = new HashMap<>();
    }

    public AbstractChainNodeGraph(List<N> vertices, List<Pair<N, N>> edges) {
        adjList = new HashMap<>();
        if (vertices == null || vertices.isEmpty()) {
            return;
        }
        vertices.forEach(vertex -> adjList.put(vertex, new LinkedList<>()));
        if (edges == null || edges.isEmpty()) {
            return;
        }
        edges.forEach(pair -> addEdge(pair.getKey(), pair.getValue()));
    }

    @Override
    public abstract void addEdge(N vertex1, N vertex2);

    @Override
    public abstract void removeEdge(N vertex1, N vertex2);

    @Override
    public void addVertex(N vertex) {
        if(adjList.containsKey(vertex)) {
            return;
        }
        adjList.put(vertex, new LinkedList<>());
    }

    /**
     * 删除顶点
     * 同时需要删除其他顶点中相连的边
     * @param vertex 顶点
     */
    @Override
    public void removeVertex(N vertex) {
        if(!adjList.containsKey(vertex)) {
            return;
        }
        adjList.remove(vertex);
        for (List<N> list : adjList.values()) {
            list.remove(vertex);
        }
    }

    /* 打印邻接表 */
    public void print() {
        System.out.println("adjList =");
        for (Map.Entry<N, List<N>> pair : adjList.entrySet()) {
            List<N> tmp = new ArrayList<>(pair.getValue());
            System.out.println(pair.getKey() + ": " + tmp);
        }
    }

    /**
     * 访问节点
     * @return
     */
    public Map<N, Boolean> createVisited() {
        Map<N, Boolean> visited = new HashMap<>(this.adjList.size());
        this.adjList.keySet().forEach(key -> visited.put(key, false));
        return visited;
    }

    /**
     * 获取所有顶点
     * @return
     */
    public List<N> getAllVertices() {
        return new ArrayList<>(this.adjList.keySet());
    }


    public List<N> getsAllTailVertices() {
        return this.adjList.values().stream()
                .flatMap(List::stream)
                .distinct()
                .toList();
    }

    public List<N> getAdjVertices(N current) {
        return this.adjList.get(current);
    }
}
