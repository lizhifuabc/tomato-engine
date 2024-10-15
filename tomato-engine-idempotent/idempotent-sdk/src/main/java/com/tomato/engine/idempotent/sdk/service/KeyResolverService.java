package com.tomato.engine.idempotent.sdk.service;

import com.tomato.engine.idempotent.sdk.annotation.Idempotent;
import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.JoinPoint;

/**
 * key 解析服务
 *
 * @author lizhifu
 * @since 2024/10/15
 */
public interface KeyResolverService {
    /**
     * el表达式解析
     *
     * @param idempotent 幂等注解
     * @param point      连接点
     * @param request HttpServletRequest对象，包含了客户端的请求信息
     * @return 解析得到的键值
     */
    String resolver(HttpServletRequest request,Idempotent idempotent, JoinPoint point);


    /**
     * url + 参数列表作为区分
     *
     * @param request HttpServletRequest对象，包含了客户端的请求信息
     * @param idempotent 幂等注解
     * @param point 连接点
     * @return 解析出的键值
     */
    String resolverDefault(HttpServletRequest request,Idempotent idempotent, JoinPoint point);
}
