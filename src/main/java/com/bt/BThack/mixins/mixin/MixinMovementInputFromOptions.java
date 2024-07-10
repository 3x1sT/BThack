package com.bt.BThack.mixins.mixin;

import com.bt.BThack.System.Client;
import com.bt.BThack.api.Utils.Interfaces.Mc;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.util.MovementInput;
import net.minecraft.util.MovementInputFromOptions;
import org.lwjgl.input.Keyboard;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(value = MovementInputFromOptions.class, priority = Integer.MAX_VALUE)
public final class MixinMovementInputFromOptions extends MovementInput implements Mc {
    @Redirect(method = "updatePlayerMoveState", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/settings/KeyBinding;isKeyDown()Z"))
    public boolean isKeyPressed(KeyBinding keyBinding) {
        if (Client.getModuleByName("GuiMove").isEnabled() && mc.currentScreen != null && !(mc.currentScreen instanceof GuiChat)) {
            return Keyboard.isKeyDown(keyBinding.getKeyCode());
        }

        return keyBinding.isKeyDown();
    }
}