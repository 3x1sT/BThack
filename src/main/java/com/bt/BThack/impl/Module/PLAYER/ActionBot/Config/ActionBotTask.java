package com.bt.BThack.impl.Module.PLAYER.ActionBot.Config;

import com.bt.BThack.api.Utils.Interfaces.Mc;

import java.util.ArrayList;
import java.util.Collections;

public class ActionBotTask implements Mc {
    private String name;
    public ArrayList<String> taskDescription = new ArrayList<>(Collections.singletonList("NULL"));

    public String mode = "";

    public Thread thread;


    public ActionBotTask(String name) {
        this.name = name;
    }

    //Начинает проигрывания задачи
    public void play() {
        while (!isConditionsAreMet()) {
            startDoing();
        }
    }


    public void sleepThread(long millis) {
        try {
            thread.sleep(millis);
        } catch (InterruptedException e) {}
    }

    //Проверяет выполнено ли условие
    public boolean isConditionsAreMet() {
        return false;
    }

    //Дейстие задачи
    public void startDoing() {

    }


    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<String> getTaskDescription() {
        return this.taskDescription;
    }

    public boolean isMoveTask() {
        return this.mode.equalsIgnoreCase("Move");
    }
    public boolean isJumpTask() {
        return this.mode.equalsIgnoreCase("Jump");
    }
    public boolean isSendMessageTask() {
        return this.mode.equalsIgnoreCase("SendMessage");
    }
    public boolean isTunnelTask() {
        return this.mode.equalsIgnoreCase("Tunnel");
    }
    public boolean isWaitTask() {
        return this.mode.equalsIgnoreCase("Wait");
    }

    public static boolean isMoveTask(String mode) {
        return mode.equalsIgnoreCase("Move");
    }
    public static boolean isJumpTask(String mode) {
        return mode.equalsIgnoreCase("Jump");
    }
    public static boolean isSendMessageTask(String mode) {
        return mode.equalsIgnoreCase("SendMessage");
    }
    public static boolean isTunnelTask(String mode) {
        return mode.equalsIgnoreCase("Tunnel");
    }
    public static boolean isWaitTask(String mode) {
        return mode.equalsIgnoreCase("Wait");
    }


    public boolean isStartOrEndTask() {
        return this.name.equalsIgnoreCase("Start") || this.name.equalsIgnoreCase("End");
    }
}
