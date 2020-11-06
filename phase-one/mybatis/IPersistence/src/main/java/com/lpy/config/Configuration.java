package com.lpy.config;

import javax.sql.DataSource;
import java.io.InputStream;

/**
 * @author lipengyu
 */
public class Configuration {

    private DataSource dataSource;

    public InputStream getResourceAsStream(String path) {
        return this.getClass().getClassLoader().getResourceAsStream(path);
    }
}
