package com.lpy.sqlsession;

import com.lpy.config.XMLConfigerBuilder;
import com.lpy.pojo.Configuration;
import org.dom4j.DocumentException;

import java.beans.PropertyVetoException;
import java.io.InputStream;

/**
 * @author lipengyu
 */
public class SqlSessionFactoryBuilder {

    private Configuration configuration;

    public SqlSessionFactoryBuilder() {
        this.configuration = new Configuration();
    }

    public SqlSessionFactory build(InputStream in) throws DocumentException, PropertyVetoException, ClassNotFoundException {
        XMLConfigerBuilder builder = new XMLConfigerBuilder(configuration);
        Configuration configuration = builder.parseConfiguration(in);
        return new DefaultSqlSessionFactory(configuration);
    }
}
