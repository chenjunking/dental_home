package com.dental.home.app.appObj.system;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import com.dental.home.app.entity.system.EntityUserLoginStatus;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName("t_user_login_status")
@ApiModel(value="EntityUserLoginStatusAO对象", description="用户登录状态")
public class EntityUserLoginStatusAO extends EntityUserLoginStatus {

}
