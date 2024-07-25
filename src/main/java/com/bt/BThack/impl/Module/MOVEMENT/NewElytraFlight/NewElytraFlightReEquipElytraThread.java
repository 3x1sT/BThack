package com.bt.BThack.impl.Module.MOVEMENT.NewElytraFlight;

import com.bt.BThack.api.Module.Module;
import com.bt.BThack.api.Utils.Interfaces.Mc;
import net.minecraft.inventory.ClickType;

public class NewElytraFlightReEquipElytraThread extends Thread implements Mc {
    protected static boolean needReTakeOff = true;

    @Override
    public void run() {
        needReTakeOff = false;
        mc.playerController.windowClick(0, 6, 1, ClickType.PICKUP, mc.player);
        mc.playerController.updateController();
        trySleep((long) Module.getSlider("NewElytraFlight", "ReEquip Delay"));
        mc.playerController.windowClick(0, 6, 1, ClickType.PICKUP, mc.player);
        mc.playerController.updateController();
        needReTakeOff = true;
    }

    private void trySleep(long millis) {
        try {
            sleep(millis);
        } catch (InterruptedException ignored) {}
    }
}
