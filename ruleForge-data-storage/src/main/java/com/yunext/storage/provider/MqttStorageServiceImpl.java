package com.yunext.storage.provider;

import com.yunext.api.dto.MqttBrokerConfigDto;
import com.yunext.api.dto.MqttV3BrokerConfigDto;
import com.yunext.api.dto.MqttV5BrokerConfigDto;
import com.yunext.api.dto.ResultDto;
import com.yunext.api.po.MqttBrokerConfig;
import com.yunext.api.po.MqttV3BrokerConfig;
import com.yunext.api.po.MqttV5BrokerConfig;
import com.yunext.api.service.storage.MqttStorageService;
import com.yunext.common.enums.MqttProtocolEnum;
import com.yunext.common.utils.ModelMapperUtil;
import com.yunext.storage.dao.MqttStorageV3Dao;
import com.yunext.storage.dao.MqttStorageV5Dao;
import jakarta.annotation.Resource;
import org.apache.dubbo.config.annotation.DubboService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.query.Query;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author ：qianjb [qianjb@hadlinks.com]
 * @description ：
 * @date ：Created in 2024/7/17 11:26
 */
@DubboService
public class MqttStorageServiceImpl implements MqttStorageService {

    @Resource
    private MqttStorageV3Dao mqttStorageV3Dao;

    @Resource
    private MqttStorageV5Dao mqttStorageV5Dao;

    private static final Logger log = LoggerFactory.getLogger(MqttStorageServiceImpl.class);

    @Override
    public ResultDto<?> delete(String id, String protocolType) {
        boolean delete =  switch (MqttProtocolEnum.valueOf(protocolType)) {
            case v3 -> mqttStorageV3Dao.removeById(id) > 0;
            case v5 -> mqttStorageV5Dao.removeById(id) > 0;
        };
        return ResultDto.excute(delete);
    }

    @Override
    public List<MqttBrokerConfigDto> findList() {
        List<MqttBrokerConfig> v3Config = mqttStorageV3Dao.find(new Query()).stream().map(item -> (MqttBrokerConfig) item).toList();
        List<MqttBrokerConfig> v5Config = mqttStorageV5Dao.find(new Query()).stream().map(item -> (MqttBrokerConfig) item).toList();
        v3Config.addAll(v5Config);
        return v3Config.stream()
                .map(d -> ModelMapperUtil.map(d, MqttBrokerConfigDto.class)).toList();
    }

    @Override
    public MqttV3BrokerConfigDto detailV3(String id) {
        MqttV3BrokerConfig config = mqttStorageV3Dao.findById(id);
        if (config == null) {
            throw new RuntimeException("v3 config not existed!");
        }
        return ModelMapperUtil.map(config, MqttV3BrokerConfigDto.class);
    }

    @Override
    public MqttV5BrokerConfigDto detailV5(String id) {
        MqttV5BrokerConfig config = mqttStorageV5Dao.findById(id);
        if (config == null) {
            throw new RuntimeException("v5 config not existed!");
        }
        return ModelMapperUtil.map(config, MqttV5BrokerConfigDto.class);
    }

    @Override
    public ResultDto<?> saveV3(MqttV3BrokerConfigDto mqttV3BrokerConfigDto) {
        MqttV3BrokerConfig save = mqttStorageV3Dao.save(mqttV3BrokerConfigDto);
        return ResultDto.excute(save != null);
    }

    @Override
    public ResultDto<?> saveV5(MqttV5BrokerConfigDto mqttV5BrokerConfigDto) {
        MqttV5BrokerConfig save = mqttStorageV5Dao.save(mqttV5BrokerConfigDto);
        return ResultDto.excute(save != null);
    }

    @Override
    public List<MqttV3BrokerConfigDto> findListV3() {
        List<MqttV3BrokerConfigDto> list = new ArrayList<>();
        try {
            List<MqttV3BrokerConfig> mqttV3BrokerConfigs = Optional.ofNullable(mqttStorageV3Dao.find(new Query())).orElse(new ArrayList<>());
            list = mqttV3BrokerConfigs.stream().map(item -> ModelMapperUtil.map(item, MqttV3BrokerConfigDto.class)).toList();
        } catch (Throwable throwable) {
            log.warn("find mqtt v3 config error:", throwable);
        }
        return list;
    }

    @Override
    public List<MqttV5BrokerConfigDto> findListV5() {
        List<MqttV5BrokerConfig> mqttV5BrokerConfigs = Optional.ofNullable(mqttStorageV5Dao.find(new Query())).orElse(new ArrayList<>());
        return mqttV5BrokerConfigs.stream().map(item -> ModelMapperUtil.map(item, MqttV5BrokerConfigDto.class)).toList();
    }
}
