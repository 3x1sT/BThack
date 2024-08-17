package com.bt.BThack.impl.Module.PLAYER.PMSpammer;

import com.bt.BTbot.api.Utils.Generate.GenerateNumber;
import com.bt.BThack.System.Client;
import com.bt.BThack.api.Module.Module;
import com.bt.BThack.api.Utils.ChatUtils;
import com.bt.BThack.api.Utils.Interfaces.Mc;
import com.mojang.realmsclient.gui.ChatFormatting;
import net.minecraft.client.network.NetworkPlayerInfo;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class PMSpammerThread extends Thread implements Mc {

    Random random = new Random();

    public static int m = 1;

    public void run() {
        Path path = Paths.get("BThack/Spammer/Spammer.txt");
        String Mode = Module.getMode("PMSpammer", "Mode");
        ChatUtils.sendMessage(Client.getModuleByName("PMSpammer").getChatName() + ChatFormatting.AQUA + " Starting to send a messages to all players...");
        while (Client.getModuleByName("PMSpammer").isEnabled()) {
            Client.getModuleByName("PMSpammer").arrayListInfo = Mode;

            if (Objects.equals(Mode, "InOrder")) {
                try {
                    if (Files.exists(path)) {
                        BufferedReader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8);
                        String line = reader.readLine();
                        if (m != 1) {
                            for (int i = 1; i < m; i++) {
                                if (line != null) {
                                    line = reader.readLine();
                                }
                            }
                        }
                        if (line != null) {
                            Set<NetworkPlayerInfo> playerInfos = new HashSet<>(mc.player.connection.getPlayerInfoMap());

                            for (NetworkPlayerInfo info : playerInfos) {
                                if (!Client.getModuleByName("PMSpammer").isEnabled()) stop();

                                String tempLine = line;

                                String playerName = getPlayerName(info);

                                if (playerName.equals(mc.player.getName())) continue;

                                ChatUtils.sendMessage(Client.getModuleByName("PMSpammer").getChatName() + ChatFormatting.AQUA + " Trying to send messages to a player: " + playerName);

                                if (Module.getCheckbox("PMSpammer", "Anti Spam Fix")) {
                                    tempLine += " " + GenerateNumber.generateInt(0, 9) + GenerateNumber.generateInt(0, 9);
                                }

                                if (Module.getCheckbox("PMSpammer", "LowerCase")) {
                                    tempLine = tempLine.toLowerCase();
                                }

                                mc.player.sendChatMessage("/w " + playerName + " " + tempLine);
                                long a = (long) (Module.getSlider("PMSpammer", "Delay(Second)") * 1000);
                                if (Module.getCheckbox("PMSpammer", "Delay Spread")) {
                                    if (!random.nextBoolean()) {
                                        a = (long) (a * GenerateNumber.generateFloat(1, 1f + (float) Module.getSlider("PMSpammer", "Delay Spread")));
                                    } else {
                                        a = (long) (a * GenerateNumber.generateFloat((float) Module.getSlider("PMSpammer", "Delay Spread"), 1));
                                    }
                                }
                                try {
                                    sleep(a);
                                } catch (Exception ignored) {
                                }
                            }
                            m = m + 1;
                            ChatUtils.sendMessage(Client.getModuleByName("PMSpammer").getChatName() + ChatFormatting.AQUA + " Message sent to all players. Moving on to the next message...");
                        } else {
                            m = 1;
                        }
                        reader.close();
                        if (!Client.getModuleByName("PMSpammer").isEnabled()) stop();
                        long a = (long) (Module.getSlider("PMSpammer", "Delay(Second)") * 1000);
                        if (Module.getCheckbox("PMSpammer", "Delay Spread")) {
                            if (!random.nextBoolean()) {
                                a = (long) (a * GenerateNumber.generateFloat(1, 1f + (float) Module.getSlider("PMSpammer", "Delay Spread")));
                            } else {
                                a = (long) (a * GenerateNumber.generateFloat((float) Module.getSlider("PMSpammer", "Delay Spread"), 1));
                            }
                        }
                        try {
                            sleep(a);
                        } catch (Exception ignored) {
                        }
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            } else
            if (Objects.equals(Mode, "Random")) {
                if (Files.exists(path)) {
                    PMSpammer.readTXT.read();
                    long randomValue = 1 + (int) (Math.random() * (PMSpammer.readTXT.value));
                    try {
                        BufferedReader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8);
                        String line = reader.readLine();
                        if (randomValue == 1)
                            mc.player.sendChatMessage(line);
                        else {
                            for (int i = 1; i < randomValue; i++) {
                                line = reader.readLine();
                            }
                            if (line != null) {
                                Set<NetworkPlayerInfo> playerInfos = new HashSet<>(mc.player.connection.getPlayerInfoMap());

                                for (NetworkPlayerInfo info : playerInfos) {
                                    if (!Client.getModuleByName("PMSpammer").isEnabled()) stop();

                                    String playerName = getPlayerName(info);

                                    String tempLine = line;

                                    if (playerName.equals(mc.player.getName())) continue;

                                    ChatUtils.sendMessage(Client.getModuleByName("PMSpammer").getChatName() + ChatFormatting.AQUA + " Sending a message to a player: " + playerName);

                                    if (Module.getCheckbox("PMSpammer", "Anti Spam Fix")) {
                                        tempLine += " " + GenerateNumber.generateInt(0, 9) + GenerateNumber.generateInt(0, 9);
                                    }

                                    if (Module.getCheckbox("PMSpammer", "LowerCase")) {
                                        tempLine = tempLine.toLowerCase();
                                    }

                                    mc.player.sendChatMessage("/w " + playerName + " " + tempLine);
                                    long a = (long) (Module.getSlider("PMSpammer", "Delay(Second)") * 1000);
                                    if (Module.getCheckbox("PMSpammer", "Delay Spread")) {
                                        if (!random.nextBoolean()) {
                                            a = (long) (a * GenerateNumber.generateFloat(1, 1f + (float) Module.getSlider("PMSpammer", "Delay Spread")));
                                        } else {
                                            a = (long) (a * GenerateNumber.generateFloat((float) Module.getSlider("PMSpammer", "Delay Spread"), 1));
                                        }
                                    }
                                    try {
                                        sleep(a);
                                    } catch (Exception ignored) {
                                    }
                                }
                            }
                        }
                        reader.close();
                        if (!Client.getModuleByName("PMSpammer").isEnabled()) stop();
                        long a = (long) (Module.getSlider("PMSpammer", "Delay(Second)") * 1000);
                        if (Module.getCheckbox("PMSpammer", "Delay Spread")) {
                            if (!random.nextBoolean()) {
                                a = (long) (a * GenerateNumber.generateFloat(1, 1f + (float) Module.getSlider("PMSpammer", "Delay Spread")));
                            } else {
                                a = (long) (a * GenerateNumber.generateFloat((float) Module.getSlider("PMSpammer", "Delay Spread"), 1));
                            }
                        }
                        try {
                            sleep(a);
                        } catch (Exception ignored) {
                        }
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }

        PMSpammer.readTXT.value = 0;
    }


    public String getPlayerName(NetworkPlayerInfo networkPlayerInfoIn) {
        return networkPlayerInfoIn.getDisplayName() != null ? networkPlayerInfoIn.getDisplayName().getFormattedText() : networkPlayerInfoIn.getGameProfile().getName();
    }
}
