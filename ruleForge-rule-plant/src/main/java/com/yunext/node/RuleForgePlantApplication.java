package com.yunext.node;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

/**
 * @author ：qianjb [qianjb@hadlinks.com]
 * @description ：
 * @date ：Created in 2024/7/2 17:34
 */
@EnableDubbo
@SpringBootApplication
public class RuleForgePlantApplication {
    public static void main(String[] args) {
        new SpringApplicationBuilder(RuleForgePlantApplication.class).web(WebApplicationType.NONE).run(args);
    }
}
