package com.yunext.core.context;

import java.io.Serializable;
import java.util.List;

/**
 * @author ：qianjb [qianjb@hadlinks.com]
 * @description ：组件上下文
 * @date ：Created in 2024/7/9 13:10
 */
public class ComponentContext implements Serializable {

    /**
     * 组件id
     */
    private String cmpId;
    /**
     * 子组件id
     */
    private List<String> subCmpId;

    public String getCmpId() {
        return cmpId;
    }

    public void setCmpId(String cmpId) {
        this.cmpId = cmpId;
    }

    public List<String> getSubCmpId() {
        return subCmpId;
    }

    public void setSubCmpId(List<String> subCmpId) {
        this.subCmpId = subCmpId;
    }
}
