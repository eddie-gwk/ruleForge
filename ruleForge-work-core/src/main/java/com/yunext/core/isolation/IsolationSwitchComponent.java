package com.yunext.core.isolation;

import com.yomahub.liteflow.core.NodeSwitchComponent;
import com.yunext.core.context.SubContext;

/**
 * 隔离上下文的选择组件
 * @author ：qianjb [qianjb@hadlinks.com]
 * @description ：
 * @date ：Created in 2024/7/9 14:13
 */
public abstract class IsolationSwitchComponent extends IsolationComponent {

    public IsolationSwitchComponent() {super();}

    public abstract String processSwitch(SubContext subContext) throws Exception;

    @Override
    public final void process() throws Exception {
        this.getLocalSubContext().forEach(context -> {
            try {
                String nodeId = this.processSwitch(context);
                this.getSlot().setSwitchResult(this.getMetaValueKey(), nodeId);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }
}
