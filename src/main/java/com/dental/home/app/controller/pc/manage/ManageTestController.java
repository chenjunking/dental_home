package com.dental.home.app.controller.pc.manage;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dental.home.app.config.MqSendMessageGateWay;
import com.dental.home.app.dao.cluster.ClusterBMapper;
import com.dental.home.app.dao.cluster.ClusterMapper;
import com.dental.home.app.vo.TestVO;
import com.dental.home.app.vo.result.HttpResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Api(tags = "pc端测试接口")
@RestController
@RequestMapping("pc/manage/test")
public class ManageTestController {

    @Autowired
    private MqSendMessageGateWay mqSendMessageGateWay;

    @Resource
    private ClusterMapper clusterMapper;

    @Resource
    private ClusterBMapper clusterBMapper;




    @ApiOperation("发送消息到默认主题")
    @GetMapping("/send")
    private ResponseEntity<String> send(String data){
        if(StrUtil.isBlank(data)){
            return new ResponseEntity<>("消息不能为空！", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        System.out.println("data:"+data);
        mqSendMessageGateWay.sendToMqtt(data);
        return new ResponseEntity<>("OK", HttpStatus.OK);
    }


    /**
     * 发送消息到指定主题
     * @param topic
     * @param data
     */
    @ApiOperation("发送消息到指定主题")
    @GetMapping("/sendToTopic")
    @Validated
    private ResponseEntity<String> send(String topic ,String data){
        if(StrUtil.isBlank(topic)){
            return new ResponseEntity<>("主题不能为空！", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if(StrUtil.isBlank(data)){
            return new ResponseEntity<>("消息不能为空！", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        mqSendMessageGateWay.sendToMqtt(topic,data);
        return new ResponseEntity<>("OK", HttpStatus.OK);
    }

    /**
     * selectAll
     */
    @ApiOperation("查询TDengine数据")
    @GetMapping("/selectAll")
    private Object selectAll(Integer begin,Integer length){
        Long count = clusterMapper.countAll();
        List<TestVO> r = new ArrayList<>();
        if(count>0){
            r = clusterMapper.selectAll();
        }
        IPage<TestVO> pageObject = new Page<>(begin,length);
        pageObject = clusterMapper.getDataList(pageObject);
        clusterBMapper.createSuperTable();
        clusterBMapper.createTable("d1002","Beijing.DaXing",2);
        int insert = clusterBMapper.insertOne("d1002","Beijing.Chaoyang",2, BigDecimal.valueOf(Double.valueOf(Math.random()*100+1)),BigDecimal.valueOf(Double.valueOf(Math.random()*100+1)),BigDecimal.valueOf(Double.valueOf(Math.random()*100+1)));
        System.err.println(insert);

        Map<String,Object> resultMap = new HashMap<>();
        resultMap.put("count",count);
        resultMap.put("data",r);
        return HttpResult.ok("",pageObject);
    }


}
