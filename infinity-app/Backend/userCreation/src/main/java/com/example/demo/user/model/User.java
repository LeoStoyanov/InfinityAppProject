package com.example.demo.user.model;

import lombok.Data;

import javax.persistence.*;

/**
 * This class just holds basic information about the user tentatively this is
 * all the information we need to set up a login
 * 
 * @author jroot,ascase
 */
//LOMBOK and the @DATA annotation get rid of getters and setters
// (nullable = false) == this means it has to be filled for it to work

@Data
@Entity
@Table(name = "users")
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@Column(name = "email",nullable = false)
	private String email;
	@Column(name = "username",nullable = false)
	private String username;
	@Column(name = "password",nullable = false)
	private String password;
	@Column(name = "displayname",nullable = false)
	private String displayname;

	
}
