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
 * @author lipengyu
 */
public class SimpleExecutor implements Executor {

    /**
     * 查询
     * @param configuration
     * @param mappedStatement
     * @param param
     * @param <E>
     * @return
     * @throws SQLException
     * @throws NoSuchFieldException
     * @throws IllegalAccessException
     * @throws IntrospectionException
     * @throws InstantiationException
     * @throws InvocationTargetException
     */
    @Override
    public <E> List<E> query(Configuration configuration, MappedStatement mappedStatement, Object[] param) throws SQLException, NoSuchFieldException, IllegalAccessException, IntrospectionException, InstantiationException, InvocationTargetException {
        // 获取链接
        Connection connection = configuration.getDataSource().getConnection();
        String sql = mappedStatement.getSql();
        // 将原sql转为带?的sql，并设置参数列表
        BoundSql boundSql = getBoundSql(sql);
        List<ParameterMapping> parameterMappingList = boundSql.getParameterMappingList();
        // 获取参数类型
        Class<?> parameterType = mappedStatement.getParameterType();

        PreparedStatement preparedStatement = connection.prepareStatement(boundSql.getBoundSql());
        for (int i = 0; i < parameterMappingList.size(); i++) {
            ParameterMapping parameterMapping = parameterMappingList.get(i);
            // 获取当前参数的名字
            String content = parameterMapping.getContent();
            // 找到该属性
            Field declaredField = parameterType.getDeclaredField(content);
            declaredField.setAccessible(true);
            // param[0] 指对象，从该对象取出该属性的值
            Object o = declaredField.get(param[0]);
            // 设置参数
            preparedStatement.setObject(i + 1, o);
        }
        ResultSet resultSet = preparedStatement.executeQuery();
        Class<?> resultType = mappedStatement.getResultType();
        List<E> results = new ArrayList<>();
        ResultSetMetaData metaData = resultSet.getMetaData();
        int columnCount = metaData.getColumnCount();
        while (resultSet.next()) {
            E e = (E) resultType.newInstance();
            for (int i = 1; i < columnCount + 1; i++) {
                String columnName = metaData.getColumnName(i);
                PropertyDescriptor propertyDescriptor = new PropertyDescriptor(columnName, resultType);
                Method writeMethod = propertyDescriptor.getWriteMethod();
                writeMethod.invoke(e, resultSet.getObject(columnName));
            }
            results.add(e);
        }
        return results;
    }

    /**
     * 获取jdbc可识别带？的sql
     * @param sql
     * @return
     */
    private BoundSql getBoundSql(String sql) {
        ParameterMappingTokenHandler handler = new ParameterMappingTokenHandler();
        GenericTokenParser parser = new GenericTokenParser("#{", "}", handler);
        String parse = parser.parse(sql);
        return new BoundSql(parse, handler.getParameterMappings());
    }

    @Override
    public void close(Configuration configuration) throws SQLException {
        configuration.getDataSource().getConnection().close();
    }
}
