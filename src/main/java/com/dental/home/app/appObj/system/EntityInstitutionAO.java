package com.dental.home.app.appObj.system;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import com.dental.home.app.entity.system.EntityInstitution;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName("t_institution")
@ApiModel(value="EntityInstitutionAO对象", description="机构信息")
public class EntityInstitutionAO extends EntityInstitution {

}
