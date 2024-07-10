package com.yunext.core.components;

import com.yomahub.liteflow.annotation.LiteflowComponent;
import com.yunext.core.context.SubContext;
import com.yunext.core.isolation.IsolationNodeComponent;

/**
 * @author ：qianjb [qianjb@hadlinks.com]
 * @description ：
 * @date ：Created in 2024/7/10 15:25
 */
@LiteflowComponent("none")
public class NoneCmp extends IsolationNodeComponent {

    @Override
    public void process(SubContext subContext) throws Exception {
        //switch 匹配失败的终点分支
    }
}
