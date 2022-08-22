package com.dental.home.app.entity.system;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * <p>
 * 客户患者关系表
 * </p>
 *
 * @author cjj
 * @since 2022-08-22
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("c_customer_sick")
@ApiModel(value = "EntityCustomerSick对象", description = "客户患者关系表")
public class EntityCustomerSick extends Model<EntityCustomerSick> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    @TableField("customer_id")
    private String customerId;

    @TableField("sick_id")
    private String sickId;


    public static final String ID = "id";

    public static final String CUSTOMER_ID = "customer_id";

    public static final String SICK_ID = "sick_id";

    @Override
    public Serializable pkVal() {
        return this.id;
    }

}
