package com.wanxian.websocket;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 聊天室服务端
 */
@ServerEndpoint("/chat-room/{username}")
public class ChatRoomServerEndpoint {
    private static Map<String, Session> livingSessions = new ConcurrentHashMap<String, Session>();

    @OnOpen
    public void onOpen(@PathParam("username") String username, Session session) {
        String sessionId = session.getId();
        livingSessions.put(sessionId, session);
        //sendText(session, "欢迎用户" + username + "来到聊天室");
        sendTextAll("欢迎用户" + username + "来到聊天室");

    }

    @OnMessage
    public void onMessage(@PathParam("username") String username, Session session, String message) {
        sendTextAll("用户" + username + "：" + message);
    }

    private void sendTextAll(String message) {
        livingSessions.forEach((sessionId, session) -> {
            sendText(session, message);
        });
    }

    @OnClose
    public void onClose(@PathParam("username") String username, Session session) {
        livingSessions.remove(session.getId());
        sendTextAll("用户" + username + "已经离开了聊天室");
    }

    public void sendText(Session session, String message) {
        RemoteEndpoint.Basic basic = session.getBasicRemote();
        try {
            basic.sendText(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
