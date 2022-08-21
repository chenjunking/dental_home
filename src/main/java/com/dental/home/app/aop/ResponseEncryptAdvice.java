package com.dental.home.app.aop;

import cn.hutool.core.codec.Base64;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.RSA;
import cn.hutool.crypto.symmetric.AES;
import com.alibaba.fastjson.JSON;
import com.dental.home.app.config.SecretConfig;
import com.dental.home.app.exception.ResultEncryptException;
import com.dental.home.app.utils.CommonUtil;
import com.dental.home.app.utils.SecretUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

//加密拦截器
@Slf4j
@ControllerAdvice
@ConditionalOnProperty(prefix = "secret", name = "enabled", havingValue = "true")
@EnableConfigurationProperties({SecretConfig.class})
public class ResponseEncryptAdvice implements ResponseBodyAdvice<Object> {

    @Autowired
    private SecretConfig secretConfig;

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter methodParameter, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        //如果有注解
        boolean supportSafeMessage = supportSecretRequest(methodParameter);
        if (supportSafeMessage) {
            try {
                KeyGenerator kg = KeyGenerator.getInstance("AES");
                String key = Base64.encode(kg.generateKey().getEncoded());
                AES aes = SecureUtil.aes(key.getBytes(StandardCharsets.UTF_8));
                String srcData = JSON.toJSONString(body);
                srcData = Base64.encode(aes.encrypt(srcData.getBytes(StandardCharsets.UTF_8)));
                Map<String,String> resultMap = new HashMap<>();

                RSA rsa = SecureUtil.rsa(null,SecretUtil.RSAPublicKeyStrA);
                key = Base64.encode(rsa.encrypt(key, KeyType.PublicKey));
                resultMap.put("key",key);
                resultMap.put("content",srcData);


//                String key = SecretUtil.genAesSecret(192);
//                String srcData = JSON.toJSONString(body);
//                srcData = SecretUtil.aesEncrypt(srcData,key);
//                Map<String,String> resultMap = new HashMap<>();
//                key = SecretUtil.rsaPubEncrypt(key,SecretUtil.RSAPublicKeyStrA);
//                resultMap.put("key",key);
//                resultMap.put("content",srcData);

                //可以在头部中加入标记告诉客户端此接口是密文返回
                response.getHeaders().setAccessControlExposeHeaders(Arrays.asList("encrypt"));
                response.getHeaders().add("encrypt", "true");
                return resultMap;
            } catch (Exception e) {
                log.error("加密失败!",e);
                throw new ResultEncryptException("返回参数加密异常！");
            }
        }
        return body;
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
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
    }
}