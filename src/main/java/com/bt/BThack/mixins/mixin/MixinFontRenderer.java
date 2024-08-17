package com.bt.BThack.mixins.mixin;

import com.bt.BThack.System.Client;
import com.bt.BThack.api.Module.Module;
import com.bt.BThack.api.Utils.Font.FontUtil;
import com.bt.BThack.api.Utils.Interfaces.Mc;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.util.StringUtils;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * @author olliem5
 *
 * TODO: Also change string width & string height checks to custom ones
 */

@Mixin(value = FontRenderer.class, priority = Integer.MAX_VALUE)
public final class MixinFontRenderer implements Mc {
    @Inject(method = "drawString(Ljava/lang/String;FFIZ)I", at = @At(value = "HEAD"), cancellable = true)
    public void renderString(String text, float x, float y, int colour, boolean shadow, CallbackInfoReturnable<Integer> callbackInfoReturnable) {
        if (mc.player == null || mc.world == null) return;

        if (Client.getModuleByName("Font").isEnabled() && Module.getCheckbox("Font", "Override Minecraft")) {
            if (Module.getCheckbox("Font", "Shadow")) {
                callbackInfoReturnable.setReturnValue(getShadowString(text, x, y, colour));
            } else {
                callbackInfoReturnable.setReturnValue(FontUtil.getCurrentCustomFont().drawString(text, x, y, colour));
            }
        }
    }

    private int getShadowString(String text, float x, float y, int colour) {
        FontUtil.getCurrentCustomFont().drawString(StringUtils.stripControlCodes(text), x + 0.5f, y + 0.5f, 0x000000);
        return FontUtil.getCurrentCustomFont().drawString(text, x, y, colour);
    }
}
