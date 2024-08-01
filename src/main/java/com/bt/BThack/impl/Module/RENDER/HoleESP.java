package com.bt.BThack.impl.Module.RENDER;

import com.bt.BThack.api.Module.Module;
import com.bt.BThack.api.Utils.Entity.BlockUtil;
import com.bt.BThack.api.Utils.HoleUtil;
import com.bt.BThack.api.Utils.Render.RenderUtils;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.input.Keyboard;

import java.awt.*;
import java.util.List;
import java.util.stream.Collectors;

public class HoleESP extends Module {

    public HoleESP() {
        super("HoleESP",
                "Highlights safe holes for crystal pvp.",
                Keyboard.KEY_NONE,
                Category.RENDER,
                false
        );

        addSlider("Range", this, 5, 1, 10, false);
        addSlider("Box Height", this, 0.2, 0.1, 1, false);

        addCheckbox("Obsidian Holes", this, true);
        addSlider("Obsidian Holes", true, "Obsidian Holes Red", this, 61, 0, 255, true);
        addSlider("Obsidian Holes", true, "Obsidian Holes Green", this, 194, 0, 255, true);
        addSlider("Obsidian Holes", true, "Obsidian Holes Blue", this, 46, 0, 255, true);

        addCheckbox("Bedrock Holes", this, true);
        addSlider("Bedrock Holes", true, "Bedrock Holes Red", this, 61, 0, 255, true);
        addSlider("Bedrock Holes", true, "Bedrock Holes Green", this, 194, 0, 255, true);
        addSlider("Bedrock Holes", true, "Bedrock Holes Blue", this, 46, 0, 255, true);
    }

    public List<BlockPos> findObsidianHoles() {
        return BlockUtil.getNearbyBlocks(mc.player, getSlider(this.name, "Range"), false).stream()
                .filter(HoleUtil::isMutableHole)
                .collect(Collectors.toList());
    }

    public List<BlockPos> findBedrockHoles() {
        return BlockUtil.getNearbyBlocks(mc.player, getSlider(this.name, "Range"), false).stream()
                .filter(HoleUtil::isBedrockHole)
                .collect(Collectors.toList());
    }

    @SubscribeEvent
    public void onRenderWorldLast(RenderWorldLastEvent event) {
        if (nullCheck()) return;

        List<BlockPos> obsidianHoles = findObsidianHoles();
        List<BlockPos> bedrockHoles = findBedrockHoles();

        if (getCheckbox(this.name, "Obsidian Holes") && obsidianHoles != null) {

            for (BlockPos obsidianHole : findObsidianHoles()) {
                RenderUtils.drawBox(obsidianHole, getSlider(this.name, "Box Height"), new Color((int) getSlider(this.name, "Obsidian Holes Red"), (int) getSlider(this.name, "Obsidian Holes Green"), (int) getSlider(this.name, "Obsidian Holes Blue"), 200), new Color((int) getSlider(this.name, "Obsidian Holes Red"), (int) getSlider(this.name, "Obsidian Holes Green"), (int) getSlider(this.name, "Obsidian Holes Blue"), 120));
            }
        }

        if (getCheckbox(this.name, "Bedrock Holes") && bedrockHoles != null) {
            for (BlockPos bedrockHole : findBedrockHoles()) {
                RenderUtils.drawBox(bedrockHole, getSlider(this.name, "Box Height"), new Color((int) getSlider(this.name, "Bedrock Holes Red"), (int) getSlider(this.name, "Bedrock Holes Green"), (int) getSlider(this.name, "Bedrock Holes Blue"), 200), new Color((int) getSlider(this.name, "Bedrock Holes Red"), (int) getSlider(this.name, "Bedrock Holes Green"), (int) getSlider(this.name, "Bedrock Holes Blue"), 120));
            }
        }
    }
}
