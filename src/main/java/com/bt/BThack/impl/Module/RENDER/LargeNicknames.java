package com.bt.BThack.impl.Module.RENDER;

import com.bt.BThack.api.Module.Module;
import com.bt.BThack.api.Social.Clans.Clan;
import com.bt.BThack.api.Social.Clans.ClansUtils;
import com.bt.BThack.api.Social.Enemies.EnemiesUtils;
import com.bt.BThack.api.Social.Friends.FriendsUtils;
import com.bt.BThack.api.Utils.Render.RenderUtils;
import com.mojang.realmsclient.gui.ChatFormatting;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;
import net.minecraft.entity.EntityLivingBase;

import java.awt.*;
import java.util.ArrayList;

public class LargeNicknames extends Module {
    private final ArrayList<RenderLivingEvent.Specials.Pre> events = new ArrayList<>();
    private final ArrayList<RenderLivingEvent.Specials.Pre> temp = new ArrayList<>();

    private static final int friendsColor = new Color(0.03f, 0.96f, 0.86f, 1).hashCode();
    private static final int enemiesColor = new Color(255, 36, 36).hashCode();

    public LargeNicknames() {
        super("LargeNicknames",
                "Makes player nicknames bigger.",
                Keyboard.KEY_NONE,
                Category.RENDER,
                false
        );
    }

    @SubscribeEvent
    public void onRender(RenderLivingEvent.Specials.Pre e) {
        if (nullCheck()) return;

        EntityLivingBase entity = e.getEntity();

        if (!(entity instanceof EntityPlayer) || entity == mc.player) {
            return;
        }

        events.add(e);

        e.setCanceled(true);
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void onRender(RenderWorldLastEvent event) {
        if (nullCheck()) return;
        for (RenderLivingEvent.Specials.Pre e : events) {
            if (!(e.getEntity() instanceof EntityPlayer) || e.getEntity() == mc.player) {
                continue;
            }
            EntityPlayer player = (EntityPlayer) e.getEntity();
            if (!mc.world.playerEntities.contains(player)) {
                temp.add(e);
                continue;
            }

            GL11.glPushMatrix();
            Vec3d pos = new Vec3d(e.getX(), e.getY() + player.height / 1.5, e.getZ());
            GL11.glTranslated(pos.x, pos.y + 1, pos.z);

            double scale = Math.max(0.6,pos.distanceTo(new Vec3d(0,0,0)) / 8);
            GL11.glScaled(scale, scale, scale);
            int health = (int) Math.ceil(player.getHealth());

            ChatFormatting colour;
            if (health > 15) {
                colour = ChatFormatting.DARK_GREEN;
            } else if (health > 8) {
                colour = ChatFormatting.YELLOW;
            } else {
                colour = ChatFormatting.RED;
            }
            if (ClansUtils.isAlly((player.getDisplayNameString()))) {
                Clan clan = ClansUtils.getFirstClanFromMember(player.getDisplayNameString());

                if (clan != null) {
                    int color = new Color(clan.getR(), clan.getG(), clan.getB(), 1).hashCode();
                    RenderUtils.drawNameplate(mc.fontRenderer, player.getDisplayName().getFormattedText() + ChatFormatting.WHITE +" HP:" + colour + health + ChatFormatting.RESET + " [" + ChatFormatting.BOLD + clan.getName() + ChatFormatting.RESET + "]", 0,0,0,0,
                            mc.getRenderManager().playerViewY,
                            mc.getRenderManager().playerViewX,
                            mc.gameSettings.thirdPersonView == 2,
                            color
                    );
                    GL11.glPopMatrix();
                    temp.add(e);
                } else {
                    RenderUtils.drawNameplate(mc.fontRenderer, player.getDisplayName().getFormattedText() + " HP:" + colour + health, 0, 0, 0, 0,
                            mc.getRenderManager().playerViewY,
                            mc.getRenderManager().playerViewX,
                            mc.gameSettings.thirdPersonView == 2,
                            -1
                    );
                    GL11.glPopMatrix();
                    temp.add(e);
                }
            } else if (FriendsUtils.isFriend((player.getDisplayNameString()))) {
                RenderUtils.drawNameplate(mc.fontRenderer, player.getDisplayName().getFormattedText() + ChatFormatting.WHITE + " HP:" + colour + health, 0,0,0,0,
                        mc.getRenderManager().playerViewY,
                        mc.getRenderManager().playerViewX,
                        mc.gameSettings.thirdPersonView == 2,
                        friendsColor
                );
                GL11.glPopMatrix();
                temp.add(e);
            } else if (EnemiesUtils.isEnemy(player)) {
                RenderUtils.drawNameplate(mc.fontRenderer, player.getDisplayName().getFormattedText() + ChatFormatting.WHITE + " HP:" + colour + health, 0,0,0,0,
                        mc.getRenderManager().playerViewY,
                        mc.getRenderManager().playerViewX,
                        mc.gameSettings.thirdPersonView == 2,
                        enemiesColor
                );
                GL11.glPopMatrix();
                temp.add(e);
            } else {
                RenderUtils.drawNameplate(mc.fontRenderer, player.getDisplayName().getFormattedText() + " HP:" + colour + health, 0, 0, 0, 0,
                        mc.getRenderManager().playerViewY,
                        mc.getRenderManager().playerViewX,
                        mc.gameSettings.thirdPersonView == 2,
                        -1
                );
                GL11.glPopMatrix();
                temp.add(e);
            }
        }
        for (RenderLivingEvent.Specials.Pre e : temp) {
            events.remove(e);
        }
    }
}
