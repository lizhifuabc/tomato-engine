package com.tomato.engine.jackson.sdk.annotation;

import com.fasterxml.jackson.annotation.JacksonAnnotationsInside;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.tomato.engine.jackson.sdk.serializer.DataMaskingSerializer;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 数据脱敏
 *
 * @author lizhifu
 * @since 2024/9/29
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@JacksonAnnotationsInside
@JsonSerialize(using = DataMaskingSerializer.class, nullsUsing = DataMaskingSerializer.class)
public @interface DataMasking {
    String type() default "";
}
