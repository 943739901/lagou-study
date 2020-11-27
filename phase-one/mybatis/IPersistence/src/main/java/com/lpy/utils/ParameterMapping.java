package com.lpy.utils;

import lombok.Getter;
import lombok.Setter;

/**
 * @author lipengyu
 */
@Getter
@Setter
public class ParameterMapping {

    private String content;

    public ParameterMapping(String content) {
        this.content = content;
    }
}
