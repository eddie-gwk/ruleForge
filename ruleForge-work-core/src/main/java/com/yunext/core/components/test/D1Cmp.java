package com.yunext.core.components.test;

import com.yomahub.liteflow.annotation.LiteflowComponent;
import com.yomahub.liteflow.core.NodeComponent;

/**
 * @author ：qianjb [qianjb@hadlinks.com]
 * @description ：
 * @date ：Created in 2024/7/5 13:44
 */
@LiteflowComponent("D1")
public class D1Cmp extends NodeComponent {

    @Override
    public void process() throws Exception {
        System.out.println("EXECUTE D1");
    }
}
