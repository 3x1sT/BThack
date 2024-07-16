package com.bt.BThack.mixins.mixin;

import com.bt.BThack.System.Client;
import com.bt.BThack.api.Module.Module;
import com.bt.BThack.api.Utils.Interfaces.Mc;
import com.bt.BThack.impl.Module.CLIENT.BThackCape;
import com.bt.BThack.impl.Module.CLIENT.CustomSkin;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.util.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = AbstractClientPlayer.class, priority = Integer.MAX_VALUE)
public class MixinAbstractClientPlayer implements Mc {


    @Shadow public NetworkPlayerInfo playerInfo;

    @Inject(method = "getLocationCape", at = @At("HEAD"), cancellable = true)
    public void modifyLocationCape(CallbackInfoReturnable<ResourceLocation> cir) {
        if (Client.getModuleByName("BThackCape").isEnabled() && this.playerInfo == mc.player.playerInfo) {
            cir.setReturnValue(BThackCape.BThack_Cape);
        }
    }

    @Inject(method = "getLocationSkin()Lnet/minecraft/util/ResourceLocation;", at = @At("HEAD"), cancellable = true)
    public void modifyLocationSkin(CallbackInfoReturnable<ResourceLocation> cir) {
        if (Client.getModuleByName("CustomSkin").isEnabled() && this.playerInfo == mc.player.playerInfo) {
            switch (Module.getMode("CustomSkin", "Skin")) {
                case "PopBob":
                    cir.setReturnValue(CustomSkin.popBobSkin);
                    break;
                case "HauseMaster":
                    cir.setReturnValue(CustomSkin.hauseMasterSkin);
                    break;
                case "ITristan":
                    cir.setReturnValue(CustomSkin.iTristanSkin);
                    break;
                case "Jared2013":
                    cir.setReturnValue(CustomSkin.jared2013Skin);
                    break;
                case "TheCampingRusher":
                    cir.setReturnValue(CustomSkin.theCampingRusherSkin);
                    break;
                case "xcc2":
                    cir.setReturnValue(CustomSkin.xcc2Skin);
                    break;
            }
        }
    }

    @Inject(method = "getSkinType", at = @At("HEAD"), cancellable = true)
    public void modifySkinType(CallbackInfoReturnable<String> cir) {
        if (Client.getModuleByName("CustomSkin").isEnabled() && Module.getCheckbox("CustomSkin", "No Slim Skin")) {
            cir.setReturnValue("default");
        }
    }
}
