package com.bt.BThack.mixins.mixin;

import com.bt.BThack.System.Client;
import com.bt.BThack.api.Module.Module;
import com.bt.BThack.impl.Events.TransformFirstPersonEvent;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHandSide;
import net.minecraftforge.common.MinecraftForge;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemRenderer.class)
public final class MixinItemRenderer {
    @Inject(method = "transformEatFirstPerson", at = @At("HEAD"), cancellable = true)
    public void transformEatFirstPerson(float p_187454_1_, EnumHandSide enumHandSide, ItemStack itemStack, CallbackInfo callbackInfo) {
        TransformFirstPersonEvent event = new TransformFirstPersonEvent.Pre(enumHandSide);
        MinecraftForge.EVENT_BUS.post(event);

        if (Client.getModuleByName("ViewModel").isEnabled() && Module.getCheckbox("ViewModel", "No Eat Anim")) {
            callbackInfo.cancel();
        }
    }

    @Inject(method = "transformFirstPerson", at = @At("HEAD"))
    public void transformFirstPersonHead(EnumHandSide enumHandSide, float p_187453_2_, CallbackInfo callbackInfo) {
        TransformFirstPersonEvent event = new TransformFirstPersonEvent.Pre(enumHandSide);
        MinecraftForge.EVENT_BUS.post(event);
    }

    @Inject(method = "transformFirstPerson", at = @At("TAIL"))
    public void transformFirstPersonTail(EnumHandSide enumHandSide, float p_187453_2_, CallbackInfo callbackInfo) {
        TransformFirstPersonEvent event = new TransformFirstPersonEvent.Post(enumHandSide);
        MinecraftForge.EVENT_BUS.post(event);
    }

    @Inject(method = "transformSideFirstPerson", at = @At("HEAD"))
    public void transformSideFirstPerson(EnumHandSide enumHandSide, float p_187459_2_, CallbackInfo callbackInfo) {
        TransformFirstPersonEvent event = new TransformFirstPersonEvent.Pre(enumHandSide);
        MinecraftForge.EVENT_BUS.post(event);
    }
}