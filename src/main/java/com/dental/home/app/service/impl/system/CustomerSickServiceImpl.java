package com.dental.home.app.service.impl.system;

import com.dental.home.app.appObj.system.EntityCustomerSickAO;
import com.dental.home.app.dao.mysql.gen.system.CustomerSickMapper;
import com.dental.home.app.service.system.CustomerSickService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
* <p>
    * 客户患者关系表 服务实现类
* </p>
*
* @author cjj
* @since 2022-08-22
*/
@Service
public class CustomerSickServiceImpl extends ServiceImpl<CustomerSickMapper, EntityCustomerSickAO> implements CustomerSickService {

}
