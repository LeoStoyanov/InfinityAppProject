package com.example.demo.draw;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.guild.*;
import com.example.demo.user.*;

@Service
public class DrawServiceImpl implements DrawService {

	@Autowired
	private DrawGuildRepository drawGuildRepository;
	@Autowired
	private DrawUserRepository drawUserRepository;
	@Autowired
	private UserService userService;
	@Autowired
	private GuildService guildService;
	
	
	@Override
	public List<DrawGuild> getAllGuildDrawings() {
		return drawGuildRepository.findAll();
	}

	@Override
	public DrawGuild getGuildDrawingId(long id) {
		Optional<DrawGuild> draw = drawGuildRepository.findById(id);
		if (draw.isPresent()) {
			return draw.get();
		} else {
			throw new ResourceNotFoundException("DrawGuild", "ID", id);
		}
	}

	@Override
	public DrawGuild newGuildDrawing(long guildId,DrawGuild drawGuild) {
		Guild guild = guildService.getGuildById(guildId);
		DrawGuild dg = drawGuildRepository.save(drawGuild);
		dg.setDrawHostGuild(guild);
		guild.getGuilddraws().add(dg);
		guildService.updateGuild(guildId, guild, guild.getOwner().getUsers().getId());
		return drawGuildRepository.save(drawGuild);
	}

	@Override
	public DrawGuild updateGuildDrawing(long id, DrawGuild draw) {
		DrawGuild existingDrawing = getGuildDrawingId(id);
		existingDrawing.setDrawname(draw.getDrawname());
		existingDrawing.setDrawing(draw.getDrawing());
		drawGuildRepository.save(existingDrawing);
		return existingDrawing;
	}

	@Override
	public DrawGuild AddUserDrawToGuildDraw(long idGuild, long idDrawUser) {
		Guild guild = guildService.getGuildById(idGuild);
		DrawUser existingUserDrawing = getUserDrawingId(idDrawUser);
		DrawGuild newDrawing = new DrawGuild(existingUserDrawing.getDrawname(), existingUserDrawing.getDrawing(), guild);
		return drawGuildRepository.save(newDrawing);
	}

	@Override
	public String deleteGuildDrawing(long id) {
		DrawGuild existingDrawing = getGuildDrawingId(id);
		drawGuildRepository.deleteById(id);
		return "Guild Drawing termination successfull!";
	}

	////////////////////////////////////////////////////

	@Override
	public List<DrawUser> getAllUserDrawings() {
		return drawUserRepository.findAll();
	}

	@Override
	public DrawUser getUserDrawingId(long id) {
		Optional<DrawUser> draw = drawUserRepository.findById(id);
		if (draw.isPresent()) {
			return draw.get();
		} else {
			throw new ResourceNotFoundException("DrawUser", "ID", id);
		}
	}

	@Override
	public DrawUser newUserDrawing(long userId, DrawUser drawUser) {
		DrawUser u = drawUser;
		u.setDrawHostUser(userService.getUserById(userId));
		return drawUserRepository.save(u);
	}

	@Override
	public DrawUser updateUserDrawing(long id, DrawUser draw) {
		DrawUser existingDrawing = getUserDrawingId(id);
		existingDrawing.setDrawname(draw.getDrawname());
		existingDrawing.setDrawing(draw.getDrawing());
		drawUserRepository.save(existingDrawing);
		return existingDrawing;
	}

	@Override
	public DrawUser AddGuildDrawToUserDraw(long idUser, long idDrawGuild) {
		User user = userService.getUserById(idUser);
		DrawGuild existingGuildDrawing = getGuildDrawingId(idDrawGuild);
		DrawUser newDrawing = new DrawUser(existingGuildDrawing.getDrawname(), existingGuildDrawing.getDrawing(), user);
		return drawUserRepository.save(newDrawing);
	}

	@Override
	public String deleteUserDrawing(long id) {
		DrawUser existingDrawing = getUserDrawingId(id);
		drawUserRepository.deleteById(id);
		return "User Drawing termination successfull!";
	}
}
