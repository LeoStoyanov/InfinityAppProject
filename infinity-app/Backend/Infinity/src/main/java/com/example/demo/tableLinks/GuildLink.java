package com.example.demo.tableLinks;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.example.demo.ConfirmedResponse;
import com.example.demo.chat.Chatroom;
import com.example.demo.guild.Guild;
import com.example.demo.guild.RoleTypes.RoleType;
import com.example.demo.user.User;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * This is a many to many class which allows a chatroom to be linked to a guild
 * Links Chatrooms with guilds
 * 
 * @author ascase, Jroot
 */
@Entity
@Table(name = "GuildLink")
public class GuildLink {

	/*
	 * This is a many to many method which allows a chatroom to be linked to a guild
	 * Links Chatrooms with guilds
	 */

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@ManyToOne
	@JoinColumn(name = "guilds_id")
	private Guild guilds;

	@ManyToOne
	@JoinColumn(name = "users_id")
	private User users;

	@Column(name = "role", nullable = false)
	private RoleType role;

	public GuildLink() {
	}
	public GuildLink(Guild guilds, User users, RoleType role) {
		this.guilds = guilds;
		this.users = users;
		this.role = role;
	}

	/**
	 * gets users in guild
	 * 
	 * @return - users in guild
	 */
	public User getUsers() {
		return users;
	}

	/**
	 * Puts users in a guild
	 * 
	 * @param users - user being put in a guild
	 */
	public void setUsers(User users) {
		this.users = users;
	}

	/**
	 * gets the instance of a guild being referenced
	 * 
	 * @return - the guild being referenced
	 */
	public Guild getGuilds() {
		return guilds;
	}

	/**
	 * sets a new guild as a reference
	 * 
	 * @param guilds - sets a new guild as a reference
	 */
	public void setGuilds(Guild guilds) {
		this.guilds = guilds;
	}

	/**
	 * a unique id of the link between guild and user
	 * 
	 * @return - id of the link between a certain guild and user
	 */
	public long getId() {
		return id;
	}

	/**
	 * Sets the Id of a relationship
	 * 
	 * @param id - id being set to this relationship
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * gets the role of the user in the relationship
	 * 
	 * @return - the role assigned to a user
	 */
	public RoleType getRole() {
		return role;
	}

	/**
	 * Assigns a role to the user in the relationship
	 * 
	 * @param role - role being set to the user
	 */
	public void setRole(RoleType role) {
		this.role = role;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null || getClass() != obj.getClass()) {
			return false;
		}
		GuildLink other = (GuildLink) obj;

		if (users == null) {
			if (other.users != null)
				return false;
		} else if (!users.equals(other.users))
			return false;

		if (guilds == null) {
			if (other.guilds != null)
				return false;
		} else if (!guilds.equals(other.guilds))
			return false;
		return true;
	}

	public GuildLink(long id, Guild guilds, User users, RoleType role) {
		this.id = id;
		this.guilds = guilds;
		this.users = users;
		this.role = role;
	}

	public GuildLink(RoleType role) {
		this.role = role;
	}
	
	public ConfirmedResponse ConfirmedGuildLink(int testCase, long id, Guild guild, User user, RoleType role) {
		ConfirmedResponse result = new ConfirmedResponse();
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
			result.setMessage("Error.Guilds.Missmatch");
			Guild g = new Guild("x", "x", "x");
			setGuilds(g);
			if ((!(guild.equals(this.guilds))) && (getGuilds().equals(g))) {
				result.setApproved(true);
				result.setMessage("null");
			}
			return result;
		}
		case (3): {
			result.setMessage("Error.Users.Missmatch");
			User u = new User("x", "x", "x", "x", "x");
			setUsers(u);
			if ((!(user.equals(this.users))) && (getUsers().equals(u))) {
				result.setApproved(true);
				result.setMessage("null");
			}
			return result;
		}
		case(4):{
			result.setMessage("Error.RoleGet.Missmatch");
			if (role.equals(this.role)) {
				result.setApproved(true);
				result.setMessage("null");
			}
			return result;
		}
		case(5):{
			result.setMessage("Error.Role.Missmatch");
			setRole(RoleType.MOD);
			if ((!(role.equals(this.role))) && (getRole().equals(RoleType.MOD))) {
				result.setApproved(true);
				result.setMessage("null");
			} 
			return result;
		}

		}
		return result;
	}
}
