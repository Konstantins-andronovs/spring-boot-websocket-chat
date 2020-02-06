package com.example.springbootwebsocketchat.chat;

import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * WebSocket Server
 *
 * @see ServerEndpoint WebSocket Client
 * @see Session   WebSocket Session
 */

@Component
@ServerEndpoint(
        value = "/chat/{username}",
        decoders = MessageDecoder.class,
        encoders = MessageEncoder.class
)
public class WebSocketChatServer {

    /**
     * All chat sessions.
     */
    private static Map<String, Session> onlineSessions = new ConcurrentHashMap<>();

    private static void broadcast(Message msg) {
        onlineSessions.forEach((username, session) -> {
            synchronized (session) {
                try {
                    session.getBasicRemote().sendObject(msg);
                } catch (IOException | EncodeException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Open connection
     */
    @OnOpen
    public void onOpen(Session session, @PathParam("username") String username) throws IOException {
        onlineSessions.put(username, session);
        Message msg = new Message(username, "Connected", onlineSessions.size());
        broadcast(msg);
    }

    /**
     * Send message
     */
    @OnMessage
    public void onMessage(Session session, Message message) throws IOException {
        message.setOnlineCount(onlineSessions.size());
        broadcast(message);
    }

    /**
     * Close connection
     */
    @OnClose
    public void onClose(Session session, @PathParam("username") String username) throws IOException {
        onlineSessions.remove(username);
        Message message = new Message();
        broadcast(message);

    }

    /**
     * Print exception.
     */
    @OnError
    public void onError(Session session, Throwable error) {
        error.printStackTrace();
    }

}
