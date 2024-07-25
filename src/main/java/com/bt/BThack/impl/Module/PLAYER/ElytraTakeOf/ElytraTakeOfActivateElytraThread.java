package com.bt.BThack.impl.Module.PLAYER.ElytraTakeOf;

import com.bt.BThack.api.Utils.Interfaces.Mc;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.ClickType;
import net.minecraft.network.play.client.CPacketPlayer;

public class ElytraTakeOfActivateElytraThread extends Thread implements Mc {

    public int needItem;

    public boolean elytraFlying;

    @Override
    public void run() {

        if (!elytraFlying) {

            while (!mc.player.isElytraFlying()) {
                mc.gameSettings.keyBindJump.pressed = true;

                try {
                    sleep(100);
                } catch (InterruptedException e) {
                }
                mc.gameSettings.keyBindJump.pressed = false;

                if (mc.player.collidedVertically)
                    stop();

                if (mc.player.isElytraFlying())
                    mc.player.playSound(SoundEvents.ENTITY_EXPERIENCE_ORB_PICKUP, 1, 1);
            }
        } else {
            sendPlayerPacket();

            mc.playerController.windowClick(0, 6, 1, ClickType.PICKUP, mc.player);
            sleep();
            mc.playerController.windowClick(0, needItem, 1, ClickType.PICKUP, mc.player);
            sleep();

            sendPlayerPacket();
        }
    }


    private void sleep() {
        try {
            sleep(70);
        } catch (InterruptedException e) {}
    }

    private void sendPlayerPacket() {
        mc.player.connection.sendPacket(new CPacketPlayer(mc.player.onGround));
    }
}
