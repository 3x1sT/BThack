package com.bt.BThack.api.Utils.Destroy;


import com.bt.BThack.api.Utils.Interfaces.Mc;
import com.bt.BThack.api.Utils.Modules.AimBotUtils;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class DestroyManager implements Mc {
    public static boolean isDestroying = false;
    public static BlockPos currentBlockPos;



    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent e) {
        if (mc.player == null || mc.world == null) return;


        try {
            if (mc.playerController.onPlayerDamageBlock(currentBlockPos, AimBotUtils.getInvertedFacingEntity(mc.player))) {
                mc.player.swingArm(EnumHand.MAIN_HAND);
            }
        } catch (Exception ignored) {}
    }






    public static void delay(long milliseconds, Thread thread) {
        try {
            thread.sleep(milliseconds);
        } catch (InterruptedException ignored) {}
    }
}
