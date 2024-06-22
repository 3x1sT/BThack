package com.bt.BThack.api.Module;

import com.bt.BThack.BThack;
import com.bt.BThack.impl.CustomGui.Gui.Client.GuiUtils.Settings.Setting;
//import com.google.common.base.MoreObjects;
import net.minecraft.client.Minecraft;
import net.minecraftforge.common.MinecraftForge;

import java.util.ArrayList;

public class Module{
    public String name;
    public String descriptionEN;
    public boolean toggled;
    public boolean autoEnabled;
    public int keyCode;
    public Category category;
    public Minecraft mc = Minecraft.getMinecraft();
    //protected MoreObjects.ToStringHelper listeners;

    public Module(String name, String descriptionEN, int key, Category c, boolean autoEnabled) {
        this.name = name;
        this.descriptionEN = descriptionEN;
        this.keyCode = key;
        this.category = c;
        this.autoEnabled = autoEnabled;
    }

    public boolean isEnabled() {
        return toggled;
    }

    public boolean isAutoEnabled() {
        return autoEnabled;
    }

    public int getKey() {
        return keyCode;
    }

    public boolean nullCheck() {
        return mc.player == null || mc.world == null;
    }

    public void onEnable() {
        MinecraftForge.EVENT_BUS.register(this);
    }

    public void onDisable() {
        MinecraftForge.EVENT_BUS.unregister(this);
    }


    public void setKey(int key) {
        this.keyCode = key;
    }

    public Category getCategory() {
        return category;
    }

    public String getName() {
        return this.name;
    }
    public String getDescription() {
        return this.descriptionEN;
    }


    public enum Category {
        COMBAT,
        EXPLOIT,
        CLIENT,
        RENDER,
        MOVEMENT,
        PLAYER,
        WORLD,
        OTHER,
    }

    public void toggle() {
        toggled = !toggled;
        if (toggled) {
            onEnable();
        } else {
            onDisable();
        }
    }

    public void setToggled(boolean toggled) {
        this.toggled = toggled;
        if (this.toggled) {
            this.onEnable();
        } else {
            this.onDisable();
        }
    }



    public static void addMode(String name, Module parent, ArrayList<String> options, String title) {
        BThack.instance.settingsManager.addModuleSetting(new Setting(name, parent, options, title));
    }


    public static void addSlider(String name, Module parent, double dVal, double min, double max, boolean onlyInt) {
        BThack.instance.settingsManager.addModuleSetting(new Setting(name, parent, dVal, min, max, onlyInt));
    }


    public static void addCheckbox(String name, Module parent, boolean bVal) {
        BThack.instance.settingsManager.addModuleSetting(new Setting(name, parent, bVal));
    }

    public static String getMode(String mod, String settingName) {
        return BThack.instance.settingsManager.getModuleSettingByName(mod, settingName).getValString();
    }

    public static double getSlider(String mod, String settingName) {
        return BThack.instance.settingsManager.getModuleSettingByName(mod, settingName).getValDouble();
    }

    public static boolean getCheckbox(String mod, String settingName) {
        return BThack.instance.settingsManager.getModuleSettingByName(mod, settingName).getValBoolean();
    }
}
