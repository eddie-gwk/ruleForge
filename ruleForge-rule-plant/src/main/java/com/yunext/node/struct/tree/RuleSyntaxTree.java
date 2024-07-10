package com.yunext.node.struct.tree;

import com.alibaba.fastjson2.JSONObject;
import com.yomahub.liteflow.builder.el.ELBus;
import com.yomahub.liteflow.builder.el.ELWrapper;
import com.yomahub.liteflow.builder.el.NodeELWrapper;
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
    private String chainId;

    /**
     * 根节点
     */
    private RuleTreeNode root;

    public RuleSyntaxTree() {
    }

    public RuleSyntaxTree(RuleTreeNode root) {
        this.root = root;
        this.chainId = StringUtil.isNotEmpty(root.getValue().getChainId()) ?
                root.getValue().getChainId() : StringUtil.randomString(10);
    }

    /**
     * 构建语法树List
     * @param graph
     */
    public static List<RuleSyntaxTree> createTrees(ChainNodeDirectedGraph<BasicNode<?, ?>> graph) {
        List<RuleSyntaxTree> treeList = new ArrayList<>();
        if (graph == null) {
            return treeList;
        }
        graph.calcRoot();
        Map<BasicNode<?, ?>, Boolean> visited = graph.createVisited();
        Map<String, RuleTreeNode> treeNodeMap = new HashMap<>();
        //从图的多个起点出发构建语法树，最终都作为虚拟根节点的子节点
        graph.getRoots().forEach(graphRoot -> {
            RuleTreeNode root = bfs(graphRoot, visited, treeNodeMap, graph);
            treeList.add(new RuleSyntaxTree(root));
        });
        return treeList;
    }

    private static RuleTreeNode bfs(BasicNode<?, ?> start, Map<BasicNode<?, ?>, Boolean> visited, Map<String, RuleTreeNode> treeNodeMap, ChainNodeDirectedGraph<BasicNode<?, ?>> graph) {
        Deque<BasicNode<?, ?>> queue = new ArrayDeque<>();

        //标记已访问
        visited.put(start, true);
        queue.addLast(start);

        while (!queue.isEmpty()) {
            BasicNode<?, ?> current = queue.poll();
            RuleTreeNode currentTreeNode = treeNodeMap.containsKey(current.getId()) ?
                    treeNodeMap.get(current.getId()) : new RuleTreeNode(current);

            List<RuleTreeNode> children = new ArrayList<>();
            for (BasicNode<?, ?> adjVex : graph.getAdjVertices(current)) {
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
        ruleSyntaxTreeDto.setChain(this.getChainId());
        ruleSyntaxTreeDto.setRuleDsl(this.getRuleDsl());
        ruleSyntaxTreeDto.setRoot(this.getRoot());
        return ruleSyntaxTreeDto;
    }


    /**
     * 语法树转DSL
     * @return
     */
    public String getRuleDsl() {
        this.deepRecursion(root);
        return root.getCommand().toEL();
    }


    private void deepRecursion(RuleTreeNode root) {
        BasicNode<?, ?> node = root.getValue();
        ComponentEnum component = node.getComponent();
        if (component == null) {
            throw new RuntimeException("unknown component");
        }
        NodeTypeEnum type = node.getType();

        List<RuleTreeNode> children = root.getChildren();
        if (CollectionUtils.isEmpty(children)) {
            //叶子节点的命令就是组件名称,节点自身还需要带一个上下文
            root.setCommand(ELBus.node(component.name())
                    .data(node.getComponent() + StringUtil.randomString(5), this.nodeData(node)));
            return ;
        }

        for (RuleTreeNode child : children) {
            deepRecursion(child);
        }

        String nodeDataName = node.getComponent() + StringUtil.randomString(5);
        switch (NodeTypeEnum.getByName(type.name())) {
            case select -> {
                //选择节点，要根据输出口和导线组进行tag语法的设置
                //TODO 没有做严格的校验，目前只是实现语法解析功能
                List<List<String>> wires = node.getWires();
                Map<String, RuleTreeNode> treeNodeMap = children.stream().collect(Collectors.toMap(k -> k.getValue().getId(), v -> v));
                List<ELWrapper> subRules = new ArrayList<>();
                for (int i = 0; i < wires.size(); i++) {
                    //一维数组就是对输出口(子分支)的分组
                    List<RuleTreeNode> subNodes = new ArrayList<>();
                    for (String w : wires.get(i)) {
                        subNodes.add(treeNodeMap.get(w));
                    }
                    //为每个输出口(子分支)创建tag, 如果某个输出口的子节点大于1，进行并行编排(when)
                    Object[] array = subNodes.stream().map(RuleTreeNode::getCommand).toArray();
                    //这里默认约定传的输出组应和tags一一对应，否则将导致执行错误
                    String tag = node.getContextData().getTags().get(i);
                    subRules.add(subNodes.size() > 1 ? ELBus.when(array).tag(tag) : subNodes.get(0).getCommand().tag(tag));
                    //加一个默认的分支，如果规则没匹配成功就走这个节点
                    subRules.add(ELBus.node(ComponentEnum.none.name()).tag("none"));
                }
                root.setCommand(
                        ELBus.switchOpt(
                        ELBus.node(component.name())
                                .data(nodeDataName, this.nodeData(node)))
                        .to(subRules.toArray())
                );
            }
            case input, output, ordinary, iterate, loop, virtual  -> {
                //子节点数 > 1，要使用并行编排(when)，否则使用串行编排(then)
                NodeELWrapper currNode = ELBus.node(component.name()).data(nodeDataName, this.nodeData(node));
                ELWrapper elWrapper = children.size() > 1 ? ELBus.then(currNode, ELBus.when(children.stream().map(RuleTreeNode::getCommand).toArray()))
                        : ELBus.then(currNode, children.get(0).getCommand());
                root.setCommand(elWrapper);
            }
            case unknown -> throw new RuntimeException("unknown node");
        }
    }

    private String nodeData(BasicNode<?, ?> node) {
        return JSONObject.toJSONString(node.getContextData());
    }

    public String getChainId() {
        return chainId;
    }

    public BasicNode<?, ?> getRoot() {
        return this.root.getValue();
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
