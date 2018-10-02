package com.ido.iptv.service.impl;

import com.ido.iptv.dao.UserMapper;
import com.ido.iptv.entity.dto.ReturnBean;
import com.ido.iptv.entity.po.User;
import com.ido.iptv.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Jun
 * @date 2018-09-25 20:05
 */
@Service
public class UserServiceImpl implements IUserService {

    @Resource
    private UserMapper userMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User getUserByUsername(String username) {
        Assert.hasText(username, "用户名不能为空");

        return userMapper.selectByUsername(username);
    }

    @Override
    public ReturnBean register(User user) {
        //密码hash
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);

        return userMapper.insertSelective(user) > 0 ? ReturnBean.success(null, null) :
                ReturnBean.failure("用户名已经存在·");
    }

    @Override
    public List<String> getRoles(Integer id) {
        return userMapper.getRoles(id);
    }
}
