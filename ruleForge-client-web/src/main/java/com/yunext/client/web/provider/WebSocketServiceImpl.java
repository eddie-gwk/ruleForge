package com.yunext.client.web.provider;

import com.yunext.api.dto.ResultDto;
import com.yunext.api.dto.WebSocketMessageDto;
import com.yunext.api.service.web.WebSocketService;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author ：qianjb [qianjb@hadlinks.com]
 * @description ：
 * @date ：Created in 2024/7/10 17:09
 */
@DubboService
public class WebSocketServiceImpl implements WebSocketService {

    private final static Map<String, WebSocketSession> SESSION_MAP = new ConcurrentHashMap<>();

    private static final AtomicInteger ONLINE_COUNT = new AtomicInteger(0);

    @Override
    public void sendMessage(WebSocketMessageDto<?> webSocketMessageDto) {
        SESSION_MAP.values().forEach(session -> {
            try {
                session.sendMessage(webSocketMessageDto);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    /**
     * websocket 接口实现
     */

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        SESSION_MAP.put(session.getId(), session);
        ONLINE_COUNT.incrementAndGet();
    }

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
        System.out.println("接受到消息" +message);
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        System.out.println("发生错误");
        exception.printStackTrace();
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
        System.out.println("连接关闭");
        SESSION_MAP.remove(session.getId());

    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }
}
