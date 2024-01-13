package com.example.demo.tableLinks;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.guild.Guild;
import com.example.demo.guild.GuildRepository;
import com.example.demo.guild.RoleTypes.RoleType;
import com.example.demo.user.User;
import com.example.demo.user.UserRepository;

/**
 * The purpose of GuildLinkController is used to
 * 
 * - Add a user to a guild.
 * 
 * - Delete a user from a guild.
 * 
 * - update a user role in a guild.
 * 
 * from the front end to the back end. It is also able to be modified to add any
 * status that the guild has on the particular user
 * 
 * @author ascase
 *
 */

@RestController
public class GuildLinkController {

	@Autowired
	private GuildLinkService guildLinkService;

	@GetMapping(path = "/guildlinks")
	public List<GuildLink> getAllGuildLink() {
		return guildLinkService.getAllGuildLink();
	}

	@GetMapping(path = "/guildlink/{id}")
	public GuildLink getGuildLinkById(@PathVariable("id") long id) {
		return guildLinkService.getGuildLinkById(id);
	}

	@PutMapping(path = "/updateUserinGuild/{guild}/{user}")
	public ResponseEntity<GuildLink> updateGuildLink(@PathVariable("guild") long guildId,
			@PathVariable("user") long userId, @RequestBody GuildLink guildlink) {
		return new ResponseEntity<GuildLink>(guildLinkService.updateGuildLink(guildId, userId, guildlink),
				HttpStatus.OK);
	}

	@PostMapping(path = "/addUserToGuild/{guildId}/{userId}")
	public ResponseEntity<String> guildAddUser(@PathVariable long guildId, @PathVariable long userId) {
		return new ResponseEntity<String>(guildLinkService.guildAddUser(guildId, userId), HttpStatus.OK);
	}

	@PostMapping(path = "/addUserToGuildVaLink/{userId}/{link}")
	public ResponseEntity<String> GuildAddUserVaLink(@PathVariable long userId, @PathVariable String link) {
		return new ResponseEntity<String>(guildLinkService.guildAddUserVaLink(userId, link), HttpStatus.OK);
	}

	@DeleteMapping(path = "/deleteUserFromGuild/{guildId}/{userId}")
	public ResponseEntity<String> guildDeleteUser(@PathVariable long guildId, @PathVariable long userId) {
		return new ResponseEntity<String>(guildLinkService.guildDeleteUser(guildId, userId), HttpStatus.OK);
	}

	@DeleteMapping(path = "/deleteGuildlink/{id}")
	public ResponseEntity<String> deleteGuildLink(@PathVariable("id") long id) {
		return new ResponseEntity<String>(guildLinkService.deleteGuildLink(id), HttpStatus.OK);
	}

}
