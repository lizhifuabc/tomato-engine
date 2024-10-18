package com.tomato.engine.mybatis.sdk.replace;

/**
 * sql 替换服务
 *
 * @author lizhifu
 * @since 2024/10/18
 */
public interface SqlReplaceService {

    /**
     * 获取替换sql
     * @param sqlId sqlId
     * @return 替换后的sql
     */
    String replace(String sqlId);
}
