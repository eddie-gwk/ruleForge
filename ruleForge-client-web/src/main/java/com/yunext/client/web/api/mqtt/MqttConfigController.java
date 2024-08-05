package com.yunext.client.web.api.mqtt;

import com.yunext.api.dto.MqttBrokerConfigDto;
import com.yunext.api.dto.MqttV3BrokerConfigDto;
import com.yunext.api.dto.MqttV5BrokerConfigDto;
import com.yunext.api.dto.ResultDto;
import com.yunext.api.service.core.MqttV3Service;
import com.yunext.api.service.core.MqttV5Service;
import com.yunext.api.service.storage.MqttStorageService;
import com.yunext.api.vo.ApiResultVo;
import com.yunext.common.enums.MqttProtocolEnum;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * mqtt配置管理
 * @author ：qianjb [qianjb@hadlinks.com]
 * @description ：
 * @date ：Created in 2024/7/17 11:52
 */
@RestController
@RequestMapping(value = "/api/web/rule/mqtt")
public class MqttConfigController {

    @DubboReference
    private MqttV3Service mqttV3Service;

    @DubboReference
    private MqttV5Service mqttV5Service;

    @DubboReference
    private MqttStorageService mqttStorageService;


    /**
     * 新增mqtt v3 broker
     * @param mqttV3BrokerConfigDto
     * @return
     */
    @PostMapping(value = "/add/broker/v3")
    public ApiResultVo<?> addBrokerV3(@RequestBody MqttV3BrokerConfigDto mqttV3BrokerConfigDto) {
        ResultDto<String> resultDto = mqttV3Service.create(mqttV3BrokerConfigDto);
        return ApiResultVo.result(resultDto);
    }

    /**
     * 更新mqtt v3 broker
     * @param mqttV3BrokerConfigDto
     * @return
     */
    @PostMapping(value = "/update/broker/v3")
    public ApiResultVo<?> updateBrokerV3(@RequestBody MqttV3BrokerConfigDto mqttV3BrokerConfigDto) {
        ResultDto<?> update = mqttV3Service.update(mqttV3BrokerConfigDto);
        return ApiResultVo.result(update);
    }


    /**
     * 新增mqtt v5 broker
     * @param mqttV5BrokerConfigDto
     * @return
     */
    @PostMapping(value = "/add/broker/v5")
    public ApiResultVo<?> addBrokerV5(@RequestBody MqttV5BrokerConfigDto mqttV5BrokerConfigDto) {
        ResultDto<String> resultDto = mqttV5Service.create(mqttV5BrokerConfigDto);
        return ApiResultVo.result(resultDto);
    }

    /**
     * 更新mqtt v5 broker
     * @param mqttV5BrokerConfigDto
     * @return
     */
    @PostMapping(value = "/update/broker/v5")
    public ApiResultVo<?> updateBrokerV5(@RequestBody MqttV5BrokerConfigDto mqttV5BrokerConfigDto) {
        ResultDto<?> update = mqttV5Service.update(mqttV5BrokerConfigDto);
        return ApiResultVo.result(update);
    }

    /**
     * 删除 mqtt broker
     * @param id
     * @param brokerId
     * @param protocolType
     * @return
     */
    @PostMapping(value = "/delete/broker")
    public ApiResultVo<?> deleteBroker(String id, String brokerId, String protocolType) {
        ResultDto<?> delete = mqttStorageService.delete(id, protocolType);
        if (delete.isSuccess()) {
            switch (MqttProtocolEnum.valueOf(protocolType)) {
                case v3 -> mqttV3Service.remove(brokerId);
                case v5 -> mqttV5Service.remove(brokerId);
            }
        }
        return ApiResultVo.result(delete);
    }


    /**
     * 手动连接mqtt服务器 v3
     *
     * @param config
     * @return
     */
    @PostMapping(value = "/connect/v3")
    public ApiResultVo<?> mqttConnectV3(@RequestBody MqttV3BrokerConfigDto config) {
        ResultDto<?> connect = mqttV3Service.connect(config);
        return ApiResultVo.result(connect);
    }

    /**
     * 手动连接mqtt服务器 v5
     *
     * @param config
     * @return
     */
    @PostMapping(value = "/connect/v5")
    public ApiResultVo<?> mqttConnectV5(@RequestBody MqttV5BrokerConfigDto config) {
        ResultDto<?> connect = mqttV5Service.connect(config);
        return ApiResultVo.result(connect);
    }

    /**
     * 列表
     * @return
     */
    @GetMapping(value = "/list")
    public ApiResultVo<List<MqttBrokerConfigDto>> list() {
        return ApiResultVo.success(mqttStorageService.findList());
    }

}
