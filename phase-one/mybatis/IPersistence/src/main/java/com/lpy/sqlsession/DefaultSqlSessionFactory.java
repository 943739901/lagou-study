package com.lpy.sqlsession;

import com.lpy.pojo.Configuration;

/**
 * SqlSession工厂 默认实现
 * @author lipengyu
 */
public class DefaultSqlSessionFactory implements SqlSessionFactory {

    private Configuration configuration;

    public DefaultSqlSessionFactory(Configuration configuration) {
        this.configuration = configuration;
    }

    @Override
    public DefaultSqlSession openSession() {
        return new DefaultSqlSession(configuration);
    }
}
