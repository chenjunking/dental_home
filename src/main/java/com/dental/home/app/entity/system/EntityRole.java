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
 * 
 * </p>
 *
 * @author cjj
 * @since 2022-05-10
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("t_role")
@ApiModel(value = "EntityRole对象", description = "")
public class EntityRole extends Model<EntityRole> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    @ApiModelProperty("机构id")
    @TableField("institution_id")
    private String institutionId;

    @ApiModelProperty("角色名称")
    @TableField("role_name")
    private String roleName;

    @ApiModelProperty("是否是管理员")
    @TableField("admin_flag")
    private Boolean adminFlag;

    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private Date createTime;

    @TableField("modify_time")
    private Date modifyTime;

    @TableField("delete_flag")
    @TableLogic
    private String deleteFlag;


    public static final String ID = "id";

    public static final String INSTITUTION_ID = "institution_id";

    public static final String ROLE_NAME = "role_name";

    public static final String ADMIN_FLAG = "admin_flag";

    public static final String CREATE_TIME = "create_time";

    public static final String MODIFY_TIME = "modify_time";

    public static final String DELETE_FLAG = "delete_flag";

    @Override
    public Serializable pkVal() {
        return this.id;
    }

}
