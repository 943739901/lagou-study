package com.lpy.dao;

import com.lpy.model.User;

import java.io.IOException;
import java.util.List;

/**
 * @author lipengyu
 */
public interface IUserDao {

    List<User> findAll() throws IOException;
}
