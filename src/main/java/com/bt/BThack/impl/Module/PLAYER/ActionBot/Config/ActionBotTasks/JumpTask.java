package com.bt.BThack.impl.Module.PLAYER.ActionBot.Config.ActionBotTasks;

import com.bt.BThack.impl.Module.PLAYER.ActionBot.Config.ActionBotTask;

import java.util.ArrayList;
import java.util.Arrays;

public class JumpTask extends ActionBotTask {

    public JumpTask() {
        super("Jump");
        this.mode = "Jump";

        this.taskDescription = new ArrayList<>(Arrays.asList(
                "When the task is activated, the player starts jumping."
        ));

    }


    @Override
    public void play() {
        mc.gameSettings.keyBindJump.pressed = true;
        sleepThread(50);
        mc.gameSettings.keyBindJump.pressed = false;

        while (!mc.player.collidedVertically) {
            sleepThread(50);
        }
    }
}
