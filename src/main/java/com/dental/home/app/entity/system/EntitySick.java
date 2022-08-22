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
 * 患者表
 * </p>
 *
 * @author cjj
 * @since 2022-08-22
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("c_sick")
@ApiModel(value = "EntitySick对象", description = "患者表")
public class EntitySick extends Model<EntitySick> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    @ApiModelProperty("姓名")
    @TableField("sick_name")
    private String sickName;

    @ApiModelProperty("性别")
    @TableField("sick_gender")
    private String sickGender;

    @ApiModelProperty("生日")
    @TableField("sick_birthday")
    private Date sickBirthday;

    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private Date createTime;

    @TableField("modify_time")
    private Date modifyTime;

    @TableField("delete_flag")
    @TableLogic
    private Boolean deleteFlag;


    public static final String ID = "id";

    public static final String SICK_NAME = "sick_name";

    public static final String SICK_GENDER = "sick_gender";

    public static final String SICK_BIRTHDAY = "sick_birthday";

    public static final String CREATE_TIME = "create_time";

    public static final String MODIFY_TIME = "modify_time";

    public static final String DELETE_FLAG = "delete_flag";

    @Override
    public Serializable pkVal() {
        return this.id;
    }

}
