package com.example.demo.guild;

import com.example.demo.chat.Chatroom;
import com.example.demo.chat.ChatroomService;
import com.example.demo.draw.DrawGuild;
import com.example.demo.draw.DrawService;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.tableLinks.GuildLink;
import com.example.demo.tableLinks.GuildLinkService;
import com.example.demo.user.User;
import com.example.demo.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class GuildServiceImpl implements GuildService {
	// THE BEST PRACTICE IS CALLING OTHER SERVICES
	// EX... A UserService should be the only thing calling
	// UserRepo, If you need to use GuildRepo in User, you
	// must go through GuildService
	@Autowired
	private GuildRepository guildRepository;
	@Autowired
	private UserService userService;
	@Autowired
	private GuildLinkService guildLinkService;
	@Autowired
	private ChatroomService chatroomService;
	@Autowired
	private DrawService drawService;

	@Override
	public List<Guild> getAllGuild() {
		return guildRepository.findAll();
	}

	@Override
	public Guild getGuildById(long id) {
		Optional<Guild> guild = guildRepository.findById(id);
		if (guild.isPresent()) {
			return guild.get();
		} else {
			throw new ResourceNotFoundException("Guild", "ID", id);
		}
	}

	@Override
	public Guild newGuild(Guild guild, long userId) {
		User user = userService.getUserById(userId);
		Guild g = guildRepository.save(guild);
		GuildLink NewLink = new GuildLink(g, user, RoleTypes.RoleType.OWNER);
		GuildLink gl = guildLinkService.saveToGuildLinkRepository(NewLink);
		// user.getGuildLink().add(gl);
		g.getLinklist().add(gl);
		guildRepository.save(g);
		userService.saveUser(user);

		return g;
	}

	@Override
	@Transactional
	public Guild updateGuild(long id, Guild guild, long idUser) {
		Guild existingGuild = getGuildById(id);
		User existingUser = userService.getUserById(idUser);

		try {
			GuildLink existingGuildLink = guildLinkService
					.getGuildLinkById(existingGuild.getGuildUserLink(existingUser).getId());
			if (!(existingGuild.getOwner().equals(existingGuildLink))) { // replaces the owner on update if the input
																			// user
				GuildLink oldOwner = existingGuild.getOwner(); // isn't the same as the original owner.
				oldOwner.setRole(RoleTypes.RoleType.MEMBER);
				existingGuildLink.setRole(RoleTypes.RoleType.OWNER);
				guildLinkService.saveToGuildLinkRepository(existingGuildLink);
				guildLinkService.saveToGuildLinkRepository(oldOwner);
			}
		} catch (NullPointerException e) {
			GuildLink oldOwner = existingGuild.getOwner(); // isn't the same as the original owner.
			oldOwner.setRole(RoleTypes.RoleType.MEMBER);
			GuildLink newOwner = new GuildLink(existingGuild, existingUser, RoleTypes.RoleType.OWNER);
			guildLinkService.saveToGuildLinkRepository(newOwner);
			guildLinkService.saveToGuildLinkRepository(oldOwner);
		}

		existingGuild.setGuildimage(guild.getGuildimage());
		existingGuild.setGuildname(guild.getGuildname());
		existingGuild.setLinklist(guild.getLinklist());
		existingGuild.setChatrooms(guild.getChatrooms());
		guildRepository.save(existingGuild);
		return existingGuild;
	}

	@Override
	public Guild guildAddChat(long idGuild, long chatId) {
		Guild guild = getGuildById(idGuild);
		Chatroom chat = chatroomService.getChatroomId(chatId);
		guild.addChatroom(chat);
		guildRepository.save(guild);
		return guild;
	}

	@Override
	public Guild guildDeleteChat(long guildId, long chatID) {
		Guild guild = getGuildById(guildId);
		Chatroom chat = chatroomService.getChatroomId(chatID);
		guild.deleteChatroom(chat);
		guildRepository.save(guild);
		return guild;

	}

	@Override
	public String deleteGuild(long id) {
		Guild existingGuild = getGuildById(id);
		List<GuildLink> nukeLink = existingGuild.getAllGuildUserLink();
		List<DrawGuild> Burn = existingGuild.getGuilddraws();
		int i = 0;
		while (i < nukeLink.size()) {
			if (nukeLink.get(i).getGuilds().getId() == id) {
				guildLinkService.deleteGuildLink(nukeLink.get(i).getId());
				nukeLink.remove(i);
				i--;
			}
			i++;
		}
		while (!Burn.isEmpty()) {
				drawService.deleteGuildDrawing(Burn.get(0).getId());
				Burn.remove(0);
		}
		guildRepository.deleteById(id);
		return "Guild termination successfull!";
	}
}
