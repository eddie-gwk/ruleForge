package com.yunext.common.node.common;

/**
 * @author ：qianjb [qianjb@hadlinks.com]
 * @description ：脚本执行组件定义
 * @date ：Created in 2024/7/16 10:49
 */
public class ScriptNode {


    public static class ScriptProp {
        /**
         * 脚本类型
         * @see com.yunext.common.enums.ScriptTypeEnum
         */
        private String scriptType;

        public String getScriptType() {
            return scriptType;
        }

        public void setScriptType(String scriptType) {
            this.scriptType = scriptType;
        }
    }


    public static class ScriptRule {
        private String script;

        public String getScript() {
            return script;
        }

        public void setScript(String script) {
            this.script = script;
        }
    }
}
