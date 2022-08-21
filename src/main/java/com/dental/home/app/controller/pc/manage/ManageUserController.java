package com.dental.home.app.controller.pc.manage;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.dental.home.app.aop.SecretBody;
import com.dental.home.app.appObj.system.EntityUserAO;
import com.dental.home.app.common.StaticDefine;
import com.dental.home.app.service.system.UserService;
import com.dental.home.app.vo.param.BaseParam;
import com.dental.home.app.vo.param.user.ManageUserParam;
import com.dental.home.app.vo.result.HttpResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Api(tags = "pc端用户信息接口")
@RestController
@RequestMapping("pc/manage/user")
public class ManageUserController {

    @Autowired
    private UserService userService;

    /**
     * 获取用户列表
     * @param manageUserParam
     * @return
     */
    @SecretBody
    @ApiOperation("获取用户列表")
    @PostMapping("loadUser")
    public Object loadUser(@RequestBody ManageUserParam manageUserParam){
        manageUserParam = null!=manageUserParam?manageUserParam:ManageUserParam.builder().build();
        manageUserParam.setInstitutionId(StaticDefine.GEN);
        IPage<EntityUserAO> page = manageUserParam.pageInit();
        return HttpResult.ok(userService.getBaseMapper().selectPage(page,userService.queryLambda(manageUserParam)));
    }

    /**
     * 新增用户
     * @param manageUserParam
     * @return
     */
    @SecretBody
    @ApiOperation("新增用户")
    @PostMapping("addUser")
    public Object addUser(@RequestBody @Validated(BaseParam.add.class)ManageUserParam manageUserParam){
        EntityUserAO manageUser = new EntityUserAO();
        BeanUtil.copyProperties(manageUserParam,manageUser);
        manageUser.setInstitutionId(StaticDefine.GEN);
        return userService.addUser(manageUser);
    }

    /**
     * 编辑用户
     * @param manageUserParam
     * @return
     */
    @SecretBody
    @ApiOperation("编辑用户")
    @PostMapping("updateUser")
    public Object updateUser(@RequestBody @Validated(BaseParam.edit.class)ManageUserParam manageUserParam){
        EntityUserAO manageUser = new EntityUserAO();
        BeanUtil.copyProperties(manageUserParam,manageUser);
        return userService.updateUserById(manageUser);
    }


    /**
     * 删除用户
     * @param manageUserParam
     * @return
     */
    @SecretBody
    @ApiOperation("删除用户")
    @PostMapping("deleteUser")
    public Object deleteUser(@RequestBody @Validated(BaseParam.delete.class)ManageUserParam manageUserParam){
        return userService.deleteUserById(manageUserParam.getId());
    }


}
