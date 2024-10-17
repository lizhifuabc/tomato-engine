package com.tomato.engine.easy.excel.sdk.convert;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.converters.ReadConverterContext;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.util.StringUtils;

import java.util.Date;

/**
 * 日期格式 date 转换
 *
 * @author lizhifu
 * @since 2024/10/17
 */
public class DateConverter implements Converter<Date> {
    @Override
    public Date convertToJavaData(ReadConverterContext<?> context) throws Exception {
        Class<?> aClass = context.getContentProperty().getField().getType();
        CellDataTypeEnum type = context.getReadCellData().getType();
        String stringValue = context.getReadCellData().getStringValue();
        if(aClass.equals(Date.class) && type.equals(CellDataTypeEnum.STRING)  && StringUtils.isBlank(stringValue)){
            return null;
        }
        return Converter.super.convertToJavaData(context);
    }
}
