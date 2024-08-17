package com.bt.BThack.mixins.mixin;

import com.bt.BThack.impl.Module.RENDER.Xray;
import net.minecraft.client.renderer.chunk.VisGraph;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = VisGraph.class, priority = Integer.MAX_VALUE)
public class MixinVisGraph {
   @Inject(
      method = {"setOpaqueCube"},
      at = {@At("HEAD")},
      cancellable = true
   )
   public void setOpaqueCubePatch(BlockPos blockPos, CallbackInfo callbackInfo) {
      if (Xray.doXray) {
         callbackInfo.cancel();
      }

   }
}
