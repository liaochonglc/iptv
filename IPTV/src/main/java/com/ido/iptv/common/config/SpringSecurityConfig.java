package com.ido.iptv.common.config;

import com.ido.iptv.security.JWTAuthenticationFilter;
import com.ido.iptv.security.MyAuthenticationFailureHandler;
import com.ido.iptv.security.MyAuthenticationSuccessHandle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.session.security.web.authentication.SpringSessionRememberMeServices;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Jun
 * @date 2018-09-25 14:41
 */
@EnableWebSecurity
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private RedisConnection conn;

    @Autowired
    private AuthenticationEntryPoint authenticationEntryPoint;

    @Autowired
    private AccessDeniedHandler accessDeniedHandler;

    @Autowired
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationProvider provider) {
        List<AuthenticationProvider> providerList = new ArrayList<>(1);
        providerList.add(provider);
        return new ProviderManager(providerList);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // @formatter:off
        http
                .rememberMe()
                    .rememberMeServices(new SpringSessionRememberMeServices())
                    .and()
                .exceptionHandling()
                    .accessDeniedHandler(accessDeniedHandler)   //当用户非匿名登录，请求被拒绝
                    .authenticationEntryPoint(authenticationEntryPoint) //用户认证无法通过
                    .and()
                .sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                    .and()
                .csrf().disable() // 关闭跨域攻击防护
                .authorizeRequests()    //需要处理的路由
                    .antMatchers(HttpMethod.POST,"/app/user/login","/app/user/register","/user/register","/user/testHtml","/user/login.html","/static/login.html","/login.html","/a").permitAll()
                    .antMatchers("/resources/**","/signUp","/about","/templates","/static").permitAll()
                    .antMatchers("/v2/api-docs", "/configuration/ui", "/swagger-resources",
                            "/configuration/security", "/swagger-ui.html", "/webjars/**",
                            "/swagger-resources/configuration/ui","/swagge‌​r-ui.html").permitAll()
                    .anyRequest().authenticated()
                    .and()
                .formLogin()
                    .loginProcessingUrl("/user/login").permitAll()
                    .failureHandler(new MyAuthenticationFailureHandler())
                    .successHandler(new MyAuthenticationSuccessHandle())
                    .and()
                .addFilterAfter(new JWTAuthenticationFilter(authenticationEntryPoint,conn),
                        LogoutFilter.class);
        //@formatter:on
    }
}
