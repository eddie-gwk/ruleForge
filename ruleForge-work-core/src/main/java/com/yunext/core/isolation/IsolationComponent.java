package com.yunext.core.isolation;

import com.yomahub.liteflow.core.NodeComponent;
import com.yunext.core.context.ComponentContext;
import com.yunext.core.context.MainContext;
import com.yunext.core.context.SubContext;
import org.apache.commons.collections.CollectionUtils;

import java.util.List;

/**
 * 数据隔离组件
 * @author ：qianjb [qianjb@hadlinks.com]
 * @description ：数据隔离组件，用来为每个组件创建单独的上下文
 * @date ：Created in 2024/7/9 14:19
 */
public abstract class IsolationComponent extends NodeComponent {

    private final ThreadLocal<List<SubContext>> indexLocal = new ThreadLocal<>();

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
        //设置子组件的下上文
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
