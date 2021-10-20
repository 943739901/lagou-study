package com.lpy.sqlsession;

/**
 * SqlSession工厂
 * @author lipengyu
 */
public interface SqlSessionFactory {

    SqlSession openSession();
}
