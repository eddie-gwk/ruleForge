package com.yunext.api.service.web;

import com.yunext.api.dto.ResultDto;
import com.yunext.api.dto.WebSocketMessageDto;

import java.util.Map;

/**
 * @author ：qianjb [qianjb@hadlinks.com]
 * @description ：
 * @date ：Created in 2024/7/10 17:08
 */
public interface WebSocketService {

    ResultDto<?> sendMessage(WebSocketMessageDto webSocketMessageDto);
}
