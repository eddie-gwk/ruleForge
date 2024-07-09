package com.yunext.core.isolation;

import com.yomahub.liteflow.core.NodeSwitchComponent;
import com.yunext.core.context.ComponentContext;
import com.yunext.core.context.MainContext;
import com.yunext.core.context.SubContext;
import org.apache.commons.collections.CollectionUtils;

import java.util.List;

/**
 * 隔离上下文的选择组件
 *
 * 因为Java不允许多重继承，lf又只允许选择组件继承自NodeSwitchComponent
 * 只能单独写个抽象类，并且实现原抽象类中的processSwitch使其不产生作用
 * @author ：qianjb [qianjb@hadlinks.com]
 * @description ：
 * @date ：Created in 2024/7/9 14:13
 */
public abstract class IsolationSwitchComponent extends NodeSwitchComponent {

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
        cmpData.getSubCmpId().forEach(subCmpId ->
                subCmpId.forEach(cmpId -> mainContext.setSubContext(cmpId, indexLocal.get())));
        indexLocal.remove();
        super.afterProcess();
    }

    public List<SubContext> getLocalSubContext() {
        return indexLocal.get();
    }

    public void setLocalSubContext(List<SubContext> subContextList) {
        indexLocal.set(subContextList);
    }


    public abstract String processSwitch(SubContext subContext) throws Exception;

    @Override
    public final void process() throws Exception {
        this.getLocalSubContext().forEach(context -> {
            try {
                String nodeId = String.format("tag:%s", this.processSwitch(context));
                this.getSlot().setSwitchResult(this.getMetaValueKey(), nodeId);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Override
    public final String processSwitch() throws Exception {
        //do nothing
        return null;
    }
}
