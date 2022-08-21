package com.dental.home.app.controller.pc.manage;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.GifCaptcha;
import cn.hutool.captcha.generator.MathGenerator;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.dental.home.app.aop.SecretBody;
import com.dental.home.app.appObj.system.EntityUserAO;
import com.dental.home.app.common.StaticDefine;
import com.dental.home.app.redis.RedisCacheTool;
import com.dental.home.app.utils.CommonUtil;
import com.dental.home.app.utils.UserHolder;
import com.dental.home.app.vo.param.BaseParam;
import com.dental.home.app.vo.param.login.PcManageLoginParam;
import com.dental.home.app.vo.result.HttpResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import sun.misc.BASE64Encoder;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * pc管理端登录
 */
@Api(tags = "pc端菜单接口")
@RestController
@RequestMapping("pc/manage/login")
public class ManageLoginController {

    @Resource
    private RedisCacheTool redisCacheTool;

    @Resource
    private UserHolder userHolder;

    private GifCaptcha captcha;

    /**
     * 图形验证码
     * @throws ServletException
     * @throws IOException
     */
    @SecretBody
    @ApiOperation("获取登录的图形验证码")
    @GetMapping(value="getLoginCode")
    public Object smsCaptchaImg(){
        captcha = CaptchaUtil.createGifCaptcha(480, 120, 1);
        // 自定义验证码内容为四则运算方式
        captcha.setGenerator(new MathGenerator());
        // 重新生成code
        captcha.createCode();
        //生成图片验证码
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        captcha.write(outputStream);
        // 对字节数组Base64编码
        BASE64Encoder encoder = new BASE64Encoder();
        String str = "data:image/jpeg;base64,";
        String base64Img = str + encoder.encode(outputStream.toByteArray()).replace("\n", "").replace("\r", "");
        return HttpResult.ok("",base64Img);
    }


    /**
     * 登录
     * @param pcManageLoginParam
     * @return
     */
    @ApiOperation("登录")
    @SecretBody
    @PostMapping(value = "doLogin")
    public Object doLogin(@RequestBody @Validated(BaseParam.login.class)PcManageLoginParam pcManageLoginParam){
        boolean verifyCode = captcha.verify(pcManageLoginParam.getCode());
        if(!verifyCode){
            return HttpResult.error("图形验证码错误！");
        }
        String token = IdUtil.simpleUUID();
        EntityUserAO userAO = new EntityUserAO();
        userAO.setId(token);
        userAO.setUserName("admin");
        redisCacheTool.putKey(token,userAO);
        return HttpResult.ok("",token);
    }

    /**
     * 获取当前登录的用户信息
     * @return
     */
    @ApiOperation("获取当前登录的用户信息")
    @SecretBody
    @PostMapping(value = "userInfo")
    public Object userInfo(){
        EntityUserAO userAO = userHolder.getManageUser();
        return HttpResult.ok("",userAO);
    }


    /**
     * logout
     * @return
     */
    @ApiOperation("登出")
    @SecretBody
    @PostMapping(value = "logout")
    public Object logout(HttpServletRequest request){
        String token = request.getHeader(StaticDefine.MANAGE_TOKEN_NAME);
        if (StrUtil.isBlank(token)) {
            return HttpResult.error("token不存在！");
        }
        return redisCacheTool.delKey(token);
    }


}
