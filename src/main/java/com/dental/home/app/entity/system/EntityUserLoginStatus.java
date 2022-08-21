package com.dental.home.app.entity.system;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
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
 * 用户登录状态
 * </p>
 *
 * @author cjj
 * @since 2022-05-10
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("t_user_login_status")
@ApiModel(value = "EntityUserLoginStatus对象", description = "用户登录状态")
public class EntityUserLoginStatus extends Model<EntityUserLoginStatus> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    @ApiModelProperty("登录账号")
    @TableField("user_account")
    private String userAccount;

    @ApiModelProperty("类型：用户类型:机构用户、平台用户")
    @TableField("user_institution_id")
    private String userInstitutionId;

    @ApiModelProperty("登录错误次数")
    @TableField("error_num")
    private Integer errorNum;

    @ApiModelProperty("是否锁定")
    @TableField("is_locked")
    private Boolean locked;

    @ApiModelProperty("锁定时间")
    @TableField("locked_time")
    private Date lockedTime;

    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private Date createTime;

    @TableField("modify_time")
    private Date modifyTime;

    @TableField("is_valid")
    private Boolean valid;


    public static final String ID = "id";

    public static final String USER_ACCOUNT = "user_account";

    public static final String USER_INSTITUTION_ID = "user_institution_id";

    public static final String ERROR_NUM = "error_num";

    public static final String IS_LOCKED = "is_locked";

    public static final String LOCKED_TIME = "locked_time";

    public static final String CREATE_TIME = "create_time";

    public static final String MODIFY_TIME = "modify_time";

    public static final String IS_VALID = "is_valid";

    @Override
    public Serializable pkVal() {
        return this.id;
    }

}
