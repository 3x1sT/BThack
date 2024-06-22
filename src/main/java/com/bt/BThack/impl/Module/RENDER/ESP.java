package com.bt.BThack.impl.Module.RENDER;

import com.bt.BThack.api.Module.Module;
import com.bt.BThack.api.Utils.Render.RenderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityGolem;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityWaterMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.input.Keyboard;

import java.util.ArrayList;
import java.util.List;

public class ESP extends Module {

    private boolean need = true;

    public static List<Entity> glowed = new ArrayList<>();

    public ESP() {
        super("ESP",
                "Highlights players, mobs and items.",
                Keyboard.KEY_NONE,
                Category.RENDER,
                false
        );

        ArrayList<String> options = new ArrayList<>();

        options.add("Box");
        options.add("Glow");

        addMode("Mode", this, options, "Mode");

        addCheckbox("Players", this, true);
        addCheckbox("Items", this, true);
        addCheckbox("Monsters", this, true);
        addCheckbox("Animals", this, true);
    }

    @SubscribeEvent
    public void onRender(RenderWorldLastEvent e) {
        if (nullCheck()) return;

        switch (getMode(this.name, "Mode")) {
            case "Box":
                if (need) {
                    for (Entity entity : glowed) {
                        entity.setGlowing(false);
                    }
                    glowed.clear();
                    need = false;
                }
                for (EntityPlayer entity : mc.world.playerEntities) {
                    if (getCheckbox(this.name, "Players") && entity != mc.player && entity != null) {
                        RenderUtils.FillLine(getAxisAlignedBB(entity),1,1,1, 0.6F,1,1,1, 0.2F);
                    }
                }
                for (Entity mob : mc.world.loadedEntityList) {
                    if (getCheckbox(this.name, "Items") && mob instanceof EntityItem) {
                        RenderUtils.FillLine(getAxisAlignedBB(mob),0.59F,0.59F, 1,0.6F,0.59F,0.59F,1, 0.2F);
                    }
                    if (getCheckbox(this.name, "Monsters") && (mob instanceof EntityMob || mob instanceof EntityGolem)) {
                        RenderUtils.FillLine(getAxisAlignedBB(mob),0.83F,0.92F,0.17F, 0.6F,0.83F,0.92F,0.17F, 0.2F);
                    }
                    if (getCheckbox(this.name, "Animals") && mob instanceof EntityAgeable) {
                        RenderUtils.FillLine(getAxisAlignedBB(mob),0.69F,1,0.34F, 0.6F,0.69F,1,0.34F, 0.2F);
                    }
                }
                break;
            case "Glow":
                need = true;
                for (EntityPlayer playerEntity : mc.world.playerEntities) {
                    if (playerEntity != mc.player && playerEntity != glowed && !playerEntity.isDead) {
                        playerEntity.setGlowing(true);
                        glowed.add(playerEntity);
                    }
                }
                for (Entity mob : mc.world.loadedEntityList) {
                    if (mob != glowed) {
                        if (mob instanceof EntityItem) {
                            mob.setGlowing(true);
                            glowed.add(mob);
                        }
                        if (mob instanceof EntityMob && !mob.isDead) {
                            mob.setGlowing(true);
                            glowed.add(mob);
                        }
                        if (mob instanceof EntityWaterMob && !mob.isDead) {
                            mob.setGlowing(true);
                            glowed.add(mob);
                        }
                    }
                }
                break;
        }
    }

    @Override
    public void onDisable() {
        if (nullCheck()) return;

        for (Entity entity : glowed) {
            entity.setGlowing(false);
        }
        glowed.clear();
        need = true;

        super.onDisable();
    }

    private static AxisAlignedBB getAxisAlignedBB(Entity mob) {
        return new AxisAlignedBB(
                mob.getEntityBoundingBox().minX
                        - 0.05
                        - mob.posX
                        + ((float) ((double) ((float) mob.lastTickPosX) + (mob.posX - mob.lastTickPosX) * Minecraft.getMinecraft().getRenderPartialTicks()) - Minecraft.getMinecraft()
                        .getRenderManager().viewerPosX),
                mob.getEntityBoundingBox().minY
                        - mob.posY
                        + ((float) ((double) ((float) mob.lastTickPosY) + (mob.posY - mob.lastTickPosY) * Minecraft.getMinecraft().getRenderPartialTicks()) - Minecraft.getMinecraft()
                        .getRenderManager().viewerPosY),
                mob.getEntityBoundingBox().minZ
                        - 0.05
                        - mob.posZ
                        + ((float) ((double) ((float) mob.lastTickPosZ) + (mob.posZ - mob.lastTickPosZ) * Minecraft.getMinecraft().getRenderPartialTicks()) - Minecraft.getMinecraft()
                        .getRenderManager().viewerPosZ),
                mob.getEntityBoundingBox().maxX
                        + 0.05
                        - mob.posX
                        + ((float) ((double) ((float) mob.lastTickPosX) + (mob.posX - mob.lastTickPosX) * Minecraft.getMinecraft().getRenderPartialTicks()) - Minecraft.getMinecraft()
                        .getRenderManager().viewerPosX),
                mob.getEntityBoundingBox().maxY
                        + 0.1
                        - mob.posY
                        + ((float) ((double) ((float) mob.lastTickPosY) + (mob.posY - mob.lastTickPosY) * Minecraft.getMinecraft().getRenderPartialTicks()) - Minecraft.getMinecraft()
                        .getRenderManager().viewerPosY),
                mob.getEntityBoundingBox().maxZ
                        + 0.05
                        - mob.posZ
                        + ((float) ((double) ((float) mob.lastTickPosZ) + (mob.posZ - mob.lastTickPosZ) * Minecraft.getMinecraft().getRenderPartialTicks()) - Minecraft.getMinecraft()
                        .getRenderManager().viewerPosZ));
    }
}
