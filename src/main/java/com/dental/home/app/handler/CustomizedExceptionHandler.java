package com.dental.home.app.handler;

import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.servlet.ServletUtil;
import com.dental.home.app.aop.SecretBody;
import com.dental.home.app.exception.NotLoginException;
import com.dental.home.app.exception.ParamDecodeException;
import com.dental.home.app.exception.ResultEncryptException;
import com.dental.home.app.vo.result.HttpResult;
import com.dental.home.app.vo.result.HttpStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 自定义异常处理
 */
@Slf4j
@RestControllerAdvice
public class CustomizedExceptionHandler {

    /**
     * 没有登录异常处理
     * @return
     */
    @ExceptionHandler(NotLoginException.class)
    @ResponseBody
    @SecretBody
    public Object mpNotLoginExceptionHandler(){
        return HttpResult.error(HttpStatus.SC_UNAUTHORIZED,"请先登录！");
    }

    /**
     * 参数解析异常
     * @return
     */
    @ExceptionHandler(ParamDecodeException.class)
    @ResponseBody
    @SecretBody
    public Object paramDecodeExceptionHandler(){
        return HttpResult.error(HttpStatus.SC_UNAUTHORIZED,"参数解析异常！");
    }

    /**
     * 返回参数加密异常
     * @return
     */
    @ExceptionHandler(ResultEncryptException.class)
    @ResponseBody
    @SecretBody
    public Object resultEncryptExceptionHandler(){
        return HttpResult.error(HttpStatus.SC_UNAUTHORIZED,"返回参数加密异常！");
    }


    /**
     * 请求参数缺失异常
     */
    @ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseBody
    @SecretBody
    public Object missParamException(MissingServletRequestParameterException e) {
        String parameterType = e.getParameterType();
        String parameterName = e.getParameterName();
        String message = StrUtil.format("缺少请求的参数{}，类型为{}", parameterName, parameterType);
        return HttpResult.error(HttpStatus.SC_INTERNAL_SERVER_ERROR, message);
    }

    /**
     * 拦截参数格式传递异常
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseBody
    @SecretBody
    public Object httpMessageNotReadable(HttpMessageNotReadableException e) {
        return HttpResult.error(HttpStatus.SC_INTERNAL_SERVER_ERROR,"请求JSON参数格式不正确，请检查参数格式");
    }


    /**
     * 拦截不支持媒体类型异常
     */
    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    @ResponseBody
    @SecretBody
    public Object httpMediaTypeNotSupport(HttpMediaTypeNotSupportedException e) {
        return HttpResult.error(HttpStatus.SC_INTERNAL_SERVER_ERROR,"参数传递格式不支持，请使用JSON格式传参");
    }

    /**
     * 拦截请求方法的异常
     *
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseBody
    @SecretBody
    public Object methodNotSupport(HttpServletRequest request) {
        if (ServletUtil.isPostMethod(request)) {
            return HttpResult.error(HttpStatus.SC_INTERNAL_SERVER_ERROR,"不支持该请求方法，请求方法应为GET");
        }
        if (ServletUtil.isGetMethod(request)) {
            return HttpResult.error(HttpStatus.SC_INTERNAL_SERVER_ERROR,"不支持该请求方法，请求方法应为GET");
        }
        return null;
    }

    /**
     * 拦截资源找不到的运行时异常
     *
     */
    @ExceptionHandler(NoHandlerFoundException.class)
    @ResponseBody
    @SecretBody
    public Object notFound(NoHandlerFoundException e) {
        return HttpResult.error(HttpStatus.SC_INTERNAL_SERVER_ERROR, "资源路径不存在，请检查请求地址");
    }

    /**
     * 拦截参数校验错误异常,JSON传参
     *
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    @SecretBody
    public Object methodArgumentNotValidException(MethodArgumentNotValidException e) {
        String argNotValidMessage = getArgNotValidMessage(e.getBindingResult());
        return HttpResult.error(HttpStatus.SC_INTERNAL_SERVER_ERROR, argNotValidMessage);
    }

    /**
     * 拦截参数校验错误异常
     *
     */
    @ExceptionHandler(BindException.class)
    @ResponseBody
    @SecretBody
    public Object paramError(BindException e) {
        String argNotValidMessage = getArgNotValidMessage(e.getBindingResult());
        return HttpResult.error(HttpStatus.SC_INTERNAL_SERVER_ERROR, argNotValidMessage);
    }


    /**
     * 获取请求参数不正确的提示信息
     * 多个信息，拼接成用逗号分隔的形式
     */
    private String getArgNotValidMessage(BindingResult bindingResult) {
        if (bindingResult == null) {
            return "";
        }
        StringBuilder stringBuilder = new StringBuilder();
        //多个错误用逗号分隔
        List<ObjectError> allErrorInfos = bindingResult.getAllErrors();
        for (ObjectError error : allErrorInfos) {
            stringBuilder.append(",").append(error.getDefaultMessage());
        }
        //最终把首部的逗号去掉
        return StrUtil.removePrefix(stringBuilder.toString(), ",");
    }
}
