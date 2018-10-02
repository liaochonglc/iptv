package com.ido.iptv.security;

import com.alibaba.fastjson.JSON;
import com.ido.iptv.entity.dto.ReturnBean;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author wujun
 * @date 2018/7/29 19:36
 */
@Component
public class JWTAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        response.setContentType("application/json;charset=utf8");

        ReturnBean failure = ReturnBean.failure("用户权限不足");
        response.getWriter().write(JSON.toJSONString(failure));
    }
}
