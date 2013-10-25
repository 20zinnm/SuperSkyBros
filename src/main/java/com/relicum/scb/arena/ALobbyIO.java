package com.relicum.scb.arena;

import com.relicum.scb.SCB;
import com.relicum.scb.objects.ArenaLobby;
import org.bukkit.configuration.ConfigurationSection;

/**
 * SuperSkyBros First Created 25/10/13
 *
 * @author Relicum
 * @version 0.1
 */
public class ALobbyIO {

    private ArenaLobby arenaLobby;

    private ConfigurationSection section;

    private Arena arena;


    public ArenaLobby getArenaLobby() {
        return arenaLobby;
    }


    public ConfigurationSection getSection() {
        return section;
    }


    public void setSection(ConfigurationSection section) {
        this.section = section;
    }


    public void setArenaLobby(ArenaLobby arenaLobby) {
        this.arenaLobby = arenaLobby;
    }


    public ALobbyIO(ArenaLobby arenaLobby) {

        this.arenaLobby = arenaLobby;
        this.section = SCB.getInstance().ARC.getConfig().getConfigurationSection("arena.arenas." + arenaLobby.getArenaId());
        this.arena = SCB.getInstance().ARM.getArenaById(arenaLobby.getArenaId());
    }


    public void saveLobby() {

        this.section.set("lobby.min", this.arenaLobby.getMin());
        this.section.set("lobby.max", this.arenaLobby.getMax());
        this.section.set("lobby.spawn", this.arenaLobby.getSpawn().toVector());
        this.section.set("lobby.world", this.arenaLobby.getWorld().getName());
        this.section.set("lobby.id", this.arenaLobby.getArenaId());

        SCB.getInstance().ARC.saveConfig();
        SCB.getInstance().ARC.reloadConfig();
        this.arena.setArenaLobby(arenaLobby);
        this.arena.saveArena();

    }
}