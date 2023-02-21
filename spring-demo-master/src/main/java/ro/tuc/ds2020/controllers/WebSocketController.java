package ro.tuc.ds2020.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import ro.tuc.ds2020.websockets.Message;


@Controller
public class WebSocketController {

    @Autowired
    SimpMessagingTemplate simpMessagingTemplate;

    public void notificationFunction(Message message, int userId) throws Exception {
        Thread.sleep(5000);
        simpMessagingTemplate.convertAndSend("/topic" , "LIMIT EXCEEDED");
    }

}
