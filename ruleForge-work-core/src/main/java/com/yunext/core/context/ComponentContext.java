package com.yunext.core.context;

import com.alibaba.fastjson2.JSONObject;

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
    private List<List<String>> subCmpId;
    /**
     * 如何是选择组件，还要保存子组件的tags
     */
    private List<String> tags;
    /**
     * 组件规则
     */
    private List<JSONObject> rules;
    /**
     * 输入参数
     */
    private List<JSONObject> props;

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

    public List<JSONObject> getRules() {
        return rules;
    }

    public void setRules(List<JSONObject> rules) {
        this.rules = rules;
    }

    public List<JSONObject> getProps() {
        return props;
    }

    public void setProps(List<JSONObject> props) {
        this.props = props;
    }
}
