package com.example.demo.chat;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.example.demo.SocketChat.Message;
import com.example.demo.guild.Guild;
import com.example.demo.tableLinks.GuildLink;
import com.example.demo.user.User;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * A place where messaging and conversations in guild's are stored in.
 * 
 * @author ascase
 *
 */

@Entity
@Table(name = "chatrooms")
public class Chatroom {

	/*
	 * Generating unique ID's for each chat channel.
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column(name = "chatname", nullable = false)
	private String chatname;

	/*
	 * @ManyToOne tells springboot that multiple instances of Chatroom can map to
	 * one instance of OR multiple rows of the Chatroom table can map to one guild
	 * row
	 * 
	 * @JoinColumn specifies the ownership of the key i.e. The Chatroom table will
	 * contain a foreign key from the Guild table and the column name will be
	 * Guild_id
	 * 
	 * @JsonIgnore is to assure that there is no infinite loop while returning
	 * either user/Chatroom objects (Chatroom->Guild->[Chatroom]->...)
	 */

	@ManyToOne
	@JoinColumn(name = "Guild_id")
	@JsonIgnore
	private Guild HostGuild;

	@OneToMany
	private List<Message> messeages;
		
	/**
	 * A chat can have many user and a user can have many chats
	 */
	@OneToMany
	@JsonIgnore
	private List<User> chatusers;

	public Chatroom(String chatname, Guild HostGuild) {
		this.HostGuild = HostGuild;
		this.chatname = chatname;
		messeages = new ArrayList<Message>();
		chatusers = new ArrayList<User>();
	}

	public Chatroom() {
		messeages = new ArrayList<Message>();
		chatusers = new ArrayList<User>();
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getChatroom() {
		return chatname;
	}

	public void setChatroom(String chatname) {
		this.chatname = chatname;
	}

	public Guild getGuild() {
		return HostGuild;
	}

	public void setGuild(Guild HostGuild) {
		this.HostGuild = HostGuild;
	}

	public List<Message> getMessageList() {
		return messeages;
	}

	public void setMessageList(List<Message> messeages) {
		this.messeages = messeages;
	}

	public void addMessageToList(Message msg) {
		messeages.add(msg);
	}

//	@JsonIgnore
	public List<User> getChatusers() {
		return chatusers;
	}
	
	public void setChatusers(List<User> chatusers) {
		 this.chatusers = chatusers;
	}
	
	public void AllGuildChatUsers() {
		List<GuildLink> gl = HostGuild.getAllGuildUserLink();
		for (int i = 0; i < gl.size(); i++) {
			if (!(chatusers.contains(gl.get(i).getUsers()))) {
				chatusers.add(gl.get(i).getUsers());
			}
		}
	}

}
