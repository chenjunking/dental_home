package com.dental.home.app.appObj.system;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import com.dental.home.app.entity.system.EntityRole;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName("t_role")
@ApiModel(value="EntityRoleAO对象", description="")
public class EntityRoleAO extends EntityRole {

}
