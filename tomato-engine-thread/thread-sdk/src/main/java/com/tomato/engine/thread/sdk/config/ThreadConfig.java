package com.tomato.engine.thread.sdk.config;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.web.embedded.tomcat.TomcatProtocolHandlerCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.core.task.support.TaskExecutorAdapter;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 配置虚拟线程池以支持高并发任务
 *
 * @author lizhifu
 * @since 2024/10/16
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnProperty(name = "spring.threads.virtual.enabled", havingValue = "true")
public class ThreadConfig implements DisposableBean {

    private final ExecutorService executorService;

    public ThreadConfig() {
        // 由于虚拟线程的特性，能调的参数可能只有平台线程的数量这一个参数，
        // 但是其默认的平台线程的数量已经是最佳值了。用户只需在配置文件中声明此executor就行了，不配置其他参数则为虚拟线程的默认参数
        this.executorService = Executors.newVirtualThreadPerTaskExecutor();
    }


    /**
     * 创建一个虚拟线程池，每个任务使用一个虚拟线程。
     *
     * @return ExecutorService
     */
    @Bean
    public ExecutorService virtualThreadPerTaskExecutor() {
        return executorService;
    }

    /**
     * 将虚拟线程池适配为 Spring 的 AsyncTaskExecutor。
     *
     * @param virtualThreadPerTaskExecutor 虚拟线程池
     * @return AsyncTaskExecutor
     */
    @Bean
    public AsyncTaskExecutor applicationTaskExecutor(ExecutorService virtualThreadPerTaskExecutor) {
        return new TaskExecutorAdapter(virtualThreadPerTaskExecutor);
    }

    /**
     * 配置 Tomcat 协议处理器使用虚拟线程池。
     *
     * @param virtualThreadPerTaskExecutor 虚拟线程池
     * @return TomcatProtocolHandlerCustomizer
     */
    @Bean
    public TomcatProtocolHandlerCustomizer<?> protocolHandlerCustomizer(ExecutorService virtualThreadPerTaskExecutor) {
        return protocolHandler -> protocolHandler.setExecutor(virtualThreadPerTaskExecutor);
    }

    @Override
    public void destroy() throws Exception {
        // 关闭线程池
        executorService.shutdown();
        // 虚拟线程在 JVM 关闭时会自动清理，所以不需要特别处理
    }
}
