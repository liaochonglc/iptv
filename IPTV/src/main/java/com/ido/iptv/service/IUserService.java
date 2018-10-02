package com.ido.iptv.service;

import com.ido.iptv.entity.dto.ReturnBean;
import com.ido.iptv.entity.po.User;

import java.util.List;

/**
 * @author Jun
 * @date 2018-09-25 20:04
 */
public interface IUserService {

    User getUserByUsername(String username);

    /**
     * 添加用户
     *
     * @param user 用户参数
     * @return
     */
    ReturnBean register(User user);

    /**
     * 通过用户ID获取角色列表
     *
     * @param id 用户ID
     * @return
     */
    List<String> getRoles(Integer id);
}
