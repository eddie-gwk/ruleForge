package com.yunext.api.dto;

import com.yunext.common.base.BasicNode;
import com.yunext.common.base.ScriptDSL;

import java.io.Serializable;
import java.util.List;

/**
 * @author ：qianjb [qianjb@hadlinks.com]
 * @description ：
 * @date ：Created in 2024/7/4 13:42
 */
public class RuleSyntaxTreeDto implements Serializable {

    private String chain;

    private String ruleDsl;

    private BasicNode root;

    /**
     * 脚本
     */
    private List<ScriptDSL> scriptDSLList;


    public String getChain() {
        return chain;
    }

    public void setChain(String chain) {
        this.chain = chain;
    }

    public String getRuleDsl() {
        return ruleDsl;
    }

    public void setRuleDsl(String ruleDsl) {
        this.ruleDsl = ruleDsl;
    }

    public BasicNode getRoot() {
        return root;
    }

    public void setRoot(BasicNode root) {
        this.root = root;
    }

    public List<ScriptDSL> getScriptDSLList() {
        return scriptDSLList;
    }

    public void setScriptDSLList(List<ScriptDSL> scriptDSLList) {
        this.scriptDSLList = scriptDSLList;
    }
}
