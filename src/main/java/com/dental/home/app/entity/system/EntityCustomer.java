package com.dental.home.app.entity.system;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.io.Serializable;
import java.util.Date;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * <p>
 * 客户表
 * </p>
 *
 * @author cjj
 * @since 2022-08-22
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("c_customer")
@ApiModel(value = "EntityCustomer对象", description = "客户表")
public class EntityCustomer extends Model<EntityCustomer> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    @ApiModelProperty("所属机构id")
    @TableField("institution_id")
    private String institutionId;

    @ApiModelProperty("微信小程序openid")
    @TableField("wx_mp_open_id")
    private String wxMpOpenId;

    @ApiModelProperty("姓名")
    @TableField("customer_name")
    private String customerName;

    @ApiModelProperty("性别")
    @TableField("customer_gender")
    private String customerGender;

    @ApiModelProperty("联系电话")
    @TableField("customer_phone")
    private String customerPhone;

    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private Date createTime;

    @TableField("modify_time")
    private Date modifyTime;

    @TableField("delete_flag")
    @TableLogic
    private Boolean deleteFlag;


    public static final String ID = "id";

    public static final String INSTITUTION_ID = "institution_id";

    public static final String WX_MP_OPEN_ID = "wx_mp_open_id";

    public static final String CUSTOMER_NAME = "customer_name";

    public static final String CUSTOMER_GENDER = "customer_gender";

    public static final String CUSTOMER_PHONE = "customer_phone";

    public static final String CREATE_TIME = "create_time";

    public static final String MODIFY_TIME = "modify_time";

    public static final String DELETE_FLAG = "delete_flag";

    @Override
    public Serializable pkVal() {
        return this.id;
    }

}
