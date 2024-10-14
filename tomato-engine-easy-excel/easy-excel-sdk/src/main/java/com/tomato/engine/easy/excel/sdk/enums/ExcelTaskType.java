package com.tomato.engine.easy.excel.sdk.enums;

import lombok.Getter;

/**
 * 操作类型
 *
 * @author lizhifu
 * @since 2024/9/30
 */
@Getter
public enum ExcelTaskType {
    /**
     * 操作类型
     */
    EXPORT(0, "导出"),
    IMPORT(1, "导入"),
    ;

    private final Integer code;
    private final String desc;

    ExcelTaskType(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
