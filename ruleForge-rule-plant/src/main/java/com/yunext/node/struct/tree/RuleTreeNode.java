package com.yunext.node.struct.tree;

import com.yomahub.liteflow.builder.el.ELWrapper;
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
        this.children = new ArrayList<>();
    }

    private BasicNode value;

    private ELWrapper command;

    private List<RuleTreeNode> children;

    public BasicNode getValue() {
        return value;
    }

    public void setValue(BasicNode value) {
        this.value = value;
    }

    public ELWrapper getCommand() {
        return command;
    }

    public void setCommand(ELWrapper command) {
        this.command = command;
    }

    public List<RuleTreeNode> getChildren() {
        return children;
    }

    public void setChildren(List<RuleTreeNode> children) {
        this.children = children;
    }
}
