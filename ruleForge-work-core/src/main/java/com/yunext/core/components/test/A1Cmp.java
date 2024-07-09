package com.yunext.core.components.test;

import com.alibaba.fastjson2.JSONObject;
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
        Integer cmpData = this.getCmpData(Integer.class);
        System.out.println(cmpData);
        System.out.println("执行A, Thread: " + Thread.currentThread().getName());
    }
}
