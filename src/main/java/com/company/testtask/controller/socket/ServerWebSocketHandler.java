package com.company.testtask.controller.socket;

import com.company.testtask.service.UserService;
import com.company.testtask.service.dto.UserResponseDto;
import com.company.testtask.service.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.SubProtocolCapable;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.security.Principal;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class ServerWebSocketHandler extends TextWebSocketHandler implements SubProtocolCapable {

    private final UserService userService;

    private static Map<UserResponseDto, WebSocketSession> sessions;

    static {
        sessions = new HashMap<>();
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        UserResponseDto authUser = findAuthUser(session);
        sessions.put(authUser, session);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        UserResponseDto authUser = findAuthUser(session);
        sessions.remove(authUser);
    }

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        session.sendMessage(message);
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) {

    }

    @Override
    public List<String> getSubProtocols() {
        return Collections.singletonList("subprotocol.demo.websocket");
    }

    public void sendMessage(Authentication authentication) throws Exception {
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        UserResponseDto userAuth = userService.findByLogin(userDetails.getUsername());
        WebSocketSession session = sessions.get(userAuth);
        TextMessage message =
                new TextMessage("{\"user\":{\"id\":\""+ userAuth.getId() + "\"," +
                        "\"login\":\"" + userAuth.getLogin() + "\"," +
                        "\"fullName\":\"" + userAuth.getFullName() +
                        "\"},\"action\":\"user request GET/users\"}");
        handleTextMessage(session, message);
    }

    private UserResponseDto findAuthUser(WebSocketSession session) {
        Principal principal = session.getPrincipal();
        String login = principal.getName();
        return userService.findByLogin(login);
    }
}
