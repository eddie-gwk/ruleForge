package com.yunext.core.components.common;

import com.yomahub.liteflow.annotation.LiteflowComponent;
import com.yunext.common.node.common.ChangeNode;
import com.yunext.core.context.SubContext;
import com.yunext.core.isolation.IsolationNodeComponent;

import java.util.List;

/**
 * @author ：qianjb [qianjb@hadlinks.com]
 * @description ：
 * @date ：Created in 2024/7/10 13:18
 */
@LiteflowComponent("change")
public class ChangeCmp extends IsolationNodeComponent {

    @Override
    public void process(SubContext subContext) throws Exception {
        List<ChangeNode.ChangeRule> ruleList = this.getRuleList(ChangeNode.ChangeRule.class);
        ruleList.forEach(rule -> rule.change(subContext.getMsg()));
    }
}
