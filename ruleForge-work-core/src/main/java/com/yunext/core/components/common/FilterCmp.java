package com.yunext.core.components.common;

import com.yomahub.liteflow.annotation.LiteflowComponent;
import com.yunext.common.node.common.FilterNode;
import com.yunext.common.node.common.SwitchNode;
import com.yunext.core.context.SubContext;
import com.yunext.core.isolation.IsolationSwitchComponent;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author ：qianjb [qianjb@hadlinks.com]
 * @description ：
 * @date ：Created in 2024/7/10 16:14
 */
@LiteflowComponent("filter")
public class FilterCmp extends IsolationSwitchComponent {

    public final Map<String, Map<Object, Object>> lastMsg = new ConcurrentHashMap<>();

    @Override
    public String processSwitch(SubContext subContext) throws Exception {
        FilterNode.FilterProp prop = this.getProp(subContext.getRuleIndex(), FilterNode.FilterProp.class);
        FilterNode.FilterRule rule = this.getRule(subContext.getRuleIndex(), FilterNode.FilterRule.class);
        boolean noBlock = prop != null && rule != null && rule.isPass(prop.getPropName(), lastMsg.get(prop.getNodeId().orElse("default")), subContext.getMsg());

        //保存最后一次的处理数据
        if (prop != null) {
            lastMsg.put(prop.getNodeId().orElse("default"), subContext.getMsg());
        }
        return noBlock ? this.getTag(subContext.getRuleIndex()) : DEFAULT_TAG;
    }
}
