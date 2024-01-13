package com.example.demo.tableLinks;

import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.guild.Guild;
import com.example.demo.guild.GuildRepository;
import com.example.demo.guild.GuildService;
import com.example.demo.guild.RoleTypes;
import com.example.demo.user.User;
import com.example.demo.user.UserRepository;
import com.example.demo.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * @author ascase, Jroot
 */
@Service
public class GuildLinkServiceImpl implements GuildLinkService {
	@Autowired
	private GuildService guildService;
	@Autowired
	private UserService userService;
	@Autowired
	private GuildLinkRepository guildLinkRepository;

	/**
	 * Gets all guildlinks
	 */
	@Override
	public List<GuildLink> getAllGuildLink() {
		return guildLinkRepository.findAll();
	}

	/**
	 * Gets a guildlinks by a id
	 */
	@Override
	public GuildLink getGuildLinkById(long id) {
		Optional<GuildLink> guildlink = guildLinkRepository.findById(id);
		if (guildlink.isPresent()) {
			return guildlink.get();
		} else {
			throw new ResourceNotFoundException("GuildLink", "ID", id);
		}
	}

	/**
	 * updates the relationship in guildlink
	 */
	@Override
	public GuildLink updateGuildLink(long idGuild, long idUser, GuildLink guildlink) {

		Guild existingGuild = guildService.getGuildById(idGuild);
		User existingUser = userService.getUserById(idUser);
		GuildLink existingGuildLink = getGuildLinkById(existingGuild.getGuildUserLink(existingUser).getId());
		existingGuildLink.setGuilds(existingGuild);
		existingGuildLink.setUsers(existingUser);
		GuildLink Owner = guildLinkRepository.findById(existingGuild.getOwner().getId())
				.orElseThrow(() -> new ResourceNotFoundException("GuildLink", "ID", existingGuild.getOwner().getId()));
		if (guildlink.getRole().equals(RoleTypes.RoleType.OWNER)) {
			Owner.setRole(RoleTypes.RoleType.MEMBER);
			guildLinkRepository.save(Owner);
		} else if (existingGuildLink.getUsers().equals(Owner.getUsers())) {
			new ResponseEntity<String>("OWNER must be met cant change", HttpStatus.OK);
			return existingGuildLink;
		}
		existingGuildLink.setRole(guildlink.getRole());
		guildLinkRepository.save(existingGuildLink);
		return existingGuildLink;
	}

	/**
	 * Adds a link to guildlinks with a user and guild also checks if a user is in
	 * the guild
	 */
	@Override
	public String guildAddUser(long idGuild, long idUser) {
		Guild existingGuild = guildService.getGuildById(idGuild);
		User existingUser = userService.getUserById(idUser);
		try {
			GuildLink g = existingGuild.getGuildUserLink(existingUser);
			if (existingGuild.getGuildUserLink(existingUser).equals(null)) {
				throw new NullPointerException();
			}
		} catch (NullPointerException e) {
			GuildLink guildlink = new GuildLink();
			guildlink.setGuilds(existingGuild);
			guildlink.setUsers(existingUser);
			guildlink.setRole(RoleTypes.RoleType.MEMBER);
			guildLinkRepository.save(guildlink);
			return "User " + existingUser.getUsername() + " has been successfully added to "
					+ existingGuild.getGuildname() + " guild! At Id:" + guildlink.getId();
		}
		return "User Already exists in guild!";
	}

	/**
	 * Adds a link to guildlinks via invite link
	 */
	@Override
	@Transactional
	public String guildAddUserVaLink(long idUser, String link) {
		User existingUser = userService.getUserById(idUser);
		List<GuildLink> guildsList = guildLinkRepository.findAll();
		for (int i = 0; i < guildsList.size(); i++) {
			if (guildsList.get(i).getGuilds().getInvitelink().equals(link)) {
				try {
					if (guildsList.get(i).getGuilds().getGuildUserLink(existingUser) == null) {
						throw new NullPointerException();
					} else {
						return "User Already exists in guild!";
					}

				} catch (NullPointerException e) {
					GuildLink guildlink = new GuildLink();
					guildlink.setUsers(existingUser);
					guildlink.setRole(RoleTypes.RoleType.MEMBER);
					guildlink.setGuilds(guildsList.get(i).getGuilds());
					guildLinkRepository.save(guildlink);
					return "User " + existingUser.getUsername() + " has been successfully added to "
							+ guildsList.get(i).getGuilds().getGuildname() + " guild! At Id:" + guildlink.getId();
				}
			}
		}
		return "Link is invalid";
	}

	/**
	 * Deletes the connection between the user and guild. Essentially this method
	 * kicks a user from a guild
	 * 
	 */
	@Override
	@Transactional
	public String guildDeleteUser(long idGuild, long idUser) {
		Guild existingGuild = guildService.getGuildById(idGuild);
		User existingUser = userService.getUserById(idUser);
		try {
			GuildLink existingGuildLink = getGuildLinkById(existingGuild.getGuildUserLink(existingUser).getId());
			if (existingGuild.getOwner().equals(existingGuildLink)) { // is it the owner
				return "Immortal user detected! Make sure that the user is not the owner.";
			} else {
				deleteGuildLink(existingGuildLink.getId());
				return "User " + existingUser.getUsername() + " has been successfully removed to"
						+ existingGuild.getGuildname() + " guild!";
			}
		} catch (NullPointerException e) {
			return "User " + existingUser.getUsername() + " Does not currently exit in " + existingGuild.getGuildname()
					+ " guild. Action Removal terminated";
		}
	}

	/**
	 * Manually deletes a Guildlink connection. This is meant for debugging
	 */
	@Override
	public String deleteGuildLink(long id) {
		getGuildLinkById(id);
		guildLinkRepository.deleteById(id);
		return "GuildLink termination successfull!";
	}

	@Override
	public GuildLink saveToGuildLinkRepository(GuildLink newlink) {
		return guildLinkRepository.save(newlink);
	}
}
