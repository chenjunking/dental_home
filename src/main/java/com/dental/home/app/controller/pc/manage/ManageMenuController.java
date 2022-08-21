package com.dental.home.app.controller.pc.manage;

import com.dental.home.app.aop.SecretBody;
import com.dental.home.app.appObj.system.EntityUserAO;
import com.dental.home.app.common.StaticDefine;
import com.dental.home.app.service.system.MenuService;
import com.dental.home.app.utils.UserHolder;
import com.dental.home.app.vo.result.HttpResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Api(tags = "pc端菜单接口")
@RestController
@RequestMapping("pc/manage/menu")
public class ManageMenuController {

    @Resource
    private MenuService menuService;

    @Resource
    private UserHolder userHolder;

    @ApiOperation("获取用户的菜单")
    @SecretBody
    @GetMapping("/systemMenu")
    private Object systemMenu(){
        EntityUserAO systemUser = userHolder.getManageUser();
        return HttpResult.ok("", menuService.treeMenu(StaticDefine.SystemType.SYSTEM,null));
    }


}
