package com.lpy.config;

import com.lpy.utils.ParameterMapping;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author lipengyu
 */
@Getter
@Setter
public class BoundSql {

    private String boundSql;

    private List<ParameterMapping> parameterMappingList;

    public BoundSql(String boundSql, List<ParameterMapping> parameterMappingList) {
        this.boundSql = boundSql;
        this.parameterMappingList = parameterMappingList;
    }
}
