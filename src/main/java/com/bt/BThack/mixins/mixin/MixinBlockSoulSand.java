package com.bt.BThack.mixins.mixin;

import com.bt.BThack.System.Client;
import com.bt.BThack.api.Module.Module;
import net.minecraft.block.BlockSoulSand;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = BlockSoulSand.class, priority = Integer.MAX_VALUE)
public class MixinBlockSoulSand {

    @Inject(method = {"onEntityCollision"}, at = {@At("HEAD")}, cancellable = true)
    public void onEntityCollided(World p_180634_1_, BlockPos p_180634_2_, IBlockState p_180634_3_, Entity p_180634_4_, CallbackInfo ci) {
        if (Client.getModuleByName("NoSlow").isEnabled() && Module.getCheckbox("NoSlow", "SoulSand")) ci.cancel();
    }
}
