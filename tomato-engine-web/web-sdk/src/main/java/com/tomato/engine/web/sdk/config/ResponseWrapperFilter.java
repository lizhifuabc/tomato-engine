package com.tomato.engine.web.sdk.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.io.IOException;

/**
 * 响应包装器:确保过滤器在一次请求的生命周期中只被调用一次，以确保响应数据被正确处理。
 *
 * @author lizhifu
 * @since 2024/10/16
 */
@Slf4j
@Component
public class ResponseWrapperFilter extends OncePerRequestFilter {
    /**
     * 重写doFilterInternal方法，用于处理HTTP请求和响应。
     *
     * @param request   HttpServletRequest对象，包含客户端发送的请求信息
     * @param response  HttpServletResponse对象，用于向客户端发送响应信息
     * @param filterChain FilterChain对象，用于调用链中的下一个过滤器
     * @throws ServletException 如果处理请求时发生Servlet异常，则抛出ServletException
     * @throws IOException      如果处理请求时发生输入输出异常，则抛出IOException
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // 使用日志记录方法的入口
        log.debug("响应包装器");

        // 创建一个响应包装器，用于缓存响应内容
        ContentCachingResponseWrapper responseWrapper = new ContentCachingResponseWrapper(response);

        // 调用过滤器链中的下一个过滤器
        filterChain.doFilter(request, responseWrapper);

        // 可以在这里处理响应数据
        byte[] body = responseWrapper.getContentAsByteArray();
        // 处理body，例如添加签名
        responseWrapper.setHeader("X-Signature", "some-signature");

        // 必须调用此方法以将响应数据发送到客户端
        responseWrapper.copyBodyToResponse();
    }
}
