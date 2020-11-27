package com.lpy.io;

import java.io.InputStream;

/**
 * @author lipengyu
 */
public class Resources {

    public static InputStream getResourceAsStream(String path) {
        return Resources.class.getClassLoader().getResourceAsStream(path);
    }
}
