package com.bt.BThack.impl.Module.CLIENT;

import com.bt.BThack.System.Client;
import com.bt.BThack.api.Module.Module;
import com.bt.BThack.api.Utils.Font.FontUtil;
import com.bt.BThack.api.Utils.Modules.AimBotUtils;
import com.bt.BThack.api.Utils.Render.ColourUtils;
import com.bt.BThack.api.Utils.Render.RenderUtils;
import com.bt.BThack.api.Utils.SpeedMathThread;
import com.bt.BThack.impl.CustomGui.Menu.BThackMenu;
import com.mojang.realmsclient.gui.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.potion.PotionEffect;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Objects;

import static org.lwjgl.opengl.GL11.*;

public class HUD extends Module {
    public HUD() {
        super("HUD",
                "Enabling and configuring HUD modules.",
                Keyboard.KEY_NONE,
                Category.CLIENT,
                true
        );

        allowRemapKeyCode = false;

        ArrayList<String> options = new ArrayList<>();
        options.add("Logo");
        options.add("Text");

        addCheckbox("Watermark", this, true);
        addMode("Watermark", true,"Logo Type", this, options);
        addCheckbox("FPS", this, true);
        addCheckbox("Coordinates", this, true);
        addCheckbox("Rotation", this, true);
        addCheckbox("Direction", this, true);
        addCheckbox("Speed",this,true);
        addCheckbox("ArrayList", this, true);
        addCheckbox("ArrayList", true,"ArrayList rainbow", this, true);
        addSlider("ArrayList rainbow", true,"Rainbow type", this, 3, 1, 8, true);
    }

    boolean isRendered = false;

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void onRender(RenderGameOverlayEvent.Post e) {
        if (Objects.requireNonNull(e.getType()) == RenderGameOverlayEvent.ElementType.TEXT) {
            if (!SpeedMathThread.active) {
                new SpeedMathThread().start();
            }

            int type = (int)getSlider(this.name, "Rainbow type");
            int y = 5;
            int ComponentY = 5;
            final int[] counter = {1};
            String pattern = "#0.00";
            Minecraft mc = Minecraft.getMinecraft();
            ScaledResolution sr = new ScaledResolution(mc);

            if (Module.getCheckbox("HUD", "Watermark")) {
                if (Module.getMode(this.name, "Logo Type").equals("Text")) {
                    FontUtil.drawText(Client.cName, 5.0F, ComponentY, Client.colourTheme.getArrayListColour());
                    ComponentY += 10;
                } else {
                    FontUtil.drawText("", 5.0F, ComponentY, Client.colourTheme.getArrayListColour());
                    ComponentY += 42;
                }
            }

            if (Module.getCheckbox("HUD", "FPS")) {
                FontUtil.drawText("FPS: " + ChatFormatting.WHITE + Minecraft.getDebugFPS(), 5.0F, ComponentY, Client.colourTheme.getArrayListColour());
                ComponentY += 10;
            }

            if (Module.getCheckbox("HUD", "Coordinates")) {
                String overWorldX;
                String overWorldZ;
                String netherX;
                String netherZ;
                if (mc.player.world.provider.isNether()) {
                    overWorldX = new DecimalFormat(pattern).format(mc.player.posX * 8);
                    overWorldZ = new DecimalFormat(pattern).format(mc.player.posZ * 8.0D);
                    netherX = new DecimalFormat(pattern).format(mc.player.posX);
                    netherZ = new DecimalFormat(pattern).format(mc.player.posZ);
                } else {
                    overWorldX = new DecimalFormat(pattern).format(mc.player.posX);
                    overWorldZ = new DecimalFormat(pattern).format(mc.player.posZ);
                    netherX = new DecimalFormat(pattern).format(mc.player.posX / 8.0D);
                    netherZ = new DecimalFormat(pattern).format(mc.player.posZ / 8.0D);
                }

                FontUtil.drawText("XYZ: " + ChatFormatting.WHITE + overWorldX + " " + Math.round(mc.player.posY) + " " + overWorldZ, 5.0F, ComponentY, Client.colourTheme.getArrayListColour());
                ComponentY += 8.5f;
                FontUtil.drawText("Nether: " + ChatFormatting.WHITE + netherX + " " + Math.round(mc.player.posY) + " " + netherZ, 5.0F, ComponentY, Client.colourTheme.getArrayListColour());
                ComponentY += 10;
            }

            if (Module.getCheckbox("HUD", "Rotation")) {
                String yaw;
                String pitch;
                double preYaw;

                preYaw = mc.player.rotationYaw / 360;
                if (preYaw < 0) {
                    preYaw = -preYaw;
                    preYaw = (-(preYaw - ((int) preYaw))) * 360;
                } else {
                    preYaw = (preYaw - ((int) preYaw)) * 360;
                }

                yaw = new DecimalFormat(pattern).format(preYaw);

                pitch = new DecimalFormat(pattern).format(mc.player.rotationPitch);

                FontUtil.drawText(
                        "Yaw: " + ChatFormatting.WHITE + yaw + ChatFormatting.RESET + " " +
                                "Pitch: " + ChatFormatting.WHITE + pitch,
                        5.0F, ComponentY, Client.colourTheme.getArrayListColour());
                ComponentY += 10;
            }

            if (Module.getCheckbox("HUD", "Direction")) {
                String direction = AimBotUtils.getDirection(mc.player);

                FontUtil.drawText("Direction: " + ChatFormatting.WHITE + direction,
                        5.0F, ComponentY, Client.colourTheme.getArrayListColour());
                ComponentY += 10;
            }

            if (Module.getCheckbox("HUD", "Speed")) {

                String speed = new DecimalFormat(pattern).format(SpeedMathThread.speed);

                FontUtil.drawText("Speed: " + ChatFormatting.WHITE + speed + "b/s",
                        5.0f, ComponentY, Client.colourTheme.getArrayListColour());
                ComponentY += 10;
            }


            ArrayList<Module> enabledModules = new ArrayList<>();

            for (Module module : Client.modules) {
                if (module.toggled) {
                    enabledModules.add(module);
                }
            }

            enabledModules.sort((module1, module2) -> (int)FontUtil.getStringWidth(module2.getName()) - (int)FontUtil.getStringWidth(module1.getName()));

            if (!Client.getModuleByName("NoOverlay").isEnabled()) {
                for (PotionEffect activePotionEffect : mc.player.getActivePotionEffects()) {
                    if (activePotionEffect.getPotion().isBeneficial()) {
                        y = 36;
                    }
                    if (activePotionEffect.getPotion().isBadEffect()) {
                        y = 36 * 2;
                    }
                }
            }

            if (Module.getCheckbox("HUD", "ArrayList")) {
                for (Module module : enabledModules) {
                    if (module.toggled && module.visible) {
                        if (Module.getCheckbox("HUD", "ArrayList rainbow")) {
                            Gui.drawRect(sr.getScaledWidth(), y, sr.getScaledWidth() - 2, y + 10, ColourUtils.rainbowType(type, counter[0]));
                            FontUtil.drawText(module.name, (sr.getScaledWidth() - 4 - FontUtil.getStringWidth(module.name)), (float) y, ColourUtils.rainbowType(type, counter[0]));
                        } else {
                            Gui.drawRect(sr.getScaledWidth(), y, sr.getScaledWidth() - 2, y + 10, (new Color(Client.colourTheme.getArrayListColour())).hashCode());
                            FontUtil.drawText(module.name, (sr.getScaledWidth() - 4 - FontUtil.getStringWidth(module.name)), (float) y, (new Color(Client.colourTheme.getArrayListColour())).hashCode());
                        }

                        y += 10;
                        counter[0]++;
                    }
                }
            }

            isRendered = true;
        } else if (Objects.requireNonNull(e.getType()) == RenderGameOverlayEvent.ElementType.HOTBAR) {
            if (Module.getCheckbox(this.name, "Watermark")) {
                if (Module.getMode(this.name, "Logo Type").equals("Logo") && isRendered) {
                    GlStateManager.pushMatrix();
                    mc.renderEngine.bindTexture(BThackMenu.BThackLogo);
                    glEnable(GL_BLEND);
                    glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
                    RenderUtils.drawScaledCustomSizeModalRect(5, -13, 0, 0, 138, 72, 138, 72, 138, 72);
                    glDisable(GL_BLEND);
                    GL11.glBindTexture(GL_TEXTURE_2D, 0);
                    GlStateManager.popMatrix();
                    isRendered = false;
                }
            }
        }
    }
}
