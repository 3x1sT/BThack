package com.bt.BThack.impl.Module.PLAYER.Spammer;

import com.bt.BTbot.api.Utils.Generate.GenerateNumber;
import com.bt.BThack.api.Managers.Thread.ThreadManager;
import com.bt.BThack.api.Module.Module;
import com.bt.BThack.api.Utils.ChatUtils;
import org.lwjgl.input.Keyboard;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class Spammer extends Module {
    protected static ReadTXT readTXT = new ReadTXT();

    public Spammer() {
        super("Spammer",
                "Automatically writes text to chat.",
                Keyboard.KEY_NONE,
                Category.PLAYER,
                false
        );

        ArrayList<String> options = new ArrayList<>();

        options.add("InOrder");
        options.add("Random");

        addMode("Mode", this, options);
        addSlider("Delay(Second)", this, 60,1,600,true);
    }

    public int m = 1;

    @Override
    public void onEnable() {
        if (nullCheck()) {
            toggle();
            return;
        }

        ThreadManager.startNewThread(thread -> {
            Path path = Paths.get("BThack/Spammer/Spammer.txt");

            while (this.isEnabled()) {
                try {
                    this.arrayListInfo = getMode(this.name, "Mode");

                    if (Files.exists(path)) {
                        BufferedReader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8);
                        String line = reader.readLine();

                        switch (getMode(this.name, "Mode")) {
                            case "InOrder":
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
                                break;
                            case "Random":
                                Spammer.readTXT.read();
                                int randomValue = GenerateNumber.generateInt(1, Spammer.readTXT.value);
                                if (randomValue == 1)
                                    mc.player.sendChatMessage(line);
                                else {
                                    for (int i = 1; i < randomValue; i++) {
                                        line = reader.readLine();
                                    }
                                    if (line != null)
                                        mc.player.sendChatMessage(line);
                                }
                                break;
                        }
                        reader.close();
                    }

                    try {
                        thread.sleep((long) (getSlider(this.name, "Delay(Second)") * 1000));
                    } catch (InterruptedException ignored) {}
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }
}
