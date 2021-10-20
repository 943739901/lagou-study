package com.lpy.dao;

import com.lpy.model.User;

import java.util.List;

/**
 * @author lipengyu
 */
public interface IUserDao {

    List<User> findAll() throws Exception;

    User findByCondition(User user) throws Exception;
}
