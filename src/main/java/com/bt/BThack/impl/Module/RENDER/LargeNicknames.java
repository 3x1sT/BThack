package com.bt.BThack.impl.Module.RENDER;

import com.bt.BThack.api.Module.Module;
import com.bt.BThack.api.Social.Allies.AlliesUtils;
import com.bt.BThack.api.Social.Allies.Ally;
import com.bt.BThack.api.Social.Friends.FriendsUtils;
import com.bt.BThack.api.Utils.Render.RenderUtils;
import com.mojang.realmsclient.gui.ChatFormatting;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;
import net.minecraft.entity.EntityLivingBase;

import java.awt.*;

public class LargeNicknames extends Module {

    private static final int friendsColor = new Color(0.03f, 0.96f, 0.86f, 1).hashCode();

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
        } if (entity.isDead || entity.getHealth() < 0) {
            return;
        }

        GL11.glPushMatrix();
        Vec3d pos = new Vec3d(e.getX(), e.getY() + entity.height / 1.5, e.getZ());
        GL11.glTranslated(pos.x, pos.y + 1, pos.z);

        double scale = Math.max(1,pos.distanceTo(new Vec3d(0,0,0)) / 6);
        GL11.glScaled(scale, scale, scale);
        int health = (int) Math.ceil(entity.getHealth());

        ChatFormatting colour;
        if (health > 15) {
            colour = ChatFormatting.DARK_GREEN;
        } else if (health > 8) {
            colour = ChatFormatting.YELLOW;
        } else {
            colour = ChatFormatting.RED;
        }
        if (AlliesUtils.isAlly(((EntityPlayer) entity).getDisplayNameString())) {
            Ally ally = AlliesUtils.getAlly(((EntityPlayer) entity).getDisplayNameString());
            assert ally != null;
            int color = new Color(ally.getR(), ally.getG(), ally.getB(), 1).hashCode();
            RenderUtils.drawNameplate(mc.fontRenderer, entity.getDisplayName().getFormattedText() + ChatFormatting.WHITE +" HP:" + colour + health + ChatFormatting.RESET + " [" + ChatFormatting.BOLD + ally.getClanName() + ChatFormatting.RESET + "]", 0,0,0,0,
                    mc.getRenderManager().playerViewY,
                    mc.getRenderManager().playerViewX,
                    mc.gameSettings.thirdPersonView == 2,
                    color
            );
            GL11.glPopMatrix();
        } else if (FriendsUtils.isFriend(((EntityPlayer) entity).getDisplayNameString())) {
            RenderUtils.drawNameplate(mc.fontRenderer, entity.getDisplayName().getFormattedText() + ChatFormatting.WHITE + " HP:" + colour + health, 0,0,0,0,
                    mc.getRenderManager().playerViewY,
                    mc.getRenderManager().playerViewX,
                    mc.gameSettings.thirdPersonView == 2,
                    friendsColor
            );
            GL11.glPopMatrix();
        } else {
            RenderUtils.drawNameplate(mc.fontRenderer, entity.getDisplayName().getFormattedText() + " HP:" + colour + health, 0, 0, 0, 0,
                    mc.getRenderManager().playerViewY,
                    mc.getRenderManager().playerViewX,
                    mc.gameSettings.thirdPersonView == 2,
                    -1
            );
            GL11.glPopMatrix();
        }
        e.setCanceled(true);
    }
}
