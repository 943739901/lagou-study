package com.lpy.sqlsession;

import com.lpy.pojo.Configuration;
import com.lpy.pojo.MappedStatement;

import java.lang.reflect.*;
import java.sql.SQLException;
import java.util.List;

/**
 * @author lipengyu
 */
public class DefaultSqlSession implements SqlSession {

    private Configuration configuration;

    public DefaultSqlSession(Configuration configuration) {
        this.configuration = configuration;
    }

    public Executor simpleExecutor = new SimpleExecutor();

    @Override
    public <E> List<E> selectList(String statementId, Object... param) throws Exception {
        MappedStatement mappedStatement = configuration.getMappedStatementMap().get(statementId);
        return simpleExecutor.query(configuration, mappedStatement, param);
    }

    @Override
    public <T> T selectOne(String statementId, Object... param) throws Exception {
        List<Object> objects = selectList(statementId, param);
        if (objects.size() > 1) {
            throw new RuntimeException("返回结果过多");
        }
        return (T) objects.get(0);
    }

    @Override
    public void close(Configuration configuration) throws SQLException {
        simpleExecutor.close(configuration);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T getMapper(Class<?> mapperClass) {
        return (T)Proxy.newProxyInstance(mapperClass.getClassLoader(), new Class[]{mapperClass}, (proxy, method, args) -> {
            String methodName = method.getName();
            String className = method.getDeclaringClass().getName();
            // 拼接key
            String key = className + "." + methodName;
            // 判断是否有泛型
            Type genericReturnType = method.getGenericReturnType();
            if (genericReturnType instanceof ParameterizedType) {
                return selectList(key, args);
            }
            return selectOne(key, args);
        });
    }
}
