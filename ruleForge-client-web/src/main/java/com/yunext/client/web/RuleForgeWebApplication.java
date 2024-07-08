package com.yunext.client.web;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author ：qianjb [qianjb@hadlinks.com]
 * @description ：
 * @date ：Created in 2024/7/5 13:02
 */
@EnableDubbo
@SpringBootApplication
public class RuleForgeWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(RuleForgeWebApplication.class, args);
    }

}
