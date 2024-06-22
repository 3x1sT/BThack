package com.bt.BThack.impl.Module.PLAYER;

import com.bt.BThack.api.Module.Module;
import net.minecraft.block.Block;
import net.minecraft.item.*;
import net.minecraft.network.play.client.CPacketPlayerDigging;
import net.minecraft.network.play.client.CPacketPlayerTryUseItem;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Keyboard;

public class FastUse extends Module {
    public FastUse() {
        super("FastUse",
                "Allows you to use certain things faster",
                Keyboard.KEY_NONE,
                Category.PLAYER,
                false
        );

        addCheckbox("Bow", this, true);
        addCheckbox("Experience", this, true);
        addCheckbox("Crystals", this, true);
        addCheckbox("Fishing Rods", this, true);
        addCheckbox("Throwables", this, true);
        addCheckbox("Blocks", this, true);
        addCheckbox("Others", this, true);
    }

    public void onUpdate(TickEvent.PlayerTickEvent e) {
        if (nullCheck()) return;

        boolean bow = getCheckbox(this.name, "Bow");
        boolean exp = getCheckbox(this.name, "Experience");
        boolean cryst = getCheckbox(this.name, "Crystals");
        boolean rods = getCheckbox(this.name, "Fishing Rods");
        boolean throwable = getCheckbox(this.name, "Throwables");
        boolean block = getCheckbox(this.name, "Blocks");
        boolean other = getCheckbox(this.name, "Others");
        if (bow) {
            if ((mc.player.getHeldItemMainhand().getItem() instanceof ItemBow)) {
                if (mc.player.isHandActive() && mc.player.getItemInUseMaxCount() >= 3) {
                    mc.player.connection.sendPacket(new CPacketPlayerDigging(CPacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, mc.player.getHorizontalFacing()));
                    mc.player.connection.sendPacket(new CPacketPlayerTryUseItem(EnumHand.MAIN_HAND));
                    mc.player.stopActiveHand();
                }
            }
            if ((mc.player.getHeldItemOffhand().getItem() instanceof ItemBow)) {
                if (mc.player.isHandActive() && mc.player.getItemInUseMaxCount() >= 3) {
                    mc.player.connection.sendPacket(new CPacketPlayerDigging(CPacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, mc.player.getHorizontalFacing()));
                    mc.player.connection.sendPacket(new CPacketPlayerTryUseItem(EnumHand.OFF_HAND));
                    mc.player.stopActiveHand();
                }
            }
        }
        if (exp) {
            if (mc.player.getHeldItemMainhand().getItem() instanceof ItemExpBottle) {
                mc.rightClickDelayTimer = 0;
            }
        }
        if (cryst) {
            if (mc.player.getHeldItemMainhand().getItem() instanceof ItemEndCrystal) {
                mc.rightClickDelayTimer = 0;
            }
        }
        if (rods) {
            if (mc.player.getHeldItemMainhand().getItem() instanceof ItemFishingRod) {
                mc.rightClickDelayTimer = 0;
            }
        }
        if (throwable) {
            if (mc.player.getHeldItemMainhand().getItem() instanceof ItemEgg || mc.player.getHeldItemMainhand().getItem() instanceof ItemEnderPearl || mc.player.getHeldItemMainhand().getItem() instanceof ItemEnderEye || mc.player.getHeldItemMainhand().getItem() instanceof ItemFireworkCharge || mc.player.getHeldItemMainhand().getItem() instanceof ItemSnowball || mc.player.getHeldItemMainhand().getItem() instanceof ItemSplashPotion) {
                mc.rightClickDelayTimer = 0;
            }
        }
        if (block) {
            if (Block.getBlockFromItem(mc.player.getHeldItemMainhand().getItem()).getDefaultState().isFullBlock()) {
                mc.rightClickDelayTimer = 0;
            }
        }
        if (other) {
            if (!(mc.player.getHeldItemMainhand().getItem() instanceof ItemBlock || mc.player.getHeldItemMainhand().getItem() instanceof ItemFishingRod)) {
                mc.rightClickDelayTimer = 0;
            }
        }
    }
}
