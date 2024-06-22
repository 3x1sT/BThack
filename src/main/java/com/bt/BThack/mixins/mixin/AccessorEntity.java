package com.bt.BThack.mixins.mixin;

import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(Entity.class)
public interface AccessorEntity {

    @Invoker("setFlag")
    void invokeSetFlag(int flag, boolean set);
}
