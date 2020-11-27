package com.lpy.config;

import com.lpy.pojo.Configuration;
import com.lpy.pojo.MappedStatement;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

/**
 * @author lipengyu
 */
public class XMLMapperBuilder {

    public Configuration configuration;

    public XMLMapperBuilder(Configuration configuration) {
        this.configuration = configuration;
    }

    public void parse(InputStream resourceAsStream) throws DocumentException, ClassNotFoundException {
        Document read = new SAXReader().read(resourceAsStream);
        Element rootElement = read.getRootElement();
        String namespace = rootElement.attributeValue("namespace");
        List<Element> selectElements = rootElement.selectNodes("select");

        Map<String, MappedStatement> mappedStatementMap = configuration.getMappedStatementMap();
        for (Element selectElement : selectElements) {
            String id = selectElement.attributeValue("id");
            String parameterType = selectElement.attributeValue("parameterType");
            String resultType = selectElement.attributeValue("resultType");
            String sql = selectElement.getTextTrim();
            Class<?> parameterTypeClass = null;
            Class<?> reusltClassType = null;
            if (parameterType != null) {
                parameterTypeClass = getClassType(parameterType);
            }
            if (resultType != null) {
                reusltClassType = getClassType(resultType);
            }
            mappedStatementMap.put(namespace + "." + id
                    , new MappedStatement(id, parameterTypeClass, reusltClassType, sql));

        }
    }

    private Class<?> getClassType(String parameterType) throws ClassNotFoundException {
        return Class.forName(parameterType);
    }

}
