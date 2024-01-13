package com.example.demo.message;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ChatController
{
    private SimpMessagingTemplate simpMessagingTemplate;

    @MessageMapping("/message") // /app/message
    @SendTo("/chatroom/public")
    public Chat receivePublicMessage(@Payload Chat chat)
    {
        return chat;
    }

    @MessageMapping("/private-message")
    public Chat receivePrivateMessage(@Payload Chat chat)
    {
        simpMessagingTemplate.convertAndSendToUser(chat.getReciverName(), "/private",chat); // /user/{NAME}/private
        return chat;
    }
}
