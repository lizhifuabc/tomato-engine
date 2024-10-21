package com.tomato.engine.mybatis.sdk.extract;

import com.tomato.engine.mybatis.sdk.util.MetaObjectUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.executor.statement.PreparedStatementHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.type.TypeHandlerRegistry;

import java.sql.Time;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;

/**
 * sql 提取
 *
 * @author lizhifu
 * @since 2024/10/18
 */
@Slf4j
public class SqlExtract {

    /**
     * 记录sqlId-check time
     */
    private static final ConcurrentHashMap<String,Long> CHECKED_ID_MAP = new ConcurrentHashMap<>();

    /**
     * 从给定的StatementHandler中提取SQL语句和相关信息，并返回提取结果
     *
     * @param statementHandler MyBatis的StatementHandler对象，包含执行SQL所需的信息
     * @return SqlExtractResult对象，包含提取的SQL语句和相关信息；如果提取失败，则返回null
     */
    public static SqlExtractResult extract(StatementHandler statementHandler){

        //MetaObjectUtil 通过mybatis 反射工具类，从入参提取相关对象
        //提取 PreparedStatementHandler ，用于提取 MappedStatement
        MetaObject delegateMetaObject = MetaObjectUtil.forObject(statementHandler);
        if(delegateMetaObject.getValue("delegate")==null){
            log.warn("sql 分析拦截器 get delegate null error");
            return null;
        }
        if(!(delegateMetaObject.getValue("delegate") instanceof PreparedStatementHandler preparedStatementHandler)){
            log.warn("sql 分析拦截器 get delegate is not PreparedStatementHandler");
            return null;
        }
        //提取 MappedStatement，用于组装完成带参数sql
        MetaObject metaObject = MetaObjectUtil.forObject(preparedStatementHandler);
        MappedStatement mappedStatement = (MappedStatement)metaObject.getValue("mappedStatement");

        // 获取到节点的id,即sql语句的id
        String sqlId = mappedStatement.getId();
        String sqlType = mappedStatement.getSqlCommandType().name();
        log.debug("sql 分析拦截器 sqlId ={},sqlType={} " ,sqlId,sqlType);

        //判断是否需要分析
        if(!needAnalysis(sqlId,sqlType)){
            return null;
        }
        //记录检查时间
        CHECKED_ID_MAP.put(sqlId,System.currentTimeMillis());

        // BoundSql就是封装myBatis最终产生的sql类
        Object parameterObject = statementHandler.getParameterHandler().getParameterObject();
        BoundSql boundSql = mappedStatement.getBoundSql(parameterObject);
        // 获取节点的配置
        Configuration cOnfiguration= mappedStatement.getConfiguration();
        // 获取到最终的sql语句
        String sql = showSql(cOnfiguration, boundSql);
        log.debug("sql 分析拦截器 sql ={} " , sql);

        SqlExtractResult result = new SqlExtractResult();
        result.setSqlId(sqlId);
        result.setSourceSql(sql);
        return result;
    }

    /**
     * 是否需要分析
     * @return true 是 ，false：否
     */
    private static boolean needAnalysis(String sqlId,String sqlType){
        // TODO
        return true;
    }




    /**
     * 展示SQL语句，包括参数替换
     *
     * @param configuration MyBatis配置信息
     * @param boundSql      已绑定的SQL语句对象
     * @return 替换参数后的SQL语句
     */
    private static String showSql(Configuration configuration, BoundSql boundSql) {
        // 获取参数
        Object parameterObject = boundSql.getParameterObject();
        List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
        // sql语句中多个空格都用一个空格代替
        String sql = boundSql.getSql().replaceAll("[\\s]+", " ");
        if (parameterMappings.isEmpty() || parameterObject == null){
            return sql;
        }
        // 获取类型处理器注册器，类型处理器的功能是进行java类型和数据库类型的转换
        TypeHandlerRegistry typeHandlerRegistry = configuration.getTypeHandlerRegistry();
        // 如果根据parameterObject.getClass(）可以找到对应的类型，则替换
        if (typeHandlerRegistry.hasTypeHandler(parameterObject.getClass())) {
            sql = sql.replaceFirst("\\?", Matcher.quoteReplacement(getParameterValue(parameterObject)));
            return sql;
        }
        // MetaObject主要是封装了originalObject对象，提供了get和set的方法用于获取和设置originalObject的属性值,主要支持对JavaBean、Collection、Map三种类型对象的操作
        MetaObject metaObject = configuration.newMetaObject(parameterObject);
        for (ParameterMapping parameterMapping : parameterMappings) {
            String propertyName = parameterMapping.getProperty();
            if (boundSql.hasAdditionalParameter(propertyName)) {
                // 动态sql
                Object obj = boundSql.getAdditionalParameter(propertyName);
                sql = sql.replaceFirst("\\?", Matcher.quoteReplacement(getParameterValue(obj)));
                continue;
            }
            if (metaObject.hasGetter(propertyName)) {
                Object obj = metaObject.getValue(propertyName);
                sql = sql.replaceFirst("\\?", Matcher.quoteReplacement(getParameterValue(obj)));
                continue;
            }
            // 未匹配到的参数
            sql = sql.replaceFirst("\\?", "缺失");
        }
        return sql;
    }

    /**
     * 根据传入的对象类型进行处理：
     * 如果对象是String类型，则在字符串两端添加单引号；
     * 如果对象是Date类型，则使用默认日期时间格式转换为字符串，并在结果两端添加单引号；
     * 如果对象是LocalDateTime类型，则转换为Timestamp类型并在结果两端添加单引号；
     * 如果对象是LocalDate类型，则转换为java.sql.Date类型并在结果两端添加单引号；
     * 如果对象是LocalTime类型，则转换为Time类型并在结果两端添加单引号；
     * 如果传入的对象为null，则返回空字符串；
     * 对于其他类型的对象，直接调用其toString()方法获取字符串表示。
     *
     * @param obj 需要处理的对象
     * @return 处理后的字符串结果
     */
    private static String getParameterValue(Object obj)
    {
        String value;
        if (obj instanceof String) {
            value = "'" + obj + "'";
        } else if (obj instanceof java.util.Date) {
            DateFormat formatter = DateFormat.getDateTimeInstance(DateFormat.DEFAULT, DateFormat.DEFAULT, Locale.CHINA);
            value = "'" + formatter.format(obj) + "'";
        }  else if (obj instanceof LocalDateTime) {
            value = "'" + Timestamp.valueOf((LocalDateTime) obj) + "'";
        } else if (obj instanceof LocalDate) {
            value = "'" + java.sql.Date.valueOf((LocalDate) obj) + "'";
        } else if (obj instanceof LocalTime) {
            value = "'" + Time.valueOf((LocalTime) obj) + "'";
        }  else{
            if (obj != null) {
                value = obj.toString();
            } else {
                value = "";
            }
        }
        return value;
    }
}
