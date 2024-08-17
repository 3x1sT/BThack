package com.bt.BThack.api.Module;

import com.bt.BThack.BThack;
import com.bt.BThack.System.Client;
import com.bt.BThack.api.Storage.MusicStorage;
import com.bt.BThack.api.Managers.Settings.Setting;
//import com.google.common.base.MoreObjects;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.MinecraftForge;
import org.lwjgl.input.Keyboard;

import java.util.ArrayList;

public class Module {
    public final String name;
    public final String descriptionEN;
    public boolean toggled;
    private final boolean autoEnabled;
    private int keyCode;
    private final Category category;
    public final Minecraft mc = Minecraft.getMinecraft();

    public String arrayListInfo = "";

    public boolean visible = true;
    public boolean allowRemapKeyCode = true;

    public Module(String name, String descriptionEN, int key, Category c, boolean autoEnabled) {
        this.name = name;
        if (descriptionEN.endsWith(".") || descriptionEN.endsWith("!") || descriptionEN.endsWith("?")) {
            this.descriptionEN = descriptionEN;
        } else {
            this.descriptionEN = descriptionEN + ".";
        }
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

    public String getChatName() {
        return "[" + this.name + "]";
    }


    public enum Category {
        COMBAT,
        EXPLOIT,
        CLIENT,
        RENDER,
        MOVEMENT,
        PLAYER,
        WORLD,
        MISC
    }

    public void toggle() {
        toggled = !toggled;
        if (toggled) {
            onEnable();
            if (!nullCheck()) {
                if (Client.getModuleByName("ModuleOnOffSound").isEnabled())
                    MusicStorage.playSound(MusicStorage.moduleOn, 0.5 * getSlider("ModuleOnOffSound", "Volume"));

            }
        } else {
            onDisable();
            if (!nullCheck()) {
                if (Client.getModuleByName("ModuleOnOffSound").isEnabled())
                    MusicStorage.playSound(MusicStorage.moduleOff, 0.5 * getSlider("ModuleOnOffSound", "Volume"));
            }
        }
    }

    public void setToggled(boolean toggled) {
        this.toggled = toggled;
        if (this.toggled) {
            this.onEnable();
            if (!nullCheck()) {
                if (Client.getModuleByName("ModuleOnOffSound").isEnabled())
                    MusicStorage.playSound(MusicStorage.moduleOn, 0.5 * getSlider("ModuleOnOffSound", "Volume"));
            }
        } else {
            this.onDisable();
            if (!nullCheck()) {
                if (Client.getModuleByName("ModuleOnOffSound").isEnabled())
                    MusicStorage.playSound(MusicStorage.moduleOff, 0.5 * getSlider("ModuleOnOffSound", "Volume"));
            }
        }
    }



    //Mode adding
    public static void addMode(String name, Module parent, ArrayList<String> options) {
        BThack.instance.settingsManager.addModuleSetting(new Setting(name, parent, options));
    }
    public static void addMode(String dependenceName, String dependenceSVal, String name, Module parent, ArrayList<String> options) {
        BThack.instance.settingsManager.addModuleSetting(new Setting(dependenceName, dependenceSVal, name, parent, options));
    }
    public static void addMode(String dependenceName, boolean dependenceBVal, String name, Module parent, ArrayList<String> options) {
        BThack.instance.settingsManager.addModuleSetting(new Setting(dependenceName, dependenceBVal, name, parent, options));
    }
    public static void addMode(String dependenceName, double dependenceDMin, double dependenceDMax, String name, Module module, ArrayList<String> options) {
        BThack.instance.settingsManager.addModuleSetting(new Setting(dependenceName, dependenceDMin, dependenceDMax, name, module, options));
    }
    ////////

    //Slider adding
    public static void addSlider(String name, Module parent, double dVal, double min, double max, boolean onlyInt) {
        BThack.instance.settingsManager.addModuleSetting(new Setting(name, parent, dVal, min, max, onlyInt));
    }
    public static void addSlider(String dependenceName, String dependenceSVal, String name, Module module, double dVal, double min, double max, boolean onlyInt) {
        BThack.instance.settingsManager.addModuleSetting(new Setting(dependenceName, dependenceSVal, name, module, dVal, min, max, onlyInt));
    }
    public static void addSlider(String dependenceName, boolean dependenceBVal, String name, Module module, double dVal, double min, double max, boolean onlyInt) {
        BThack.instance.settingsManager.addModuleSetting(new Setting(dependenceName, dependenceBVal, name, module, dVal, min, max, onlyInt));
    }
    public static void addSlider(String dependenceName, double dependenceDMin, double dependenceDMax, String name, Module module, double dVal, double min, double max, boolean onlyInt) {
        BThack.instance.settingsManager.addModuleSetting(new Setting(dependenceName, dependenceDMin, dependenceDMax, name, module, dVal, min, max, onlyInt));
    }
    ////////

    //Checkbox adding
    public static void addCheckbox(String name, Module parent, boolean bVal) {
        BThack.instance.settingsManager.addModuleSetting(new Setting(name, parent, bVal));
    }
    public static void addCheckbox(String dependenceName, String dependenceSVal, String name, Module module, boolean bVal) {
        BThack.instance.settingsManager.addModuleSetting(new Setting(dependenceName, dependenceSVal, name, module, bVal));
    }
    public static void addCheckbox(String dependenceName, boolean dependenceBVal, String name, Module module, boolean bVal) {
        BThack.instance.settingsManager.addModuleSetting(new Setting(dependenceName, dependenceBVal, name, module, bVal));
    }
    public static void addCheckbox(String dependenceName, double dependenceDMin, double dependenceDMax, String name, Module module, boolean bVal) {
        BThack.instance.settingsManager.addModuleSetting(new Setting(dependenceName, dependenceDMin, dependenceDMax, name, module, bVal));
    }
    ////////

    //KeyCode adding
    public static void addKeyCode(String name, Module parent) {
        BThack.instance.settingsManager.addModuleSetting(new Setting(name, parent));
    }
    public static void addKeyCode(String dependenceName, String dependenceSVal, String name, Module module) {
        BThack.instance.settingsManager.addModuleSetting(new Setting(dependenceName, dependenceSVal, name, module));
    }
    public static void addKeyCode(String dependenceName, boolean dependenceBVal, String name, Module module) {
        BThack.instance.settingsManager.addModuleSetting(new Setting(dependenceName, dependenceBVal, name, module));
    }
    public static void addKeyCode(String dependenceName, double dependenceDMin, double dependenceDMax, String name, Module module) {
        BThack.instance.settingsManager.addModuleSetting(new Setting(dependenceName, dependenceDMin, dependenceDMax, name, module));
    }
    ////////

    //OpenGuiButton adding
    public static void addOpenGuiButton(String name, Module module, GuiScreen screen) {
        BThack.instance.settingsManager.addModuleSetting(new Setting(name, module, screen));
    }

    public static void addOpenGuiButton(String dependenceName, String dependenceSVal, String name, Module module, GuiScreen screen) {
        BThack.instance.settingsManager.addModuleSetting(new Setting(dependenceName, dependenceSVal, name, module, screen));
    }

    public static void addOpenGuiButton(String dependenceName, boolean dependenceBVal, String name, Module module, GuiScreen screen) {
        BThack.instance.settingsManager.addModuleSetting(new Setting(dependenceName, dependenceBVal, name, module, screen));
    }

    public static void addOpenGuiButton(String dependenceName, double dependenceDMin, double dependenceDMax, String name, Module module, GuiScreen screen) {
        BThack.instance.settingsManager.addModuleSetting(new Setting(dependenceName, dependenceDMin, dependenceDMax, name, module, screen));
    }

    ////////

    public static String getMode(String mod, String settingName) {
        return BThack.instance.settingsManager.getModuleSettingByName(mod, settingName).getValString();
    }

    public static double getSlider(String mod, String settingName) {
        return BThack.instance.settingsManager.getModuleSettingByName(mod, settingName).getValDouble();
    }

    public static boolean getCheckbox(String mod, String settingName) {
        return BThack.instance.settingsManager.getModuleSettingByName(mod, settingName).getValBoolean();
    }

    public static int getKeyCode(String mod, String settingName) {
        return BThack.instance.settingsManager.getModuleSettingByName(mod, settingName).getKeyCodeVal();
    }

    public static boolean isKeyCodePressed(String mod, String settingName) {
        return Keyboard.isKeyDown(BThack.instance.settingsManager.getModuleSettingByName(mod, settingName).getKeyCodeVal());
    }
}
