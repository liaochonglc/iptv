package com.ido.iptv.dao;

import com.ido.iptv.entity.po.User;

import java.util.List;

public interface UserMapper {

    int deleteByPrimaryKey(Integer id);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(User record);

    User selectByUsername(String username);

    List<String> getRoles(Integer id);
}