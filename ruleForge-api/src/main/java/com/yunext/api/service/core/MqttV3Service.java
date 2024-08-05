package com.yunext.api.service.core;


import com.yunext.api.dto.MqttV3BrokerConfigDto;
import com.yunext.api.dto.ResultDto;
import com.yunext.api.listener.MqttV3MessageListener;

import java.util.List;
import java.util.Map;

/**
 * @author ：qianjb [qianjb@hadlinks.com]
 * @description ：
 * @date ：Created in 2024/7/15 17:40
 */
public interface MqttV3Service {

    /**
     * 新建
     *
     * @param v3Config
     * @return
     */
    ResultDto<String> create(MqttV3BrokerConfigDto v3Config);

    /**
     * 启动客户端
     * @param v3Config
     * @return
     */
    ResultDto<String> startUp(MqttV3BrokerConfigDto v3Config);

    /**
     * 更新
     *
     * @param v3Config
     * @return
     */
    ResultDto<?> update(MqttV3BrokerConfigDto v3Config);

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
     * @param mqttV3MessageListener
     * @return
     */
    ResultDto<?> subscribe(String brokerId, String topic, int qos, MqttV3MessageListener mqttV3MessageListener);

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
     * 连接服务器
     * @param v3Config
     * @return
     */
    ResultDto<?> connect(MqttV3BrokerConfigDto v3Config);

    /**
     * 查找v3配置
     * @return
     */
    List<MqttV3BrokerConfigDto> findList();
}
