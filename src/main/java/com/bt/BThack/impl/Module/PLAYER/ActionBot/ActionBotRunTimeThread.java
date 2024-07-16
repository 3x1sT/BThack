package com.bt.BThack.impl.Module.PLAYER.ActionBot;

import com.bt.BThack.System.Client;
import com.bt.BThack.api.Module.Module;
import com.bt.BThack.api.Utils.ChatUtils;
import com.bt.BThack.impl.Module.PLAYER.ActionBot.Config.ActionBotConfig;
import com.bt.BThack.impl.Module.PLAYER.ActionBot.Config.ActionBotTask;
import com.mojang.realmsclient.gui.ChatFormatting;

public class ActionBotRunTimeThread extends Thread {
    public static boolean isPlaying = false;

    @Override
    public void run() {
        ChatUtils.sendMessage("[ActionBot] "  + ChatFormatting.AQUA + "Starting to reproduce the task chain.");

        if (Module.getCheckbox("ActionBot", "Repeat")) {
            while (Client.getModuleByName("ActionBot").isEnabled()) {
                play();
            }

            Client.getModuleByName("ActionBot").setToggled(false);
        } else {
            play();

            Client.getModuleByName("ActionBot").setToggled(false);
        }
    }




    public void play() {
        isPlaying = true;

        for (ActionBotTask task : ActionBotConfig.tasks) {
            if (!task.isStartOrEndTask()) {
                task.thread = this;

                task.play();
            }
        }

        isPlaying = false;
    }
}
