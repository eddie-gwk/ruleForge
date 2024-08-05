package com.yunext.common.base;

import java.io.Serializable;

/**
 * @author ：qianjb [qianjb@hadlinks.com]
 * @description ：
 * @date ：Created in 2024/7/16 11:27
 */
public class ScriptDSL implements Serializable {
    /**
     * key
     */
    private String key;
    /**
     * 脚本内容
     */
    private String script;

    private final static String CONTEXT_SCRIPT_PART_1 = """
            let cmpData = _meta.cmpData;
            if (cmpData === undefined || cmpData === null) {
                console.log("cmp data can not be null, check config");
                cmpData = {};
            }
                        
            let subContextList = mainContext.getSubContext(cmpData.cmpId);
            if (Array.isArray(subContextList) && subContextList.length === 0) {
                subContextList.push(mainContext.copyToSubContext());
            }
            let copyList = subContextList.map(sub => sub.copy());
            copyList.map(function(subContext) {
                //insert outer code here
            
            """;

    private final static String CONTEXT_SCRIPT_PART_2 = """
            });
                        
            let subCmpId = cmpData.subCmpId;
            for (let index = 0; index < subCmpId.length; index++) {
                let cmpIdList = subCmpId[index];
                copyList.forEach(copy => copy.ruleIndex = index);
                cmpIdList.forEach(cmpId => {
                    mainContext.setSubContextForce(cmpId, copyList);
                });
            }
            """;

    public ScriptDSL() {
    }

    public ScriptDSL(String key, String script) {
        this.key = key;
        this.script = script;
    }

    public String getScript() {
        return CONTEXT_SCRIPT_PART_1 + this.script + CONTEXT_SCRIPT_PART_2;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void setScript(String script) {
        this.script = script;
    }
}
