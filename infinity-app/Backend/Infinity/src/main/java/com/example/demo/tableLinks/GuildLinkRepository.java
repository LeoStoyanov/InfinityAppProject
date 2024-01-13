package com.example.demo.tableLinks;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.guild.Guild;
import com.example.demo.user.User;
/**
 * The repository for all of the guildlinks in the database
 * 
 * @author jroot,ascase
 */
public interface GuildLinkRepository extends JpaRepository<GuildLink, Long> {
	
}
