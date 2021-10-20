package com.lpy.sqlsession;

import com.lpy.pojo.Configuration;

import java.sql.SQLException;
import java.util.List;

/**
 * SqlSession
 * @author lipengyu
 */
public interface SqlSession {

    /**
     * 查询所有
     */
    <E> List<E> selectList(String statementId, Object ...param) throws Exception;

    /**
     * 根据条件查询单个
     */
    <T> T selectOne(String statementId, Object ...param) throws Exception;

    <T> T getMapper(Class<?> mapperClass);
}
