package com.yunext.core.components.common;

import com.yomahub.liteflow.annotation.LiteflowComponent;
import com.yomahub.liteflow.core.FlowExecutor;
import com.yunext.core.context.ComponentContext;
import com.yunext.core.context.SubContext;
import com.yunext.core.isolation.IsolationNodeComponent;
import jakarta.annotation.Resource;

/**
 * 注入组件
 *
 * @author ：qianjb [qianjb@hadlinks.com]
 * @description ：
 * 可以作为数据输入的起点
 * 目前规划的功能有 手动触发输入/循环重复输入
 * @date ：Created in 2024/7/9 17:45
 */
@LiteflowComponent("inject")
public class InjectCmp extends IsolationNodeComponent {

    @Override
    public void process(SubContext subContext) throws Exception {
        //do nothing
    }
}
