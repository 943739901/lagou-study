package com.lpy.pojo;

import lombok.Getter;
import lombok.Setter;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * 将sqlMapConfig.xml解析成对象
 * @author lipengyu
 */
@Getter
@Setter
public class Configuration {

    /**
     * 数据源
     */
    private DataSource dataSource;
    /**
     * key: statementId value: 封装好的mappedStatement对象
     */
    private Map<String, MappedStatement> mappedStatementMap = new HashMap<>();
}
