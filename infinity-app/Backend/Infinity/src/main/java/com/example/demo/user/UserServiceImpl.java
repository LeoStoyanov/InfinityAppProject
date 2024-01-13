package com.example.demo.user;

import com.example.demo.draw.DrawUser;
import com.example.demo.draw.DrawUserRepository;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.guild.Guild;
import com.example.demo.guild.GuildRepository;
import com.example.demo.guild.GuildService;
import com.example.demo.tableLinks.GuildLink;
import com.example.demo.tableLinks.GuildLinkRepository;
import com.example.demo.tableLinks.GuildLinkService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author ascase, Jroot
 */

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	private UserRepository userRepository;
	// THE BEST PRACTICE IS CALLING OTHER SERVICES
	// EX... A UserService should be the only thing calling
	// UserRepo, If you need to use GuildRepo in User, you
	// must go through GuildService
	@Autowired
	private GuildRepository guildRepository;

	@Autowired
	private GuildLinkService guildLinkService;

	@Autowired
	private GuildLinkRepository guildLinkRepository;

	@Autowired
	private DrawUserRepository drawUserRepository;

	/**
	 * gets all of the users in the database
	 * 
	 * @return all users in the database
	 */
	@Override
	public List<User> getAllUsers() {
		return userRepository.findAll();
	}

	/**
	 * Gets the user based on their id
	 * 
	 * @param id - unique id of the user
	 * @return - the user based on the id given
	 */
	@Override
	public User getUserById(long id) {
		Optional<User> user = userRepository.findById(id);
		if (user.isPresent()) {
			return user.get();
		} else {
			throw new ResourceNotFoundException("User", "ID", id);
		}
	}

	/**
	 * take an new user object then saves it into the database
	 * 
	 * @param user - the instance of a new user
	 * @return - An HTTP status
	 */
	@Override
	public User saveUser(User user) {
		return userRepository.save(user);
		
//		return new ResponseEntity<>(userRepository.save(user), HttpStatus.CREATED);
	}

	/**
	 * Updates an existing user with new information
	 * 
	 * @param id   - id of existing user
	 * @param user - All of the information we are updating the existing user with
	 * @return - HTTP status
	 */
	@Override
	public User updateUser(long id, User user) {
		User existingUser = userRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("User", "ID", id));
//		existingUser.setId(user.getId());		
		existingUser.setUsername(user.getUsername());
		existingUser.setEmail(user.getEmail());
		existingUser.setPassword(user.getPassword());
		existingUser.setDisplayname(user.getDisplayname());
		existingUser.setGuildLink(user.getGuildLink());
		existingUser.setImageid(user.getImageid());
		existingUser.setUserDrawings(user.getUserDrawings());
		userRepository.save(existingUser);
		return existingUser;
		
	}

	/**
	 * Deletes an existing user
	 * 
	 * @param id - unique id of user we are deleting
	 * @return - String of completion, as well as HTTP Status
	 */
	@Override
	public String deleteUser(long id) {
		User existingUser = userRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("User", "ID", id));
		List<GuildLink> nukeLink = existingUser.getGuildLink();
		List<Guild> nukeGuild = new ArrayList<>();
		List<DrawUser> BurnDrawings = existingUser.getUserDrawings();
		int i = 0;
		while (i < nukeLink.size()) {
			if (nukeLink.get(i).getUsers().getId() == id) {
				if (nukeLink.get(i).getGuilds().getOwner() == nukeLink.get(i)) {
					nukeGuild.add(nukeLink.get(i).getGuilds());
				} else {
					guildLinkRepository.deleteById(nukeLink.get(i).getId());
					nukeLink.remove(i);
					i--;
				}
			}
			i++;
		}
		while (!(nukeGuild.isEmpty())) {
			i = 0;
			Guild Terminate = nukeGuild.get(0);
			List<GuildLink> nukeGuildLink = Terminate.getLinklist();
			while (i < nukeGuildLink.size()) {
				if (nukeGuildLink.get(i).getGuilds().getId() == Terminate.getId()) {
					guildLinkRepository.deleteById(nukeGuildLink.get(i).getId());
					nukeGuildLink.remove(i);
					i--;
				}
				i++;
			}
			guildRepository.deleteById(Terminate.getId());
			nukeGuild.remove(0);
		}
		i = 0;
		while (!(BurnDrawings.isEmpty())) {
			if (BurnDrawings.get(i).getDrawHostUser().getId() == existingUser.getId()) {
				drawUserRepository.deleteById(BurnDrawings.get(i).getId());
				BurnDrawings.remove(0);
			}
			i++;
		}

		userRepository.deleteById(id);
		return "User termination successfull!";
	}

	public Integer CountOfUser() {
		return userRepository.findAll().size();
		
	}

}
