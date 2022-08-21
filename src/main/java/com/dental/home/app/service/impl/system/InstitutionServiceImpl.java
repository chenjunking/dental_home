package com.dental.home.app.service.impl.system;

import com.dental.home.app.appObj.system.EntityInstitutionAO;
import com.dental.home.app.dao.mysql.gen.system.InstitutionMapper;
import com.dental.home.app.service.system.InstitutionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
* <p>
    * 机构信息 服务实现类
* </p>
*
* @author cjj
* @since 2022-05-10
*/
@Service
public class InstitutionServiceImpl extends ServiceImpl<InstitutionMapper, EntityInstitutionAO> implements InstitutionService {

}
