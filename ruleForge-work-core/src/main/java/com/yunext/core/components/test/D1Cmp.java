package com.yunext.core.components.test;

import com.yomahub.liteflow.annotation.LiteflowComponent;
import com.yomahub.liteflow.core.NodeComponent;
import com.yunext.core.context.SubContext;
import com.yunext.core.isolation.IsolationNodeComponent;

/**
 * @author ：qianjb [qianjb@hadlinks.com]
 * @description ：
 * @date ：Created in 2024/7/5 13:44
 */
@LiteflowComponent("D1")
public class D1Cmp extends IsolationNodeComponent {

    @Override
    public void process(SubContext subContext) throws Exception {
        System.out.println("执行D, Thread: " + Thread.currentThread().getName());
    }
}
