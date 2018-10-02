package com.ido.iptv.common.config;

import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.session.web.context.AbstractHttpSessionApplicationInitializer;

/**
 * @author Jun
 * @date 2018-09-25 21:39
 */
@EnableRedisHttpSession
public class SpringSessionConfig extends AbstractHttpSessionApplicationInitializer {

    public SpringSessionConfig(){
        super(RedisConfig.class);
    }
}
