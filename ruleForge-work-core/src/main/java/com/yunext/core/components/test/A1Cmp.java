package com.yunext.core.components.test;

import com.alibaba.fastjson2.JSONObject;
import com.yomahub.liteflow.annotation.LiteflowComponent;
import com.yomahub.liteflow.core.NodeComponent;
import com.yunext.common.utils.StringUtil;
import com.yunext.core.context.ComponentContext;
import com.yunext.core.context.SubContext;
import com.yunext.core.isolation.IsolationNodeComponent;

/**
 * @author ：qianjb [qianjb@hadlinks.com]
 * @description ：
 * @date ：Created in 2024/7/5 13:44
 */
@LiteflowComponent("A1")
public class A1Cmp extends IsolationNodeComponent {
    @Override
    public void process(SubContext subContext) throws Exception {
        ComponentContext cmpData = this.getCmpData(ComponentContext.class);
        String topic = subContext.getTopic();
        subContext.setTopic(topic + ":"  + StringUtil.randomNumberString(2));
        System.out.println("执行A, Thread: " + Thread.currentThread().getName());
        System.out.printf("组件ID :%s,  topic: %s \n",cmpData.getCmpId(), subContext.getTopic());
    }
}
