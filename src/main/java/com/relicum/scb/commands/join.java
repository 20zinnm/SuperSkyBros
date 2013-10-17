package com.relicum.scb.commands;

import com.relicum.scb.SCB;
import com.relicum.scb.SmashPlayer;
import com.relicum.scb.events.PlayerJoinLobbyEvent;
import com.relicum.scb.objects.inventory.ClearInventory;
import com.relicum.scb.utils.playerStatus;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 * Bukkit-SCB
 *
 * @author Relicum
 * @version 0.1
 */
public class join extends SubBase {

    /**
     * @param player Player
     * @param args   String[]
     * @return boolean
     */
    @Override
    public boolean onCommand(Player player, String[] args) {

        if (SCB.getInstance().LBS.isInLobby(player)) {
            player.sendMessage(SCB.getMessageManager().getErrorMessage("listeners.playerJoin.alreadyInLobby"));
            return true;
        }
        SmashPlayer splayer = SmashPlayer.wrap(player);

        splayer.pStatus = playerStatus.UNKNOWN;
        PlayerJoinLobbyEvent event = new PlayerJoinLobbyEvent(splayer, "COMMAND", SCB.getInstance().getConfig().getBoolean("dedicatedSSB"));
        Bukkit.getServer().getPluginManager().callEvent(event);

        return true;

    }


    /**
     * Teleports the player to the given location
     *
     * @param p Player
     * @param l Location
     * @return boolean
     * @throws IllegalArgumentException
     */
    public boolean teleportToLobby(final Player p, final Location l) {

        SCB.getInstance().getServer().getScheduler().runTaskLater(SCB.getInstance(), new Runnable() {

            @Override
            public void run() {


                if (!p.teleport(l)) {
                    System.out.println("Error teleporting player to lobby");
                }
                p.sendMessage(SCB.MM.getMessage("command.message.teleportToLobby"));
            }
        }, 10L);

        return true;
    }


    /**
     * Simplify set this function to set the field descNode with the commands description will come from in the
     * messages.yml file You do not need to enter the full node as it will be prefixed for you. Eg is the full node is
     * command.description.createarena you only need to set this to createarena
     */
    @Override
    public void setmDescription() {
        mNode = "join";
    }


    /**
     * Simply set this to return the the number of arguments The command should receive
     *
     * @return Integer
     */
    @Override
    public Integer setNumArgs() {
        return 0;
    }


    /**
     * Simply set this to return the clist permission
     *
     * @return String
     */
    @Override
    public String setPermission() {
        return "ssb.player.join";
    }


    /**
     * Simply set this to return the clist Usage
     *
     * @return String
     */
    @Override
    public String setUsage() {
        return "/ssb join";
    }


    /**
     * Set this to the label of the command
     *
     * @return String
     */
    @Override
    public String setLabel() {
        return "ssb join";
    }


    /**
     * Set com
     *
     * @return String
     */
    @Override
    public String setCmd() {
        return "ssb join";
    }
}
