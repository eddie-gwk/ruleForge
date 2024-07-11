package com.yunext.core.components.common;

import com.yomahub.liteflow.annotation.LiteflowComponent;
import com.yunext.common.node.common.SwitchNode;
import com.yunext.core.context.SubContext;
import com.yunext.core.isolation.IsolationSwitchComponent;

/**
 * @author ：qianjb [qianjb@hadlinks.com]
 * @description ：
 * @date ：Created in 2024/7/10 14:59
 */
@LiteflowComponent("switch")
public class SwitchCmp extends IsolationSwitchComponent {

    @Override
    public String processSwitch(SubContext subContext) throws Exception {
        SwitchNode.SwitchProp prop = this.getProp(subContext.getRuleIndex(), SwitchNode.SwitchProp.class);
        SwitchNode.SwitchRule rule = this.getRule(subContext.getRuleIndex(), SwitchNode.SwitchRule.class);
        if (prop != null && rule != null && rule.isPass(subContext.getMsg().get(prop.getPropName()))) {
            return this.getTag(subContext.getRuleIndex());
        }
        return DEFAULT_TAG;
    }
}
