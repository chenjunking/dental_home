package com.dental.home.app.appObj.system;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import com.dental.home.app.entity.system.EntityUser;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName("t_user")
@ApiModel(value="EntityUserAO对象", description="用户信息")
public class EntityUserAO extends EntityUser {

    @TableField(exist = false)
    private List<String> role;

}
