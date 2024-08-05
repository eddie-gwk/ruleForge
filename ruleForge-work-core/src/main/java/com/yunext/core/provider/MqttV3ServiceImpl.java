package com.yunext.core.provider;

import com.alibaba.fastjson2.JSONObject;
import com.yunext.api.dto.MqttV3BrokerConfigDto;
import com.yunext.api.dto.ResultDto;
import com.yunext.api.listener.MqttV3MessageListener;
import com.yunext.api.service.core.MqttV3Service;
import com.yunext.api.service.storage.MqttStorageService;
import com.yunext.common.utils.StringUtil;
import com.yunext.core.mqtt.factory.MqttV3Factory;
import jakarta.annotation.PostConstruct;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.config.annotation.DubboService;
import org.eclipse.paho.client.mqttv3.MqttAsyncClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author ：qianjb [qianjb@hadlinks.com]
 * @description ：
 * @date ：Created in 2024/7/15 17:43
 */
@DubboService
public class MqttV3ServiceImpl implements MqttV3Service {

    private final static String BROKER_PREFIX = "mqtt_broker_v3";

    private final static Logger log = LoggerFactory.getLogger(MqttV3ServiceImpl.class);

    private final static Map<String, MqttAsyncClient> V3_CLIENTS = new ConcurrentHashMap<>();

    @DubboReference
    private MqttStorageService mqttStorageService;

    @PostConstruct
    public void startUpMqtt() {
        log.info("------------------------->start up mqtt v3 client<-------------------------");
        List<MqttV3BrokerConfigDto> configDtoList = mqttStorageService.findListV3();
        for (MqttV3BrokerConfigDto mqttV3BrokerConfigDto : configDtoList) {
            this.startUp(mqttV3BrokerConfigDto);
        }
    }


    @Transactional
    @Override
    public ResultDto<String> create(MqttV3BrokerConfigDto v3Config) {
        return this.createAndStartUp(v3Config, true);
    }

    @Override
    public ResultDto<String> startUp(MqttV3BrokerConfigDto v3Config) {
        return this.createAndStartUp(v3Config, false);
    }

    private ResultDto<String> createAndStartUp(MqttV3BrokerConfigDto v3Config, boolean save) {
        String brokerId = v3Config.getBrokerId();
        if (StringUtil.isEmpty(brokerId)) {
            brokerId = BROKER_PREFIX + StringUtil.uuid();
        }
        if (V3_CLIENTS.containsKey(brokerId)) {
            return ResultDto.fail("broker already exists, retry to create");
        }
        if (save) {
            mqttStorageService.saveV3(v3Config);
        }
        try {
            V3_CLIENTS.put(brokerId, MqttV3Factory.createClient(v3Config));
        } catch (Throwable throwable) {
            log.warn("create mqtt v3 client error:", throwable);
        }
        log.info("start up mqtt v3 client:" + String.format("id=%s, brokerId=%s, name=%s" , v3Config.getId(), v3Config.getBrokerId(), v3Config.getName()));
        return ResultDto.success(brokerId);
    }

    @Override
    public ResultDto<?> update(MqttV3BrokerConfigDto v3Config) {
        String brokerId = v3Config.getBrokerId();
        this.remove(brokerId);
        mqttStorageService.saveV3(v3Config);
        V3_CLIENTS.put(brokerId, MqttV3Factory.createClient(v3Config));
        return ResultDto.success();
    }

    @Override
    public ResultDto<?> remove(String brokerId) {
        if (!V3_CLIENTS.containsKey(brokerId)) {
            return ResultDto.success("broker not exists");
        }
        MqttAsyncClient mqttAsyncClient = V3_CLIENTS.get(brokerId);
        //清除一下broker占用的资源
        try {
            mqttAsyncClient.close(true);
        } catch (MqttException e) {
            log.error("close v3 mqtt client error,", e);
        }
        V3_CLIENTS.remove(brokerId);
        return ResultDto.success();
    }

    @Override
    public ResultDto<?> subscribe(String brokerId, String topic, int qos, MqttV3MessageListener mqttV3MessageListener) {
        if (!V3_CLIENTS.containsKey(brokerId)) {
            return ResultDto.fail("mqtt v3 broker not exists");
        }
        MqttAsyncClient mqttAsyncClient = V3_CLIENTS.get(brokerId);
        if (!mqttAsyncClient.isConnected()) {
            return ResultDto.fail("mqtt v3 broker is not connected.");
        }
        try {
            mqttAsyncClient.unsubscribe(topic);
            mqttAsyncClient.subscribe(topic, qos, mqttV3MessageListener);
        } catch (MqttException e) {
            log.error("mqtt v3 subscribe topic [" + topic + "] error, ", e);
            return ResultDto.fail("mqtt v3 subscribe error");
        }
        return ResultDto.success();
    }

    @Override
    public ResultDto<?> publish(String brokerId, String topic, int qos, boolean retained, Map<Object, Object> payload) {
        if (!V3_CLIENTS.containsKey(brokerId)) {
            return ResultDto.fail("mqtt v3 broker not exists");
        }
        MqttAsyncClient mqttAsyncClient = V3_CLIENTS.get(brokerId);
        if (!mqttAsyncClient.isConnected()) {
            return ResultDto.fail("mqtt v3 broker is not connected.");
        }
        try {
            String json = JSONObject.toJSONString(payload);
            mqttAsyncClient.publish(topic, json.getBytes(StandardCharsets.UTF_8), qos, retained);
        } catch (MqttException e) {
            log.error("mqtt v3 subscribe topic [" + topic + "] error, ", e);
            return ResultDto.fail("mqtt v3 subscribe error");
        }
        return ResultDto.success();
    }

    @Override
    public ResultDto<?> connect(MqttV3BrokerConfigDto v3Config) {
        String brokerId = v3Config.getBrokerId();
        if (!V3_CLIENTS.containsKey(brokerId)) {
            return ResultDto.fail("mqtt v3 broker not exists");
        }
        MqttAsyncClient mqttAsyncClient = V3_CLIENTS.get(brokerId);
        MqttConnectOptions options = new MqttConnectOptions();
        options.setCleanSession(v3Config.isCleanSession());
        options.setAutomaticReconnect(v3Config.isAutoRetryConnect());
        options.setKeepAliveInterval(v3Config.getKeepAliveDuration());
        //验权相关
        options.setUserName(v3Config.getUserName());
        options.setPassword(v3Config.getPassword().toCharArray());
        try {
            mqttAsyncClient.connect(options);
        } catch (MqttException e) {
            log.error("mqtt v3 connect failed, ", e);
        }
        return ResultDto.success();
    }

    @Override
    public List<MqttV3BrokerConfigDto> findList() {
        return mqttStorageService.findListV3();
    }
}
