package com.lpy.config;

import com.lpy.io.Resources;
import com.lpy.pojo.Configuration;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.beans.PropertyVetoException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

/**
 * @author lipengyu
 */
public class XMLConfigerBuilder {

    private Configuration configuration;

    public XMLConfigerBuilder(Configuration configuration) {
        this.configuration = configuration;
    }

    /**
     * 从当前节点的儿子节点中选择名称为 item 的节点。
     * SelectNodes("item")
     * 从根节点的儿子节点中选择名称为 item 的节点。
     * SelectNodes("/item")
     * 从任意位置的节点上选择名称为 item 的节点。要重点突出这个任意位置，它不受当前节点的影响，也就是说假如当前节点是在第 100 层（有点夸张），也可以选择第一层的名称为 item 的节点。
     * SelectNodes("//item")
     * @param in
     * @return
     * @throws DocumentException
     * @throws PropertyVetoException
     */
    public Configuration parseConfiguration(InputStream in) throws DocumentException, PropertyVetoException, ClassNotFoundException {
        Document read = new SAXReader().read(in);
        Element rootElement = read.getRootElement();
        List<Element> propertyElements = rootElement.selectNodes("//property");
        System.out.println(propertyElements);
        Properties properties = new Properties();
        propertyElements.forEach(element -> {
            properties.put(element.attributeValue("name"), element.attributeValue("value"));
        });

        ComboPooledDataSource dataSource = new ComboPooledDataSource();
        dataSource.setJdbcUrl(properties.getProperty("jdbcUrl"));
        dataSource.setDriverClass(properties.getProperty("driverClass"));
        dataSource.setUser(properties.getProperty("user"));
        dataSource.setPassword(properties.getProperty("password"));

        configuration.setDataSource(dataSource);

        List<Element> mapperElements = rootElement.selectNodes("//mapper");
        XMLMapperBuilder xmlMapperBuilder = new XMLMapperBuilder(configuration);
        for (Element mapperElement : mapperElements) {
            String mapperPath = mapperElement.attributeValue("resource");
            InputStream resourceAsStream = Resources.getResourceAsStream(mapperPath);
            xmlMapperBuilder.parse(resourceAsStream);
        }
        return configuration;
    }
}
