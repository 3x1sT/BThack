package com.bt.BThack.impl.Module.PLAYER.ActionBot.Config;

import java.util.ArrayList;
import java.util.Arrays;

public class ActionBotConfig {
    public static final ActionBotTask startTask = new ActionBotTask("Start");
    public static final ActionBotTask endTask = new ActionBotTask("End");

    public static ArrayList<ActionBotTask> tasks = new ArrayList<>(Arrays.asList(
            startTask,
            endTask
    ));


    public static void addActionTask(ActionBotTask task) {
        tasks.remove(endTask);
        tasks.add(task);
        tasks.add(endTask);
    }

    public static void addActionTask(ActionBotTask task, int index) {
        ArrayList<ActionBotTask> tempList = tasks;
        tempList.remove(startTask);
        tempList.remove(endTask);

        ArrayList<ActionBotTask> tempList2 = new ArrayList<>();

        tempList2.add(startTask);

        for (int i = 0; i < tempList.size(); i++) {
            if (i == index) {
                tempList2.add(task);
            }

            tempList2.add(tempList.get(i));
        }

        tempList2.add(endTask);

        tasks.clear();
        tasks = tempList2;
    }

    public static void removeActionTask() {
        tasks.remove(endTask);
        tasks.remove(tasks.size() - 1);
        tasks.add(endTask);
    }

    public static void removeActionTask(int index) {
        ArrayList<ActionBotTask> tempList = tasks;
        tempList.remove(startTask);
        tempList.remove(endTask);

        ArrayList<ActionBotTask> tempList2 = new ArrayList<>();

        tempList2.add(startTask);

        for (int i = 0; i < tempList.size(); i++) {
            if (i != index) {
                tempList2.add(tempList.get(i));
            }
        }

        tempList2.add(endTask);

        tasks.clear();
        tasks = tempList2;
    }
}
