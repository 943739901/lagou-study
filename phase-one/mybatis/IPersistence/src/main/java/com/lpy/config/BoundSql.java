package com.lpy.config;

import com.lpy.utils.ParameterMapping;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * sql及参数映射
 * @author lipengyu
 */
@Getter
@Setter
public class BoundSql {

    /**
     * 解析过后的sql
     */
    private String boundSql;

    /**
     * 参数映射
     */
    private List<ParameterMapping> parameterMappingList;

    public BoundSql(String boundSql, List<ParameterMapping> parameterMappingList) {
        this.boundSql = boundSql;
        this.parameterMappingList = parameterMappingList;
    }
}
