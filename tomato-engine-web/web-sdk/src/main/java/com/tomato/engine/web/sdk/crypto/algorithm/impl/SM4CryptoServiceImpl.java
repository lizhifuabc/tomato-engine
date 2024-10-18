package com.tomato.engine.web.sdk.crypto.algorithm.impl;

import com.tomato.engine.web.sdk.crypto.algorithm.CryptoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 国产 SM4 加密 和 解密
 * <p>
 * 1、国密SM4 要求秘钥为 128bit，转化字节为 16个字节；
 * <p>
 * 2、js前端使用 UCS-2 或者 UTF-16 编码，字母、数字、特殊符号等 占用1个字节；
 * <p>
 * 3、java中 每个 字母数字 也是占用1个字节；
 * <p>
 * 4、所以：前端和后端的 秘钥Key 组成为：字母、数字、特殊符号 一共16个即可
 *
 * @author lizhifu
 * @since 2024/10/18
 */
@Slf4j
@Service
public class SM4CryptoServiceImpl implements CryptoService {
    @Override
    public String decrypt(String data) {
        return "";
    }

    @Override
    public String encrypt(String data) {
        return "";
    }
}
