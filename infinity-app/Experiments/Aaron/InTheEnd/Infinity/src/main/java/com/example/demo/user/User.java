package com.example.demo.user;

//import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.example.demo.guild.Guild;

@Entity
public class User {
	/*
	 * The annotation @ID marks the field below as the primary key for the table
	 * created by springboot The @GeneratedValue generates a value if not already
	 * present, The strategy in this case is to start from 1 and increment for each
	 * table
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String email;
	private String username;
	private String password;
	private String displayname;

	/*
	 * @ManyToOne tells springboot that multiple instances of Phone can map to one
	 * instance of OR multiple rows of the phone table can map to one user row
	 * 
	 * @JoinColumn specifies the ownership of the key i.e. The Phone table will
	 * contain a foreign key from the User table and the column name will be user_id
	 * 
	 * @JsonIgnore is to assure that there is no infinite loop while returning
	 * either user/phone objects (phone->user->[phones]->...)
	 */
	@ManyToOne
	@JoinColumn(name = "Guild_ID")
	@JsonIgnore
	private Guild guild;

	public User(String email, String username, String password, String displayname) {
		this.email = email;
		this.username = username;
		this.password = password;
		this.displayname = displayname;
	}

	public User() {
	}

	public long getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getDisplayname() {
		return displayname;
	}

	public void setDisplayname(String displayname) {
		this.displayname = displayname;
	}

	public Guild getGuild() {
		return guild;
	}

	public void setGuild(Guild guild) {
		this.guild = guild;
	}

}
