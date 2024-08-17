package com.bt.BThack.mixins.mixin;

import com.bt.BThack.api.Utils.Build.BuildManager;
import com.bt.BThack.api.Utils.Destroy.DestroyManager;
import net.minecraft.client.audio.SoundManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SoundManager.class)
public abstract class MixinSoundManager {
    @Unique
    int bThack$postTick = 0;

    @Inject(method = "updateAllSounds", at = @At("HEAD"), cancellable = true)
    public void modifyUpdateAllSounds(CallbackInfo ci) {
        if (BuildManager.isBuilding || DestroyManager.isDestroying) {
            bThack$postTick = 20;
            ci.cancel();
        } else {
            if (bThack$postTick > 0) {
                ci.cancel();
                bThack$postTick--;
            }
        }
    }
}
