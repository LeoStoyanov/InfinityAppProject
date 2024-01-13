package com.example.demo.guild;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * The repository for all of the guilds in the database
 * 
 * @author jroot,ascase
 */

public interface GuildRepository extends JpaRepository<Guild, Long> {
}
