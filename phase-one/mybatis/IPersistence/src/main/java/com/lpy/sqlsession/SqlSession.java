package com.lpy.sqlsession;

import com.lpy.pojo.Configuration;

import java.sql.SQLException;
import java.util.List;

/**
 * @author lipengyu
 */
public interface SqlSession {

    <E> List<E> selectList(String statementId, Object ...param) throws Exception;

    <T> T selectOne(String statementId, Object ...param) throws Exception;

    void close(Configuration configuration) throws SQLException;

    <T> T getMapper(Class<?> mapperClass);
}
