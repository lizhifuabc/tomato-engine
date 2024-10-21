package com.tomato.engine.mybatis.sdk.explain;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * sql 解析规则
 *
 * @author lizhifu
 * @since 2024/10/18
 */
@Slf4j
public class SqlExplainRule {

    public static void explainResult(List<SqlExplainResult> sqlExplainResults){
        sqlExplainResults.forEach(SqlExplainRule::explainResult);
    }

    public static void explainResult(SqlExplainResult sqlExplainResult){
        String type = sqlExplainResult.getType();
        String extra = sqlExplainResult.getExtra();
        Double filtered = sqlExplainResult.getFiltered();
        Long rows = sqlExplainResult.getRows();
        String result = typeResult(type) + "<>" + extraResult(extra) + "<>" + filteredResult(filtered) + "<>" + rowsResult(rows);
        sqlExplainResult.setResult(result);
        log.debug("sql 分析拦截器 执行 SqlExplainRule 结果:sqlId:{} {}",sqlExplainResult.getSqlExtractResult().getSqlId(),result);
    }

    public static String typeResult(String type) {
        if (!StringUtils.hasLength(type)){
            return "";
        }
        if (type.contains("ALL")){
            return "不走索引，建议创建索引";
        }
        return "";
    }

    public static String extraResult(String extra) {
        if (!StringUtils.hasLength(extra)){
            return "";
        }
        if (extra.contains("filesort")){
            return "排序不走索引，建议优化索引或者优化sql";
        }
        return "";
    }

    public static String filteredResult(Double filtered) {
        if (filtered == null){
            return "";
        }
        if (filtered < 60){
            return "索引过滤效果不佳，建议优化索引或者优化sql";
        }
        return "";
    }

    public static String rowsResult(Long rows) {
        if (rows == null){
            return "";
        }
        if (rows > 5000){
            return "遍历大于5000，建议优化索引或者优化sql";
        }

        if (rows < 50){
            return "遍历行数小于50，暂不需要优化";
        }
        return "";
    }
}
