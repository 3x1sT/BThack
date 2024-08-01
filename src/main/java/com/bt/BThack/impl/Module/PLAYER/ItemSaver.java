package com.bt.BThack.impl.Module.PLAYER;

import com.bt.BThack.api.Module.Module;
import com.bt.BThack.api.Utils.Font.FontUtil;
import com.bt.BThack.api.Utils.ItemsUtil;
import com.bt.BThack.impl.Events.PacketEvent;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.CPacketUseEntity;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.input.Keyboard;

import java.awt.*;
import java.util.Objects;

public class ItemSaver extends Module {
    private boolean stopRender;
    private boolean needRender;
    private int alpha = 0;

    private final String warn = "The strength of the item is too low!";

    public ItemSaver() {
        super("ItemSaver",
                "Prevents the use of items with less than ultimate strength.",
                Keyboard.KEY_NONE,
                Category.PLAYER,
                false
        );

        addSlider("Min Strength(%)", this, 5, 1, 90, false);
        addCheckbox("Attack Saver", this, true);
    }

    @SubscribeEvent
    public void onRenderOverlay(RenderGameOverlayEvent e) {
        ScaledResolution sc = new ScaledResolution(mc);
        if (Objects.requireNonNull(e.getType()) == RenderGameOverlayEvent.ElementType.TEXT) {
            if (needRender) {
                if (!stopRender) {
                    FontUtil.drawTextNoShadow(warn, (sc.getScaledWidth() / 2f) - (FontUtil.getStringWidth(warn) / 2), (sc.getScaledHeight() / 2f) + 40, new Color(255, 98, 0).hashCode());
                } else {
                    if (alpha > 0) {
                        FontUtil.drawTextNoShadow(warn, (sc.getScaledWidth() / 2f) - (FontUtil.getStringWidth(warn) / 2), (sc.getScaledHeight() / 2f) + 40, new Color(255, 98, 0, alpha > 255 ? 255 : alpha).hashCode());
                        alpha--;
                    } else {
                        needRender = false;
                    }
                }
            }
        }
        stopRender = true;
    }

    @SubscribeEvent
    public void onLeftClickBlock(PlayerInteractEvent.LeftClickBlock e) {
        if (nullCheck()) return;

        check(e);
    }

    @SubscribeEvent
    public void onSendPacket(PacketEvent.Send e) {
        if (nullCheck()) return;

        if (e.getPacket() instanceof CPacketUseEntity) {
            CPacketUseEntity cPacketUseEntity = (CPacketUseEntity) e.getPacket();

            if (cPacketUseEntity.getAction() == CPacketUseEntity.Action.ATTACK && getCheckbox(this.name, "Attack Saver")) {
                check(e);
            }
        }
    }


    private void check(Event e) {
        ItemStack item = ItemsUtil.getItem(mc.player.inventory.currentItem);
        float currentDamage = ItemsUtil.getItemDurabilityInPercentages(item);
        if (currentDamage < getSlider(this.name, "Min Strength(%)")) {
            /*
            ItemsUtil.swapItem(GenerateIntNumber.generate(1,8));
            ChatUtils.sendMessage("[ItemSaver] The strength of the item is too low!");

             */
            if (!needRender) needRender = true;
            if (stopRender) stopRender = false;
            if (alpha != 380) alpha = 380;
            e.setCanceled(true);
        }
    }
}
