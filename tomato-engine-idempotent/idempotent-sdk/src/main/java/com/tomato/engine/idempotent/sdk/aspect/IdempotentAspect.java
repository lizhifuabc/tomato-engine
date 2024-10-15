package com.tomato.engine.idempotent.sdk.aspect;

import com.tomato.engine.idempotent.sdk.annotation.Idempotent;
import com.tomato.engine.idempotent.sdk.exception.IdempotentException;
import com.tomato.engine.idempotent.sdk.properties.IdempotentProperties;
import com.tomato.engine.idempotent.sdk.service.KeyResolverService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.redisson.api.RMapCache;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 幂等拦截切面
 *
 * @author lizhifu
 * @since 2024/10/15
 */
@Slf4j
@Aspect
public class IdempotentAspect {

    private static final ThreadLocal<Map<String, Object>> THREAD_CACHE = ThreadLocal.withInitial(HashMap::new);

    @Autowired
    private IdempotentProperties idempotentProperties;

    @Autowired
    private RedissonClient redissonClient;

    @Autowired
    private KeyResolverService keyResolver;

    /**
     * 幂等拦截
     */
    @Pointcut("@annotation(com.tomato.engine.idempotent.sdk.annotation.Idempotent)")
    public void idempotent() {}

    @Before("idempotent()")
    public void before(JoinPoint joinPoint) {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes();
        if (requestAttributes == null) {
            log.error("幂等拦截，没有可用的HTTP请求上下文");
            throw new IdempotentException("没有可用的HTTP请求上下文");
        }

        HttpServletRequest request = requestAttributes.getRequest();

        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        if (!method.isAnnotationPresent(Idempotent.class)) {
            return;
        }
        Idempotent idempotent = method.getAnnotation(Idempotent.class);

        String key = StringUtils.hasLength(idempotent.key())
                ? keyResolver.resolver(request, idempotent, joinPoint)
                : keyResolver.resolverDefault(request, idempotent, joinPoint);;

        long expireTime = idempotent.expireTime();
        String msg = idempotent.msg();
        TimeUnit timeUnit = idempotent.timeUnit();
        boolean delKey = idempotent.delKey();

        // do not need check null
        RMapCache<String, Object> rMapCache = redissonClient.getMapCache(idempotentProperties.getRedisCacheKey());
        String value = LocalDateTime.now().toString().replace("T", " ");
        Object v1;
        if (null != rMapCache.get(key)) {
            // had stored
            throw new IdempotentException(msg);
        }
        // putIfAbsent 如果key不存在，就设置value，线程安全
        v1 = rMapCache.putIfAbsent(key, value, expireTime, timeUnit);
        if (null != v1) {
            throw new IdempotentException(msg);
        }
        else {
            log.info("幂等拦截，key={},value={},expireTime={}{},now={}", key, value, expireTime,timeUnit, LocalDateTime.now());
        }

        Map<String, Object> map = THREAD_CACHE.get();
        map.put(idempotentProperties.getKeyName(), key);
        map.put(idempotentProperties.getDelKeyName(), delKey);
    }

    @After("idempotent()")
    public void after() {
        try {
            Map<String, Object> map = THREAD_CACHE.get();
            if (CollectionUtils.isEmpty(map)) {
                return;
            }

            RMapCache<Object, Object> mapCache = redissonClient.getMapCache(idempotentProperties.getRedisCacheKey());
            if (mapCache.isEmpty()) {
                return;
            }

            String key = map.get(idempotentProperties.getKeyName()).toString();
            boolean delKey = (boolean) map.get(idempotentProperties.getDelKeyName());

            if (delKey) {
                mapCache.fastRemove(key);
                log.info("幂等拦截，删除key={}",key);
            }
        }finally {
            THREAD_CACHE.remove();
        }
    }
}
