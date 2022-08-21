package com.dental.home.app.appObj.system;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.dental.home.app.vo.MenuMeta;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import com.dental.home.app.entity.system.EntityMenu;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName("t_menu")
@ApiModel(value="EntityMenuAO对象", description="")
public class EntityMenuAO extends EntityMenu {
    @TableField(exist = false)
    private MenuMeta meta;

    @TableField(exist = false)
    private String redirect;
    @TableField(exist = false)
    private List<EntityMenuAO> children;
    @TableField(exist = false)
    private Boolean hidden;
}
