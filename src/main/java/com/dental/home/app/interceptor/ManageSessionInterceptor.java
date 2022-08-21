package com.dental.home.app.interceptor;

import cn.hutool.core.util.StrUtil;
import com.dental.home.app.appObj.system.EntityUserAO;
import com.dental.home.app.common.StaticDefine;
import com.dental.home.app.redis.RedisCacheTool;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 管理后台拦截器
 */
@Component
@Slf4j
public class ManageSessionInterceptor implements HandlerInterceptor {
    /**
     * redisCacheTool
     */
    @Resource
    private RedisCacheTool redisCacheTool;

    @Override
    public boolean preHandle(final HttpServletRequest request, final HttpServletResponse response, final Object handler) throws Exception {
        // 获取移动端传过来的token
        String token = request.getHeader(StaticDefine.MANAGE_TOKEN_NAME);
        if (StrUtil.isNotBlank(token)) {
            Object sessionData = redisCacheTool.getKey(token).getData();
            EntityUserAO systemUser = (EntityUserAO)sessionData;
            request.setAttribute(StaticDefine.MANAGE_SESSION_ID, systemUser);
            redisCacheTool.expireTimeKey(token,20);
        }
        return true;
    }
}
