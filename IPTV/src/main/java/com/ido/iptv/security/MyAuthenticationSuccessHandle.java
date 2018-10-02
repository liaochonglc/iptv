package com.ido.iptv.security;

import com.alibaba.fastjson.JSON;
import com.ido.iptv.entity.dto.ReturnBean;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Jun
 * @date 2018-09-25 22:05
 */
public class MyAuthenticationSuccessHandle implements AuthenticationSuccessHandler {


    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        response.setCharacterEncoding("utf8");
        response.setHeader("Content-type", "application/json;charset=utf8");

        ReturnBean<Object> result = ReturnBean.success(null, "登录成功");
        response.getWriter().write(JSON.toJSONString(result));
    }
}
