package com.dental.home.app.appObj.system;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import com.dental.home.app.entity.system.EntityCustomerSick;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName("c_customer_sick")
@ApiModel(value="EntityCustomerSickAO对象", description="客户患者关系表")
public class EntityCustomerSickAO extends EntityCustomerSick {

}
