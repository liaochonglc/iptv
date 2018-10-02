package com.ido.iptv.security;

import com.alibaba.fastjson.JSON;
import com.ido.iptv.entity.dto.ReturnBean;
import com.ido.iptv.exception.InvalidJWTException;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Component
public class JWTAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        response.setContentType("application/json;charset=utf8");

        String msg = "用户未通过认证";
        Throwable cause = authException.getCause();

        if (cause instanceof InvalidJWTException) {
            msg = authException.getMessage();
        }
        if (cause instanceof MalformedJwtException) {
            msg = "令牌结构异常";
        }
        if (cause instanceof ExpiredJwtException) {
            msg = "令牌过期";
        }
        if (cause instanceof SignatureException) {
            msg = "非法签名";
        }

        ReturnBean failure = ReturnBean.failure(msg);
        PrintWriter writer = response.getWriter();
        writer.write(JSON.toJSONString(failure));
    }
}
