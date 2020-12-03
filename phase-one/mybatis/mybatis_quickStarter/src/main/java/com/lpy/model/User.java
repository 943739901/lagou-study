package com.lpy.model;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author lipengyu
 */
@Getter
@Setter
@ToString
public class User {
    private Integer id;

    private String username;

    private String password;

    private String birthday;
}
