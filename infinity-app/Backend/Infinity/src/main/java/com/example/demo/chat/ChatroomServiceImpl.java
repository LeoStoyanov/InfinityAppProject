package com.example.demo.chat;

import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.guild.GuildService;
import com.example.demo.user.User;
import com.example.demo.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * 
 * 
 * @author ascase
 *
 */
@Service
public class ChatroomServiceImpl implements ChatroomService
{
    @Autowired
    private ChatroomRepository chatroomRepository;
    @Autowired
    private UserService userService;

    /**
     * Gets all chatrooms
     */
    @Override
    public List<Chatroom> getAllChatrooms()
    {
        return chatroomRepository.findAll();
    }
    
    /**
     * Gets a chatroom by an id or throws a error
     */
    @Override
    public Chatroom getChatroomId(long id)
    {
        Optional<Chatroom> Chatroom = chatroomRepository.findById(id);
        if (Chatroom.isPresent()) {
            return Chatroom.get();
        } else {
            throw new ResourceNotFoundException("Chatroom", "ID", id);
        }
    }
    
    /**
     * 
     */
    @Override
    public ResponseEntity<Chatroom> newChat(Chatroom chatroom)
    {
        return new ResponseEntity<>(chatroomRepository.save(chatroom), HttpStatus.CREATED);
    }

    /**
     * updates a chatroom 
     */
    @Override
    public ResponseEntity<Chatroom> updateChat(long id, Chatroom chatroom)
    {
        Chatroom existingChatroom = chatroomRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Chatroom", "ID", id));
        existingChatroom.setChatroom(chatroom.getChatroom());
        existingChatroom.setChatusers(chatroom.getChatusers());
        chatroomRepository.save(existingChatroom);
        
        return new ResponseEntity<Chatroom>(existingChatroom, HttpStatus.OK);
    }
    
    @Override
	public ResponseEntity<Chatroom> GenerateGeneralChat(long idChat) {
    	Chatroom existingChatroom = getChatroomId(idChat);
    	existingChatroom.AllGuildChatUsers();
		chatroomRepository.save(existingChatroom);
		return new ResponseEntity<Chatroom>(existingChatroom, HttpStatus.OK);
	}
    
    /**
     * Adds a user to a chatroom
     */
    
    @Override
	public ResponseEntity<Chatroom> ChatAddUser(long idChat, long idUser) {
    	Chatroom chat = getChatroomId(idChat);
		User user = userService.getUserById(idUser);
		chat.getChatusers().add(user);
		userService.saveUser(user);
		chatroomRepository.save(chat);
		return new ResponseEntity<Chatroom>(chat, HttpStatus.OK);
	}
    
    /**
     * Removes a user to a chatroom
     */
	@Override
	public ResponseEntity<Chatroom> ChatDeleteUser(long idChat, long idUser) {
		Chatroom chat = getChatroomId(idChat);
		User user = userService.getUserById(idUser);
		chat.getChatusers().remove(user);
		userService.saveUser(user);
		chatroomRepository.save(chat);
		return new ResponseEntity<Chatroom>(chat, HttpStatus.OK);
	}
    
    /**
     * Deletes a chatroom by its id
     */
    @Override
    public ResponseEntity<String> deleteChatroom(long id)
    {	
    	Chatroom chat = getChatroomId(id);
    	chat.getChatusers().clear();
    	chatroomRepository.save(chat);
    	chatroomRepository.deleteById(id);
        return new ResponseEntity<String>("Chatroom termination successfull!", HttpStatus.OK);
    }
    
}
