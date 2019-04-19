package com.wanxian.websocket;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;

/**
 * 聊天室服务端
 */
@ServerEndpoint("/chat-room/{username}")
public class ChatRoomServerEndpoint {
    @OnOpen
    public void onOpen(@PathParam("username") String username, Session session) {
        sendText(session, "欢迎用户" + username + "来到聊天室");
    }

    @OnMessage
    public void onMessage(@PathParam("username") String username, Session session, String message) {
        sendText(session, "用户" + username + "：" + message);
    }

    @OnClose
    public void onClose(@PathParam("username") String username, Session session) {

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
