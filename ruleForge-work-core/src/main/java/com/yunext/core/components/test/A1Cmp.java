package com.yunext.core.components.test;

import com.yomahub.liteflow.annotation.LiteflowComponent;
import com.yomahub.liteflow.core.NodeComponent;
import com.yunext.core.isolation.IsolationNodeComponent;

/**
 * @author ：qianjb [qianjb@hadlinks.com]
 * @description ：
 * @date ：Created in 2024/7/5 13:44
 */
@LiteflowComponent("A1")
public class A1Cmp extends IsolationNodeComponent {
    @Override
    public void process() throws Exception {
        System.out.println("执行A, Thread: " + Thread.currentThread().getName());
    }
}
