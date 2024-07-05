package com.yunext.node;

import cn.hutool.core.lang.Pair;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.yunext.node.struct.graph.ChainNodeUndirectedGraph;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

/**
 * @author ：qianjb [qianjb@hadlinks.com]
 * @description ：
 * @date ：Created in 2024/7/2 17:44
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = SpringBootApplication.class)
@ActiveProfiles("local")
public class ChainGraphTest {

    private final String TEST_DATA = """
            [
               {
                 "vertices": ["a3", "b3", "c3", "d3", "e3", "f3", "g3", "h3"],
                 "edges": [["a3", "b3"], ["c3", "b3"], ["b3", "d3"], ["d3", "e3"], ["f3", "g3"]]
               },
               {
                 "vertices": ["w1", "w2", "w3", "w4"],
                 "edges": [["w1", "w2"], ["w2", "w3"], ["w3", "w4"], ["w2", "w4"]]
               },
               {
                 "vertices": ["x1", "x2", "x3", "x5"],
                 "edges": [["x1", "x2"], ["x2", "x3"], ["x1", "x5"], ["x3", "x5"]]
               },
               {
                 "vertices": ["z1", "z2", "z4", "z5", "z7"],
                 "edges": [["z1", "z2"], ["z2", "z4"], ["z4", "z5"], ["z5", "z7"], ["z2", "z7"]]
               },
               {
                 "vertices": ["y1", "y2", "y3", "y6"],
                 "edges": [["y1", "y2"], ["y2", "y3"], ["y6", "y1"], ["y6", "y3"]]
               }
            ]
            """;

    @Test
    public void testGraphCreate() {
        this.createAndExecute(graph -> {
            graph.print();
            return null;
        });
    }

    /**
     * 通过DFS获取连通子图
     */
    @Test
    public void testGetConnectedSubGraph() {
        this.createAndExecute(graph -> {
            List<ChainNodeUndirectedGraph<String>> subGraphByDFS = graph.connectedSubGraphByDfs();
            subGraphByDFS.forEach(ChainNodeUndirectedGraph::print);
            return null;
        });
    }

    private <T> void createAndExecute(Function<ChainNodeUndirectedGraph<String>, Void> fuc) {
        int count = 0;
        JSONArray testData = JSONArray.parseArray(TEST_DATA);
        for (Object data : testData) {
            if (data instanceof JSONObject obj) {
                System.out.printf("------------------>测试块[%d]<------------------%n", (++count));
                JSONArray vertices = obj.getJSONArray("vertices");
                JSONArray edges = obj.getJSONArray("edges");
                List<Pair<String, String>> edgeList = new ArrayList<>();
                for (Object edge : edges) {
                    if (edge instanceof JSONArray e) {
                        edgeList.add(new Pair<>(e.getString(0), e.getString(1)));
                    }
                }
                ChainNodeUndirectedGraph<String> adjTable = new ChainNodeUndirectedGraph<>(vertices.toList(String.class), edgeList);
                fuc.apply(adjTable);
            }
        }
    }
}
