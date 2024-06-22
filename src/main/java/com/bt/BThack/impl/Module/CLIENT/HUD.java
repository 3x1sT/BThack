package com.bt.BThack.impl.Module.CLIENT;

import com.bt.BThack.Client;
import com.bt.BThack.api.Module.Module;
import com.bt.BThack.api.Utils.Font.FontUtil;
import com.bt.BThack.api.Utils.Modules.AimBotUtils;
import com.bt.BThack.api.Utils.Render.ColourUtils;
import com.bt.BThack.api.Utils.SpeedMathThread;
import com.mojang.realmsclient.gui.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.potion.PotionEffect;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.input.Keyboard;

import java.awt.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Objects;

public class HUD extends Module {
    public HUD() {
        super("HUD",
                "Enabling and configuring HUD modules.",
                Keyboard.KEY_NONE,
                Category.CLIENT,
                true
        );

        addCheckbox("Watermark", this, true);
        addCheckbox("FPS", this, true);
        addCheckbox("Coordinates", this, true);
        addCheckbox("Rotation", this, true);
        addCheckbox("Direction", this, true);
        addCheckbox("Speed",this,true);
        addCheckbox("ArrayList", this, true);
        addCheckbox("ArrayList rainbow", this, true);
        addSlider("Rainbow type", this, 3, 1, 8, true);
    }

    @SubscribeEvent
    public void onRender(RenderGameOverlayEvent.Post e) {
        if (Objects.requireNonNull(e.getType()) == RenderGameOverlayEvent.ElementType.TEXT) {
            if (!SpeedMathThread.active) {
                new SpeedMathThread().start();
            }

            int type = (int)getSlider(this.name, "Rainbow type");
            int y = 5;
            float ComponentY = 5;
            final int[] counter = {1};
            String pattern = "#0.00";
            Minecraft mc = Minecraft.getMinecraft();
            ScaledResolution sr = new ScaledResolution(mc);

            if (Module.getCheckbox("HUD", "Watermark")) {
                FontUtil.drawText(Client.cName, 5.0F, ComponentY, Client.colourTheme.getArrayListColour());
                ComponentY += 10;
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
                    if (module.toggled) {
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
        }
    }
}
