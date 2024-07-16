package com.bt.BThack.mixins.mixin;

import com.bt.BThack.System.Client;
import com.bt.BThack.api.Utils.ChatUtils;
import com.bt.BThack.api.Utils.Interfaces.Mc;
import com.mojang.realmsclient.gui.ChatFormatting;
import net.minecraft.entity.EntityLivingBase;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EntityLivingBase.class)
public abstract class MixinEntityLivingBase implements Mc {

    @Shadow public float randomUnused1;

    @Shadow public float randomUnused2;

    @Inject(method = "isChild", at = @At("HEAD"), cancellable = true)
    public void modifyIsChild(CallbackInfoReturnable<Boolean> cir) {
        if (mc.world != null && mc.player != null) {
            if (this.randomUnused1 == mc.player.randomUnused1 && this.randomUnused2 == mc.player.randomUnused2 && Client.getModuleByName("ChildModel").isEnabled()) {
                if (!Client.getModuleByName("BThackCape").isEnabled()) {
                    cir.setReturnValue(true);
                } else {
                    ChatUtils.sendMessage(ChatFormatting.YELLOW + "The ChildModel module is not compatible with the BThackCape module. To avoid problems, the ChildModel module is automatically disabled.");
                    Client.getModuleByName("ChildModel").setToggled(false);
                }
            }
        }
    }
}
