package com.example.demo.draw;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

public interface DrawService {
		
	public List<DrawGuild> getAllGuildDrawings();
	
    public DrawGuild getGuildDrawingId(long id);
    
    public DrawGuild newGuildDrawing(long guildid, DrawGuild drawGuild);
    
    public DrawGuild updateGuildDrawing(long id, DrawGuild draw);
    
    public DrawGuild AddUserDrawToGuildDraw(long idGuild, long idDrawUser);
    
    public String deleteGuildDrawing(long id);
    
    //------------------------------------------//
    
    public List<DrawUser> getAllUserDrawings();
    
    public DrawUser getUserDrawingId(long id);
    
    public DrawUser newUserDrawing(long userId, DrawUser drawUser);
    
    public DrawUser updateUserDrawing(long id, DrawUser draw);
    
    public DrawUser AddGuildDrawToUserDraw(long idUser, long idDrawGuild);
    
    public String deleteUserDrawing(long id);
    
}
