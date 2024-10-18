package com.tomato.engine.web.sdk.crypto.adapter;

import com.tomato.engine.web.sdk.crypto.algorithm.CryptoService;
import com.tomato.engine.web.sdk.crypto.annotation.Decrypt;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdviceAdapter;

import java.io.InputStream;
import java.lang.reflect.Type;

/**
 * 解密
 *
 * @author lizhifu
 * @since 2024/10/18
 */
public class DecryptRequestBodyAdviceAdapter extends RequestBodyAdviceAdapter {


    @Override
    public boolean supports(MethodParameter methodParameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
        return false;
    }
}
