package com.bt.BThack.mixins.mixin;

import com.bt.BThack.System.Client;
import com.bt.BThack.api.Module.Module;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = BlockLiquid.class, priority = Integer.MAX_VALUE)
public class MixinBlockLiquid {
   @Inject(method = {"modifyAcceleration"}, at = {@At("HEAD")}, cancellable = true)
   public void modifyAcceleration(World world, BlockPos blockPos, Entity entity, Vec3d vec3d, CallbackInfoReturnable callbackInfoReturnable) {
      if (Client.getModuleByName("Velocity").isEnabled() && Module.getCheckbox("Velocity", "NoPush Liquids")) {
         callbackInfoReturnable.setReturnValue(vec3d);
         callbackInfoReturnable.cancel();
      }

   }

   @Inject(method = "canCollideCheck", at = @At("HEAD"), cancellable = true)
   public void modifyCollideCheck(IBlockState iBlockState, boolean hitIfLiquid, CallbackInfoReturnable<Boolean> cir) {
      cir.setReturnValue(Client.getModuleByName("LiquidInteract").isEnabled() || (hitIfLiquid && iBlockState.getValue(BlockLiquid.LEVEL) == 0));
   }
}
