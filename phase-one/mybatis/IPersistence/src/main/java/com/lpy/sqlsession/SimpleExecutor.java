package com.lpy.sqlsession;

import com.lpy.config.BoundSql;
import com.lpy.pojo.Configuration;
import com.lpy.pojo.MappedStatement;
import com.lpy.sqlsession.Executor;
import com.lpy.utils.GenericTokenParser;
import com.lpy.utils.ParameterMapping;
import com.lpy.utils.ParameterMappingTokenHandler;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 简单执行器 jdbc实现
 * @author lipengyu
 */
public class SimpleExecutor implements Executor {

    /**
     * 查询
     */
    @Override
    public <E> List<E> query(Configuration configuration, MappedStatement mappedStatement, Object[] param) throws SQLException, NoSuchFieldException, IllegalAccessException, IntrospectionException, InstantiationException, InvocationTargetException {
        // 1. 注册驱动 获取连接
        Connection connection = configuration.getDataSource().getConnection();
        // 2. 获取sql语句：select * from user where id = #{id} and username = #{username}
        //    转换sql语句：select * from user where id = ? and username = ? , 转换的过程中，还需要对#{}里面的值进行解析存储
        String sql = mappedStatement.getSql();
        BoundSql boundSql = getBoundSql(sql);

        // 3. 获取预处理对象 preparedStatement
        PreparedStatement preparedStatement = connection.prepareStatement(boundSql.getBoundSql());

        // 4. 设置参数
        // 获取参数类型
        Class<?> parameterType = mappedStatement.getParameterType();
        List<ParameterMapping> parameterMappingList = boundSql.getParameterMappingList();
        for (int i = 0; i < parameterMappingList.size(); i++) {
            ParameterMapping parameterMapping = parameterMappingList.get(i);
            // 获取当前参数的名字
            String content = parameterMapping.getContent();
            // 通过反射找到该属性
            Field declaredField = parameterType.getDeclaredField(content);
            // 暴力访问
            declaredField.setAccessible(true);
            // param[0] 指对象，从该对象取出该属性的值
            Object o = declaredField.get(param[0]);
            // 设置参数
            preparedStatement.setObject(i + 1, o);
        }

        // 5. 执行sql
        ResultSet resultSet = preparedStatement.executeQuery();

        // 6. 封装返回结果集
        // 获取元数据
        ResultSetMetaData metaData = resultSet.getMetaData();
        // 获取列的数量
        int columnCount = metaData.getColumnCount();
        // 获取返回值类型
        Class<?> resultType = mappedStatement.getResultType();
        List<E> results = new ArrayList<>();
        while (resultSet.next()) {
            E e = (E) resultType.newInstance();
            for (int i = 1; i < columnCount + 1; i++) {
                String columnName = metaData.getColumnName(i);
                // 使用反射或者内省，根据数据库和实体相关的对应关系，完成封装。
                PropertyDescriptor propertyDescriptor = new PropertyDescriptor(columnName, resultType);
                Method writeMethod = propertyDescriptor.getWriteMethod();
                // 执行写方法
                writeMethod.invoke(e, resultSet.getObject(columnName));
            }
            results.add(e);
        }
        return results;
    }

    /**
     * 完成对#{}的解析工作：
     *  1. 将#{}使用?进行代替。
     *  2. 解析出#{}里面的值进行存储
     */
    private BoundSql getBoundSql(String sql) {
        // 标记处理类：配置标记解析器来完成对占位符的解析处理工作
        ParameterMappingTokenHandler handler = new ParameterMappingTokenHandler();
        GenericTokenParser parser = new GenericTokenParser("#{", "}", handler);
        // 解析出来的sql
        String parse = parser.parse(sql);
        // #{}里面解析出来的参数名称
        return new BoundSql(parse, handler.getParameterMappings());
    }
}
