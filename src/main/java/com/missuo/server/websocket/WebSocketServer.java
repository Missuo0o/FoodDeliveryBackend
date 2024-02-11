package com.missuo.server.websocket;

import jakarta.websocket.OnClose;
import jakarta.websocket.OnMessage;
import jakarta.websocket.OnOpen;
import jakarta.websocket.Session;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@ServerEndpoint("/ws/{sid}")
@Slf4j
public class WebSocketServer {

  private static Map<String, Session> sessionMap = new HashMap<>();

  @OnOpen
  public void onOpen(Session session, @PathParam("sid") String sid) {
    log.info("onOpen: sid={}", sid);
    sessionMap.put(sid, session);
  }

  @OnMessage
  public void onMessage(String message, @PathParam("sid") String sid) {
    log.info("onMessage: sid={}, message={}", sid, message);
  }

  @OnClose
  public void onClose(@PathParam("sid") String sid) {
    sessionMap.remove(sid);
  }

  public void sendToAllClient(String message) {
    Collection<Session> sessions = sessionMap.values();
    for (Session session : sessions) {
      try {
        session.getBasicRemote().sendText(message);
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }
}
