package com.yunext.storage.dao;

import com.yunext.api.po.MqttV5BrokerConfig;
import com.yunext.common.mongodb.BaseDao;
import org.springframework.stereotype.Repository;

/**
 * @author ：qianjb [qianjb@hadlinks.com]
 * @description ：
 * @date ：Created in 2024/7/17 11:39
 */
@Repository
public class MqttStorageV5Dao extends BaseDao<MqttV5BrokerConfig> {

    public MqttStorageV5Dao() {
        super(MqttV5BrokerConfig.class);
    }
}
