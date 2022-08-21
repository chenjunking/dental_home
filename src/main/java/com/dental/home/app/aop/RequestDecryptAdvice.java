package com.dental.home.app.aop;

import cn.hutool.core.codec.Base64;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.RSA;
import cn.hutool.crypto.symmetric.AES;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dental.home.app.config.SecretConfig;
import com.dental.home.app.exception.ParamDecodeException;
import com.dental.home.app.utils.SecretUtil;
import com.dental.home.app.vo.result.SecretHttpMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdviceAdapter;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

//解密拦截器
@Slf4j
@ControllerAdvice
@ConditionalOnProperty(prefix = "secret", name = "enabled", havingValue = "true")
@EnableConfigurationProperties({SecretConfig.class})
public class RequestDecryptAdvice extends RequestBodyAdviceAdapter {

    @Autowired
    private SecretConfig secretConfig;

    @Override
    public HttpInputMessage beforeBodyRead(HttpInputMessage inputMessage, MethodParameter parameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) throws IOException {
        //如果有注解
        boolean supportSafeMessage = supportSecretRequest(parameter);
        if (supportSafeMessage) {
            String httpBody;
            InputStream encryptStream = inputMessage.getBody();
            String encryptBody = StreamUtils.copyToString(encryptStream, Charset.defaultCharset());
            try {
                //AES+RSA组合解密
                httpBody = combinationDecryptBody(encryptBody, SecretUtil.RSAPrivateKeyStrB);
            } catch (Exception e) {
                log.error("解密失败:" + encryptBody);
                throw new ParamDecodeException("登录超时，请重新登录");
            }
            //返回处理后的消息体给messageConvert
            return new SecretHttpMessage(new ByteArrayInputStream(httpBody.getBytes()), inputMessage.getHeaders());
        }
        return inputMessage;
    }

    /**
     * 是否支持加密消息体
     */
    private boolean supportSecretRequest(MethodParameter methodParameter) {
        if (!secretConfig.isScanAnnotation()) {
            return true;
        }
        //判断class是否存在注解
        if (methodParameter.getContainingClass().getAnnotation(secretConfig.getAnnotationClass()) != null) {
            return true;
        }
        //判断方法是否存在注解
        return methodParameter.getMethodAnnotation(secretConfig.getAnnotationClass()) != null;
    }

    @Override
    public boolean supports(MethodParameter methodParameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
    }

    /**
     * AES+RSA组合解密
     */
    private String combinationDecryptBody(String encryptBody, String priKeyStr) throws Exception {
        String original;
        JSONObject jsonObject = JSON.parseObject(encryptBody);
        String key = String.valueOf(jsonObject.get("key"));
        String content = String.valueOf(jsonObject.get("content"));

//        // 1.先使用RSA私钥解密出AESKey
//        String AESKey = SecretUtil.rsaPriDecrypt(key, priKeyStr);
//        // 2.使用AESKey解密内容
//        original = SecretUtil.aesDecrypt(content, AESKey);

        RSA rsa = SecureUtil.rsa(SecretUtil.RSAPrivateKeyStrB,null);
        key = new String(rsa.decrypt(key, KeyType.PrivateKey));
        AES aes = SecureUtil.aes(key.getBytes(StandardCharsets.UTF_8));
        original = aes.decryptStr(content);
        return original;
    }
}
