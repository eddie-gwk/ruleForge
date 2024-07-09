package com.yunext.core.isolation;

import com.yomahub.liteflow.core.NodeComponent;
import com.yunext.core.context.ComponentContext;
import com.yunext.core.context.MainContext;
import com.yunext.core.context.SubContext;
import org.apache.commons.collections.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author ：qianjb [qianjb@hadlinks.com]
 * @description ：线程隔离节点
 * @date ：Created in 2024/7/8 10:02
 */
public abstract class IsolationNodeComponent extends NodeComponent {

    private final ThreadLocal<List<SubContext>> indexLocal = new ThreadLocal<>();

    public abstract void process(SubContext subContext) throws Exception;

    @Override
    public final void process() throws Exception {
        this.getLocalSubContext().forEach(context -> {
            try {
                process(context);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Override
    public void beforeProcess() {
        super.beforeProcess();
        ComponentContext cmpData = this.getCmpData(ComponentContext.class);
        if (cmpData == null) {
            return;
        }
        MainContext mainContext = this.getContextBean(MainContext.class);
        List<SubContext> subContextList = mainContext.getSubContext(cmpData.getCmpId());
        if (CollectionUtils.isEmpty(subContextList)) {
            subContextList.add(mainContext.copyToSubContext());
        }
        //进行一个拷贝，不要对原数据进行修改
        List<SubContext> copySubContextList = subContextList.stream().map(SubContext::copy).toList();
        indexLocal.set(copySubContextList);
    }

    @Override
    public void afterProcess() {
        ComponentContext cmpData = this.getCmpData(ComponentContext.class);
        if (cmpData == null) {
            return;
        }
        MainContext mainContext = this.getContextBean(MainContext.class);
        cmpData.getSubCmpId().forEach(subCmpId -> mainContext.setSubContext(subCmpId, indexLocal.get()));
        indexLocal.remove();
        super.afterProcess();
    }

    public List<SubContext> getLocalSubContext() {
        return indexLocal.get();
    }

    public void setLocalSubContext(List<SubContext> subContextList) {
        indexLocal.set(subContextList);
    }
}
