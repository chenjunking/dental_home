package com.dental.home.app.vo.param.user;

import com.dental.home.app.vo.param.BaseParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "ManageUserParam对象", description = "pc端用户参数信息")
public class ManageUserParam extends BaseParam{
    /**
     * id
     */
    @NotBlank(message = "id不能为空！", groups = {BaseParam.edit.class,BaseParam.detail.class,BaseParam.delete.class})
    @ApiModelProperty("id")
    private String id;
    /**
     * institutionId
     */
    @ApiModelProperty("机构id")
    private String institutionId;
    /**
     * userName
     */
    @NotBlank(message = "姓名不能为空！", groups = {BaseParam.add.class,BaseParam.edit.class})
    @ApiModelProperty("姓名")
    private String userName;
}
