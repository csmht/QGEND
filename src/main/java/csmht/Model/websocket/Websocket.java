package csmht.Model.websocket;

import com.alibaba.fastjson2.JSON;
import csmht.dao.*;
import csmht.dao.classobject.UserClass;
import csmht.dao.classobject.WebsocketClass;

import javax.servlet.http.HttpSession;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.sql.Connection;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@ServerEndpoint(value = "/chat", configurator = GetHttpSessions.class)
public class Websocket {
    private static final Map<String, Session> sessions = new ConcurrentHashMap<>();
    private static final Map<String, String> userSessionMap = new HashMap<>();
    private String form;

    @OnOpen
    public void onOpen(Session session, EndpointConfig endpointConfig) {
        Connection con = null;
        try {
            con = Pool.Pool.getPool();

            HttpSession httpsession = (HttpSession) endpointConfig.getUserProperties().get("Httpsession");

            String form = (String) httpsession.getAttribute("id");
            this.form = form;
            sessions.put(form, session);

            userSessionMap.put(session.getId(), form);

            List<WebsocketClass> websocketClasses = new ArrayList<>();
            WebsocketClass wc = new WebsocketClass();

            websocketClasses = WebsocketClass.Read(form);

            List<UserClass> users = new ArrayList<>();

            Set<String> user = JedisTool.FindFollowUser(form).keySet();
            for (String u : user) {
                if(JedisTool.isFollowUser(u, form)) {
                    if(Objects.equals(u, form)) {
                        continue;
                    }
                    users.add(Find.FindUser(con,"user_id",u,""));
                }
            }

            WebsocketClass wc2 = new WebsocketClass();
            wc2.setUsers(users);
            wc2.setWebsocketClasses(websocketClasses);

            String Json = JSON.toJSONString(wc2);
            session.getAsyncRemote().sendText(Json);

        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if(con != null) {
                Pool.Pool.returnConn(con);
            }
        }
    }

    @OnMessage
    public void onMessage(String message, Session session) {
        WebsocketClass wc = JSON.parseObject(message, WebsocketClass.class);



        try {
            String userId = userSessionMap.get(session.getId());
            wc.setForm(Integer.parseInt(userId));
            WebsocketClass.Save(wc);


            int to = wc.getTo();
            String Json = JSON.toJSONString(wc);


            Session se = sessions.get(to + "");

            if (se != null && se.isOpen()) {
                se.getAsyncRemote().sendText(Json);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @OnClose
    public void onClose(Session session, CloseReason closeReason) {
        String userId = userSessionMap.get(session.getId());
        if (userId != null) {
            sessions.remove(userId);
            userSessionMap.remove(session.getId());
        }

    }

    @OnError
    public void onError(Session session, Throwable throwable) {
        System.err.println("WebSocket 发生错误: " + throwable.getMessage());
        // 可以在这里添加更详细的错误处理逻辑，比如记录日志、通知用户等
        try {
            if (session != null && session.isOpen()) {
                session.close();
            }
        } catch (IOException e) {
            System.err.println("关闭连接时出错: " + e.getMessage());
        }
    }
}
