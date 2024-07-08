package com.yunext.core.components.test;

import com.yomahub.liteflow.annotation.LiteflowComponent;
import com.yomahub.liteflow.core.NodeComponent;
import com.yunext.core.isolation.IsolationNodeComponent;

/**
 * @author ：qianjb [qianjb@hadlinks.com]
 * @description ：
 * @date ：Created in 2024/7/5 13:44
 */
@LiteflowComponent("C1")
public class C1Cmp extends IsolationNodeComponent {

    @Override
    public void process() throws Exception {
        System.out.println("执行C, Thread: " + Thread.currentThread().getName());
    }
}
