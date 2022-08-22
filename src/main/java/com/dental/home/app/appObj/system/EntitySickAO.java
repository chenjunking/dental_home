package com.dental.home.app.appObj.system;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import com.dental.home.app.entity.system.EntitySick;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName("c_sick")
@ApiModel(value="EntitySickAO对象", description="患者表")
public class EntitySickAO extends EntitySick {

}
