package com.example.demo.user;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.example.demo.ConfirmedResponse;
import com.example.demo.chat.Chatroom;
import com.example.demo.draw.DrawUser;
import com.example.demo.tableLinks.GuildLink;

/**
 * The Object of user that holds all of the needed info for a unique client
 * 
 * @author ascase, Jroot
 *
 */

@Entity
@Table(name = "users")
public class User {
	/*
	 * Generates a unique Id for the user.
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String email;
	private String username;
	private String password;
	private String displayname;
	private String imageid;

	/**
	 * This links the users to the guilds so that we are able to tell which user has
	 * joined which guild and vice versa
	 *
	 * @JsonIgnore is to assure that there is no infinite loop while returning
	 *             either Guild/User objects (Guild->User->[Guild]->...)
	 */

	@OneToMany(mappedBy = "users")
	@JsonIgnore
	private List<GuildLink> guildLink;

	@ManyToOne
//    @JoinTable(name = "chats", joinColumns = @JoinColumn(name = "users_id"), inverseJoinColumns = @JoinColumn(name = "chatroom_id"))
	@JsonIgnore
	private Chatroom chats;

	@OneToMany
	private List<DrawUser> userDrawings;

	/**
	 * Basic Constructor
	 * 
	 * @param email       - email attributed to this user
	 * @param username    - a unique username
	 * @param password    - a password
	 * @param displayname - what other users will see as their name
	 */

	public User(String email, String username, String password, String displayname, String imageid) {
		this.email = email;
		this.username = username;
		this.password = password;
		this.displayname = displayname;
		this.imageid = imageid;
		guildLink = new ArrayList<GuildLink>();
//		chats = new ArrayList<Chatroom>();
		userDrawings = new ArrayList<DrawUser>();
	}

	public User() {
		guildLink = new ArrayList<GuildLink>();
		userDrawings = new ArrayList<DrawUser>();
//		chats = new ArrayList<Chatroom>();
	}

	/**
	 * gets users unique id
	 * 
	 * @return - unique id of this user
	 */
	public long getId() {
		return id;
	}

	/**
	 * Sets the id of this user *Testing purposes*
	 * 
	 * @param id - id to change to
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * gets users email
	 * 
	 * @return - users email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * sets users email
	 * 
	 * @param email - email you want it changed to
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * gets username
	 * 
	 * @return - users username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * sets username
	 * 
	 * @param username - sets username of this user
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * gets users password
	 * 
	 * @return - gets users password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * sets new password
	 * 
	 * @param password - sets new password
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * gets the display name of this user
	 * 
	 * @return - display name of this user
	 */
	public String getDisplayname() {
		return displayname;
	}

	/**
	 * sets display name of this user
	 * 
	 * @param displayname - users display name this is being changed to
	 */
	public void setDisplayname(String displayname) {
		this.displayname = displayname;
	}

	public String getImageid() {
		return imageid;
	}

	public void setImageid(String imageid) {
		this.imageid = imageid;
	}

	/**
	 * Gets the users and guild connection list
	 * 
	 * @return
	 */
	public List<GuildLink> getGuildLink() {
		return guildLink;
	}

	/**
	 * Sets the users and guild connection list
	 * 
	 * @param guildLink
	 */
	public void setGuildLink(List<GuildLink> guildLink) {
		this.guildLink = guildLink;
	}

//	public List<Chatroom> getChats() {
//		return chats;
//	}
//	
//	public void setChats(List<Chatroom> chats) {
//		this.chats = chats;
//	}

//  <------------------------------ Draw ------------------------------>  //
	
	
	public List<DrawUser> getUserDrawings() {
		return userDrawings;
	}

	public void setUserDrawings(List<DrawUser> userDrawings) {
		this.userDrawings = userDrawings;
	}

//  <------------------------------ Testing ------------------------------>  //

	public User(long id, String email, String username, String password, String displayname, String imageid) {
		this.email = email;
		this.username = username;
		this.password = password;
		this.displayname = displayname;
		this.imageid = imageid;
		this.id = id;
		guildLink = new ArrayList<GuildLink>();
		userDrawings = new ArrayList<DrawUser>();
//		chats = new ArrayList<Chatroom>();
	}

	public ConfirmedResponse ConfirmedUser(int testCase, long id, String email, String username, String password,
			String displayname, String imageid) {
		ConfirmedResponse result = new ConfirmedResponse();
		result.setApproved(false);
		switch (testCase) {
		case (0): {
			result.setApproved(true);
			return result;
		}
		case (1): {
			result.setMessage("Error.id.Missmatch");
			long inf = Integer.MAX_VALUE;
			setId(inf);
			if ((id != this.id) && (getId() == inf)) {
				result.setApproved(true);
				result.setMessage("null");
			}
			return result;
		}
		case (2): {
			result.setMessage("Error.email.Missmatch");
			setEmail("null");
			if ((!(email.equals(this.email))) && (getEmail().equals("null"))) {
				result.setApproved(true);
				result.setMessage("null");
			}
			return result;
		}
		case (3): {
			result.setMessage("Error.username.Missmatch");
			setUsername("null");
			if ((!(username.equals(this.username))) && (getUsername().equals("null"))) {
				result.setApproved(true);
				result.setMessage("null");
			}
			return result;
		}
		case (4): {
			result.setMessage("Error.password.Missmatch");
			setPassword("null");
			if ((!(password.equals(this.password)) && getPassword().equals("null"))) {
				result.setApproved(true);
				result.setMessage("null");
			}
			return result;
		}
		case (5): {
			result.setMessage("Error.displayname.Missmatch");
			setDisplayname("null");
			if ((!(displayname.equals(this.displayname))) && (getDisplayname().equals("null"))) {
				result.setApproved(true);
				result.setMessage("null");
			}
			return result;
		}
		case (6): {
			result.setMessage("Error.imageid.Missmatch");
			setImageid("null");
			if ((!(imageid.equals(this.imageid))) && (getImageid().equals("null"))) {
				result.setApproved(true);
				result.setMessage("null");
			}
			return result;
		}
		case (7): {
			result.setMessage("error.imageid.Missmatch");
			List<GuildLink> l = new ArrayList<>();
			setGuildLink(l);
			if ((l.size() == 0) && (getGuildLink().equals(this.guildLink))) {
				result.setApproved(true);
				result.setMessage("null");
			}
			return result;
		}
		case (8): {
			result.setMessage("error.DrawUser.Missmatch");
			List<DrawUser> drawlist = new ArrayList<DrawUser>();
			setUserDrawings(drawlist);
			DrawUser d = new DrawUser();
			User u = this;
			d.setDrawHostUser(u);
			userDrawings.add(d);
			if ((getUserDrawings().size() == 1) && (getUserDrawings().equals(this.userDrawings))) {
				result.setApproved(true);
				result.setMessage("null");
			}
			return result;
		}
		}
		return result;
	}

}
