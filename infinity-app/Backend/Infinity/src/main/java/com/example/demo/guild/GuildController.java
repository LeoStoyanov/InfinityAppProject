package com.example.demo.guild;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.demo.chat.Chatroom;
import com.example.demo.chat.ChatroomRepository;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.guild.RoleTypes.RoleType;
import com.example.demo.tableLinks.GuildLink;
import com.example.demo.tableLinks.GuildLinkRepository;
import com.example.demo.user.User;
import com.example.demo.user.UserRepository;

/**
 * This class handles all of the client to backend requests all of these classes
 *
 * @author jroot,ascase
 *
 */

@RestController
public class GuildController {

	// Creates a reference to the repository's so that we can talk to the server
	@Autowired
	private GuildService guildService;

	/**
	 * GET
	 *
	 * Finds all the guilds in the data base with their information.
	 *
	 * @return - list of all guilds and their information.
	 */
	@GetMapping(path = "/guilds")
	public List<Guild> getAllGuild() {
		return guildService.getAllGuild();
	}

	/**
	 * GET
	 *
	 * Finds a specific guild and checks if it exists. Then displays all information
	 * on the specific guild.
	 *
	 * @param id - The Id of the specific guild in the database.
	 * @return - Displays all information of the specific guild called.
	 */
	@GetMapping(path = "/guild/{id}")
	public Guild getGuildById(@PathVariable("id") long id) {
		return guildService.getGuildById(id);
	}
	
	/**
	 * POST
	 *
	 * Creates an instance of a guild and adds them to the database with a new
	 * unique Id.
	 *
	 * @param guild - The information of the new guild.
	 * @return - The information of the guild with a new Id.
	 */

	@PostMapping(path = "/newGuild/{userId}")
	public ResponseEntity<String> newGuild(@RequestBody Guild guild, @PathVariable long userId) {
		Guild g = guildService.newGuild(guild, userId);
		return new ResponseEntity<String>("Guild " + g.getGuildname() + " has been successfully created with "
				+ g.getOwner().getUsers().getUsername() + " as the guild owner! Made at Id:" + g.getId(), HttpStatus.CREATED);
	}

	/**
	 * PUT
	 *
	 * Updates an existing guild by taking the new information and storing it to the
	 * specified guild.
	 *
	 * @param id      - The Id of the specific guild we are updating.
	 * @param guild   - The new information we are storing to the specific guild.
	 * @param ownerId - The owner of the guild
	 * @return - The existing guild at the specified Id but with the new and updated
	 *         information.
	 */
	@PutMapping(path = "/updateGuild/{id}/{ownerId}")
	public ResponseEntity<Guild> updateGuild(@PathVariable("id") long id, @RequestBody Guild guild,
											 @PathVariable("ownerId") long ownerId) {
	
		return new ResponseEntity<Guild>(guildService.updateGuild(id, guild, ownerId), HttpStatus.OK);
	}



	/**
	 * This part is for demo 4
	 *
	 * @param guildId
	 * @param chatId
	 * @return
	 */
	@PutMapping(path = "/AddGuildChat/{guildId}/{chatId}")
	public ResponseEntity<Guild> guildAddChat(@PathVariable long guildId, @PathVariable long chatId) {
		return new ResponseEntity<Guild>(guildService.guildAddChat(guildId, chatId), HttpStatus.OK);

	}

	/**
	 * This part is for demo 4
	 *
	 * @param guildId
	 * @param chatId
	 * @return
	 */
	@DeleteMapping(path = "/deleteGuildsChat/{guildId}/{chatId}")
	public ResponseEntity<Guild> guildDeleteChat(@PathVariable long guildId, @PathVariable long chatId) {
		return new ResponseEntity<Guild>(guildService.guildDeleteChat(guildId, chatId), HttpStatus.OK);
	}

	/**
	 * DELETE
	 *
	 * Deletes the guild from the database.
	 *
	 * @param id - The Id of the specific guild being deleted.
	 * @return - A string that states if the deletion of the guild was successful or
	 *         not.
	 */
	@DeleteMapping(path = "/deleteGuild/{id}")
	public ResponseEntity<String> deleteGuild(@PathVariable("id") long id) {
		String s = guildService.deleteGuild(id);
		return new ResponseEntity<String>(s, HttpStatus.OK);
	}


}