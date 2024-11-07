package com.tomato.engine.mybatis.example.domain;

import cn.mybatis.mp.db.annotations.Table;
import cn.mybatis.mp.db.annotations.TableId;
import cn.mybatis.mp.db.annotations.TenantId;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * example
 *
 * @author lizhifu
 * @since 2024/10/18
 */
@Data
@Table("example")
public class Example {

    @TableId
    private Long id;

    private String name;

    private String code;

    private Integer age;

    private LocalDateTime createTime;

    @TenantId
    private Integer tenantId;
}
