package com.example.demo.SocketDraw;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.example.demo.draw.DrawGuild;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Entity
@Table(name = "canvas")
@Data
public class Canvas {

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

	@ManyToOne
	@JsonIgnore
	private DrawGuild hostcanvas;

	public Canvas() {
	};

	public Canvas(String userName, String content) {
		this.userName = userName;
		this.content = content;
	}
	public Canvas(String userName, String content, DrawGuild hostcanvas) {
		this.userName = userName;
		this.content = content;
		this.hostcanvas = hostcanvas;
	}

	public Long getId(String userName, String content) {
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
	
	public DrawGuild getHostcanvas() {
		return hostcanvas;
	}

	public void setHostcanvas(DrawGuild hostcanvas) {
		this.hostcanvas = hostcanvas;
	}
}
