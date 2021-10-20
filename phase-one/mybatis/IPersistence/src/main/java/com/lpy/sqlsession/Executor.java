package com.lpy.sqlsession;

import com.lpy.pojo.Configuration;
import com.lpy.pojo.MappedStatement;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.List;

/**
 * 执行器接口
 * @author lipengyu
 */
public interface Executor {

    <E> List<E> query(Configuration configuration, MappedStatement mappedStatement, Object[] param) throws Exception;
}
