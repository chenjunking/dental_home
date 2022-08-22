package com.dental.home.app.service.impl.system;

import com.dental.home.app.appObj.system.EntityCustomerAO;
import com.dental.home.app.dao.mysql.gen.system.CustomerMapper;
import com.dental.home.app.service.system.CustomerService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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

}
