package com.example.demo.tableLinks;

import org.springframework.http.ResponseEntity;

import java.util.List;

/**
 * @author ascase, Jroot
 */
public interface GuildLinkService {
	/**
	 * gets all of the Guildlinks in the database
	 * 
	 * @return all Guildlinks in data base
	 */
	public List<GuildLink> getAllGuildLink();

	/**
	 * gets a guildlink by a id from the database
	 * 
	 * @param id - the id of the guildlink
	 * @return - the guildlink based on the id
	 */
	public GuildLink getGuildLinkById(long id);

	/**
	 * Updates an existing user guild connection with new information
	 * 
	 * @param idGuild   - the guild id of the existing link
	 * @param idUser    - the user id of the existing link
	 * @param guildLink - all the information we are updating
	 * @return - HTTP Status
	 */
	public GuildLink updateGuildLink(long idGuild, long idUser, GuildLink guildLink);

	/**
	 * Adds a new user to a guild by making a new guildlink connection
	 * 
	 * @param idGuild - the guild id of an existing guild
	 * @param idUser  - the user id of an existing user
	 * @return - HTTP Status
	 */
	public String guildAddUser(long idGuild, long idUser);

	/**
	 * Adds a new user to a guild via invite link this makes a new guildlink connection
	 * 
	 * @param idUser - user id of an existing user
	 * @param link - the invite link code of what guild we are joining
	 * @return - HTTP Status
	 */
	public String guildAddUserVaLink(long idUser, String link);

	/**
	 * Deletes a user from a guild by removing the guildlink connection
	 * @param idGuild - guild id of an existing guild
	 * @param idUser - user id of an existing user in the guild
	 * @return - HTTP Status
	 */
	public String guildDeleteUser(long idGuild, long idUser);

	/**
	 * Deletes an existing guildlink manually. This is for debugging.
	 * @param id - guildlink id of an existing guildlink 
	 * @return - HTTP Status
	 */
	public String deleteGuildLink(long id);

	/**
	 * This method saves a guildlink in the repository
	 * @param newlink - guildlink id 
	 */
	public GuildLink saveToGuildLinkRepository(GuildLink newlink);
}
