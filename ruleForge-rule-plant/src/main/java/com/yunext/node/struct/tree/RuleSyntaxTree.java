package com.yunext.node.struct.tree;

import com.yunext.api.dto.RuleSyntaxTreeDto;
import com.yunext.common.base.BasicNode;
import com.yunext.common.enums.ComponentEnum;
import com.yunext.common.enums.NodeTypeEnum;
import com.yunext.common.utils.StringUtil;
import com.yunext.node.struct.graph.ChainNodeDirectedGraph;
import org.apache.commons.collections4.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author ：qianjb [qianjb@hadlinks.com]
 * @description ：语法树
 * 根节点是一个无意义节点，其下的子节点是真正的根节点
 * @date ：Created in 2024/7/4 13:23
 */
public class RuleSyntaxTree {

    /**
     * 规则链名称
     * 一个语法树对象就是一个规则链
     */
    private String chainName;

    /**
     * 根节点
     */
    private RuleTreeNode root;

    public RuleSyntaxTree() {
    }

    public RuleSyntaxTree(RuleTreeNode root) {
        this.root = root;
    }

    /**
     * 构建语法树List
     * @param graph
     */
    public static List<RuleSyntaxTree> createTrees(ChainNodeDirectedGraph<BasicNode> graph) {
        List<RuleSyntaxTree> treeList = new ArrayList<>();
        if (graph == null) {
            return treeList;
        }
        graph.calcRoot();
        Map<BasicNode, Boolean> visited = graph.createVisited();
        Map<String, RuleTreeNode> treeNodeMap = new HashMap<>();
        //从图的多个起点出发构建语法树，最终都作为虚拟根节点的子节点
        graph.getRoots().forEach(graphRoot -> {
            RuleTreeNode root = bfs(graphRoot, visited, treeNodeMap, graph);
            treeList.add(new RuleSyntaxTree(root));
        });
        return treeList;
    }

    /**
     * 虚拟节点作为根节点
     * @return
     */
    private BasicNode virtualNode() {
        BasicNode basicNode = new BasicNode();
        basicNode.setId(StringUtil.uuid());
        basicNode.setType(NodeTypeEnum.virtual);
        basicNode.setComponent(ComponentEnum.ROOT);
        return basicNode;
    }

    private static RuleTreeNode bfs(BasicNode start, Map<BasicNode, Boolean> visited, Map<String, RuleTreeNode> treeNodeMap, ChainNodeDirectedGraph<BasicNode> graph) {
        Deque<BasicNode> queue = new ArrayDeque<>();

        //标记已访问
        visited.put(start, true);
        queue.addLast(start);

        while (!queue.isEmpty()) {
            BasicNode current = queue.poll();
            RuleTreeNode currentTreeNode = treeNodeMap.containsKey(current.getId()) ?
                    treeNodeMap.get(current.getId()) : new RuleTreeNode(current);

            List<RuleTreeNode> children = new ArrayList<>();
            for (BasicNode adjVex : graph.getAdjVertices(current)) {
                if (!Optional.ofNullable(visited.get(adjVex)).orElse(false)) {
                    //没有访问过要取出来标记并且加入到树节点中
                    visited.put(adjVex, true);
                    queue.add(adjVex);
                    RuleTreeNode child = new RuleTreeNode(adjVex);
                    treeNodeMap.put(adjVex.getId(), child);
                    children.add(child);
                } else {
                    //访问过的直接拿出来当做当前节点子节点
                    children.add(treeNodeMap.get(adjVex.getId()));
                }
            }
            currentTreeNode.setChildren(children);
            treeNodeMap.put(current.getId(), currentTreeNode);
        }

        return treeNodeMap.get(start.getId());
    }

    public RuleSyntaxTreeDto toDto() {
        RuleSyntaxTreeDto ruleSyntaxTreeDto = new RuleSyntaxTreeDto();
        ruleSyntaxTreeDto.setChain(this.chainName);
        ruleSyntaxTreeDto.setRuleDsl(this.getRuleDsl());
        return ruleSyntaxTreeDto;
    }


    /**
     * 语法树转DSL
     * @return
     */
    public String getRuleDsl() {
        this.deepRecursion(root);
        return root.getCommand() + ";";
    }


    private void deepRecursion(RuleTreeNode root) {
        BasicNode node = root.getValue();
        ComponentEnum component = node.getComponent();
        if (component == null) {
            throw new RuntimeException("unknown component");
        }
        NodeTypeEnum type = node.getType();

        List<RuleTreeNode> children = root.getChildren();
        if (CollectionUtils.isEmpty(children)) {
            //叶子节点的命令就是组件名称
            root.setCommand(component.name());
            return ;
        }

        for (RuleTreeNode child : children) {
            deepRecursion(child);
        }

        if (NodeTypeEnum.isSelect(type.name())) {
            //选择分支，要根据输出口和导线组进行tag语法的设置
            //TODO 没有做严格的校验，目前只是实现语法解析功能
            //TODO 用ELBUS
            String[][] wires = node.getWires();
            Map<String, RuleTreeNode> treeNodeMap = children.stream().collect(Collectors.toMap(k -> k.getValue().getId(), v -> v));
            List<String> subRules = new ArrayList<>();
            for (int i = 0; i < wires.length; i++) {
                //一维数组就是对输出接口的分组，下面的组件都是并行编排
                List<RuleTreeNode> subNodes = new ArrayList<>();
                for (String w : wires[i]) {
                    subNodes.add(treeNodeMap.get(w));
                }
                String subRule = String.format((subNodes.size() > 1 ? "WHEN(%s).tag(%s)" : "%s.tag(%s)"), subNodes.stream().map(RuleTreeNode::getCommand).collect(Collectors.joining(",")),
                        StringUtil.randomString(5) + "-" + i);
                subRules.add(subRule);
            }
            root.setCommand(String.format("SWITCH(%s).to(%s)", component, String.join(",", subRules)));
        } else {
            if (children.size() > 1) {
                root.setCommand(String.format("THEN(%s, WHEN(%s))", component,
                        children.stream().map(RuleTreeNode::getCommand).collect(Collectors.joining(","))));
            } else {
                root.setCommand(String.format("THEN(%s, %s)", component, children.get(0).getCommand()));
            }
        }


    }

    public String getChainName() {
        return chainName;
    }

    /**
     * 层序遍历
     */
    public void print() {
        Deque<RuleTreeNode> queue = new ArrayDeque<>();
        queue.addLast(root);
        Set<String> visited = new HashSet<>();

        while (!queue.isEmpty()) {
            RuleTreeNode current = queue.poll();
            System.out.println(current.getValue());

            for (RuleTreeNode child : current.getChildren()) {
                if (visited.add(child.getValue().getId())) {
                    queue.addLast(child);
                }
            }
        }
    }

}
