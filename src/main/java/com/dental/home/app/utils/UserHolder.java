package com.dental.home.app.utils;


import com.dental.home.app.appObj.system.EntityUserAO;
import com.dental.home.app.common.StaticDefine;
import com.dental.home.app.exception.NotLoginException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;

/**
 * 获取回话中的用户
 */
@Component
@Slf4j
public class UserHolder{

    /**
     * 获取PC端管理用户
     * @return 返回管理用户信息
     */
    public EntityUserAO getManageUser() {
        EntityUserAO systemUser = (EntityUserAO) RequestHolder.getRequest().getAttribute(StaticDefine.MANAGE_SESSION_ID);
        if(null==systemUser){
            throw new NotLoginException("");
        }
        return systemUser;
    }


    /**
     * 获取PC端管理用户
     * @return 返回管理用户信息
     */
    public EntityUserAO getManageUserNotThrowException() {
        EntityUserAO entityManageUserAO = null;
        try{
            entityManageUserAO = (EntityUserAO) RequestHolder.getSession().getAttribute(StaticDefine.MANAGE_SESSION_ID);
        }catch (Exception e){
            entityManageUserAO = null;
        }
        return entityManageUserAO;
    }

}
