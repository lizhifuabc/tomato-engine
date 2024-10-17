package com.tomato.engine.web.sdk.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * xss 请求包装器
 *
 * @author lizhifu
 * @since 2024/10/17
 */
public class XssHttpServletRequestWrapper extends HttpServletRequestWrapper {

    public XssHttpServletRequestWrapper(HttpServletRequest request) {
        super(request);
    }

    /**
     * 重写 getHeader 方法，根据给定的头部名称获取对应的头部值，并进行清洗。
     *
     * @param name 需要获取的头部名称
     * @return 清洗后的头部值，如果未找到对应的头部则返回原始头部值
     */
    @Override
    public String getHeader(String name) {
        return cleaning(super.getHeader(name));
    }

    /**
     * 重写 getParameter 方法，根据参数名称获取参数值，并对获取到的值进行清洗。
     *
     * @param name 参数名称
     * @return 清洗后的参数值，如果没有找到指定名称的参数，则返回原始参数值
     */
    @Override
    public String getParameter(String name) {
        return cleaning(super.getParameter(name));
    }

    /**
     * 重写 getParameterValues 方法，获取指定名称的参数的所有值，并对获取到的值进行清洗。
     *
     * @param name 参数名称
     * @return 清洗后的参数值数组，如果没有找到指定名称的参数，则返回原始参数值数组
     */
    @Override
    public String[] getParameterValues(String name) {
        return cleaning(super.getParameterValues(name));
    }

    /**
     * 获取请求参数映射。
     *
     * @return 请求参数映射，其中键为参数名，值为参数值的字符串数组。如果参数映射为空或不存在，则直接返回null。
     *         如果参数映射存在，则对参数值进行清洗。
     */
    @Override
    public Map<String, String[]> getParameterMap() {
        Map<String, String[]> parameterMap = super.getParameterMap();
        // 如果为空，则直接返回，否则进行清洗
        if (parameterMap == null || parameterMap.isEmpty()){
            return parameterMap;
        }
        return parameterMap.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, entry -> cleaning(entry.getValue())));
    }

    /**
     * 清洗字符串数组中的每个元素
     *
     * @param parameters 待清洗的字符串数组
     * @return 清洗后的字符串数组
     */
    private String[] cleaning(String[] parameters) {
        // 如果为空，则直接返回，否则进行清洗
        if (parameters == null || parameters.length == 0){
            return parameters;
        }
        List<String> cleanParameters = Arrays.stream(parameters).map(this::cleaning).toList();
        String[] results = new String[cleanParameters.size()];
        return cleanParameters.toArray(results);
    }

    /**
     * 清洗字符串
     *
     * @param value 待清洗的字符串
     * @return 清洗后的字符串
     */
    private String cleaning(String value) {
        // 如果为空，则直接返回，否则进行清洗
        if (StringUtils.hasLength(value)){
            // TODO 清理
            return value;
        }
        return value;
    }
}
