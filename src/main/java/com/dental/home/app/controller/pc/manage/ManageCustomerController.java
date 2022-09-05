package com.dental.home.app.controller.pc.manage;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.dental.home.app.aop.SecretBody;
import com.dental.home.app.appObj.system.EntityCustomerAO;
import com.dental.home.app.appObj.system.EntityUserAO;
import com.dental.home.app.common.StaticDefine;
import com.dental.home.app.service.system.CustomerService;
import com.dental.home.app.service.system.UserService;
import com.dental.home.app.vo.param.BaseParam;
import com.dental.home.app.vo.param.customer.ManageCustomerParam;
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


@Api(tags = "pc端客户信息接口")
@RestController
@RequestMapping("pc/manage/customer")
public class ManageCustomerController {

    @Autowired
    private CustomerService customerService;

    /**
     * 获取客户列表
     * @param manageCustomerParam
     * @return
     */
    @SecretBody
    @ApiOperation("获取客户列表")
    @PostMapping("loadCustomer")
    public Object loadCustomer(@RequestBody ManageCustomerParam manageCustomerParam){
        manageCustomerParam = null!=manageCustomerParam?manageCustomerParam:ManageCustomerParam.builder().build();
        manageCustomerParam.setInstitutionId(StaticDefine.GEN);
        IPage<EntityCustomerAO> page = manageCustomerParam.pageInit();
        return HttpResult.ok(customerService.getBaseMapper().selectPage(page,customerService.queryLambda(manageCustomerParam)));
    }


    /**
     * 新增客户
     * @param manageCustomerParam
     * @return
     */
    @SecretBody
    @ApiOperation("新增客户")
    @PostMapping("addCustomer")
    public Object addCustomer(@RequestBody @Validated(BaseParam.add.class)ManageCustomerParam manageCustomerParam){
        EntityCustomerAO customerAO = new EntityCustomerAO();
        BeanUtil.copyProperties(manageCustomerParam,customerAO);
        customerAO.setInstitutionId(StaticDefine.GEN);
        return customerService.addUser(customerAO);
    }


}
