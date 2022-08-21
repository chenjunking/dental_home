package com.dental.home.app.service.impl.system;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.dental.home.app.appObj.system.EntityUserAO;
import com.dental.home.app.dao.mysql.gen.system.UserMapper;
import com.dental.home.app.service.system.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dental.home.app.vo.param.user.ManageUserParam;
import com.dental.home.app.vo.result.HttpResult;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.springframework.stereotype.Service;

/**
* <p>
    * 用户信息 服务实现类
* </p>
*
* @author cjj
* @since 2022-05-10
*/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, EntityUserAO> implements UserService {

    @Override
    public LambdaQueryWrapper queryLambda(ManageUserParam manageUserParam) {
        LambdaQueryWrapper<EntityUserAO> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        if(null==manageUserParam){
            return lambdaQueryWrapper;
        }
        String institutionId = manageUserParam.getInstitutionId();
        String userName = manageUserParam.getUserName();
        if(StrUtil.isNotBlank(institutionId)){
            lambdaQueryWrapper.eq(EntityUserAO::getInstitutionId,institutionId);
        }
        if(StrUtil.isNotBlank(userName)){
            lambdaQueryWrapper.like(EntityUserAO::getUserName,userName);
        }
        return lambdaQueryWrapper;
    }


    @Override
    public HttpResult addUser(EntityUserAO manageUser) {
        return HttpResult.ok(manageUser.insert());
    }

    @Override
    public HttpResult updateUserById(EntityUserAO manageUser) {
        return HttpResult.ok(manageUser.updateById());
    }

    @Override
    public HttpResult deleteUserById(String id) {
        return HttpResult.ok(removeById(id));
    }


}
