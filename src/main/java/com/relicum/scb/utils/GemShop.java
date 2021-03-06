package com.relicum.scb.utils;

import com.relicum.scb.SCB;
import com.relicum.scb.utils.IconMenu.OptionClickEventHandler;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

/**
 * SuperSkyBros First Created 04/10/13
 *
 * @author Relicum
 * @version 0.1
 */
public class GemShop {

    private Material gem;

    public static IconMenu iconMenu;

    public static SCB scb;


    public GemShop(SCB p) {
        GemShop.scb = p;
    }


    //TODO Create the Inventory
    //TODO Add the Gem
    //TODO Add Interact Event
    //TODO Add Click Event
    //TODO Handle Click Event
    //TODO Inventory Close and Update
    //TODO Prevent Ways from players glitching it
    //TODO MultiThreading
    //TODO Include Join Book
    //TODO Settings in config file
    //TODO Option for compass
    //TODO Maybe include Buyable perks as well as Classes


    public static void openGem(Player player) {

        List<String> creeperDescription = new ArrayList<>();
        creeperDescription.add("Creeper Class");
        creeperDescription.add("Triple Jump");
        creeperDescription.add("Super Speed");
        creeperDescription.add("FREE");

        List<String> spiderDescription = new ArrayList<>();
        spiderDescription.add("Spider Class");
        spiderDescription.add("Magic Ink");
        spiderDescription.add("Invisibility");
        spiderDescription.add("VIP");

        iconMenu = new IconMenu(
                "Classes", 27, new OptionClickEventHandler() {

            @Override
            public void onOptionClick(final IconMenu.OptionClickEvent event) {
                Player p = event.getPlayer();
                if (event.getPosition() == 0) setClass(p, "Creeper");
                if (event.getPosition() == 1) setClass(p, "Spider");
                event.setWillClose(true);
                event.setWillDestroy(true);

            }
        }, scb).setOption(0, new ItemStack(Material.CACTUS, 1), "Creeper", creeperDescription.toArray(new String[creeperDescription.size()])).setOption(
                1, new ItemStack(
                Material.SPIDER_EYE, 1), "Spider", spiderDescription.toArray(new String[spiderDescription.size()]));

        iconMenu.open(player);

    }


    public static void setClass(Player player, String name) {
        player.sendMessage(ChatColor.GREEN + "Name of the class selected is " + ChatColor.GOLD + name);


    }


}
