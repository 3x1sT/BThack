package com.bt.BThack.mixins.mixin;

import com.bt.BThack.api.Storage.MusicStorage;
import com.bt.BThack.api.Utils.Interfaces.Mc;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.util.SoundCategory;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GuiButton.class)
public class MixinGuiButton implements Mc {

    @Inject(method = "playPressSound", at = @At("HEAD"), cancellable = true)
    public void modifyPlayPressSound(SoundHandler p_playPressSound_1_, CallbackInfo ci) {
        MusicStorage.buttonClicked.play(mc.gameSettings.getSoundLevel(SoundCategory.MASTER) / 2);
        ci.cancel();
    }
}
