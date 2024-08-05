package com.yunext.storage.dao;

import com.yunext.api.po.MqttV3BrokerConfig;
import com.yunext.common.mongodb.BaseDao;
import org.springframework.stereotype.Repository;

/**
 * @author ：qianjb [qianjb@hadlinks.com]
 * @description ：
 * @date ：Created in 2024/7/17 11:39
 */
@Repository
public class MqttStorageV3Dao extends BaseDao<MqttV3BrokerConfig> {

    public MqttStorageV3Dao() {
        super(MqttV3BrokerConfig.class);
    }
}
