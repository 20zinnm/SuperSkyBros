package com.relicum.scb.commands;

import com.relicum.scb.SCB;
import com.relicum.scb.SmashPl;
import com.relicum.scb.objects.inventory.RestoreInventory;
import com.relicum.scb.types.SkyApi;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

/**
 * SuperSkyBros
 *
 * @author Relicum
 * @version 0.1
 */
public class leave extends SubBase {

    /**
     * @param player Player
     * @param args   String[]
     * @return boolean
     */
    @Override
    public boolean onCommand(Player player, String[] args) {


        if (SkyApi.getLobbyManager().isInLobby(player)) {
            if (!(player instanceof SmashPl)) {
                SmashPl splayer = SkyApi.getLobbyManager().getSmashPlayer(player.getName());
                if (SCB.getInstance().getConfig().getBoolean(SCB.DEDICATED_SSB)) {
                    if (!SkyApi.getSm().getAdminMode().contains(player.getPlayer().getName())) {
                        String tmp;
                        player.sendMessage(SCB.getMessageManager().getErrorMessage("command.message.leaveNoWhereToGo"));
                        return true;
                    }
                }
                player.removeAttachment(splayer.getPermissionAttachment());
                RestoreInventory.restore(player);
                SkyApi.getInventoryManager().removePlayerFromStore(player.getName());
                SkyApi.getLobbyManager().removePlayer(splayer);

                player.teleport(SkyApi.getLobbyManager().getLobbyRg().getWorld().getSpawnLocation());
                splayer.sendMessage(SCB.getMessageManager().getMessage("command.message.leaveSuccess"));
            } else {

                player.sendMessage(SCB.getMessageManager().getErrorMessage("command.message.leaveNoWhereToGo"));
            }
        }
        return true;
    }


    /**
     * Simplify set this function to set the field mNode with the commands description will come from in the
     * messages.yml file You do not need to enter the full node as it will be prefixed for you. Eg is the full node is
     * command.description.createarena you only need to set this to createarena
     */
    @Override
    public void setmDescription() {
        mNode = "leave";
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
        return "ssb.player.leave";
    }


    /**
     * Simply set this to return the clist Usage
     *
     * @return String
     */
    @Override
    public String setUsage() {
        return "/ssb leave";
    }


    /**
     * Set this to the label of the command
     *
     * @return String
     */
    @Override
    public String setLabel() {
        return "ssb leave";
    }


    /**
     * Set com
     *
     * @return String
     */
    @Override
    public String setCmd() {
        return "ssb leave";
    }


    @Override
    public Plugin getPlugin() {
        return SCB.getInstance();
    }
}
