package com.bt.BThack.mixins.mixin;

import com.bt.BThack.System.Client;
import com.bt.BThack.api.Module.Module;
import net.minecraft.block.BlockSlime;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = BlockSlime.class, priority = Integer.MAX_VALUE)
public class MixinBlockSlime {

    @Inject(method = {"onLanded"}, at = {@At("HEAD")}, cancellable = true)
    public void onLanded(World p_176216_1_, Entity p_176216_2_, CallbackInfo ci) {
        if (Client.getModuleByName("NoSlow").isEnabled() && Module.getCheckbox("NoSlow", "Slime Landed")) {
            ci.cancel();
        }
    }
}
