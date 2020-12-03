package com.lpy.mapper;

import com.lpy.model.User;

import java.io.IOException;
import java.util.List;

/**
 * @author lipengyu
 */
public interface IUserMapper {

    List<User> findAll();
}
