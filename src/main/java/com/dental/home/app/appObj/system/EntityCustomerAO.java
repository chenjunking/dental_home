package com.dental.home.app.appObj.system;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import com.dental.home.app.entity.system.EntityCustomer;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName("c_customer")
@ApiModel(value="EntityCustomerAO对象", description="客户表")
public class EntityCustomerAO extends EntityCustomer {

}
