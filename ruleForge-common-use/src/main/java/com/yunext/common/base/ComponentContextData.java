package com.yunext.common.base;

import com.alibaba.fastjson2.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 组件参数
 * @notice 需要和ComponentContext结构保持一致
 * @author ：qianjb [qianjb@hadlinks.com]
 * @description ：
 * @date ：Created in 2024/7/9 14:58
 */
public class ComponentContextData implements Serializable {

    /**
     * 组件id
     */
    private String cmpId;
    /**
     * 子组件id
     */
    private List<List<String>> subCmpId;
    /**
     * 如何是选择组件，还要保存子组件的tags
     */
    private List<String> tags;
    /**
     * 组件规则
     */
    private List<?> rules;
    /**
     * 组件参数
     */
    private List<?> props;

    public String getCmpId() {
        return cmpId;
    }

    public void setCmpId(String cmpId) {
        this.cmpId = cmpId;
    }

    public List<List<String>> getSubCmpId() {
        return subCmpId;
    }

    public void setSubCmpId(List<List<String>> subCmpId) {
        this.subCmpId = subCmpId;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public List<?> getRules() {
        return rules;
    }

    public void setRules(List<?> rules) {
        this.rules = rules;
    }

    public List<?> getProps() {
        return props;
    }

    public void setProps(List<?> props) {
        this.props = props;
    }

    public void initList(int size) {
        this.subCmpId = new ArrayList<>(size);
        this.tags = new ArrayList<>(size);
        this.rules = new ArrayList<>(size);
        this.props = new ArrayList<>(size);
    }
}
