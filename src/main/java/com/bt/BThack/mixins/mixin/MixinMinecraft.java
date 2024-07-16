package com.bt.BThack.mixins.mixin;

import com.bt.BThack.System.Client;
import net.minecraft.client.Minecraft;
import org.lwjgl.opengl.Display;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = Minecraft.class, priority = Integer.MAX_VALUE)
public class MixinMinecraft {


    @Inject(method = {"createDisplay"}, at = {@At("TAIL")})
    public void modifyCreateDisplay(CallbackInfo ci) {
        Display.setTitle("Launching " + Client.name);
    }
}
