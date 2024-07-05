package com.yunext.api.dto;

import java.io.Serializable;

/**
 * @author ：qianjb [qianjb@hadlinks.com]
 * @description ：
 * @date ：Created in 2024/7/4 13:42
 */
public class RuleSyntaxTreeDto implements Serializable {

    private String chain;

    private String ruleDsl;

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
}
