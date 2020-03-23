package com.wmx.quartzapp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * @author wangmaoxiong
 */
@Configuration
public class BeanConfig {

    @Bean
    public RestTemplate restTemplate() {
        //往 spring 容器中添加 RestTemplate 实例
        return new RestTemplate();
    }
}
