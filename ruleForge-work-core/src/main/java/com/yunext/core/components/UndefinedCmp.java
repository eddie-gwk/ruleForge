package com.yunext.core.components;

import com.yomahub.liteflow.annotation.LiteflowComponent;
import com.yomahub.liteflow.core.NodeComponent;

/**
 * @author ：qianjb [qianjb@hadlinks.com]
 * @description ：
 * @date ：Created in 2024/7/5 13:59
 */
@LiteflowComponent("UNDEFINED")
public class UndefinedCmp extends NodeComponent {

    @Override
    public void process() throws Exception {
        System.out.println("EXECUTE UNDEFINED");
    }
}
