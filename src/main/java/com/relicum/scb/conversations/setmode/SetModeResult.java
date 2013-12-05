package com.relicum.scb.conversations.setmode;

import com.relicum.scb.SCB;
import com.relicum.scb.objects.signs.utils.Col;
import com.relicum.scb.types.SkyApi;
import com.relicum.scb.utils.DelayedShutDown;
import org.bukkit.ChatColor;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.MessagePrompt;
import org.bukkit.conversations.Prompt;

/**
 * SuperSkyBros First Created 19/11/13
 *
 * @author Relicum
 * @version 0.1
 */
public class SetModeResult extends MessagePrompt {


    public SetModeResult() {

    }


    @Override
    protected Prompt getNextPrompt(ConversationContext context) {
        return Prompt.END_OF_CONVERSATION;
    }


    @Override
    public String getPromptText(ConversationContext context) {
        if (context.getSessionData("mode").toString().equalsIgnoreCase("mixed")) {
            for (int i = 0; i < 10; i++) {
                context.getForWhom().sendRawMessage("");
            }
            context.getForWhom().sendRawMessage(Col.Grey() + "---------------------------");
            if (!setupMode(false)) {
                return Col.Dark_Red() + "Error occurred while setting mode to mixed please check logs";
            }


            context.getForWhom().sendRawMessage(ChatColor.YELLOW + "Mode successfully set to MIXED MODE");
            context.getForWhom().sendRawMessage(Col.Grey() + "---------------------------");
            DelayedShutDown.shutDown();
            return ChatColor.YELLOW + "The server will shutdown";
        }
        if (context.getSessionData("mode").toString().equalsIgnoreCase("dedicated")) {
            if (!setupMode(true)) {
                return Col.Dark_Red() + "Error occurred while setting mode to DEDICATED please check logs";
            }


            context.getForWhom().sendRawMessage(ChatColor.YELLOW + "Mode successfully set to DEDICATED");
            context.getForWhom().sendRawMessage(Col.Grey() + "---------------------------");
            DelayedShutDown.shutDown();
            return ChatColor.YELLOW + "The server will shutdown";
        }


        return Col.Green() + "About to shut down";
    }


    private boolean setupMode(boolean isDedicated) {

        SCB plugin = SCB.getInstance();

        try {
            SkyApi.getCMsg().INFO("setMode is now before saving " + SkyApi.getSCB().getConfig().get("modeSet"));
            SkyApi.getSCB().getConfig().set(SCB.DEDICATED_SSB, isDedicated);
            SkyApi.getSCB().getConfig().set("modeSet", true);
            SkyApi.getSCB().saveOnDisable = true;
            SkyApi.getSCB().saveConfig();
            SkyApi.getSCB().reloadConfig();
            SkyApi.getCMsg().INFO("Set Mode has been saved to file its value is  " + SkyApi.getSCB().getConfig().get("modeSet"));


        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
        return true;


    }
}
