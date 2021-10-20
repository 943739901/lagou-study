package com.lpy.pojo;


import lombok.Getter;
import lombok.Setter;

/**
 * 将mapper.xml中sql标签解析成对象
 * @author lipengyu
 */
@Getter
@Setter
public class MappedStatement {
    /**
     * id标识
     */
    private String id;
    /**
     * 返回值类型
     */
    private Class<?> parameterType;
    /**
     * 参数值类型
     */
    private Class<?> resultType;
    /**
     * sql语句
     */
    private String sql;

    public MappedStatement(String id, Class<?> parameterType, Class<?> resultType, String sql) {
        this.id = id;
        this.parameterType = parameterType;
        this.resultType = resultType;
        this.sql = sql;
    }
}
