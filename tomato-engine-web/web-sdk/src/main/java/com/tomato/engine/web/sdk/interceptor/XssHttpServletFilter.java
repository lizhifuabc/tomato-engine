package com.tomato.engine.web.sdk.interceptor;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * Xss 过滤器
 *
 * @author lizhifu
 * @since 2024/10/17
 */
@Component
@Slf4j
public class XssHttpServletFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;

        XssHttpServletRequestWrapper xssRequest = new XssHttpServletRequestWrapper(request);
        log.debug("Xss 过滤器 request uri:{}", request.getRequestURI());
        filterChain.doFilter(xssRequest, servletResponse);
    }
}
