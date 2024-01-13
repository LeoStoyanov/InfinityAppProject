package com.example.demo.guild;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.management.relation.Role;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.example.demo.ConfirmedResponse;
import com.example.demo.chat.Chatroom;
import com.example.demo.draw.DrawGuild;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.guild.RoleTypes.RoleType;
import com.example.demo.tableLinks.GuildLink;
import com.example.demo.user.User;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * This class just holds basic information about the guilds. This is the
 * information we will use for the dashboard as well as the guild servers. This
 * will be further developed with later in the semester
 *
 * @author jroot,ascase
 */

@Entity
@Table(name = "guilds")
public class Guild {

	/*
	 * Generating unique ID's for each guild server.
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String guildname;
	private String guildimage;
	private String invitelink;

	/*
	 * This Links the users and guilds so that we are able to tell which user has
	 * joined which guild and vice versa
	 */

	@OneToMany(mappedBy = "guilds")
	@JsonIgnore
	private List<GuildLink> linklist;

	// demo 4 stuff
	/*
	 * @OneToMany tells springboot that one instance of Guild can map to multiple
	 * instances of Chatroom OR one Guild row can map to multiple rows of the
	 * Chatroom table
	 */
	
	@OneToMany
	private List<Chatroom> Guildrooms;
	@OneToMany
	private List<DrawGuild> Guilddraws;

//	@OneToMany
//	private List<NoteList> GuildNotes;

//  <------------------------------ Basic setters and getters ------------------------------>  //

	public Guild(String guildname, String guildimage, String invitelink) {
		this.guildname = guildname;
		this.guildimage = guildimage;
		this.invitelink = invitelink;
		linklist = new ArrayList<GuildLink>();
		Guildrooms = new ArrayList<Chatroom>();
		Guilddraws = new ArrayList<DrawGuild>();
	}

	public Guild() {
		linklist = new ArrayList<GuildLink>();
		Guildrooms = new ArrayList<Chatroom>();
		Guilddraws = new ArrayList<DrawGuild>();
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getGuildname() {
		return guildname;
	}

	public void setGuildname(String guildname) {
		this.guildname = guildname;
	}

	public String getGuildimage() {
		return guildimage;
	}

	public void setGuildimage(String guildimage) {
		this.guildimage = guildimage;
	}

	public String getInvitelink() {
		return invitelink;
	}

	public void setInvitelink(String invitelink) {
		this.invitelink = invitelink;
	}

	public List<GuildLink> getLinklist() {
		return linklist;
	}

	public void setLinklist(List<GuildLink> gl) {
		this.linklist = gl;
	}

//  <------------------------------ USERS ------------------------------>  //

	/**
	 * This finds the user in the guild returns null if not found
	 *
	 * @param user
	 * @return
	 */
	public GuildLink getGuildUserLink(User user) {
		for (int i = 0; i < linklist.size(); i++) {
			if (linklist.get(i).getUsers().equals(user) && linklist.get(i).getGuilds().equals(this)) {
				return linklist.get(i);
			}
		}
		return null;
	}

	@JsonIgnore
	public List<GuildLink> getAllGuildUserLink() {
		List<GuildLink> allUsers = new ArrayList<GuildLink>();
		for (int i = 0; i < linklist.size(); i++) {
			if (linklist.get(i).getGuilds().equals(this)) {
				allUsers.add(linklist.get(i));
			}
		}
		return allUsers;
	}

//  <------------------------------ ROLES ------------------------------>  //

	@JsonIgnore
	public GuildLink getOwner() {
		for (int i = 0; i < linklist.size(); i++) {
			if (linklist.get(i).getRole().equals(RoleTypes.RoleType.OWNER)
					&& linklist.get(i).getGuilds().equals(this)) {
				return linklist.get(i);
			}
		}
		return null;
	}

//  <------------------------------ CHAT ------------------------------>  //

	public List<Chatroom> getChatrooms() {
		return Guildrooms;
	}

	public void setChatrooms(List<Chatroom> Guildrooms) {
		this.Guildrooms = Guildrooms;
	}

	public void addChatroom(Chatroom Guildrooms) {
		this.Guildrooms.add(Guildrooms);
	}

	public void deleteChatroom(Chatroom Guildrooms) {
		this.Guildrooms.remove(Guildrooms);
	}

//  <------------------------------ Draw ------------------------------>  //

	public List<DrawGuild> getGuilddraws() {
		return Guilddraws;
	}

	public void setGuilddraws(List<DrawGuild> Guilddraws) {
		this.Guilddraws = Guilddraws;
	}

//  <------------------------------ Testing ------------------------------>  //

	public Guild(long id, String guildname, String guildimage, String invitelink) {
		this.guildname = guildname;
		this.guildimage = guildimage;
		this.invitelink = invitelink;
		this.id = id;
		linklist = new ArrayList<GuildLink>();
		Guildrooms = new ArrayList<Chatroom>();
		Guilddraws = new ArrayList<DrawGuild>();
	}

	public ConfirmedResponse ConfirmedGuild(int testCase, long id, String guildname, String guildimage,
			String invitelink) {
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
			result.setMessage("Error.guildname.Missmatch");
			setGuildname("null");
			if ((!(guildname.equals(this.guildname))) && (getGuildname().equals("null"))) {
				result.setApproved(true);
				result.setMessage("null");
			}
			return result;
		}
		case (3): {
			result.setMessage("Error.guildimage.Missmatch");
			setGuildimage("null");
			if ((!(guildimage.equals(this.guildimage))) && (getGuildimage().equals("null"))) {
				result.setApproved(true);
				result.setMessage("null");
			}
			return result;
		}
		case (4): {
			result.setMessage("Error.invitelink.Missmatch");
			setInvitelink("null");
			if ((!(invitelink.equals(this.invitelink))) && (getInvitelink().equals("null"))) {
				result.setApproved(true);
				result.setMessage("null");
			}
			return result;
		}
		case (5): {
			result.setMessage("Error.Linklist.Missmatch");
			GuildLink RandomGuildLink1 = new GuildLink(this, new User(), RoleType.MEMBER);
			GuildLink RandomGuildLink2 = new GuildLink(this, new User(), RoleType.MEMBER);
			GuildLink OwnerGuildLink = new GuildLink(this, new User(), RoleType.OWNER);		
			List<GuildLink> GuildLinkList = new ArrayList<GuildLink>();
			GuildLinkList.add(RandomGuildLink1);
			GuildLinkList.add(OwnerGuildLink);
			GuildLinkList.add(RandomGuildLink2);
			setLinklist(GuildLinkList);
			if(getLinklist().equals(GuildLinkList)) {
				result.setApproved(true);
				result.setMessage("null");
			}
			return result;
		}
		case (6): {
			result.setMessage("Error.getOwner.Missmatch");
			GuildLink RandomGuildLink1 = new GuildLink(this, new User(), RoleType.MEMBER);
			GuildLink RandomGuildLink2 = new GuildLink(this, new User(), RoleType.MEMBER);
			GuildLink OwnerGuildLink = new GuildLink(this, new User(), RoleType.OWNER);		
			List<GuildLink> GuildLinkList = new ArrayList<GuildLink>();
			GuildLinkList.add(RandomGuildLink1);
			GuildLinkList.add(OwnerGuildLink);
			GuildLinkList.add(RandomGuildLink2);
			setLinklist(GuildLinkList);
			if(getOwner().equals(OwnerGuildLink)) {
				result.setApproved(true);
				result.setMessage("null");
			}
			return result;
		}
		case (7): {
			result.setMessage("Error.GuildlinkUser.Missmatch");
			GuildLink RandomGuildLink1 = new GuildLink(this, new User(), RoleType.MEMBER);
			GuildLink RandomGuildLink2 = new GuildLink(this, new User(), RoleType.MEMBER);
			GuildLink OwnerGuildLink = new GuildLink(this, new User(), RoleType.OWNER);	
			User u = new User();
			GuildLink GuildlinkUser = new GuildLink(this, u, RoleType.MEMBER);
			List<GuildLink> GuildLinkList = new ArrayList<GuildLink>();
			GuildLinkList.add(RandomGuildLink1);
			GuildLinkList.add(OwnerGuildLink);
			GuildLinkList.add(GuildlinkUser);
			GuildLinkList.add(RandomGuildLink2);
			setLinklist(GuildLinkList);
			if(getGuildUserLink(u).equals(GuildlinkUser)) {
				result.setApproved(true);
				result.setMessage("null");
			}
			return result;
		}
		case (8):{
			result.setMessage("Error.GuildlinkUser.Missmatch");
			List<DrawGuild> Guilddrawing = new ArrayList<DrawGuild>();
			DrawGuild dg = new DrawGuild("name","art",this);
			Guilddrawing.add(dg);		
			setGuilddraws(Guilddrawing);
			if(getGuilddraws().equals(Guilddrawing)) {
				result.setApproved(true);
				result.setMessage("null");
			}
			return result;
		}
		

		}
		return result;
	}
}
