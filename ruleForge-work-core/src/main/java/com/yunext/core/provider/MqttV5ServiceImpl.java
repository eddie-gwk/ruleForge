package com.yunext.core.provider;

import com.alibaba.fastjson2.JSONObject;
import com.yunext.api.dto.MqttV5BrokerConfigDto;
import com.yunext.api.dto.ResultDto;
import com.yunext.api.listener.MqttV5MessageListener;
import com.yunext.api.service.core.MqttV5Service;
import com.yunext.api.service.storage.MqttStorageService;
import com.yunext.common.utils.StringUtil;
import com.yunext.core.mqtt.factory.MqttV5Factory;
import jakarta.annotation.PostConstruct;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.config.annotation.DubboService;
import org.eclipse.paho.mqttv5.client.MqttAsyncClient;
import org.eclipse.paho.mqttv5.client.MqttConnectionOptions;
import org.eclipse.paho.mqttv5.common.MqttException;
import org.eclipse.paho.mqttv5.common.MqttSubscription;
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
public class MqttV5ServiceImpl implements MqttV5Service {

    private final static String BROKER_PREFIX = "mqtt_broker_v5";

    private final static Logger log = LoggerFactory.getLogger(MqttV5ServiceImpl.class);

    private final static Map<String, MqttAsyncClient> V5_CLIENTS = new ConcurrentHashMap<>();

    @DubboReference
    private MqttStorageService mqttStorageService;

    @PostConstruct
    public void startUpMqtt() {
        log.info("------------------------->start up mqtt v5 client<-------------------------");
        List<MqttV5BrokerConfigDto> configDtoList = mqttStorageService.findListV5();
        for (MqttV5BrokerConfigDto mqttV5BrokerConfigDto : configDtoList) {
            this.startUp(mqttV5BrokerConfigDto);
        }
    }

    @Transactional
    @Override
    public ResultDto<String> create(MqttV5BrokerConfigDto v5Config) {
        return this.createAndStartUp(v5Config, true);
    }

    @Override
    public ResultDto<String> startUp(MqttV5BrokerConfigDto v5Config) {
        return this.createAndStartUp(v5Config, false);
    }

    private ResultDto<String> createAndStartUp(MqttV5BrokerConfigDto v5Config, boolean save) {
        String brokerId = BROKER_PREFIX + StringUtil.uuid();
        if (V5_CLIENTS.containsKey(brokerId)) {
            return ResultDto.fail("broker already exists, retry to create");
        }
        if (save) {
            mqttStorageService.saveV5(v5Config);
        }
        V5_CLIENTS.put(brokerId, MqttV5Factory.createClient(v5Config));
        log.info("start up mqtt v5 client:" + String.format("id=%s, brokerId=%s, name=%s" , v5Config.getId(), v5Config.getBrokerId(), v5Config.getName()));
        return ResultDto.success(brokerId);
    }

    @Override
    public ResultDto<?> update(MqttV5BrokerConfigDto v5Config) {
        String brokerId = v5Config.getBrokerId();
        this.remove(brokerId);
        mqttStorageService.saveV5(v5Config);
        V5_CLIENTS.put(brokerId, MqttV5Factory.createClient(v5Config));
        return ResultDto.success();
    }

    @Override
    public ResultDto<?> remove(String brokerId) {
        if (!V5_CLIENTS.containsKey(brokerId)) {
            return ResultDto.success("broker not exists");
        }
        MqttAsyncClient mqttAsyncClient = V5_CLIENTS.get(brokerId);
        //清除一下broker占用的资源
        try {
            mqttAsyncClient.close(true);
        } catch (MqttException e) {
            log.error("close v3 mqtt client error,", e);
        }
        V5_CLIENTS.remove(brokerId);
        return ResultDto.success();
    }

    @Override
    public ResultDto<?> subscribe(String brokerId, String topic, int qos, MqttV5MessageListener mqttV5MessageListener) {
        if (!V5_CLIENTS.containsKey(brokerId)) {
            return ResultDto.fail("mqtt v5 broker not exists");
        }
        MqttAsyncClient mqttAsyncClient = V5_CLIENTS.get(brokerId);
        if (!mqttAsyncClient.isConnected()) {
            return ResultDto.fail("mqtt v5 broker is not connected.");
        }
        try {
            mqttAsyncClient.unsubscribe(topic);
            mqttAsyncClient.subscribe(new MqttSubscription(topic, qos), mqttV5MessageListener);
        } catch (MqttException e) {
            log.error("mqtt v5 subscribe topic [" + topic + "] error, ", e);
            return ResultDto.fail("mqtt v5 subscribe error");
        }
        return ResultDto.success();
    }

    @Override
    public ResultDto<?> publish(String brokerId, String topic, int qos, boolean retained, Map<Object, Object> payload) {
        if (!V5_CLIENTS.containsKey(brokerId)) {
            return ResultDto.fail("mqtt v5 broker not exists");
        }
        MqttAsyncClient mqttAsyncClient = V5_CLIENTS.get(brokerId);
        if (!mqttAsyncClient.isConnected()) {
            return ResultDto.fail("mqtt v5 broker is not connected.");
        }
        try {
            String json = JSONObject.toJSONString(payload);
            mqttAsyncClient.publish(topic, json.getBytes(StandardCharsets.UTF_8), qos, retained);
        } catch (MqttException e) {
            log.error("mqtt v5 subscribe topic [" + topic + "] error, ", e);
            return ResultDto.fail("mqtt v5 subscribe error");
        }
        return ResultDto.success();
    }

    @Override
    public ResultDto<?> connect(MqttV5BrokerConfigDto v5Config) {
        String brokerId = v5Config.getBrokerId();
        if (!V5_CLIENTS.containsKey(brokerId)) {
            return ResultDto.fail("mqtt v5 broker not exists");
        }
        MqttAsyncClient mqttAsyncClient = V5_CLIENTS.get(brokerId);
        MqttConnectionOptions options = new MqttConnectionOptions();
        options.setCleanStart(v5Config.isCleanStart());
        options.setSessionExpiryInterval(v5Config.getSessionExpireInterval());
        options.setUserProperties(MqttV5Factory.cover(v5Config.getUserAttribute()));
        options.setAutomaticReconnect(v5Config.isAutoRetryConnect());
        //鉴权相关
        options.setUserName(v5Config.getUserName());
        options.setPassword(v5Config.getPassword().getBytes(StandardCharsets.UTF_8));
        try {
            mqttAsyncClient.connect(options);
        } catch (MqttException e) {
            log.error("mqtt v5 connect failed, ", e);
        }
        return ResultDto.success();
    }

    @Override
    public List<MqttV5BrokerConfigDto> findList() {
        return mqttStorageService.findListV5();
    }
}
