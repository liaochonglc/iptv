package com.ido.iptv.security;


import com.ido.iptv.common.constant.Secure;
import com.ido.iptv.exception.InvalidJWTException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * APP登陆用过滤器
 *
 * @author wujun
 * @date 2018/7/30 14:50
 */
public class JWTAuthenticationFilter extends OncePerRequestFilter {

    private AuthenticationEntryPoint authenticationEntryPoint;

    private RedisConnection conn;

    private boolean ignoreFailure = false;

    public JWTAuthenticationFilter(AuthenticationEntryPoint authenticationEntryPoint,
                                   RedisConnection conn) {
        this.authenticationEntryPoint = authenticationEntryPoint;
        this.conn = conn;
    }

    private void preCheck() {
        Assert.notNull(authenticationEntryPoint, "authenticationEntryPoint cannot be null");
        Assert.notNull(conn, "redis cache cannot be null");
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        preCheck();

        String head = request.getHeader("Authorization");

        if (StringUtils.isEmpty(head) || !head.startsWith("Bearer ")) {
            chain.doFilter(request, response);
            return;
        }

        try {
            String jwtString = head.substring(6);
            Claims claims = Jwts.parser()
                    .setSigningKey(Secure.JWT_KEY)
                    .parseClaimsJws(jwtString).getBody();

            String username = claims.getSubject();
            if (StringUtils.isEmpty(username)) {
                throw new InvalidJWTException("无法从令牌中获取用户名");
            }

            /*byte[] bytes = conn.get(jwtString.getBytes(CharsetUtil.UTF_8));
            if (!Objects.isNull(bytes)) {
                throw new InvalidJWTException("用户凭证已经过期");
            }*/

           /* UsernamePasswordAuthenticationToken token;
            try {
                byte[] cacheToken = conn.get(username.getBytes(Charset.forName("utf8")));
                token = SerializeUtils.byte2Obj(cacheToken, UsernamePasswordAuthenticationToken.class);
            } catch (ClassNotFoundException e) {
                throw new InvalidJWTException("用户实体序列化失败", e);
            }*/
            String roles = claims.get("roles", String.class);

            List<GrantedAuthority> list = new ArrayList<>();
            StringUtils.commaDelimitedListToSet(roles)
                    .forEach(e -> list.add(new SimpleGrantedAuthority(e)));

            Authentication token = new UsernamePasswordAuthenticationToken(username, null, list);

            SecurityContextHolder.getContext().setAuthentication(token);

            onSuccessfulAuthentication(request, response, token);
        } catch (AuthenticationException failed) {
            SecurityContextHolder.clearContext();

            if (ignoreFailure) {
                chain.doFilter(request, response);
            } else {
                this.authenticationEntryPoint.commence(request, response, failed);
            }

            return;
        } catch (JwtException e) {
            SecurityContextHolder.clearContext();

            InvalidJWTException jwtException = new InvalidJWTException(e.getMessage(), e);

            this.authenticationEntryPoint.commence(request, response, jwtException);

            return;
        }

        chain.doFilter(request, response);
    }

    protected void onSuccessfulAuthentication(HttpServletRequest request,
                                              HttpServletResponse response,
                                              Authentication token) {

    }
}
