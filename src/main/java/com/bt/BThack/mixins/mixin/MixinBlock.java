package com.bt.BThack.mixins.mixin;

import com.bt.BThack.impl.Module.RENDER.Xray;
import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = Block.class, priority = Integer.MAX_VALUE)
public class MixinBlock {
   @Inject(
      method = {"shouldSideBeRendered"},
      at = {@At("HEAD")},
      cancellable = true
   )
   public void renderSidePatch(IBlockState blockState, IBlockAccess iBlockAccess, BlockPos blockPos, EnumFacing enumFacing, CallbackInfoReturnable callbackInfoReturnable) {
      if (Xray.doXray) {
         if (Xray.xrayBlocks.contains(blockState.getBlock())) {
            callbackInfoReturnable.setReturnValue(true);
         } else {
            callbackInfoReturnable.setReturnValue(false);
            callbackInfoReturnable.cancel();
         }
      }

   }

   @Inject(
      method = {"isFullCube"},
      at = {@At("HEAD")},
      cancellable = true
   )
   public void fullCubePatch(IBlockState iBlockState, CallbackInfoReturnable callbackInfoReturnable) {
      if (Xray.doXray) {
         callbackInfoReturnable.setReturnValue(Xray.xrayBlocks.contains(iBlockState.getBlock()));
      }

   }
}
