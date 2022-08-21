package com.dental.home.app.entity.system;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
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
 * 
 * </p>
 *
 * @author cjj
 * @since 2022-05-10
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("t_menu")
@ApiModel(value = "EntityMenu对象", description = "")
public class EntityMenu extends Model<EntityMenu> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    @TableField("p_id")
    private String pId;

    @ApiModelProperty("(system institution)")
    @TableField("system_type")
    private String systemType;

    @TableField("`name`")
    private String name;

    @TableField("title")
    private String title;

    @TableField("icon")
    private String icon;

    @TableField("path")
    private String path;

    @TableField("component")
    private String component;

    @ApiModelProperty("资源code")
    @TableField("resource_code")
    private String resourceCode;

    @ApiModelProperty("排序")
    @TableField("source_index")
    private Integer sourceIndex;

    @TableField("delete_flag")
    @TableLogic
    private Boolean deleteFlag;


    public static final String ID = "id";

    public static final String P_ID = "p_id";

    public static final String SYSTEM_TYPE = "system_type";

    public static final String NAME = "name";

    public static final String TITLE = "title";

    public static final String ICON = "icon";

    public static final String PATH = "path";

    public static final String COMPONENT = "component";

    public static final String RESOURCE_CODE = "resource_code";

    public static final String SOURCE_INDEX = "source_index";

    public static final String DELETE_FLAG = "delete_flag";

    @Override
    public Serializable pkVal() {
        return this.id;
    }

}
