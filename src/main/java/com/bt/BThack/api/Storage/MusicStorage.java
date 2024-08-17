package com.bt.BThack.api.Storage;

import com.bt.BThack.api.SoundSystem.Music;
import com.bt.BThack.api.SoundSystem.Sound;
import com.bt.BThack.api.SoundSystem.TinySound;

public class MusicStorage {
    public static Sound buttonClicked;
    public static Sound moduleOn;
    public static Sound moduleOff;







    public static void init() {
        buttonClicked = TinySound.loadSound("assets/bthack/Sounds/buttonClicked.wav");
        moduleOn = TinySound.loadSound("assets/bthack/Sounds/ModuleOn.wav");
        moduleOff = TinySound.loadSound("assets/bthack/Sounds/ModuleOff.wav");
    }

    public static void playSound(Sound sound, double volume) {
        if (!TinySound.isInitialized()) return;

        sound.play(volume);
    }

    public static void playMusic(Music music, double volume, boolean loop) {
        if (!TinySound.isInitialized()) return;

        music.play(loop, volume);
    }
}
