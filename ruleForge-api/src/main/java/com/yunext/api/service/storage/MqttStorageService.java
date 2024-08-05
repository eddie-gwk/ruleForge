package com.yunext.api.service.storage;



import com.yunext.api.dto.MqttBrokerConfigDto;
import com.yunext.api.dto.MqttV3BrokerConfigDto;
import com.yunext.api.dto.MqttV5BrokerConfigDto;
import com.yunext.api.dto.ResultDto;

import java.util.List;

/**
 * mqtt配置保存
 * @author ：qianjb [qianjb@hadlinks.com]
 * @description ：
 * @date ：Created in 2024/7/17 11:23
 */
public interface MqttStorageService {

    ResultDto<?> delete(String id, String protocolType);

    List<MqttBrokerConfigDto> findList();

    MqttV3BrokerConfigDto detailV3(String id);

    MqttV5BrokerConfigDto detailV5(String id);

    ResultDto<?> saveV3(MqttV3BrokerConfigDto mqttV3BrokerConfigDto);

    ResultDto<?> saveV5(MqttV5BrokerConfigDto mqttV5BrokerConfigDto);

    List<MqttV3BrokerConfigDto> findListV3();

    List<MqttV5BrokerConfigDto> findListV5();
}
