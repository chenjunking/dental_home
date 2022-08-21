package com.dental.home.app.service.impl.system;

import com.dental.home.app.appObj.system.EntityUserLoginStatusAO;
import com.dental.home.app.dao.mysql.gen.system.UserLoginStatusMapper;
import com.dental.home.app.service.system.UserLoginStatusService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
* <p>
    * 用户登录状态 服务实现类
* </p>
*
* @author cjj
* @since 2022-05-10
*/
@Service
public class UserLoginStatusServiceImpl extends ServiceImpl<UserLoginStatusMapper, EntityUserLoginStatusAO> implements UserLoginStatusService {

}
