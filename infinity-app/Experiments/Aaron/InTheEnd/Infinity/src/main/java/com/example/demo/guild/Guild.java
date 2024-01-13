package com.example.demo.guild;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import com.example.demo.user.User;

/**
 * This class just holds basic information about the guilds. This is the
 * information we will use for the dashboard and will be further developed with
 * later in the semester
 * 
 * @author jroot,ascase
 */

@Entity
public class Guild {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column(name = "guildname", nullable = false)
	private String guildname;

	@OneToMany
	private List<User> userlist;

	public Guild(String guildname) {
		this.guildname = guildname;
		userlist = new ArrayList<User>();
	}
	public Guild() {
		userlist = new ArrayList<User>();
	}

	public long getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getGuildname() {
		return guildname;
	}

	public void setGuildname(String guildname) {
		this.guildname = guildname;
	}

	public List<User> getUserlist() {
		return userlist;
	}

	public void setUserlist(List<User> userlist) {
		this.userlist = userlist;
	}

	public void addUserlist(User userlist) {
		this.userlist.add(userlist);
	}
	public void deleteUserlist(User userlist) {
		this.userlist.remove(userlist);
	}

}
