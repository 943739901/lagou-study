package com.lpy.model;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.management.relation.Role;
import java.util.List;

/**
 * User实体类
 * @author lipengyu
 */
@Getter
@Setter
@ToString
public class User {
    private Long id;

    private String username;
}
