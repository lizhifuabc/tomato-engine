package com.tomato.engine.mybatis.sdk.replace;

/**
 * sql 替换服务
 *
 * @author lizhifu
 * @since 2024/10/18
 */
public interface SqlReplaceService {
    /**
     * 替换 sql
     *
     * @param sql sql
     * @return 替换后的 sql
     */
    String replace(String sql);
}
