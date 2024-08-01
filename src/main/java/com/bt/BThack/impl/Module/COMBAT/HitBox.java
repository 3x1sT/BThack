package com.bt.BThack.impl.Module.COMBAT;

import com.bt.BThack.api.Module.Module;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.input.Keyboard;

public class HitBox extends Module {
    public HitBox() {
        super("HitBox",
                "Increases/decreases player hitboxes.",
                Keyboard.KEY_NONE,
                Category.COMBAT,
                false
        );
        addSlider("Size", this, 0.3,0.1,1,false);
    }

    @SubscribeEvent
    public void onUpdate(RenderWorldLastEvent e) {
        if (nullCheck()) return;

        arrayListInfo = "" + getSlider(this.name, "Size");

        double size = getSlider(this.name, "Size");

        for (EntityPlayer player : mc.world.playerEntities) {
            if (player != null && player != mc.player) {
                player.setEntityBoundingBox(new AxisAlignedBB(
                        player.posX - size,
                        player.getEntityBoundingBox().minY,
                        player.posZ - size,
                        player.posX + size,
                        player.getEntityBoundingBox().maxY,
                        player.posZ + size
                ));
            }
        }
    }

    @Override
    public void onDisable() {
        super.onDisable();

        if (nullCheck()) return;

        for (EntityPlayer player : mc.world.playerEntities) {
            if (player != null && player != mc.player) {
                player.setEntityBoundingBox(new AxisAlignedBB(
                        player.posX - 0.3F,
                        player.getEntityBoundingBox().minY,
                        player.posZ - 0.3F,
                        player.posX + 0.3F,
                        player.getEntityBoundingBox().maxY,
                        player.posZ + 0.3F
                ));
            }
        }
    }
}
