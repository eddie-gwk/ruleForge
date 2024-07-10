package com.yunext.client.web.provider;

import com.yunext.api.dto.ResultDto;
import com.yunext.api.dto.WebSocketMessageDto;
import com.yunext.api.service.web.WebSocketService;
import org.apache.dubbo.config.annotation.DubboService;

import java.util.Map;

/**
 * @author ：qianjb [qianjb@hadlinks.com]
 * @description ：
 * @date ：Created in 2024/7/10 17:09
 */
@DubboService
public class WebSocketServiceImpl implements WebSocketService {

    @Override
    public ResultDto<?> sendMessage(WebSocketMessageDto webSocketMessageDto) {
        return null;
    }
}
