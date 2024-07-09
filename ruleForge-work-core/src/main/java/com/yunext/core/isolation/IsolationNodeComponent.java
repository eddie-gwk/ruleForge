package com.yunext.core.isolation;

import com.yomahub.liteflow.core.NodeComponent;
import com.yunext.core.context.ComponentContext;
import com.yunext.core.context.MainContext;
import com.yunext.core.context.SubContext;
import org.apache.commons.collections.CollectionUtils;

import java.util.List;
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

}
