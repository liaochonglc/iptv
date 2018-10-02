package com.ido.iptv.controller.app;

import com.ido.iptv.common.constant.Secure;
import com.ido.iptv.entity.dto.ReturnBean;
import com.ido.iptv.entity.po.User;
import com.ido.iptv.service.IUserService;
import freemarker.template.Template;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.time.Duration;
import java.util.*;

/**
 * 用户相关接口
 *
 * @author Jun
 * @date 2018-09-25 15:49
 */
@Api(tags = "用户相关接口")
@RestController
@RequestMapping(value = "user", produces = "application/json;charset=utf8")
public class UserController {


    @Autowired
    private IUserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @ApiOperation("app用户登录")
    @PostMapping("login")
    public ReturnBean login(@Validated User user) {
        //校验用户

        User dbUser = userService.getUserByUsername(user.getUsername());

        if (dbUser == null) {
            return ReturnBean.failure("用户不存在");
        }

        if (!passwordEncoder.matches(user.getPassword(), dbUser.getPassword())) {
            return ReturnBean.failure("密码错误");
        }

        //获取用户角色
        List<String> roles = userService.getRoles(dbUser.getId());

        Map<String, Object> claims = new HashMap<>(2);
        claims.put("roles", StringUtils.collectionToDelimitedString(roles,","));

        String jwt = Jwts.builder()
                .setClaims(claims)
                .setSubject(user.getUsername())
                .setExpiration(new Date(System.currentTimeMillis() + Duration.ofDays(7).toMillis()))
                .signWith(SignatureAlgorithm.HS256, Secure.JWT_KEY)
                .compact();

        return ReturnBean.success("Bearer " + jwt, null);
    }

    @ApiOperation("添加用户")
    @PostMapping("register")
    public ReturnBean register(@Validated User user){
        return userService.register(user);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("authTest")
    public ReturnBean authTest() {
        return ReturnBean.success(null, "权限通过");
    }

    @PostMapping("session")
    public ReturnBean session(HttpSession session){
        return ReturnBean.success(session.getId(), null);
    }

}
