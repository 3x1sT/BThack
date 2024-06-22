package com.bt.BThack.impl.Module.COMBAT;

import com.bt.BThack.api.Module.Module;
import com.bt.BThack.api.Utils.Modules.AutoSwordUtil;
import com.bt.BThack.impl.Events.PacketEvent;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.network.play.client.CPacketUseEntity;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.input.Keyboard;

public class AutoSword extends Module {
    public AutoSword() {
        super("AutoSword",
                "Automatically draws the sword when hit.",
                Keyboard.KEY_NONE,
                Category.COMBAT,
                false
        );
    }

    @SubscribeEvent
    public void onPacket(PacketEvent.Send e) {
        if (e.getPacket() instanceof CPacketUseEntity) {
            CPacketUseEntity cPacketUseEntity = (CPacketUseEntity)e.getPacket();
            if (cPacketUseEntity.getAction() == CPacketUseEntity.Action.ATTACK) {
                if (cPacketUseEntity.getEntityFromWorld(mc.world) instanceof EntityEnderCrystal) {
                    return;
                }

                int inventorySlot = AutoSwordUtil.getSword();
                if (inventorySlot != -1) {
                    mc.player.inventory.currentItem = inventorySlot;
                }
            }
        }

    }
}
