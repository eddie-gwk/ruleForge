package com.yunext.api.service.core;



import com.yunext.api.dto.MqttV5BrokerConfigDto;
import com.yunext.api.dto.ResultDto;
import com.yunext.api.listener.MqttV5MessageListener;

import java.util.List;
import java.util.Map;

/**
 * @author ：qianjb [qianjb@hadlinks.com]
 * @description ：
 * @date ：Created in 2024/7/15 17:42
 */
public interface MqttV5Service {
    /**
     * 新建
     *
     * @param v5Config
     * @return
     */
    ResultDto<String> create(MqttV5BrokerConfigDto v5Config);

    /**
     * 启动客户端
     * @param v5Config
     * @return
     */
    ResultDto<String> startUp(MqttV5BrokerConfigDto v5Config);

    /**
     * 更新
     *
     * @param v5Config
     * @return
     */
    ResultDto<?> update(MqttV5BrokerConfigDto v5Config);

    /**
     * 删除
     *
     * @param brokerId
     * @return
     */
    ResultDto<?> remove(String brokerId);

    /**
     * 订阅
     * @param brokerId
     * @param topic
     * @param qos
     * @param mqttV5MessageListener
     * @return
     */
    ResultDto<?> subscribe(String brokerId, String topic, int qos, MqttV5MessageListener mqttV5MessageListener);

    /**
     * 发布
     * @param brokerId
     * @param topic
     * @param qos
     * @param retained
     * @param payload
     * @return
     */
    ResultDto<?> publish(String brokerId, String topic, int qos, boolean retained, Map<Object, Object> payload);

    /**
     * 连接
     * @param v5Config
     * @return
     */
    ResultDto<?> connect(MqttV5BrokerConfigDto v5Config);

    /**
     * v5 配置信息
     * @return
     */
    List<MqttV5BrokerConfigDto> findList();

}
