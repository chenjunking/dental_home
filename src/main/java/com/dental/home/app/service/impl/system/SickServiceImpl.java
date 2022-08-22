package com.dental.home.app.service.impl.system;

import com.dental.home.app.appObj.system.EntitySickAO;
import com.dental.home.app.dao.mysql.gen.system.SickMapper;
import com.dental.home.app.service.system.SickService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
* <p>
    * 患者表 服务实现类
* </p>
*
* @author cjj
* @since 2022-08-22
*/
@Service
public class SickServiceImpl extends ServiceImpl<SickMapper, EntitySickAO> implements SickService {

}
