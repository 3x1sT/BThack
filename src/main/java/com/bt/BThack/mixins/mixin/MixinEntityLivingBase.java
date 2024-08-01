package com.bt.BThack.mixins.mixin;

import com.bt.BThack.System.Client;
import com.bt.BThack.api.Module.Module;
import com.bt.BThack.api.Utils.Build.BuildManager;
import com.bt.BThack.api.Utils.ChatUtils;
import com.bt.BThack.api.Utils.Interfaces.Mc;
import com.bt.BThack.impl.Module.MOVEMENT.ElytraFlight.WorldExtensions;
import com.mojang.realmsclient.gui.ChatFormatting;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
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
            if (EntityLivingBase.class.isInstance(mc.player) && Client.getModuleByName("ChildModel").isEnabled()) {
                if (!Client.getModuleByName("BThackCape").isEnabled()) {
                    cir.setReturnValue(true);
                } else {
                    ChatUtils.sendMessage(ChatFormatting.YELLOW + "The ChildModel module is not compatible with the BThackCape module. To avoid problems, the ChildModel module is automatically disabled.");
                    Client.getModuleByName("ChildModel").setToggled(false);
                }
            }
        }
    }


    @Inject(method = "getJumpUpwardsMotion", at = @At("HEAD"), cancellable = true)
    public void modifyGetJumpMotion(CallbackInfoReturnable<Float> cir) {
        if (Client.getModuleByName("NewElytraFlight").isEnabled() && Module.getCheckbox("NewElytraFlight", "Edit Jump Motion")) {
            if (Module.getCheckbox("NewElytraFlight", "Auto Control Jump Height")) {
                Vec3d vec3d = WorldExtensions.getGroundPos(mc.world, mc.player);
                BlockPos blockPos = new BlockPos(vec3d.x, vec3d.y + 3.5, vec3d.z);

                if (BuildManager.ignoreBlocks.contains(mc.world.getBlockState(blockPos).getBlock())) {
                    cir.setReturnValue(0.42f);
                } else {
                    cir.setReturnValue(0.40f);
                }
            } else
                cir.setReturnValue((float) Module.getSlider("NewElytraFlight", "Jump Height"));
        }
    }
}
