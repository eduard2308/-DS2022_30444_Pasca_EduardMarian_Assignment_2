package ro.tuc.ds2020.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import ro.tuc.ds2020.websockets.Message;

@Controller
public class ChatController {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/chat.receiveMessage")
    public void receiveMessage(@Payload Message message) {
        if(message.isTyping()){
            System.out.println(message.getSender() + "is typing...");
        }
    }

    @MessageMapping("/chat.sendMessage")
    public void sendMessage(Message message) {
        messagingTemplate.convertAndSend("/topic/public", message);
    }

    @MessageMapping("/chat.typing")
    public void typing(Message message) {
        messagingTemplate.convertAndSend("/topic/typing", message);
    }
}
