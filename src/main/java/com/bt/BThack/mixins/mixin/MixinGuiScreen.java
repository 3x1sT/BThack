package com.bt.BThack.mixins.mixin;

import com.bt.BThack.System.Client;
import com.bt.BThack.impl.Module.RENDER.ShulkerPreview;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.item.ItemShulkerBox;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GuiScreen.class)
public class MixinGuiScreen {
    @Inject(method = "renderToolTip", at = @At("HEAD"), cancellable = true)
    public void renderToolTip(ItemStack itemStack, int x, int y, CallbackInfo callbackInfo) {
        if (itemStack != null) {
            if (Client.getModuleByName("ShulkerPreview").isEnabled() && itemStack.getItem() instanceof ItemShulkerBox) {
                if (!itemStack.isEmpty() && itemStack.getTagCompound() != null && itemStack.getTagCompound().hasKey("BlockEntityTag", 10)) {
                    callbackInfo.cancel();
                    ShulkerPreview.renderShulkerTooltip(itemStack, x + 6, y - 33);
                }
            }
        }
    }
}
