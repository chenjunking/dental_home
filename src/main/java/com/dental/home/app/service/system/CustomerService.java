package com.dental.home.app.service.system;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.dental.home.app.appObj.system.EntityCustomerAO;
import com.baomidou.mybatisplus.extension.service.IService;
import com.dental.home.app.vo.param.customer.ManageCustomerParam;
import com.dental.home.app.vo.param.user.ManageUserParam;

/**
* <p>
    * 客户表 服务类
* </p>
*
* @author cjj
* @since 2022-08-22
*/
public interface CustomerService extends IService<EntityCustomerAO> {

    LambdaQueryWrapper queryLambda(ManageCustomerParam manageCustomerParam);
}
