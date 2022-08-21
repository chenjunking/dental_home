package com.dental.home.app.vo.param.login;

import com.dental.home.app.vo.param.BaseParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
/**
 * pc管理端登录参数
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "PcManageLoginParam对象", description = "pc端用户登录参数信息")
public class PcManageLoginParam extends BaseParam {
    /**
     * userName
     */
    @ApiModelProperty("登录账号")
    @NotBlank(message = "登录账号不能为空！", groups = {login.class})
    private String userName;
    /**
     * passWord
     */
    @ApiModelProperty("登录账号密码")
    @NotBlank(message = "登录账号密码不能为空！", groups = {login.class})
    private String passWord;
    /**
     * code
     */
    @ApiModelProperty("图形验证码")
    @NotBlank(message = "图形验证码不能为空！", groups = {login.class})
    private String code;

}
