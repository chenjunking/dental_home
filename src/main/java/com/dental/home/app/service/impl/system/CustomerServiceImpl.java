package com.dental.home.app.service.impl.system;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.dental.home.app.appObj.system.EntityCustomerAO;
import com.dental.home.app.appObj.system.EntityUserAO;
import com.dental.home.app.dao.mysql.gen.system.CustomerMapper;
import com.dental.home.app.service.system.CustomerService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dental.home.app.vo.param.customer.ManageCustomerParam;
import com.dental.home.app.vo.param.user.ManageUserParam;
import org.springframework.stereotype.Service;

/**
* <p>
    * 客户表 服务实现类
* </p>
*
* @author cjj
* @since 2022-08-22
*/
@Service
public class CustomerServiceImpl extends ServiceImpl<CustomerMapper, EntityCustomerAO> implements CustomerService {

    @Override
    public LambdaQueryWrapper queryLambda(ManageCustomerParam manageCustomerParam) {
        LambdaQueryWrapper<EntityUserAO> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        if(null==manageCustomerParam){
            return lambdaQueryWrapper;
        }
        String institutionId = manageCustomerParam.getInstitutionId();
        String userName = manageCustomerParam.getUserName();
        if(StrUtil.isNotBlank(institutionId)){
            lambdaQueryWrapper.eq(EntityUserAO::getInstitutionId,institutionId);
        }
        if(StrUtil.isNotBlank(userName)){
            lambdaQueryWrapper.like(EntityUserAO::getUserName,userName);
        }
        return lambdaQueryWrapper;
    }




}
