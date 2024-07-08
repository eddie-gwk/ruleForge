package com.yunext.core.components.test;

import com.yomahub.liteflow.annotation.LiteflowComponent;
import com.yomahub.liteflow.core.NodeComponent;
import com.yunext.core.isolation.IsolationNodeComponent;

/**
 * @author ：qianjb [qianjb@hadlinks.com]
 * @description ：
 * @date ：Created in 2024/7/5 13:44
 */
@LiteflowComponent("E1")
public class E1Cmp extends IsolationNodeComponent {

    @Override
    public void process() throws Exception {
        System.out.println("执行E, Thread: " + Thread.currentThread().getName());
    }
}
