package com.example.demo.SocketChat;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.example.demo.chat.Chatroom;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Entity
@Table(name = "messages")
@Data
public class Message {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	@Size(max = 100)
	@Column
	private String userName;

	@NotNull
	@Lob
	private String content;

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "sent")
	private Date sent = new Date();

	@ManyToOne
	@JsonIgnore
	private Chatroom chat;
	
	public Message() {
	};

	public Message(String userName, String content) {
		this.userName = userName;
		this.content = content;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getSent() {
		return sent;
	}

	public void setSent(Date sent) {
		this.sent = sent;
	}

	

//	public enum MessageType {
//		CHAT, JOIN, LEAVE
//	}
//
//	private MessageType messageType;
//	private String content;
//	private String sender;
//
//	public MessageType getType() {
//		return messageType;
//	}
//
//	public void setType(MessageType messageType) {
//		this.messageType = messageType;
//	}
//
//	public String getContent() {
//		return content;
//	}
//
//	public void setContent(String content) {
//		this.content = content;
//	}
//
//	public String getSender() {
//		return sender;
//	}
//
//	public void setSender(String sender) {
//		this.sender = sender;
//	}
}