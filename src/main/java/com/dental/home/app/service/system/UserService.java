package com.dental.home.app.service.system;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.dental.home.app.appObj.system.EntityUserAO;
import com.baomidou.mybatisplus.extension.service.IService;
import com.dental.home.app.vo.param.user.ManageUserParam;
import com.dental.home.app.vo.result.HttpResult;

/**
* <p>
    * 用户信息 服务类
* </p>
*
* @author cjj
* @since 2022-05-10
*/
public interface UserService extends IService<EntityUserAO> {
    LambdaQueryWrapper queryLambda(ManageUserParam manageUserParam);

    HttpResult addUser(EntityUserAO manageUser);

    HttpResult updateUserById(EntityUserAO manageUser);

    HttpResult deleteUserById(String id);
}
