package com.lpy.io;

import java.io.InputStream;

/**
 * @author lipengyu
 * 获取资源类
 */
public class Resources {

    /**
     * 根据配置文件的路径，将配置文件加载成字节输入流，存储在内存中
     */
    public static InputStream getResourceAsStream(String path) {
        return Resources.class.getClassLoader().getResourceAsStream(path);
    }
}
