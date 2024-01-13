package com.example.demo.chat;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface ChatroomService
{
    public List<Chatroom> getAllChatrooms();

    public Chatroom getChatroomId(long id);

    public ResponseEntity<Chatroom> newChat(Chatroom chatroom);

    public ResponseEntity<Chatroom> updateChat(long id, Chatroom chatroom);
    
    public ResponseEntity<Chatroom> ChatAddUser(long idChat, long idUser);    	
    
    public ResponseEntity<Chatroom> ChatDeleteUser(long idChat,  long idUser);
    
    public ResponseEntity<Chatroom> GenerateGeneralChat(long idChat);
    
    public ResponseEntity<String> deleteChatroom(long id);

}
