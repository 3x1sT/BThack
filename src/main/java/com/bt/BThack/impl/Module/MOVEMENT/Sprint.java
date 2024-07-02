package com.bt.BThack.impl.Module.MOVEMENT;

import com.bt.BThack.api.Module.Module;
import net.minecraft.client.Minecraft;
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

    public static boolean needSprint() {
        Minecraft mc = Minecraft.getMinecraft();
        return  !mc.gameSettings.keyBindSneak.isKeyDown()
                && !mc.player.isElytraFlying()
                && mc.player.foodStats.foodLevel > 6
                && isMoving()
                && !mc.player.capabilities.isFlying
                && !mc.player.collidedHorizontally;
    }

    public static boolean isMoving() {
        Minecraft mc = Minecraft.getMinecraft();
        return !mc.player.isSneaking() && mc.player.movementInput.moveForward > 0 && !mc.player.collidedHorizontally;
    }
}
