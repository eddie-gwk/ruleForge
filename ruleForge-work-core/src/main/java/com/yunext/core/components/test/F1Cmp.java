package com.yunext.core.components.test;

import com.yomahub.liteflow.annotation.LiteflowComponent;
import com.yomahub.liteflow.core.NodeSwitchComponent;
import com.yunext.core.context.ComponentContext;
import com.yunext.core.context.SubContext;
import com.yunext.core.isolation.IsolationSwitchComponent;

/**
 * @author ：qianjb [qianjb@hadlinks.com]
 * @description ：
 * @date ：Created in 2024/7/5 13:44
 */
@LiteflowComponent("F1")
public class F1Cmp extends IsolationSwitchComponent {

    @Override
    public String processSwitch(SubContext subContext) throws Exception {
        ComponentContext cmpData = this.getCmpData(ComponentContext.class);
        System.out.println("EXECUTE F1");
        return cmpData.getTags().get(1);
    }
}
