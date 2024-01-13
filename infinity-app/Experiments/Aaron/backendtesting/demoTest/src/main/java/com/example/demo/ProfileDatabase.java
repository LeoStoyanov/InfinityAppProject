package com.example.demo;

import javax.persistence.*;

@Entity
//entity tells us that is is an entity
@Table(name = "Profile")
public class ProfileDatabase {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@Column(name = "Content", nullable = false)
	private String UserName;
	@Column(name = "ModPerms")
	private boolean mod;
	@Column(name = "AdminPerms")
	private boolean Admin;
	@Column(name = "Status") // Online/Offline Status False is offline
	private boolean Status;
	@Column(name = "BannedStatus")
	private boolean Banned;
}

//--------------
