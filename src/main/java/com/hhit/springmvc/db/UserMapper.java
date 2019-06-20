package com.hhit.springmvc.db;

import com.hhit.springmvc.bean.User;

public interface UserMapper {
    public User getUser(Long id);

    public int insertUser(User user);

    public int deleteUser(Long id);
}