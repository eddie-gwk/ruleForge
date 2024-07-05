package com.yunext.storage;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

/**
 * @author ：qianjb [qianjb@hadlinks.com]
 * @description ：
 * @date ：Created in 2024/7/5 11:47
 */
@SpringBootApplication
public class RuleForgeStorageApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder(RuleForgeStorageApplication.class).web(WebApplicationType.NONE).run(args);
    }
}
