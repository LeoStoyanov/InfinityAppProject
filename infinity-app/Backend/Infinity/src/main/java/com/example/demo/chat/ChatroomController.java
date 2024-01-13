package com.example.demo.chat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.guild.Guild;
import com.example.demo.guild.GuildRepository;
import com.example.demo.user.User;
import com.example.demo.user.UserRepository;

import static java.lang.String.format;

import java.util.List;
import java.util.Optional;

/**
 * A controller for chatrooms
 * @author ascase
 *
 */
@Controller
public class ChatroomController {
	//	private static final Logger logger = LoggerFactory.getLogger(ChatroomController.class);
//	@Autowired
//	private SimpMessageSendingOperations messagingTemplate;
	@Autowired
	private ChatroomService chatroomService;


	@GetMapping(path = "/Chatrooms")
	public List<Chatroom> getAllChatrooms() {
		return chatroomService.getAllChatrooms();
	}

	@GetMapping(path = "/Chatrooms/{id}")
	public Chatroom getChatroomById(@PathVariable("id") long id) {
		return chatroomService.getChatroomId(id);
	}

	@PostMapping(path = "/Chatrooms")
	public ResponseEntity<Chatroom> newChat(@RequestBody Chatroom chatroom) {
		return chatroomService.newChat(chatroom);
	}

	@PutMapping(path = "/Chatrooms/{id}")
	public ResponseEntity<Chatroom> updateChat(@PathVariable("id") long id, @RequestBody Chatroom chatroom) {
		return chatroomService.updateChat(id, chatroom);
	}

	@PutMapping(path = "/guilds/{id}/ChatroomsAdd/{idChat}/users/{idUser}")
	public ResponseEntity<Chatroom> ChatAddUser(@PathVariable long idChat, @PathVariable long idUser) {
		return chatroomService.ChatAddUser(idChat, idUser);
	}

	@PutMapping(path = "/guilds/{id}/ChatDeleteUser/{idChat}/users/{idUser}")
	public ResponseEntity<Chatroom> ChatDeleteUser(@PathVariable long idChat, @PathVariable long idUser) {
		return chatroomService.ChatDeleteUser(idChat, idUser);
	}

	@DeleteMapping(path = "/Chatrooms/{id}")
	public ResponseEntity<String> deleteCatroom(@PathVariable("id") long id) {
		return chatroomService.deleteChatroom(id);
	}


}
