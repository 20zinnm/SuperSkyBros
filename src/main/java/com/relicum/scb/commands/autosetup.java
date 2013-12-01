package com.relicum.scb.commands;

import com.relicum.scb.types.SkyBrosApi;
import com.relicum.scb.utils.DelayedShutDown;
import org.bukkit.ChatColor;
import org.bukkit.conversations.ConversationFactory;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.io.IOException;

/**
 * SuperSkyBros
 * First Created 29/11/13
 *
 * @author Relicum
 * @version 0.1
 */
public class autosetup extends SubBase {

    public static ConversationFactory factory;

    private final String cHeader = "    \u00A72>\u25AC*\u25AC*\u25AC*\u25AC*\u25AC*\u25AC*\u25AC*\u25AC*\u25AC*\u25AC" +
            "[\u00A7b\u00A7lSuper-Sky-Bros\u00A72]\u25AC*\u25AC*\u25AC*\u25AC*\u25AC" +
            "*\u25AC*\u25AC*\u25AC*\u25AC*\u25AC<\n";

    private StringBuilder sb;
    String NORMAL = "\033[m";
    String MENU = "\033[36m";  //Blue
    String RED_TEXT = "\033[31m";  //Red
    String GREEN_TEXT = "\033[32m";  //Green

    /**
     * @param player Player
     * @param args   String[]
     * @return boolean
     */
    @Override
    public boolean onCommand(Player player, String[] args) throws IOException, ClassNotFoundException {
        SkyBrosApi.getSCB().getConfig().set("generateDefaultWorld", true);
        SkyBrosApi.getSCB().saveConfig();
        SkyBrosApi.getSCB().reloadConfig();
        player.sendMessage(ChatColor.GREEN + "The server will now shutdown you will need to stop and start the server 4 times\n to complete the setup the console will display details of progress.");
        player.sendMessage("");
        player.sendMessage(ChatColor.RED + "The server will now shutdown in 6 seconds");
        System.out.println(GREEN_TEXT + "The server will now shutdown in 6 seconds" + NORMAL);
        DelayedShutDown.shutDown();
        return true;

    }

    /**
     * Simplify set this function to set the field mNode with the commands description will come from in the
     * messages.yml file You do not need to enter the full node as it will be prefixed for you. Eg is the full node is
     * command.description.createarena you only need to set this to createarena
     */
    @Override
    public void setmDescription() {
        mNode = "autosetup";
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
        return "ssbw.admin.autosetup";
    }

    /**
     * Simply set this to return the clist Usage
     *
     * @return String
     */
    @Override
    public String setUsage() {
        return "/ssbw autosetup";
    }

    /**
     * Set this to the label of the command
     *
     * @return String
     */
    @Override
    public String setLabel() {
        return "ssbw autosetup";
    }

    /**
     * Set com
     *
     * @return String
     */
    @Override
    public String setCmd() {
        return "ssbw autosetup";
    }

    @Override
    public Plugin getPlugin() {
        return SkyBrosApi.getSCB();
    }


}