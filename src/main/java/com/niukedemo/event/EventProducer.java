package com.niukedemo.event;

import com.alibaba.fastjson.JSONObject;
import com.niukedemo.entity.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

/**
 * @author 刘欢
 * @date 2022年11月01日 20:16
 */
@Component
public class EventProducer {
    @Autowired
    private KafkaTemplate kafkaTemplate;
    //处理事件
    public void fireEvent(Event event){
        //将时间发布到指定的主题
        kafkaTemplate.send(event.getTopic(), JSONObject.toJSONString(event));
    }
}
