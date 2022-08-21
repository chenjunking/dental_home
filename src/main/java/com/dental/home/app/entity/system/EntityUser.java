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
 * 用户信息
 * </p>
 *
 * @author cjj
 * @since 2022-05-10
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("t_user")
@ApiModel(value = "EntityUser对象", description = "用户信息")
public class EntityUser extends Model<EntityUser> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    @ApiModelProperty("机构id")
    @TableField("institution_id")
    private String institutionId;

    @ApiModelProperty("登录账号")
    @TableField("user_account")
    private String userAccount;

    @ApiModelProperty("姓名")
    @TableField("user_name")
    private String userName;

    @ApiModelProperty("性别")
    @TableField("user_gender")
    private Boolean userGender;

    @ApiModelProperty("职称")
    @TableField("user_rank")
    private String userRank;

    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private Date createTime;

    @TableField("modify_time")
    private Date modifyTime;

    @TableField("delete_flag")
    @TableLogic
    private Boolean deleteFlag;


    public static final String ID = "id";

    public static final String INSTITUTION_ID = "institution_id";

    public static final String USER_ACCOUNT = "user_account";

    public static final String USER_NAME = "user_name";

    public static final String USER_GENDER = "user_gender";

    public static final String USER_RANK = "user_rank";

    public static final String CREATE_TIME = "create_time";

    public static final String MODIFY_TIME = "modify_time";

    public static final String DELETE_FLAG = "delete_flag";

    @Override
    public Serializable pkVal() {
        return this.id;
    }

}
