package com.bt.BThack.mixins.mixin;

import com.bt.BThack.Client;
import com.bt.BThack.api.Module.Module;
import net.minecraft.block.BlockLiquid;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin({BlockLiquid.class})
public class MixinBlockLiquid {
   @Inject(
      method = {"modifyAcceleration"},
      at = {@At("HEAD")},
      cancellable = true
   )
   public void modifyAcceleration(World world, BlockPos blockPos, Entity entity, Vec3d vec3d, CallbackInfoReturnable callbackInfoReturnable) {
      if (Client.getModuleByName("Velocity").isEnabled() && Module.getCheckbox("Velocity", "NoPush Liquids")) {
         callbackInfoReturnable.setReturnValue(vec3d);
         callbackInfoReturnable.cancel();
      }

   }
}
