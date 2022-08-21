package com.dental.home.app.service.impl.system;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.dental.home.app.appObj.system.EntityMenuAO;
import com.dental.home.app.common.StaticDefine;
import com.dental.home.app.dao.mysql.gen.system.MenuMapper;
import com.dental.home.app.service.system.MenuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dental.home.app.vo.MenuMeta;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author cjj
 * @since 2022-05-10
 */
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, EntityMenuAO> implements MenuService {


    /**
     * 获取菜单树
     * @param systemType 系统类型
     * @param resourceCode 资源code
     * @return
     */
    @Override
    public EntityMenuAO treeMenu(String systemType, String... resourceCode) {
        if(StrUtil.isBlank(systemType)){
            return null;
        }
        List<String> resourceCodeList = new ArrayList<>();
        if(null!=resourceCode && resourceCode.length>0){
            resourceCodeList = Arrays.asList(resourceCode);
        }
        LambdaQueryWrapper<EntityMenuAO> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.orderByAsc(EntityMenuAO::getSourceIndex);
        lambdaQueryWrapper.eq(EntityMenuAO::getSystemType,systemType);
        if(CollUtil.isNotEmpty(resourceCodeList)){
            lambdaQueryWrapper.in(EntityMenuAO::getResourceCode,resourceCodeList);
        }
        List<EntityMenuAO> menuList = list(lambdaQueryWrapper);
        if(CollUtil.isEmpty(menuList)){
            return null;
        }
        menuList.forEach(e->{
            String title = e.getTitle();
            String icon = e.getIcon();
            e.setMeta(MenuMeta.builder().title(title).icon(icon).build());
        });

        menuList = transChildren(menuList);
        menuList = menuList.stream().sorted(Comparator.comparing(EntityMenuAO::getSourceIndex)).collect(Collectors.toList());
        EntityMenuAO menuAO = new EntityMenuAO();
        menuAO.setId(StaticDefine.GEN);
        menuAO.setPath("/");
        menuAO.setComponent("Layout");
        if(CollUtil.isNotEmpty(menuList)){
            menuAO.setChildren(menuList);
            menuAO.setRedirect(menuList.get(0).getPath());
        }
        menuAO.setHidden(true);
        return menuAO;
    }


    /**
     * 构建菜单上下级关系
     * @param menuList
     * @return
     */
    public List<EntityMenuAO> transChildren(List<EntityMenuAO> menuList){
        if(CollUtil.isEmpty(menuList)){
            return null;
        }
        List<EntityMenuAO> parMenuList = menuList.stream().filter(e-> StaticDefine.GEN.equals(e.getPId())).collect(Collectors.toList());
        for(EntityMenuAO parMenu:parMenuList){
            String id = parMenu.getId();
            List<EntityMenuAO> subMenuList = menuList.stream().filter(e-> id.equals(e.getPId())).collect(Collectors.toList());
            if(CollUtil.isNotEmpty(subMenuList)){
                subMenuList = subMenuList.stream().sorted(Comparator.comparing(EntityMenuAO::getSourceIndex)).collect(Collectors.toList());
                parMenu.setChildren(subMenuList);
                parMenu.setRedirect(subMenuList.get(0).getPath());
            }
        }
        parMenuList = ListUtil.sortByProperty(parMenuList,EntityMenuAO.SOURCE_INDEX);
        return parMenuList;
    }


}