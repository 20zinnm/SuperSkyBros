package com.relicum.scb;

import com.relicum.scb.configs.WorldConfig;
import com.relicum.scb.objects.world.WorldConfigurator;
import com.relicum.scb.types.SkyBrosApi;
import com.relicum.scb.utils.DelayedShutDown;
import org.bukkit.*;
import org.bukkit.World.Environment;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.metadata.MetadataStoreBase;
import org.bukkit.metadata.MetadataValue;

import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * SuperSkyBros First Created 29/10/13
 *
 * @author Relicum
 * @version 0.1
 */
public class WorldManager {


    public WorldConfig config;
    private boolean defaultGenerate;
    private SCB plugin;
    private WorldCreator template;
    private Map<String, Object> worldSettings;
    private World defaultWorld;
    //Set PVP to True
    //Stop pvp in lobbies
    //Difficulty Hard
    //GameMode Adventure
    //Set Default Gamemode
    //Set player time  to midday (if mixed mode)
    //Disable bed spawn
    //Keep time at midday
    //Set weather to clear 9999999
    //Stop any rain or thunder
    //nether to false
    //end to false
    //fly enabled
    //npcs false
    //view distance to 7
    //player timeout to 5 mins
    //firsttime players
    //set world spawns
    //restrict commands Lobby and Game


    public WorldManager() {
        this.plugin = SkyBrosApi.getSCB();
        defaultGenerate = this.plugin.getConfig().getBoolean("generateDefaultWorld");
        this.config = SkyBrosApi.getSettingsManager2().getWorldConfig();


        //this.worldSettings = this.config.getConfig().getConfigurationSection("worldSettings").getValues(true);

        WorldConfigurator worldSettings = new WorldConfigurator();
        plugin.getLogger().info("WorldGenerator Successfully Loaded");


    }

    private void getMainWorldSettings() throws NoSuchFieldException {

        World theWorld = Bukkit.getWorld("world");

        //Method[] fields = World.class.getDeclaredMethods();
        //Field f2 = fields.getClass().getDeclaredField("generator");

        System.out.println(theWorld.getClass().getDeclaredMethods().toString());
    }

    /**
     * Auto generate default world.
     */
    public void autoGenerateDefaultWorld() {

        String NORMAL = "\033[m";
        String BLUE_TEXT = "\033[36m";  //Blue
        String RED_TEXT = "\033[31m";  //Red
        String GREEN_TEXT = "\033[32m";  //Green
        Integer stage = (Integer) this.plugin.getConfig().getInt("worldGenerateStage");
        String defaultWo = SkyBrosApi.getSCB().getConfig().getString("worldDefault");
        WorldConfigurator wc = new WorldConfigurator();
        switch (stage) {
            case 1:
                wc.setBukkit(SkyBrosApi.getSCB().getConfig().getBoolean("disableTheEnd"), SkyBrosApi.getSCB().getConfig().getString("worldGeneratorPlugin") + ":.", "SSB", stage);
                wc.setMainProperties();
                wc.removeDefaultWorld(defaultWo);
                wc.removeDefaultWorld(defaultWo + "_the_end");
                wc.removeDefaultWorld(defaultWo + "_nether");
                this.plugin.getConfig().set("worldGenerateStage", 2);
                this.plugin.saveConfig();
                this.plugin.reloadConfig();

                SkyBrosApi.getSCB().getLogger().info(GREEN_TEXT + "Part 1 of 3 complete. The server will now automatically stop itself in 5 seconds. Ignore any errors and restart the server" + NORMAL);
                DelayedShutDown.shutDown();
                break;
            case 2:
                wc.removeDefaultWorld(this.plugin.getConfig().getString("worldDefault") + "_the_end");
                wc.setBukkit(this.plugin.getConfig().getBoolean("disableTheEnd"), this.plugin.getConfig().getString("worldGeneratorPlugin"), defaultWo, stage);
                wc.setMainProperties();
                this.plugin.getConfig().set("worldGenerateStage", 3);
                this.plugin.saveConfig();
                this.plugin.reloadConfig();

                SkyBrosApi.getSCB().getLogger().info(GREEN_TEXT + "Part 3 of 4 complete. The server will now automatically stop itself in 5 seconds. Ignore any errors and restart the server" + NORMAL);
                this.config.getConfig().set("worlds.default", defaultWo);
                this.config.saveConfig();
                this.config.reloadConfig();
                DelayedShutDown.shutDown();
                break;
            case 3:
                wc.removeDefaultWorld(this.plugin.getConfig().getString("newWorldDefault"));
                wc.removeDefaultWorld(this.plugin.getConfig().getString("newWorldDefault") + "_the_end");
                this.plugin.getConfig().set("worldGenerateStage", 1);
                this.plugin.getConfig().set("generateDefaultWorld", false);
                // this.config.getConfig().set("worlds.default." + defaultWo + ".uuid", this.defaultWorld.getUID().toString());
                this.plugin.saveConfig();
                this.plugin.reloadConfig();
                SkyBrosApi.getSCB().getLogger().info(BLUE_TEXT + "You have successfully complete the process of converting the main world into a \nVoid world. Do not be surprised" +
                        " when you login you will spawn on a single gold block" + NORMAL);
                DelayedShutDown.shutDown();
                break;
            default:
                SkyBrosApi.getSCB().getLogger().severe(RED_TEXT + "Default world creation switch statement has hit default something has happened" + NORMAL);
                break;


        }


    }

    public void getDefaultWorldHandle(String name) {
        WorldCreator worldCreator = new WorldCreator(name);
        try {
            this.defaultWorld = worldCreator.createWorld();
        } catch (Exception e) {
            e.printStackTrace();
            Bukkit.broadcast(ChatColor.RED + "Error loading the main load check logs", "bukkit.broadcast.admin");
        }
        this.defaultWorld = this.applyWorldDefaultSettings(name);

    }

    /**
     * Set template defaults for new works.
     */
    private void setTemplateDefaults() {

        this.template = new WorldCreator(this.getConfig().getString("default.name"));
        this.template.type(WorldType.valueOf(this.getConfig().getString("default.type")));
        this.template.environment(Environment.valueOf(this.getConfig().getString("default.environment")));
        this.template.generateStructures(this.getConfig().getBoolean("default.structures"));
        this.template.generator(this.getConfig().getString("default.generator"));

    }

    private WorldCreator applyDefaultSettings(String name) {

        WorldCreator worldCreator = new WorldCreator(name);
        worldCreator.type(WorldType.FLAT);
        worldCreator.environment(Environment.NORMAL);
        worldCreator.generateStructures(false);
        worldCreator.generator("CleanroomGenerator:.");
        worldCreator.seed(this.randomSeed());

        return worldCreator;
    }

    /**
     * Gets config as FileConfiguration
     *
     * @return the config as a FileConfiguration object
     */
    public FileConfiguration getConfig() {
        return config.getConfig();
    }

    public void setConfig(WorldConfig config) {
        this.config = config;
    }

    public WorldConfig getConfigs() {
        return this.config;
    }

    /**
     * Generate template world.
     *
     * @return the boolean true if world was created successfully
     */
    private boolean generateTemplate() {

        try {
            this.plugin.getLogger().info("Attempting to create template World");
            World world = this.template.createWorld();
            MetadataStoreBase meta = new MetadataStoreBase() {

                @Override
                protected String disambiguate(Object o, String s) {
                    return null;
                }
            };

            List<MetadataValue> wm = world.getMetadata("456");
            System.out.println(wm.toString());


            this.plugin.getLogger().info("Finished creating template World");
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    /**
     * If true the default world will be deleted and recreated As a empty void world
     *
     * @return the boolean if true recreate default world
     */
    public boolean isDefaultGenerate() {
        return defaultGenerate;
    }

    private World createNewWorld(String name) {

        WorldCreator worldCreator = this.applyDefaultSettings("Template");

        World world;
        try {
            world = worldCreator.createWorld();

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        System.out.println("New world called " + name + "has been created");
        return world;
    }

    public World applyWorldDefaultSettings(String name) {

        World world = null;

        try {
            world = Bukkit.getServer().getWorld(name);
        } catch (NullPointerException e) {
            e.printStackTrace();
            return null;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }

        world.loadChunk(0, 0, true);
        world.setSpawnLocation(0, 32, 0);
        world.setKeepSpawnInMemory(true);

        world.getSpawnLocation().getWorld().getChunkAt(world.getSpawnLocation()).load();
        Block block = world.getBlockAt(0, 31, 0);
        block.getState().setType(Material.GOLD_BLOCK);
        block.getState().update(true);
        world.setAutoSave(true);
        world.setDifficulty(Difficulty.HARD);
        world.setStorm(false);
        world.setThundering(false);
        world.setWeatherDuration(9999999);
        world.setTime(6000);
        plugin.getServer().setDefaultGameMode(GameMode.ADVENTURE);
        world.setGameRuleValue("doDaylightCycle", "false");
        world.setGameRuleValue("doFireTick", "false");
        world.setGameRuleValue("mobGriefing", "false");
        world.save();

        System.out.println("Settings applied for  " + name + " has been successful");
        return world;
    }

    private Location convertToLocation(String l) {

        return null;
    }

    private long randomSeed() {

        Random random = new Random();
        return random.nextLong();
    }

}
