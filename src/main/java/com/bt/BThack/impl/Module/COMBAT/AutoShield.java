package com.bt.BThack.impl.Module.COMBAT;

import com.bt.BThack.api.Module.Module;
import com.bt.BThack.api.Utils.ItemsUtil;
import com.bt.BThack.api.Utils.Modules.AimBotUtils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.item.ItemShield;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.util.EnumHand;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Keyboard;

import java.util.Comparator;

public class AutoShield extends Module {

    public AutoShield() {
        super("AutoShield",
                "If you have a shield in your right hand, it will activate if an arrow flies at you.",
                Keyboard.KEY_NONE,
                Category.COMBAT,
                false
        );
    }


    private boolean pressed = false;
    private short delayTick = 0;

    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent e) {
        if (nullCheck()) return;

        Entity entity1 = mc.world.loadedEntityList.stream().filter(entity -> entity instanceof EntityArrow).filter(entity -> !((EntityArrow) entity).inGround).min(Comparator.comparing(
                entity -> entity.getDistance(mc.player))).filter(entity -> entity.getDistance(mc.player) <= 5).orElse(null);

        EntityArrow arrow = (EntityArrow) entity1;

        if (arrow != null) {
            if (mc.player.getHeldItemOffhand().getItem() instanceof ItemShield) {
                mc.player.connection.sendPacket(new CPacketPlayer.Rotation(AimBotUtils.rotations(arrow)[0], mc.player.rotationPitch, mc.player.onGround));

                //I don't know why but without it, minecraft doesn't want to recognize that the shield is activated
                mc.gameSettings.keyBindUseItem.pressed = true;

                mc.player.setActiveHand(EnumHand.OFF_HAND);
                ItemsUtil.useItemNoSwing(EnumHand.OFF_HAND);
                pressed = true;

                delayTick = 10;
            }
        } else {
            if (delayTick > 0) {
                delayTick--;
            } else {
                if (mc.gameSettings.keyBindUseItem.pressed) {
                    if (pressed) {
                        mc.gameSettings.keyBindUseItem.pressed = false;
                        mc.player.resetActiveHand();
                        pressed = false;
                    }
                }
            }
        }
    }
}
