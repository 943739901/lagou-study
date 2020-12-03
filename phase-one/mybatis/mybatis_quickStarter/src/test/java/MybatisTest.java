import com.lpy.dao.IUserDao;
import com.lpy.dao.impl.IUserDaoImpl;
import com.lpy.mapper.IUserMapper;
import com.lpy.model.User;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

/**
 * @author lipengyu
 */
public class MybatisTest {

    SqlSession sqlSession = null;

    @Before
    public void init() throws IOException {
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(Resources.getResourceAsStream("sqlMapConfig.xml"));
        // 传入true参数 自动提交
        sqlSession = sqlSessionFactory.openSession(true);
    }

    @Test
    public void selectList() {
        List<User> users = sqlSession.selectList("userMapper.findAll");
        users.forEach(System.out::println);
    }

    @Test
    public void insert() {
        User user = new User();
        user.setUsername("lpy");
        user.setPassword("123");
        user.setBirthday("2020-12-03");
        sqlSession.insert("userMapper.insertOne", user);
        System.out.println(user);
        List<User> users = sqlSession.selectList("userMapper.findOneByCondition", user);
        users.forEach(System.out::println);
    }

    @Test
    public void update() {
        List<User> users = sqlSession.selectList("userMapper.findAll");
        Integer id = users.get(0).getId();
        User param = new User();
        param.setId(id);
        param.setBirthday("2020-12-02");
        sqlSession.update("userMapper.updateOne", param);
        User user = sqlSession.selectOne("userMapper.findById", id);
        System.out.println(user);
    }

    @Test
    public void delete() {
        List<User> users = sqlSession.selectList("userMapper.findAll");
        Integer id = users.get(users.size() - 1).getId();
        sqlSession.delete("userMapper.deleteById", id);
        User user = sqlSession.selectOne("userMapper.findById", id);
        System.out.println(user);
    }

    @Test
    public void traditional() throws IOException {
        IUserDao iUserDao = new IUserDaoImpl();
        System.out.println(iUserDao.findAll());
    }

    @Test
    public void mapper() {
        IUserMapper mapper = sqlSession.getMapper(IUserMapper.class);
        System.out.println(mapper.findAll());
    }
























}
