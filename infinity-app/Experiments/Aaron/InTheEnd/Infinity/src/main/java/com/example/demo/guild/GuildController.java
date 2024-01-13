package com.example.demo.guild;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.user.User;
import com.example.demo.user.UserRepository;

/**
 * This class handles all of the client to backend requests all of these classes
 * hand all requests off to the Service Layer.
 * 
 * @author jroot,ascase
 *
 */

@RestController
//@RequestMapping("/api/guilds")
public class GuildController {

	@Autowired
	private GuildRepository guildRepository;
	@Autowired
	private UserRepository userRepository;

	// GET
	/*
	 * Gets all guilds
	 */
	@GetMapping(path = "/guilds")
	public List<Guild> getAllGuild() {
		return guildRepository.findAll();
	}

	/*
	 * Gets a guild by that Id
	 */

	@GetMapping(path = "/guilds/{id}")
	public Guild getGuildById(@PathVariable("id") long id) {
		Optional<Guild> guild = guildRepository.findById(id);
		if (guild.isPresent()) {
			return guild.get();
		} else {
			throw new ResourceNotFoundException("Guild", "ID", id);
		}
	}

	/*
	 * Saves the guild
	 */
	// POST

	@PostMapping(path = "/guilds")
	public ResponseEntity<Guild> saveGuild(@RequestBody Guild guild) {
		return new ResponseEntity<>(guildRepository.save(guild), HttpStatus.CREATED);
	}

	/*
	 * Updates the guild by what is inputed
	 */
	// PUT
//	@PutMapping("{id}")
	@PutMapping(path = "/guilds/{id}")
	public ResponseEntity<Guild> updateGuild(@PathVariable("id") long id, @RequestBody Guild guild) {
		Guild existingGuild = guildRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("User", "ID", id));
		existingGuild.setGuildname(guild.getGuildname());
		existingGuild.setUserlist(guild.getUserlist());
		existingGuild.setUserlist(guild.getUserlist());
		guildRepository.save(existingGuild);
		return new ResponseEntity<Guild>(existingGuild, HttpStatus.OK);
	}

	@PutMapping(path = "/guilds/{idGuild}/users/{idUser}")
	public ResponseEntity<Guild> GuildAddUser( @PathVariable long idGuild, @PathVariable long idUser) {
		Guild guild = guildRepository.findById(idGuild)
				.orElseThrow(() -> new ResourceNotFoundException("Guild", "ID", idGuild));
		User user = userRepository.findById(idUser)
				.orElseThrow(() -> new ResourceNotFoundException("User", "ID", idUser));
		guild.addUserlist(user);
		guildRepository.save(guild);
		return new ResponseEntity<Guild>(guild, HttpStatus.OK);
	}
	
	
	@PutMapping(path = "/guilds/{idGuild}/users/{idUser}")
	public ResponseEntity<Guild> GuildDeleteUser(@PathVariable long guildid, @PathVariable long userid) {
		Guild guild = guildRepository.findById(guildid)
				.orElseThrow(() -> new ResourceNotFoundException("Guild", "ID", guildid));
		User user = userRepository.findById(userid)
				.orElseThrow(() -> new ResourceNotFoundException("User", "ID", userid));
		guild.deleteUserlist(user);
		guildRepository.save(guild);
		return new ResponseEntity<Guild>(guild, HttpStatus.OK);
	}

	@DeleteMapping(path = "/guilds/{id}")
	public ResponseEntity<String> deleteGuild(@PathVariable("id") long id) {
		guildRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Guild", "ID", id));
		guildRepository.deleteById(id);
		return new ResponseEntity<String>("Guild termination successfull!", HttpStatus.OK);
	}

}