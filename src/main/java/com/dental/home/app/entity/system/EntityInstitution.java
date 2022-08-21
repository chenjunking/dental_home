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
 * 机构信息
 * </p>
 *
 * @author cjj
 * @since 2022-05-10
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("t_institution")
@ApiModel(value = "EntityInstitution对象", description = "机构信息")
public class EntityInstitution extends Model<EntityInstitution> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    @ApiModelProperty("机构名称")
    @TableField("institution_name")
    private String institutionName;

    @ApiModelProperty("机构类型（医院，诊所）")
    @TableField("institution_type")
    private String institutionType;

    @ApiModelProperty("联系人")
    @TableField("institution_linkman")
    private String institutionLinkman;

    @ApiModelProperty("联系电话")
    @TableField("institution_phone")
    private String institutionPhone;

    @ApiModelProperty("省")
    @TableField("institution_province")
    private String institutionProvince;

    @ApiModelProperty("市")
    @TableField("institution_city")
    private String institutionCity;

    @ApiModelProperty("区")
    @TableField("institution_district")
    private String institutionDistrict;

    @ApiModelProperty("启用/停用")
    @TableField("on_sale")
    private Boolean onSale;

    @ApiModelProperty("(不)支持医保")
    @TableField("medical_insurance")
    private Boolean medicalInsurance;

    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private Date createTime;

    @TableField("modify_time")
    private Date modifyTime;

    @TableField("delete_flag")
    @TableLogic
    private Boolean deleteFlag;


    public static final String ID = "id";

    public static final String INSTITUTION_NAME = "institution_name";

    public static final String INSTITUTION_TYPE = "institution_type";

    public static final String INSTITUTION_LINKMAN = "institution_linkman";

    public static final String INSTITUTION_PHONE = "institution_phone";

    public static final String INSTITUTION_PROVINCE = "institution_province";

    public static final String INSTITUTION_CITY = "institution_city";

    public static final String INSTITUTION_DISTRICT = "institution_district";

    public static final String ON_SALE = "on_sale";

    public static final String MEDICAL_INSURANCE = "medical_insurance";

    public static final String CREATE_TIME = "create_time";

    public static final String MODIFY_TIME = "modify_time";

    public static final String DELETE_FLAG = "delete_flag";

    @Override
    public Serializable pkVal() {
        return this.id;
    }

}
