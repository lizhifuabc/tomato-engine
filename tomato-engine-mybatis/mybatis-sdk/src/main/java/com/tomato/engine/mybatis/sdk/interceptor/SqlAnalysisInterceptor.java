package com.tomato.engine.mybatis.sdk.interceptor;

import com.tomato.engine.mybatis.sdk.explain.SqlExplain;
import com.tomato.engine.mybatis.sdk.explain.SqlExplainResult;
import com.tomato.engine.mybatis.sdk.extract.SqlExtract;
import com.tomato.engine.mybatis.sdk.extract.SqlExtractResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

import java.sql.Connection;
import java.util.List;
import java.util.Properties;

/**
 * sql 分析拦截器
 *
 * @author lizhifu
 * @since 2024/10/18
 */
@Intercepts({@Signature(
        type = StatementHandler.class,
        method = "prepare",
        args = {Connection.class, Integer.class}
), @Signature(
        type = Executor.class,
        method = "update",
        args = {MappedStatement.class, Object.class}
),@Signature(
        type = Executor.class,
        method = "query",
        args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class}
)})
@Slf4j
public class SqlAnalysisInterceptor implements Interceptor {
    /**
     * 拦截方法
     *
     * @param invocation 调用信息对象，包含了拦截方法的目标对象、方法、参数等信息
     * @return 拦截方法执行后的返回值
     * @throws Throwable 如果在拦截过程中发生异常，则抛出该异常
     */
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        try {
            // 获取第一个参数
            Object firstArg = invocation.getArgs()[0];
            // sql替换
            if (firstArg instanceof MappedStatement mappedStatement) {
                // 根据 id 进行 sql替换 TODO
                String id = mappedStatement.getId();
            }
            // sql 分析
            if(firstArg instanceof Connection){
                StatementHandler statementHandler = (StatementHandler)invocation.getTarget();
                Connection connection = (Connection)invocation.getArgs()[0];
                //提取待执行的完整sql语句
                SqlExtractResult sqlExtractResult = SqlExtract.extract(statementHandler);
                if (sqlExtractResult != null){
                    log.info("sql 分析拦截器 待执行sql:{}",sqlExtractResult.getSourceSql());
                    List<SqlExplainResult> explain = SqlExplain.explain(sqlExtractResult.getSourceSql(), connection);
                }
            }
        }catch (Exception e){
            log.error("sql 分析拦截器异常",e);
        }
        // 继续执行，即执行原方法
        return invocation.proceed();
    }

    /**
     * 重写plugin方法，用于将当前插件包装到目标对象上。
     *
     * @param target 目标对象，即需要被包装的对象。
     * @return 包装后的对象，其中包含了当前插件的功能。
     */
    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    /**
     * 重写 setProperties 方法，用于设置拦截器的属性。
     *
     * @param properties 需要设置的属性集合
     */
    @Override
    public void setProperties(Properties properties) {

    }
}
