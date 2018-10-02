package com.ido.iptv.security;

import com.ido.iptv.dao.UserMapper;
import com.ido.iptv.entity.po.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Jun
 * @date 2018-09-25 22:43
 */
@Component
public class MyUserDetailsService implements UserDetailsService {

    @Resource
    private UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userMapper.selectByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException("用户不存在");
        }

        List<String> roles = userMapper.getRoles(user.getId());
        List<GrantedAuthority> authorityList = new ArrayList<>(roles.size());

        roles.forEach(role -> authorityList.add(new SimpleGrantedAuthority(role)));

        return new org.springframework.security.core.userdetails.User(user.getUsername(),user.getPassword(),
                true,true,true,true,authorityList);
    }
}
