package com.tomato.engine.web.sdk.crypto.algorithm.impl;

import com.tomato.engine.web.sdk.crypto.algorithm.CryptoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * AES 加密和解密
 * <p>
 * 1、AES加密算法支持三种密钥长度：128位、192位和256位，这里选择128位
 * <p>
 * 2、AES 要求秘钥为 128bit，转化字节为 16个字节；
 * <p>
 * 3、js前端使用 UCS-2 或者 UTF-16 编码，字母、数字、特殊符号等 占用1个字节；
 * <p>
 * 4、所以：秘钥Key 组成为：字母、数字、特殊符号 一共16个即可
 * <p>
 * @author lizhifu
 * @since 2024/10/18
 */
@Slf4j
@Service
public class AESCryptoServiceImpl implements CryptoService {
    @Override
    public String decrypt(String data) {
        return "";
    }

    @Override
    public String encrypt(String data) {
        return "";
    }
}
