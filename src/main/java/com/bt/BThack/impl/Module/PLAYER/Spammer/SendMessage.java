package com.bt.BThack.impl.Module.PLAYER.Spammer;

import com.bt.BThack.System.Client;
import com.bt.BThack.api.Module.Module;
import com.bt.BThack.api.Utils.Interfaces.Mc;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;


public class SendMessage extends Thread implements Mc {

    public static int m = 1;

    public void run() {
        Path path = Paths.get("BThack/Spammer/Spammer.txt");
        String Mode = Module.getMode("Spammer", "Mode");

        Client.getModuleByName("Spammer").arrayListInfo = Mode;

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
                        mc.player.sendChatMessage(line);
                        m = m + 1;
                    } else {
                        m = 1;
                    }
                    reader.close();
                    if (!Client.getModuleByName("Spammer").isEnabled()) stop();
                    new timerThread().start();
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        if (Objects.equals(Mode, "Random")) {
            if (Files.exists(path)) {
                Spammer.readTXT.Read();
                long randomValue = 1 + (int) (Math.random() * (Spammer.readTXT.Value));
                try {
                    BufferedReader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8);
                    String line = reader.readLine();
                    if (randomValue == 1)
                        mc.player.sendChatMessage(line);
                    else {
                        for (int i = 1; i < randomValue; i++) {
                            line = reader.readLine();
                        }
                        if (line != null)
                            mc.player.sendChatMessage(line);
                    }
                    reader.close();
                    if (!Client.getModuleByName("Spammer").isEnabled()) stop();
                    new timerThread().start();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
