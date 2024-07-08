package com.yunext.core.isolation;

import com.yomahub.liteflow.core.NodeComponent;
import com.yunext.core.context.MainContext;
import com.yunext.core.context.SubContext;

/**
 * @author ：qianjb [qianjb@hadlinks.com]
 * @description ：线程隔离节点
 * @date ：Created in 2024/7/8 10:02
 */
public abstract class IsolationNodeComponent extends NodeComponent {

    private final ThreadLocal<Integer> indexLocal = new ThreadLocal<>();

    @Override
    public void beforeProcess() {
        super.beforeProcess();
        MainContext contextBean = this.getContextBean(MainContext.class);
        SubContext subContext = contextBean.copyToSub();
        contextBean.getSubContexts().add(subContext);
        int index = contextBean.getSubContexts().indexOf(subContext);
        indexLocal.set(index);
    }

    @Override
    public void afterProcess() {
        super.afterProcess();
    }

    public Integer subContextIndex() {
        return indexLocal.get();
    }
}
