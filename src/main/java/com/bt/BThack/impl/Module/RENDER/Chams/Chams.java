package com.bt.BThack.impl.Module.RENDER.Chams;

import com.bt.BThack.api.Module.Module;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Keyboard;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

public class Chams extends Module {
    public static ChamsRender chamsRender = new ChamsRender();

    public Chams() {
        super("Chams",
                "Highlights entities",
                Keyboard.KEY_NONE,
                Category.RENDER,
                false
        );

        addMode("Mode", this, new ArrayList<>(Arrays.asList("HighLight", "Vanilla")));

        addCheckbox("Left Arm", this, true);
        addSlider("Left Arm", true, "Left Arm Red", this, 106, 0, 255, true);
        addSlider("Left Arm", true, "Left Arm Green", this, 22, 0, 255, true);
        addSlider("Left Arm", true, "Left Arm Blue", this, 219, 0, 255, true);
        addSlider("Left Arm", true, "Left Arm Alpha", this, 100, 0, 255, true);

        addCheckbox("Right Arm", this, true);
        addSlider("Right Arm", true, "Right Arm Red", this, 106, 0, 255, true);
        addSlider("Right Arm", true, "Right Arm Green", this, 22, 0, 255, true);
        addSlider("Right Arm", true, "Right Arm Blue", this, 219, 0, 255, true);
        addSlider("Right Arm", true, "Right Arm Alpha", this, 100, 0, 255, true);

        addCheckbox("Crystals", this, true);
        addSlider("Crystals", true, "Crystals Red", this, 106, 0, 255, true);
        addSlider("Crystals", true, "Crystals Green", this, 22, 0, 255, true);
        addSlider("Crystals", true, "Crystals Blue", this, 219, 0, 255, true);
        addSlider("Crystals", true, "Crystals Alpha", this, 100, 0, 255, true);

        addCheckbox("Players", this, true);
        addSlider("Players", true, "Players Red", this, 106, 0, 255, true);
        addSlider("Players", true, "Players Green", this, 22, 0, 255, true);
        addSlider("Players", true, "Players Blue", this, 219, 0, 255, true);
        addSlider("Players", true, "Players Alpha", this, 100, 0, 255, true);

        addCheckbox("Animals", this, true);
        addSlider("Animals", true, "Animals Red", this, 75, 0, 255, true);
        addSlider("Animals", true, "Animals Green", this, 219, 0, 255, true);
        addSlider("Animals", true, "Animals Blue", this, 22, 0, 255, true);
        addSlider("Animals", true, "Animals Alpha", this, 100, 0, 255, true);

        addCheckbox("Mobs", this, true);
        addSlider("Mobs", true, "Mobs Red", this, 236, 0, 255, true);
        addSlider("Mobs", true, "Mobs Green", this, 13, 0, 255, true);
        addSlider("Mobs", true, "Mobs Blue", this, 29, 0, 255, true);
        addSlider("Mobs", true, "Mobs Alpha", this, 100, 0, 255, true);
    }

    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent e) {
        if (nullCheck()) return;

        arrayListInfo = getMode(this.name, "Mode");
    }


    public static Color getChamsColour(Entity entity) {
        if (entity instanceof EntityEnderCrystal) {
            return new Color((int) getSlider("Chams", "Crystals Red"), (int) getSlider("Chams", "Crystals Green"), (int) getSlider("Chams", "Crystals Blue"), (int) getSlider("Chams", "Crystals Alpha"));
        }

        if (entity instanceof EntityPlayer) {
            return new Color((int) getSlider("Chams", "Players Red"), (int) getSlider("Chams", "Players Green"), (int) getSlider("Chams", "Players Blue"), (int) getSlider("Chams", "Players Alpha"));
        }

        if (entity instanceof EntityAnimal) {
            return new Color((int) getSlider("Chams", "Animals Red"), (int) getSlider("Chams", "Animals Green"), (int) getSlider("Chams", "Animals Blue"), (int) getSlider("Chams", "Animals Alpha"));
        }

        if (entity instanceof EntityMob) {
            return new Color((int) getSlider("Chams", "Mobs Red"), (int) getSlider("Chams", "Mobs Green"), (int) getSlider("Chams", "Mobs Blue"), (int) getSlider("Chams", "Mobs Alpha"));
        }

        return new Color(255, 255, 255, 255);
    }

    public static boolean entityCheck(Entity entity) {
        return entity instanceof EntityEnderCrystal && getCheckbox("Chams", "Crystals") || entity instanceof EntityPlayer && getCheckbox("Chams", "Players") || entity instanceof EntityAnimal && getCheckbox("Chams", "Animals") || entity instanceof EntityMob && getCheckbox("Chams", "Mobs");
    }
}
