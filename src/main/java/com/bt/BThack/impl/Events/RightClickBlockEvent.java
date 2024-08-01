package com.bt.BThack.impl.Events;

import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.fml.common.eventhandler.Cancelable;
import net.minecraftforge.fml.common.eventhandler.Event;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Cancelable
public class RightClickBlockEvent extends Event {
    public final EntityPlayerSP entityPlayerSP;
    public final WorldClient worldClient;
    public final BlockPos blockPos;
    public final EnumFacing enumFacing;
    public final Vec3d vec3d;
    public final EnumHand enumHand;
    public final CallbackInfoReturnable<EnumActionResult> callbackInfo;

    public RightClickBlockEvent(EntityPlayerSP entityPlayerSP, WorldClient worldClient, BlockPos blockPos, EnumFacing enumFacing, Vec3d vec3d, EnumHand enumHand, CallbackInfoReturnable<EnumActionResult> callbackInfo) {
        this.entityPlayerSP = entityPlayerSP;
        this.worldClient = worldClient;
        this.blockPos = blockPos;
        this.enumFacing = enumFacing;
        this.vec3d = vec3d;
        this.enumHand = enumHand;
        this.callbackInfo = callbackInfo;
    }
}
