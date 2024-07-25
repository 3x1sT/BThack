package com.bt.BThack.impl.Module.MOVEMENT;

import com.bt.BThack.api.Module.Module;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Keyboard;

public class Sprint extends Module{
    public Sprint() {
        super("Sprint",
                "The player always runs.",
                Keyboard.KEY_NONE,
                Category.MOVEMENT,
                false
        );

    }

    @SubscribeEvent
    public void onPlayerTick(TickEvent.PlayerTickEvent e) {
        if (nullCheck()) return;

        if (mc.player.sprintingTicksLeft > 2 || !mc.player.isSprinting()) {
            try {
                if (needSprint()) {
                    mc.player.setSprinting(true);
                }
            } catch (Exception ignored) {}
        }
    }

    public static boolean needSprint() {
        Minecraft mc = Minecraft.getMinecraft();

        if (mc.player == null || mc.world == null) return false;

        return
                !mc.gameSettings.keyBindSneak.isKeyDown()
                && !mc.player.isElytraFlying()
                && mc.player.foodStats.foodLevel > 6
                && isMoving()
                && !mc.player.capabilities.isFlying;
    }

    public static boolean isMoving() {
        Minecraft mc = Minecraft.getMinecraft();

        return
                !mc.player.isSneaking()
                && mc.player.movementInput.moveForward > 0
                        && !mc.player.collidedHorizontally;
    }
}
