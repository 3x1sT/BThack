package com.bt.BThack.impl.Module.PLAYER.ElytraTakeOf;

import com.bt.BThack.System.Client;
import com.bt.BThack.api.Utils.Interfaces.Mc;
import net.minecraft.inventory.ClickType;
import net.minecraft.network.play.client.CPacketPlayer;

public class ElytraTeakeOfReEquipElytraThread extends Thread implements Mc {
    public int needSlot;

    @Override
    public void run() {
        boolean elytraFlightEnabled = false;

        if (Client.getModuleByName("ElytraFlight").isEnabled()) {
            elytraFlightEnabled = true;
        }

        int item;
        if (needSlot < 9)
            item = needSlot + 36;
        else
            item = needSlot;

        Client.getModuleByName("ElytraFlight").setToggled(false);

        sleep();

        sendPlayerPacket();

        mc.playerController.windowClick(0, 6, 1, ClickType.PICKUP, mc.player);
        sleep();
        mc.playerController.windowClick(0, item, 1, ClickType.PICKUP, mc.player);
        sleep();

        sendPlayerPacket();

        sleep();
        mc.playerController.windowClick(0, 6, 1, ClickType.PICKUP, mc.player);
        sleep();
        mc.playerController.updateController();

        sendPlayerPacket();

        if (elytraFlightEnabled) {
            Client.getModuleByName("ElytraFlight").setToggled(true);
        }

        sleep();

        mc.playerController.windowClick(0, 6, 1, ClickType.PICKUP, mc.player);
        sleep();
        mc.playerController.windowClick(0, item, 1, ClickType.PICKUP, mc.player);
        sleep();

        sendPlayerPacket();

        sleep();
        mc.playerController.windowClick(0, 6, 1, ClickType.PICKUP, mc.player);
        sleep();
        mc.playerController.updateController();

        sleep();

        sendPlayerPacket();
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
