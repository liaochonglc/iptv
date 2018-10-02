package com.ido.iptv.security;

import com.alibaba.fastjson.JSON;
import com.ido.iptv.entity.dto.ReturnBean;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Jun
 * @date 2018-09-25 22:08
 */
public class MyAuthenticationFailureHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        response.setCharacterEncoding("utf8");
        response.setHeader("Content-type", "application/json;charset=utf8");

        ReturnBean<Object> result = ReturnBean.failure("登录失败");
        response.getWriter().write(JSON.toJSONString(result));
    }
}
