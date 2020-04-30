package com.zl.websocket.handle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.*;

/**
 * @author zl
 * @date 2019/2/12.
 */
public class SimpleWebSocketHandler extends TextWebSocketHandler {

    private Logger logger = LoggerFactory.getLogger(SimpleWebSocketHandler.class);

    private static Map<String,Set<WebSocketSession>> map = new HashMap<>();

    public SimpleWebSocketHandler() {
    }

    /**
     * 连接成功时候
     */
    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        String chatId= (String) session.getAttributes().get("chatId");
        if(map.containsKey(chatId)){
            map.get(chatId).add(session);
        }else {
            Set<WebSocketSession> set = new HashSet<>();
            set.add(session);
            map.put(chatId,set);
        }
    }

    /**
     * 关闭连接时触发
     */
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
        String chatId= (String) session.getAttributes().get("chatId");
        map.get(chatId).remove(session);
    }

    /**
     * 收到客户端消息触发
     */
    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message)
            throws Exception {
        String msg = message.getPayload();
        String chatId= (String) session.getAttributes().get("chatId");
        sendMessageToUsers(new TextMessage(msg),chatId,session);
    }

    /**
     * 抛出异常时处理
     * @param session
     * @param exception
     * @throws Exception
     */
    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception)
            throws Exception {
        session.close(CloseStatus.SERVER_ERROR);
        if(session.isOpen()){
            session.close();
        }
        String chatId= (String) session.getAttributes().get("chatId");
        map.get(chatId).remove(session);
    }


    /**
     * 给所有在线用户发送消息,不包含本人
     */
    public void sendMessageToUsers(TextMessage message,String chatId,WebSocketSession self) {
        Set<WebSocketSession> users = map.get(chatId);
        for (WebSocketSession user : users) {
            if(!self.getId().equals(user.getId())){
                try {
                    if (user.isOpen()) {
                        user.sendMessage(message);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
