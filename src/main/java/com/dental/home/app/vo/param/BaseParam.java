package com.dental.home.app.vo.param;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 通用基础参数
 */
@Data
public class BaseParam implements Serializable {

    private static final long serialVersionUID = 1L;

    public Page pageInit(){
        return new Page(this.pageNo,this.pageSize);
    }

    /**
     * 每页大小（默认20）
     */
    @ApiModelProperty("每页大小（默认20）")
    public int pageSize = 20;
    /**
     * 第几页（从1开始）
     */
    @ApiModelProperty("第几页（从1开始）")
    public int pageNo = 1;

    /**
     * 参数校验分组：列表
     */
    public @interface list {
    }

    /**
     * 参数校验分组：分页
     */
    public @interface page {
    }

    /**
     * 参数校验分组：增加
     */
    public @interface add {
    }

    /**
     * 参数校验分组：编辑
     */
    public @interface edit {
    }

    /**
     * 参数校验分组：删除
     */
    public @interface delete {
    }

    /**
     * 参数校验分组：详情
     */
    public @interface detail {
    }

    /**
     * 参数校验分组：登录
     */
    public @interface login {
    }
}
