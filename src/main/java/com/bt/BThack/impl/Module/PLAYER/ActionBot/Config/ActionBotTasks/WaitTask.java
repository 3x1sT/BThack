package com.bt.BThack.impl.Module.PLAYER.ActionBot.Config.ActionBotTasks;

import com.bt.BThack.impl.Module.PLAYER.ActionBot.Config.ActionBotTask;

import java.util.ArrayList;
import java.util.Arrays;

public class WaitTask extends ActionBotTask {
    double seconds;

    public WaitTask(double seconds) {
        super("Wait");
        this.mode = "Wait";

        this.seconds = seconds;

        this.taskDescription = new ArrayList<>(Arrays.asList(
                "When a task is activated, the player waits up to a set number of seconds,",
                "after which the next task starts."
        ));
    }

    @Override
    public void play() {
        sleepThread((long) (seconds * 1000));
    }

    public double getSeconds() {
        return this.seconds;
    }
}
