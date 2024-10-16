package com.tomato.engine.web.sdk.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;

import java.io.IOException;

/**
 * 请求包装器:确保过滤器在一次请求的生命周期中只被调用一次
 *
 * @author lizhifu
 * @since 2024/10/16
 */
@Component
@Slf4j
public class RequestWrapperFilter extends OncePerRequestFilter {
    /**
     * 重写 doFilterInternal 方法，用于处理传入的 HttpServletRequest 和 HttpServletResponse 对象。
     *
     * @param request     HttpServletRequest 对象，包含了客户端的请求信息
     * @param response    HttpServletResponse 对象，用于向客户端发送响应信息
     * @param filterChain FilterChain 对象，用于调用链中的下一个过滤器
     * @throws ServletException 如果处理请求时发生 Servlet 异常，则抛出 ServletException
     * @throws IOException      如果处理请求时发生输入输出异常，则抛出 IOException
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        log.debug("请求包装器");
        ContentCachingRequestWrapper requestWrapper = new ContentCachingRequestWrapper(request);
        // 可以在这里处理请求数据
        byte[] body = requestWrapper.getContentAsByteArray();
        // 处理body，例如记录日志
        filterChain.doFilter(requestWrapper, response);
    }
}
