package com.yunext.core.components.test;

import com.yomahub.liteflow.annotation.LiteflowComponent;
import com.yomahub.liteflow.core.NodeSwitchComponent;

/**
 * @author ：qianjb [qianjb@hadlinks.com]
 * @description ：
 * @date ：Created in 2024/7/5 13:44
 */
@LiteflowComponent("F1")
public class F1Cmp extends NodeSwitchComponent {

    @Override
    public String processSwitch() throws Exception {
        System.out.println("EXECUTE F1");
        return null;
    }
}
