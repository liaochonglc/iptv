package com.ido.iptv.common.config;

import freemarker.template.Template;
import org.springframework.context.annotation.Bean;
import freemarker.template.Configuration;
import javax.annotation.Resource;
import java.io.IOException;

/**
 * @author LiaoChong
 * @date 2018-10-01 15:15
 */

@org.springframework.context.annotation.Configuration
public class StaticHtmlConfig {
    @Resource
    Configuration cfg;
    @Bean
    public Template getTemplate(){
        Template template = null;
        try {
            template = cfg.getTemplate("/login.ftl");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return template;
    }
}
