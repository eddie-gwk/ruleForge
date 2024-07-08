package com.yunext.core;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

/**
 * @author ：qianjb [qianjb@hadlinks.com]
 * @description ：
 * @date ：Created in 2024/7/5 11:47
 */
@EnableDubbo
@SpringBootApplication
public class RuleForgeCoreApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder(RuleForgeCoreApplication.class).web(WebApplicationType.NONE).run(args);
    }
}
