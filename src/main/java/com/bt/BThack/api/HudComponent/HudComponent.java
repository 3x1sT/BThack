package com.bt.BThack.api.HudComponent;

import com.bt.BThack.BThack;
import com.bt.BThack.api.Module.Module;
import com.bt.BThack.api.Utils.Font.FontUtil;
import com.bt.BThack.api.Utils.Render.ColourUtils;
import com.bt.BThack.api.Managers.Settings.Setting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import org.lwjgl.input.Keyboard;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class HudComponent {
    public final String name;
    public final Minecraft mc = Minecraft.getMinecraft();
    public volatile boolean toggled;

    private float x; //Left edge (min 0; max 100)
    private float y; //Upper edge  (min 0; max 100)

    private float noScaledX;
    private float noScaledY;

    private int scaledWidth;
    private int scaledHeight;

    public volatile float width;  //Right
    public volatile float height; //Down


    public  final Module module;

    public  final String pattern = "#0.00";

    public final DecimalFormat decimal = new DecimalFormat("0.00");

    public HudComponent(String name, float x, float y, boolean autoToggled, Module module) {

        this.name = name;
        this.setX(x, new ScaledResolution(mc).getScaledWidth());
        this.setY(y, new ScaledResolution(mc).getScaledHeight());

        this.module = module;

        Module.addCheckbox(name, module, autoToggled);
    }

    public void setX(float value, int scaledWidth) {
        this.x = (value / (float) scaledWidth) * 100f;

        this.scaledWidth = scaledWidth;
        this.noScaledX = value;
    }

    public void setY(float value, int scaledHeight) {
        this.y = (value / (float) scaledHeight) * 100f;

        this.scaledHeight = scaledHeight;
        this.noScaledY = value;
    }

    public float getX() {
        ScaledResolution sc = new ScaledResolution(mc);
        return (sc.getScaledWidth() / 100f) * x;


        /*
        ScaledResolution sc = new ScaledResolution(mc);
        float factor = (this.x / scaledWidth) * 100;
        return (sc.getScaledWidth() / 100f) * factor;
         */
        //return x;
    }

    public float getY() {
        ScaledResolution sc = new ScaledResolution(mc);
        return (sc.getScaledHeight() / 100f) * y;

        /*
        ScaledResolution sc = new ScaledResolution(mc);
        float factor = (this.y / scaledHeight) * 100;
        return (sc.getScaledHeight() / 100f) * factor;

         */
        //return y;
    }

    public float getNoScaledX() {
        return this.noScaledX;
    }

    public float getNoScaledY() {
        return this.noScaledY;
    }

    public int getScaledWidth() {
        return this.scaledWidth;
    }

    public int getScaledHeight() {
        return this.scaledHeight;
    }

    public boolean isEnabled() {
        return toggled;
    }

    public boolean nullCheck() {
        return mc.player == null || mc.world == null;
    }

    public void render(RenderType type) {}

    public String getName() {
        return this.name;
    }

    public void toggle() {
        toggled = !toggled;
    }

    public void setToggled(boolean toggled) {
        this.toggled = toggled;
    }

    public void drawText(String text, float x, float y) {
        FontUtil.drawText(text, x, y, ColourUtils.rainbowType((int) Module.getSlider("HUD","Rainbow type")));
    }

    public void outOfBoundsCheck() {
        ScaledResolution sc = new ScaledResolution(mc);

        if (this.x > (sc.getScaledWidth() - 5))
            this.x = sc.getScaledWidth() - 5;

        if (this.y > sc.getScaledHeight() - 5)
            this.y = sc.getScaledHeight() - 5;
    }

    //Mode adding
    public void addMode(String name, ArrayList<String> options) {
        BThack.instance.settingsManager.addModuleSetting(new Setting(this.name, true, name, this.module, options));
    }
    ////////

    //Slider adding
    public void addSlider(String name, double dVal, double min, double max, boolean onlyInt) {
        BThack.instance.settingsManager.addModuleSetting(new Setting(this.name, true, name, this.module, dVal, min, max, onlyInt));
    }
    ////////

    //Checkbox adding
    public void addCheckbox(String name, boolean bVal) {
        BThack.instance.settingsManager.addModuleSetting(new Setting(this.name, true, name, this.module, bVal));
    }
    ////////

    //KeyCode adding
    public void addKeyCode(String name) {
        BThack.instance.settingsManager.addModuleSetting(new Setting(this.name, true, name, this.module));
    }
    ////////

    //OpenGuiButton adding
    public void addOpenGuiButton(String name, GuiScreen screen) {
        BThack.instance.settingsManager.addModuleSetting(new Setting(this.name, true, name, this.module, screen));
    }
    ////////

    public String getMode(String settingName) {
        return BThack.instance.settingsManager.getModuleSettingByName(this.module.name, settingName).getValString();
    }

    public double getSlider(String settingName) {
        return BThack.instance.settingsManager.getModuleSettingByName(this.module.name, settingName).getValDouble();
    }

    public boolean getCheckbox(String settingName) {
        return BThack.instance.settingsManager.getModuleSettingByName(this.module.name, settingName).getValBoolean();
    }

    public int getKeyCode(String settingName) {
        return BThack.instance.settingsManager.getModuleSettingByName(this.module.name, settingName).getKeyCodeVal();
    }

    public boolean isKeyCodePressed(String settingName) {
        return Keyboard.isKeyDown(BThack.instance.settingsManager.getModuleSettingByName(this.module.name, settingName).getKeyCodeVal());
    }


    public enum RenderType {
        IMAGE,
        TEXT
    }
}
