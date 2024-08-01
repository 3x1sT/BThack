package com.bt.BThack.impl.Module.PLAYER;

import com.bt.BThack.api.Managers.Thread.ThreadManager;
import com.bt.BThack.api.Module.Module;
import com.bt.BThack.impl.Events.RightClickBlockEvent;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.CPacketPlayerTryUseItemOnBlock;
import net.minecraft.util.EnumActionResult;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.input.Keyboard;

public class PacketPlace extends Module {

    public PacketPlace() {
        super("PacketPlace",
                "Replaces the standard method of placing blocks with a packet method.",
                Keyboard.KEY_NONE,
                Category.PLAYER,
                false
        );

        addCheckbox("Shifting", this, false);
        addSlider("Send Packets", this, 2, 1, 15, true);
    }

    @SubscribeEvent
    public void onPlaceBlock(RightClickBlockEvent e) {

        ItemStack itemStack = mc.player.itemStackMainHand;
        if (itemStack.getItem() instanceof ItemBlock) {
            ItemBlock itemBlock = (ItemBlock) itemStack.getItem();
            if (itemBlock.canPlaceBlockOnSide(e.worldClient, e.blockPos, e.enumFacing, e.entityPlayerSP, itemStack)) {

                if (getCheckbox(this.name, "Shifting")) {
                    mc.player.setSneaking(true);
                    mc.gameSettings.keyBindSneak.pressed = true;
                }

                e.callbackInfo.setReturnValue(EnumActionResult.SUCCESS);
                e.callbackInfo.cancel();
                ThreadManager.startNewThread(thread -> {
                    try {
                        thread.sleep(5);
                    } catch (InterruptedException ignored) {
                    }
                    float f = (float) (e.vec3d.x - (double) e.blockPos.getX());
                    float f1 = (float) (e.vec3d.y - (double) e.blockPos.getY());
                    float f2 = (float) (e.vec3d.z - (double) e.blockPos.getZ());

                    for (int i = 0; i < (int) getSlider("PacketPlace", "Send Packets"); i++) {
                        mc.player.connection.sendPacket(new CPacketPlayerTryUseItemOnBlock(e.blockPos, e.enumFacing, e.enumHand, f, f1, f2));
                        try {
                            thread.sleep(5);
                        } catch (InterruptedException ignored) {
                        }
                    }

                    if (getCheckbox(this.name, "Shifting")) {
                        mc.player.setSneaking(false);
                        mc.gameSettings.keyBindSneak.pressed = false;
                    }
                });
            }
        }
    }
}
