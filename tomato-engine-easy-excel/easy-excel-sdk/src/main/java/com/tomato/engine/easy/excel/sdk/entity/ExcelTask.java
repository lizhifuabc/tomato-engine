package com.tomato.engine.easy.excel.sdk.entity;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * excel 任务
 *
 * @author lizhifu
 * @since 2024/9/30
 */
@Data
public class ExcelTask {
    /**
     * 任务ID
     */
    private Long taskId;

    /**
     * 任务类型，0：导出，1：导入
     */
    private Integer taskType;

    /**
     * 处理状态，0：待处理，1：处理中，2：成功，3：失败
     */
    private Integer handleStatus;

    /**
     * excel链接
     */
    private String excelUrl;

    /**
     * 链路ID
     */
    private String traceId;

    /**
     * 请求参数
     */
    private String requestParams;

    /**
     * 异常类型
     */
    private String exceptionType;

    /**
     * 异常描述
     */
    private String errorMsg;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
}
