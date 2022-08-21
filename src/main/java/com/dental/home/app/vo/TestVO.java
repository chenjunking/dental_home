package com.dental.home.app.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("log")
public class TestVO {

    @TableField("ts")
    private Date ts;
    @TableField("level")
    private Integer level;
    @TableField("content")
    private String content;
    @TableId(value = "dnode_id", type = IdType.ASSIGN_ID)
    private Integer dnodeId;
    @TableField("dnode_ep")
    private String dnodeEp;
}
