package com.tomato.engine.easy.excel.sdk.util;

import com.alibaba.excel.EasyExcelFactory;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.exception.ExcelAnalysisException;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.excel.write.builder.ExcelWriterBuilder;
import com.alibaba.excel.write.metadata.style.WriteCellStyle;
import com.alibaba.excel.write.metadata.style.WriteFont;
import com.alibaba.excel.write.style.HorizontalCellStyleStrategy;
import com.tomato.engine.easy.excel.sdk.oss.OssService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * excel工具类
 *
 * @author lizhifu
 * @since 2024/9/30
 */
@Slf4j
public class ExcelUtil {


    /**
     * 旧版Excel文件（.xls）的MIME类型
     */
    private static final String XLS_CONTENT_TYPE = "application/vnd.ms-excel";

    /**
     * 新版Excel文件（.xlsx）的MIME类型
     */
    private static final String XLSX_CONTENT_TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";

    /**
     * CSV文件的MIME类型
     */
    private static final String CSV_CONTENT_TYPE = "text/csv";


    /**
     * 导出Excel
     * @param clazz 类
     * @param excelData 数据
     * @param fileName 文件名
     * @param excelType 文件类型
     * @param response 响应
     * @throws IOException 异常
     */
    public static void exportExcel(Class<?> clazz, List<?> excelData, String fileName, ExcelTypeEnum excelType, HttpServletResponse response) throws IOException {
        HorizontalCellStyleStrategy styleStrategy = setCellStyle();
        setResponse(fileName, excelType, response);
        ExcelWriterBuilder writeWork = EasyExcelFactory.write(response.getOutputStream(), clazz);
        writeWork.registerWriteHandler(styleStrategy).excelType(excelType).sheet().doWrite(excelData);
    }

    /**
     * 初始化导出填充
     * @param fileName 文件名
     * @param excelType 文件类型
     * @param templatePath 模板路径
     * @param response 响应
     * @return ExcelWriter 填充写入器
     * @throws IOException 异常
     */
    public static ExcelWriter initExportFillWriter(String fileName, ExcelTypeEnum excelType, String templatePath, HttpServletResponse response) throws IOException {
        setResponse(fileName, excelType, response);
        return EasyExcelFactory.write(response.getOutputStream())
                .excelType(excelType)
                .withTemplate(templatePath).build();
    }


    /**
     * 校验Excel模板
     * @param headMap 模板头
     * @param clazz 类
     * @param fields 字段
     */
    public static void validateExcelTemplate(Map<Integer, String> headMap, Class<?> clazz, Field[] fields) {
        Collection<String> headNames = headMap.values();
        // 类上是否存在忽略excel字段的注解
        boolean classIgnore = clazz.isAnnotationPresent(ExcelIgnoreUnannotated.class);
        int count = 0;
        for (Field field : fields) {
            // 忽略序列化ID字段
            if ("serialVersionUID".equals(field.getName())) {
                continue;
            }
            // 如果字段上存在忽略注解，则跳过当前字段
            if (field.isAnnotationPresent(ExcelIgnore.class)) {
                continue;
            }

            ExcelProperty excelProperty = field.getAnnotation(ExcelProperty.class);
            if (null == excelProperty) {
                // 如果类上也存在忽略注解，则跳过所有未使用ExcelProperty注解的字段
                if (classIgnore) {
                    continue;
                }
                // 如果检测到既未忽略、又未映射excel列的字段，则抛出异常提示模板不正确
                throw new ExcelAnalysisException("请检查导入的excel文件是否按模板填写!");
            }

            // 校验数据模型类上绑定的名称，是否与excel列名匹配
            String[] value = excelProperty.value();
            String name = value[0];
            if (name != null && !name.isEmpty() && !headNames.contains(name)) {
                throw new ExcelAnalysisException("请检查导入的excel文件是否按模板填写!");
            }
            // 更新有效字段的数量
            count++;
        }
        // 最后校验数据模型类的有效字段数量，与读到的excel列数量是否匹配
        if (headMap.size() != count) {
            throw new ExcelAnalysisException("请检查导入的excel文件是否按模板填写!");
        }
    }

    /**
     * 导出Excel到OSS
     * @param clazz 类
     * @param excelData 数据
     * @param fileName 文件名
     * @param excelType 文件类型
     * @return url 地址
     * @throws IOException 异常
     */
    public static String exportExcelToOss(Class<?> clazz, List<?> excelData, String fileName, ExcelTypeEnum excelType,OssService ossService) throws IOException {
        HorizontalCellStyleStrategy styleStrategy = ExcelUtil.setCellStyle();
        fileName = fileName + excelType.getValue();
        File excelFile = File.createTempFile(fileName, excelType.getValue());
        EasyExcelFactory.write(excelFile, clazz).registerWriteHandler(styleStrategy).sheet().doWrite(excelData);
        String url = ossService.upload(excelFile);

        if (excelFile.exists()) {
            boolean flag = excelFile.delete();
            log.info("删除临时文件结果：{}，fileName：{}", flag , fileName);
        }
        return url;
    }

    /**
     * 设置响应
     * @param fileName 文件名
     * @param excelType 文件类型
     * @param response 响应
     */
    public static void setResponse(String fileName, ExcelTypeEnum excelType, HttpServletResponse response) {
        // 对文件名进行UTF-8编码、拼接文件后缀名
        fileName = URLEncoder.encode(fileName, StandardCharsets.UTF_8).replaceAll("\\+", "%20") + excelType.getValue();
        switch (excelType) {
            case XLS:
                response.setContentType(XLS_CONTENT_TYPE);
                break;
            case XLSX:
                response.setContentType(XLSX_CONTENT_TYPE);
                break;
            case CSV:
                response.setContentType(CSV_CONTENT_TYPE);
                break;
            default:
                throw new RuntimeException("不支持的文件类型");
        }
        response.setCharacterEncoding("utf-8");
        response.setHeader("Content-Disposition", "attachment;filename*=utf-8''" + fileName);
    }

    /**
     * 设置样式
     * @return HorizontalCellStyleStrategy 样式
     */
    public static HorizontalCellStyleStrategy setCellStyle(){
        // 设置表头的样式（背景颜色、字体、居中显示）
        WriteCellStyle headStyle = new WriteCellStyle();
        //设置表头的背景颜色
        headStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        WriteFont headFont = new WriteFont();
        headFont.setFontHeightInPoints((short)12);
        headFont.setBold(true);
        headStyle.setWriteFont(headFont);
        headStyle.setHorizontalAlignment(HorizontalAlignment.CENTER);

        // 设置Excel内容策略(水平居中)
        WriteCellStyle cellStyle = new WriteCellStyle();
        cellStyle.setHorizontalAlignment(HorizontalAlignment.CENTER);
        return new HorizontalCellStyleStrategy(headStyle, cellStyle);
    }
}
