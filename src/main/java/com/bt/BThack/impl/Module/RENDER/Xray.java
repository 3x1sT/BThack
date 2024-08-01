package com.bt.BThack.impl.Module.RENDER;

import com.bt.BThack.api.Module.Module;
import net.minecraft.block.Block;
import org.lwjgl.input.Keyboard;

import java.util.HashSet;

/**
 * @see com.bt.BThack.mixins.mixin.MixinVisGraph
 * @see com.bt.BThack.mixins.mixin.MixinBlockModelRenderer
 * @see com.bt.BThack.mixins.mixin.MixinBlock
 */

public class Xray extends Module {
    public static boolean doXray;
    public static HashSet<Block> xrayBlocks = new HashSet<>();



    public Xray() {
        super("Xray",
                "Allows you to cancel the rendering of blocks.",
                Keyboard.KEY_NONE,
                Category.RENDER,
                false
        );
    }

    @Override
    public void onEnable() {
        if (nullCheck()) return;

        doXray = true;

        mc.renderGlobal.loadRenderers();
    }

    @Override
    public void onDisable() {
        if (nullCheck()) return;

        doXray = false;

        mc.renderGlobal.loadRenderers();
    }
}
