package com.example.demo.draw;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DrawController {

	@Autowired
	private DrawService drawService;

	@GetMapping(path = "/guildDrawings")
	public List<DrawGuild> getAllGuildDrawings() {
		return drawService.getAllGuildDrawings();
	}

	@GetMapping(path = "/guildDrawings/{id}")
	public DrawGuild getAllGuildDrawingById(@PathVariable("id") long id) {
		return drawService.getGuildDrawingId(id);
	}

	@PostMapping(path = "/newGuildDrawing/{guildid}")
	ResponseEntity<String> newGuildDrawing(@PathVariable("guildid") long guildid,@RequestBody DrawGuild draw) {
		DrawGuild gd = drawService.newGuildDrawing(guildid,draw);
		return new ResponseEntity<String>("New guild drawing made! At Id:"+ gd.getId() , HttpStatus.CREATED);
	}

	@PutMapping(path = "/guildDrawings/{id}")
	public ResponseEntity<DrawGuild> updateGuildDrawing(@PathVariable long id, @RequestBody DrawGuild draw) {
		return new ResponseEntity<DrawGuild>(drawService.updateGuildDrawing(id, draw), HttpStatus.OK);
	}

	/**
	 * Adds a guild drawing to the user
	 * 
	 * @param idGuild
	 * @param idDrawUser
	 * @return
	 */
	@PostMapping(path = "/drawUserToGuild/{idGuild}/{idDrawUser}")
	ResponseEntity<DrawGuild> AddUserDrawToGuildDraw(@PathVariable long idGuild, @PathVariable long idDrawUser) {
		return new ResponseEntity<>(drawService.AddUserDrawToGuildDraw(idGuild, idDrawUser), HttpStatus.CREATED);
	}

	@DeleteMapping(path = "/deleteGuildDrawing/{id}")
	public ResponseEntity<String> deleteGuildDrawing(@PathVariable("id") long id) {
		return new ResponseEntity<String>(drawService.deleteGuildDrawing(id), HttpStatus.OK);
	}

	////////////

	@GetMapping(path = "/userDrawings")
	public List<DrawUser> getAllUserDrawings() {
		return drawService.getAllUserDrawings();
	}

	@GetMapping(path = "/userDrawings/{id}")
	public DrawUser getAllUserDrawingById(@PathVariable("id") long id) {
		return drawService.getUserDrawingId(id);
	}

	@PostMapping(path = "/newUserDrawing/{userId}")
	ResponseEntity<String> newUserDrawing(@PathVariable("userId") long userId, @RequestBody DrawUser drawUser) {
		DrawUser du = drawService.newUserDrawing(userId, drawUser);
		return new ResponseEntity<String>("New user drawing was made at Id:" + du.getId(), HttpStatus.CREATED);
	}

	@PutMapping(path = "/updateUserDrawings/{id}")
	public ResponseEntity<DrawUser> updateUserDrawing(@PathVariable long id, @RequestBody DrawUser draw) {
		return new ResponseEntity<DrawUser>(drawService.updateUserDrawing(id, draw), HttpStatus.OK);
	}

	/**
	 * Adds a User drawing to the guild
	 * 
	 * @param idUser
	 * @param idDrawGuild
	 * @return
	 */
	@PostMapping(path = "/drawGuildToUser/{idUser}/{idDrawGuild}")
	ResponseEntity<DrawUser> AddGuildDrawToUserDraw(@PathVariable long idUser, @PathVariable long idDrawGuild) {
		return new ResponseEntity<>(drawService.AddGuildDrawToUserDraw(idUser, idDrawGuild), HttpStatus.CREATED);
	}

	@DeleteMapping(path = "/userDrawing/{id}")
	public ResponseEntity<String> deleteUserDrawing(@PathVariable("id") long id) {
		return new ResponseEntity<String>(drawService.deleteUserDrawing(id), HttpStatus.OK);
	}

}
