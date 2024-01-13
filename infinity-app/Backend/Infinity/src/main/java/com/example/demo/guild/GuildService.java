package com.example.demo.guild;

import org.springframework.http.ResponseEntity;

import java.util.List;

public interface GuildService
{
    public List<Guild> getAllGuild();

    public Guild getGuildById(long id);

    public Guild newGuild(Guild guild, long userId);

    public Guild updateGuild(long idGuild, Guild guildUpdates, long idOwner);

    public Guild guildAddChat(long idGuild, long chatId);

    public Guild guildDeleteChat(long idGuild, long chatID);

    public String deleteGuild(long id);
}
