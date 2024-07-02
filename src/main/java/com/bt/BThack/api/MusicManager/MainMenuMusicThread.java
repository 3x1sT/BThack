package com.bt.BThack.api.MusicManager;

import com.bt.BTbot.api.Utils.Generate.GenerateIntNumber;
import com.bt.BThack.api.SoundSystem.Music;
import com.bt.BThack.api.SoundSystem.TinySound;
import com.bt.BThack.api.Utils.Interfaces.Mc;

import java.nio.file.Paths;

public class MainMenuMusicThread extends Thread implements Mc {
    public static boolean started = false;

    private Music mainMenuMusic1;
    private Music mainMenuMusic2;
    private Music mainMenuMusic3;
    private Music mainMenuMusic4;

    public void init() {
        mainMenuMusic1 = TinySound.loadMusic(Paths.get("BThack_Music/CosmicMusic1.wav").toFile());
        mainMenuMusic2 = TinySound.loadMusic(Paths.get("BThack_Music/CosmicMusic2.wav").toFile());
        mainMenuMusic3 = TinySound.loadMusic(Paths.get("BThack_Music/CosmicMusic3.wav").toFile());
        mainMenuMusic4 = TinySound.loadMusic(Paths.get("BThack_Music/CosmicMusic4.wav").toFile());
    }

    @Override
    public void run() {
        started = true;
        byte oldMusic = 0;
        byte currentMusic;
        while (true) {
            if (mc.player != null) {
                try {
                    sleep(5000);
                } catch (InterruptedException ignored) {}
            } else {
                do {
                    currentMusic = (byte) GenerateIntNumber.generate(1, 4);
                } while (currentMusic == oldMusic);

                if (currentMusic <= 1) {
                    mainMenuMusic1.play(false);
                    oldMusic = currentMusic;

                    while (mainMenuMusic1.playing()) {
                        if (mc.player != null) {
                            mainMenuMusic1.stop();
                        } else {
                            try {
                                sleep(200);
                            } catch (InterruptedException ignored) {}
                        }
                    }
                } else if (currentMusic == 2) {
                    mainMenuMusic2.play(false);
                    oldMusic = currentMusic;

                    while (mainMenuMusic2.playing()) {
                        if (mc.player != null) {
                            mainMenuMusic2.stop();
                        } else {
                            try {
                                sleep(200);
                            } catch (InterruptedException ignored) {}
                        }
                    }
                } else if (currentMusic == 3) {
                    mainMenuMusic3.play(false);
                    oldMusic = currentMusic;

                    while (mainMenuMusic3.playing()) {
                        if (mc.player != null) {
                            mainMenuMusic3.stop();
                        } else {
                            try {
                                sleep(200);
                            } catch (InterruptedException ignored) {}
                        }
                    }
                } else {
                    mainMenuMusic4.play(false);
                    oldMusic = currentMusic;

                    while (mainMenuMusic4.playing()) {
                        if (mc.player != null) {
                            mainMenuMusic4.stop();
                        } else {
                            try {
                                sleep(200);
                            } catch (InterruptedException ignored) {}
                        }
                    }
                }
                try {
                    sleep(5000);
                } catch (InterruptedException ignored) {}
            }
        }
    }
}
