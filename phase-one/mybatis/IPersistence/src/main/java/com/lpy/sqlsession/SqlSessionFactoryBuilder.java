package com.lpy.sqlsession;

import com.lpy.config.XMLConfigerBuilder;
import com.lpy.pojo.Configuration;
import org.dom4j.DocumentException;

import java.beans.PropertyVetoException;
import java.io.InputStream;

/**
 * 解析配置文件以及生产sqlSession
 * @author lipengyu
 */
public class SqlSessionFactoryBuilder {

    private Configuration configuration;

    public SqlSessionFactoryBuilder() {
        this.configuration = new Configuration();
    }

    public SqlSessionFactory build(InputStream in) throws DocumentException, PropertyVetoException, ClassNotFoundException {
        // 第一：使用dom4j解析配置文件，将解析出来的内容封装到Configuration中
        XMLConfigerBuilder builder = new XMLConfigerBuilder(configuration);
        Configuration configuration = builder.parseConfig(in);
        // 第二：创建sqlSessionFactory对象 工厂类：生产sqlSession：会话对象
        return new DefaultSqlSessionFactory(configuration);
    }
}
