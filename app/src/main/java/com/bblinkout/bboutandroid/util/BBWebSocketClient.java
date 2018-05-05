package com.bblinkout.bboutandroid.util;

import java.util.Scanner;

/*import org.springframework.messaging.converter.StringMessageConverter;
import org.springframework.messaging.simp.stomp.StompSessionHandler;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
import org.springframework.scheduling.concurrent.ConcurrentTaskScheduler;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;*/

public class BBWebSocketClient {
    /*public static void start(StompSessionHandlerAdapter stompSessionHandlerAdapter) {
        org.springframework.web.socket.client.WebSocketClient webSocketClient = new StandardWebSocketClient();
        WebSocketStompClient stompClient = new WebSocketStompClient(webSocketClient);
        //stompClient.setMessageConverter(new MappingJackson2MessageConverter());
        stompClient.setMessageConverter(new StringMessageConverter());
        stompClient.setTaskScheduler(new ConcurrentTaskScheduler());
        String url = BBConstants.BASE_WS_URL+"bbserver-websocket";
        StompSessionHandler sessionHandler = stompSessionHandlerAdapter;
        stompClient.connect(url, sessionHandler);
        new Scanner(System.in).nextLine(); //Don't close immediately.
    }*/
}
