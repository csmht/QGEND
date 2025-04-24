package csmht.Model.websocket;

import org.apache.http.HttpRequest;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpSession;
import javax.websocket.HandshakeResponse;
import javax.websocket.Session;
import javax.websocket.server.HandshakeRequest;
import javax.websocket.server.ServerEndpoint;
import javax.websocket.server.ServerEndpointConfig;

public class GetHttpSessions extends ServerEndpointConfig.Configurator {

    @Override
    public void modifyHandshake(ServerEndpointConfig config, HandshakeRequest request, HandshakeResponse response) {
       HttpSession session = (HttpSession) request.getHttpSession();
       config.getUserProperties().put("Httpsession", session);
    }
}
