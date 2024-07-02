package com.bt.BThack.impl.Module.RENDER;

import com.bt.BThack.api.Module.Module;
import com.bt.BThack.api.Utils.Render.RenderUtils;
import net.minecraft.entity.Entity;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.input.Keyboard;

public class AttackTrace extends Module {
    public static Entity attackEntity = null;
    public AttackTrace() {
        super("AttackTrace",
                "When a entity is attacked, his tracer turns red.",
                Keyboard.KEY_NONE,
                Category.RENDER,
                false
        );
    }

    @SubscribeEvent
    public void onRender(RenderWorldLastEvent e) {
        if (nullCheck()) return;

        if (attackEntity != null && !attackEntity.isDead) {
            if (mc.player.getDistance(attackEntity) < 20) {
            RenderUtils.trace(attackEntity, mc.getRenderPartialTicks(), 1, 0.5, 0.5, 1); }
        }
    }

    @SubscribeEvent
    public void onAttack(AttackEntityEvent e) {
        if (nullCheck()) return;

        attackEntity = e.getTarget();
    }
}
