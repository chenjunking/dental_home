package com.dental.home.app.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class MqttCallbackHandler {

    public void handle(String topic, String payload){
        log.info("MqttCallbackHandle:" + topic + "---"+ payload);
        // 根据topic分别进行消息处理。

    }

}
