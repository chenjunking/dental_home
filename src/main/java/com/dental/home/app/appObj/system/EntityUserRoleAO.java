package com.dental.home.app.appObj.system;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import com.dental.home.app.entity.system.EntityUserRole;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName("t_user_role")
@ApiModel(value="EntityUserRoleAO对象", description="")
public class EntityUserRoleAO extends EntityUserRole {

}
