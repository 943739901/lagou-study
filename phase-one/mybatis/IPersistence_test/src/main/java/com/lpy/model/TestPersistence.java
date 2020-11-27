package com.lpy.model;

import com.lpy.io.Resources;
import com.lpy.dao.IUserDao;
import com.lpy.sqlsession.SqlSession;
import com.lpy.sqlsession.SqlSessionFactory;
import com.lpy.sqlsession.SqlSessionFactoryBuilder;

import java.util.List;

/**
 * @author lipengyu
 */
public class TestPersistence {
    public static void main(String[] args) throws Exception {
        SqlSessionFactory build = new SqlSessionFactoryBuilder().build(Resources.getResourceAsStream("sqlMapConfig.xml"));
        SqlSession sqlSession = build.openSession();

        User param = new User();
        param.setId(1);
        param.setUsername("lucy");

//        List<User> users = sqlSession.selectList("user.selectList");
//        System.out.println(users);
//
//        User user = sqlSession.selectOne("user.selectOne", param);
//        System.out.println(user);

        IUserDao mapper = sqlSession.getMapper(IUserDao.class);
        User user1 = mapper.findByCondition(param);
        List<User> all = mapper.findAll();
        System.out.println(user1);
        System.out.println(all);
    }
}















