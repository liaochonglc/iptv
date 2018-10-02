package com.ido.iptv.security;

import com.ido.iptv.common.constant.Secure;
import io.netty.util.CharsetUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;

import java.util.Objects;

@Component
public class MD5PasswordEncoder implements PasswordEncoder {

    /**
     * 加密
     *
     * @param rawPassword 待加密字符串
     * @return
     */
    @Override
    public String encode(CharSequence rawPassword) {
        String msg = rawPassword.toString() + Secure.PASSWORD_SALT;
        return DigestUtils.md5DigestAsHex(msg.getBytes(CharsetUtil.UTF_8));
    }

    /**
     * 密钥比对
     *
     * @param rawPassword     待加密的密码
     * @param encodedPassword 已经加加密过的密码
     * @return
     */
    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        return Objects.equals(encode(rawPassword), encodedPassword);
    }
}
