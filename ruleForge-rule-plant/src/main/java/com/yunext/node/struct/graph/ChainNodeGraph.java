package com.yunext.node.struct.graph;

/**
 * @author ：qianjb [qianjb@hadlinks.com]
 * @description ：
 * @date ：Created in 2024/7/4 9:32
 */
public interface ChainNodeGraph<N> {

    public void addEdge(N vertex1, N vertex2);

    public void removeEdge(N vertex1, N vertex2);

    public void addVertex(N vertex);

    public void removeVertex(N vertex);

}
