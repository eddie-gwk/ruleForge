package com.yunext.client.web.api.mqtt;

import com.yunext.client.web.vo.MqttAclResultVo;
import com.yunext.client.web.vo.MqttAclVo;
import com.yunext.client.web.vo.MqttAuthResultVo;
import com.yunext.client.web.vo.MqttAuthVo;
import com.yunext.common.utils.StringUtil;
import org.apache.dubbo.config.annotation.DubboReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping("/api/mqtt")
public class MqttApiController {
    private static final Logger log = LoggerFactory.getLogger(MqttApiController.class);

    public static final String SER_PASSWD = "YCwn1dr1zCel0zAE";



    @PostMapping("/auth")
    @ResponseBody
    public MqttAuthResultVo auth(
            @RequestBody MqttAuthVo vo) {
        boolean pass = false;
        try {
            log.info("mqtt auth :{}", vo);
            if (vo == null || vo.isAnyEmpty()) {
                return MqttAuthResultVo.DENY_VO;
            }
            final String clientid = vo.getClientid();
            final String password = vo.getPassword();
            final String username = vo.getUsername();
            int index = clientid.indexOf(":");
            if (index == -1) {
                return MqttAuthResultVo.DENY_VO;
            }
            String prefix = clientid.substring(0, index);
            if (prefix.startsWith("SER:")) {
                pass = SER_PASSWD.equalsIgnoreCase(password);
            }
            log.info("mqtt auth pass:{}", pass);
            return pass ? MqttAuthResultVo.ALLOW_VO : MqttAuthResultVo.DENY_VO;
        } catch (Exception e) {
            log.error("mqtt auth err:{}", e.getMessage());
            return MqttAuthResultVo.DENY_VO;
        }
    }

    @PostMapping("/acl")
    @ResponseBody
    public MqttAclResultVo acl(@RequestBody MqttAclVo vo) {
        log.info("MQTT acl :{}", vo);
        return MqttAclResultVo.ALLOW_VO;
    }




}
