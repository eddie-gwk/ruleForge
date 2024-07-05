package com.yunext.node.struct.tree;

import com.yunext.common.base.BasicNode;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ：qianjb [qianjb@hadlinks.com]
 * @description ：
 * @date ：Created in 2024/7/4 14:57
 */
public class RuleTreeNode {

    public RuleTreeNode(BasicNode basicNode) {
        this.value = basicNode;
        this.dslList = new ArrayList<>();
        this.children = new ArrayList<>();
    }

    private BasicNode value;

    private String command;

    private final List<String> dslList;

    private List<RuleTreeNode> children;

    public BasicNode getValue() {
        return value;
    }

    public void setValue(BasicNode value) {
        this.value = value;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public List<RuleTreeNode> getChildren() {
        return children;
    }

    public void setChildren(List<RuleTreeNode> children) {
        this.children = children;
    }

    public List<String> getDslList() {
        return dslList;
    }
}
