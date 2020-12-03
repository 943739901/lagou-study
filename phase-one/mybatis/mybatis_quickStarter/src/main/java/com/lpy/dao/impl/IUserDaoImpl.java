package com.lpy.dao.impl;

import com.lpy.dao.IUserDao;
import com.lpy.model.User;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.util.List;

/**
 * @author lipengyu
 */
public class IUserDaoImpl implements IUserDao {

    @Override
    public List<User> findAll() throws IOException {
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(Resources.getResourceAsStream("sqlMapConfig.xml"));
        SqlSession sqlSession = sqlSessionFactory.openSession(true);
        return sqlSession.selectList("userMapper.findAll");
    }
}
