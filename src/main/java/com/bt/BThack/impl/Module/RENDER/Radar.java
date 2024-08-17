package com.bt.BThack.impl.Module.RENDER;

import com.bt.BThack.api.Module.Module;
import com.bt.BThack.api.Social.Clans.Clan;
import com.bt.BThack.api.Social.Clans.ClansUtils;
import com.bt.BThack.api.Social.Enemies.EnemiesUtils;
import com.bt.BThack.api.Social.Friends.FriendsUtils;
import com.bt.BThack.api.Utils.FontUtils;
import com.bt.BThack.api.Utils.Render.RenderUtils;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.monster.EntityGolem;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityWaterMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.input.Keyboard;

import java.awt.*;
import java.util.Objects;

public class Radar extends Module {

    private static final Color mobColor = new Color(255, 255, 0);
    private static final Color animalColor = new Color(0, 255, 0);
    private static final Color friendColor = new Color(0, 225, 255);
    private static final Color enemyColor = new Color(255, 0, 0);

    public Radar() {
        super("Radar",
                "A radar that displays entities that are near you.",
                Keyboard.KEY_NONE,
                Category.RENDER,
                false
        );

        addSlider("Opacity", this, 0.5, 0.05, 1, false);
        addSlider("Scale", this, 100, 100, 150, true);
        addSlider("Range", this, 100, 50, 200, true);
        addCheckbox("Outline Rect", this, false);

        addCheckbox("Players", this, true);
        addCheckbox("Mobs", this, true);
        addCheckbox("Animals", this, true);
    }

    @SubscribeEvent
    public void onRender(RenderGameOverlayEvent.Post e) {
        if (Objects.requireNonNull(e.getType()) == RenderGameOverlayEvent.ElementType.TEXT) {
            ScaledResolution sr = new ScaledResolution(mc);
            Color rectColor = new Color(0, 0, 0, (float)getSlider(this.name, "Opacity"));
            float yaw = (mc.player.rotationYaw / 360);
            yaw = yaw - (float)Math.floor(yaw);
            yaw = yaw * 360;

            float range = (float) getSlider(this.name, "Range");
            float scale = (float) getSlider(this.name, "Scale");

            Gui.drawRect(sr.getScaledWidth(), sr.getScaledHeight(), sr.getScaledWidth() - (int) scale, sr.getScaledHeight() - (int) scale, rectColor.hashCode());
            RenderUtils.drawTriangle(sr.getScaledWidth() - (scale / 2), (int)((sr.getScaledHeight() - (scale / 2)) + 3), 4, yaw, -1);
            FontUtils.drawCenteredString(mc.fontRenderer, "X-", sr.getScaledWidth() - 6, sr.getScaledHeight() - ((int) scale / 2), -1);
            mc.fontRenderer.drawString("X+", (int)(sr.getScaledWidth() - scale) + 2, sr.getScaledHeight() - ((int) scale / 2), -1);
            FontUtils.drawCenteredString(mc.fontRenderer, "Z+", sr.getScaledWidth() - (int)(scale / 2), (sr.getScaledHeight() - (int) scale) + 1, -1);
            FontUtils.drawCenteredString(mc.fontRenderer, "Z-", sr.getScaledWidth() - (int)(scale / 2), sr.getScaledHeight() - 9, -1);

            if (getCheckbox(this.name, "Outline Rect")) {
                RenderUtils.drawOutlineRect(sr.getScaledWidth(), sr.getScaledHeight(), sr.getScaledWidth() - (int) scale, sr.getScaledHeight() - (int) scale, 1, -1);
            }

            for (EntityPlayer player : mc.world.playerEntities) {
                if (player != mc.player) {
                    double posX = player.posX;
                    double posZ = player.posZ;
                    double radarPosX = mc.player.posX - posX;
                    double radarPosZ = mc.player.posZ - posZ;

                    if (radarPosX < (int) range && radarPosZ < (int) range) {

                        radarPosX = ((sr.getScaledWidth() - (sr.getScaledWidth() - scale)) / 100) * ((radarPosX / range) * 100);
                        radarPosZ = ((sr.getScaledHeight() - (sr.getScaledHeight() - scale)) / 100) * ((radarPosZ / range) * 100);

                        int x = sr.getScaledWidth() - ((int) scale / 2);
                        int y = sr.getScaledHeight() - ((int) scale / 2);

                        x = x + (int) radarPosX;
                        y = y + (int) radarPosZ;

                        if (isCurrentCords(x,y, (sr.getScaledWidth() - (int) scale) + 1, sr.getScaledWidth() - 1, (sr.getScaledHeight() - (int) scale) + 1, sr.getScaledHeight() - 1)) {
                            if (FriendsUtils.isFriend(player)) {
                                RenderUtils.drawSquare(x, y, 1, friendColor.hashCode());
                            } else if (EnemiesUtils.isEnemy(player)) {
                                RenderUtils.drawSquare(x, y, 1, enemyColor.hashCode());
                            } else if (ClansUtils.isAlly(player)) {
                                Clan clan = ClansUtils.getFirstClanFromMember(player.getDisplayNameString());

                                if (clan != null) {
                                    RenderUtils.drawSquare(x,y,3, -1);
                                    Color color = new Color(clan.getR(), clan.getG(), clan.getB());
                                    RenderUtils.drawSquare(x,y,2, color.hashCode());
                                } else {
                                    RenderUtils.drawSquare(x, y, 1, -1);
                                }
                            } else if (getCheckbox(this.name, "Players")) {
                                RenderUtils.drawSquare(x, y, 1, -1);
                            }
                        }
                    }
                }
            }
            for (Entity entity : mc.world.loadedEntityList) {
                double posX = entity.posX;
                double posZ = entity.posZ;
                double radarPosX = mc.player.posX - posX;
                double radarPosZ = mc.player.posZ - posZ;

                if (radarPosX < (int) range && radarPosZ < (int) range) {
                    radarPosX = ((sr.getScaledWidth() - (sr.getScaledWidth() - scale)) / 100) * ((radarPosX / range) * 100);
                    radarPosZ = ((sr.getScaledHeight() - (sr.getScaledHeight() - scale)) / 100) * ((radarPosZ / range) * 100);

                    int x = sr.getScaledWidth() - ((int) scale / 2);
                    int y = sr.getScaledHeight() - ((int) scale / 2);

                    x = x + (int) radarPosX;
                    y = y + (int) radarPosZ;

                    if (isCurrentCords(x,y, (sr.getScaledWidth() - (int) scale) + 1, sr.getScaledWidth() - 1, (sr.getScaledHeight() - (int) scale) + 1, sr.getScaledHeight() - 1)) {
                        if ((entity instanceof EntityMob || entity instanceof EntityGolem) && getCheckbox(this.name, "Mobs")) {
                            RenderUtils.drawSquare(x, y, 1, mobColor.hashCode());
                        } else if ((entity instanceof EntityWaterMob || entity instanceof EntityAgeable) && getCheckbox(this.name, "Animals")) {
                            RenderUtils.drawSquare(x, y, 1, animalColor.hashCode());
                        }
                    }
                }
            }
        }
    }

    private boolean isCurrentCords(int checkX, int checkY, int needMinX, int needMaxX, int needMinY, int needMaxY) {
        return checkX > needMinX && checkX < needMaxX && checkY > needMinY && checkY < needMaxY;
    }
}
