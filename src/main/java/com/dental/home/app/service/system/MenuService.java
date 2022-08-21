package com.dental.home.app.service.system;

import com.dental.home.app.appObj.system.EntityMenuAO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* <p>
    *  服务类
* </p>
*
* @author cjj
* @since 2022-05-10
*/
public interface MenuService extends IService<EntityMenuAO> {
    EntityMenuAO treeMenu(String systemType, String ... resourceCode);
}
