package com.yunext.core.isolation;

import com.alibaba.fastjson2.JSONObject;
import com.yomahub.liteflow.core.NodeComponent;
import com.yunext.common.node.common.ChangeNode;
import com.yunext.common.utils.ModelMapperUtil;
import com.yunext.core.context.ComponentContext;
import com.yunext.core.context.MainContext;
import com.yunext.core.context.SubContext;
import org.apache.commons.collections.CollectionUtils;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 隔离上下文的普通组件
 * @author ：qianjb [qianjb@hadlinks.com]
 * @description ：
 * @date ：Created in 2024/7/8 10:02
 */
public abstract class IsolationNodeComponent extends IsolationComponent {

    public IsolationNodeComponent() {
        super();
    }

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

    /**
     * 不区分规则组的组件规则
     * @param clazz
     * @return
     * @param <T>
     */
    public <T> List<T> getRuleList(Class<T> clazz) {
        ComponentContext cmpData = this.getCmpData(ComponentContext.class);
        return cmpData.getRules().stream().map(e->ModelMapperUtil.map(e, clazz)).toList();
    }

    public <T> T getProp(int index, Class<T> clazz) {
        ComponentContext cmpData = this.getCmpData(ComponentContext.class);
        return Optional.ofNullable(cmpData)
                .map(ComponentContext::getProps)
                .map(p -> p.get(index))
                .map(p -> ModelMapperUtil.map(p, clazz))
                .orElse(null);
    }

}
