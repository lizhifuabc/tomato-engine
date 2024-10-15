package com.tomato.engine.idempotent.sdk.service.impl;

import com.tomato.engine.idempotent.sdk.annotation.Idempotent;
import com.tomato.engine.idempotent.sdk.exception.IdempotentException;
import com.tomato.engine.idempotent.sdk.service.KeyResolverService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.StandardReflectionParameterNameDiscoverer;
import org.springframework.expression.Expression;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.util.StringUtils;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


/**
 * key 解析服务
 *
 * @author lizhifu
 * @since 2024/10/15
 */
@Slf4j
public class DefaultKeyResolverServiceImpl implements KeyResolverService {

    /**
     * 缓存 MethodSignature
     */
    private final Map<String, Method> methodCache = new ConcurrentHashMap<>();

    private static final SpelExpressionParser PARSER = new SpelExpressionParser();

    private static final StandardReflectionParameterNameDiscoverer DISCOVERER = new StandardReflectionParameterNameDiscoverer();

    @Override
    public String resolver(HttpServletRequest request,Idempotent idempotent, JoinPoint point) {
        // 获取方法参数
        Object[] arguments = point.getArgs();
        // 获取方法参数名
        String[] params = DISCOVERER.getParameterNames(getMethod(point));
        // 创建解析上下文
        StandardEvaluationContext context = new StandardEvaluationContext();

        if (params != null && params.length > 0) {
            for (int len = 0; len < params.length; len++) {
                context.setVariable(params[len], arguments[len]);
            }
        }

        Expression expression = PARSER.parseExpression(idempotent.key());
        String key = expression.getValue(context, String.class);
        if (!StringUtils.hasLength(key)){
            log.error("el表达式key不能为空");
            throw new IdempotentException("el表达式key不能为空");
        }
        return key;
    }

    @Override
    public String resolverDefault(HttpServletRequest request,Idempotent idempotent, JoinPoint point) {
        String url = request.getRequestURL().toString();
        String argString = Arrays.asList(point.getArgs()).toString();
        String key = url + argString;
        if (!StringUtils.hasLength(key)){
            return request.getRequestURL().toString();
        }
        return key;
    }

    /**
     * 获取连接点对应的方法
     *
     * @param joinPoint 连接点
     * @return 方法对象
     * @throws RuntimeException 如果在获取方法时发生异常，则抛出运行时异常
     */
    private Method getMethod(JoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        // 构建唯一标识符，包含方法名和参数类型
        String methodKey = getMethodKey(signature);

        return methodCache.computeIfAbsent(methodKey, key -> {
            Method method = signature.getMethod();
            if (method.getDeclaringClass().isInterface()) {
                try {
                    method = joinPoint.getTarget().getClass()
                            .getDeclaredMethod(signature.getName(), method.getParameterTypes());
                } catch (SecurityException | NoSuchMethodException e) {
                    log.error("Failed to get method from joinPoint: {}", joinPoint, e);
                    throw new RuntimeException("Failed to retrieve method due to: " + e.getMessage(), e);
                }
            }
            return method;
        });
    }

    /**
     * 根据方法签名生成缓存键，唯一标识方法
     *
     * @param signature 方法签名
     * @return 生成的缓存键
     */
    private String getMethodKey(MethodSignature signature) {
        StringBuilder keyBuilder = new StringBuilder(signature.getName());
        for (Class<?> parameterType : signature.getParameterTypes()) {
            keyBuilder.append("#").append(parameterType.getName());
        }
        return keyBuilder.toString();
    }
}
