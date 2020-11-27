package com.lpy.pojo;


import lombok.Getter;
import lombok.Setter;

/**
 * @author lipengyu
 */
@Getter
@Setter
public class MappedStatement {

    private String id;

    private Class<?> parameterType;

    private Class<?> resultType;

    private String sql;

    public MappedStatement(String id, Class<?> parameterType, Class<?> resultType, String sql) {
        this.id = id;
        this.parameterType = parameterType;
        this.resultType = resultType;
        this.sql = sql;
    }
}
