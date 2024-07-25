package com.bt.BThack.impl.Module.WORLD;

import com.bt.BThack.api.Module.Module;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import org.lwjgl.input.Keyboard;

public class BlockReach extends Module {
    public BlockReach() {
        super("BlockReach",
                "Increases the player's reach range.",
                Keyboard.KEY_NONE,
                Category.WORLD,
                false
        );

        addSlider("Range", this, 0.5,0.1,4,false);
    }

    @Override
    public void onEnable() {
        if (nullCheck()) return;

        double range = getSlider(this.name, "Range");
        EntityPlayer player = mc.player;
        player.getEntityAttribute(EntityPlayer.REACH_DISTANCE).applyModifier(new AttributeModifier(player.getUniqueID(), "custom_reach", range, 1));
    }

    @Override
    public void onDisable() {
        if (nullCheck()) return;

        mc.player.getEntityAttribute(EntityPlayer.REACH_DISTANCE).removeModifier(mc.player.getUniqueID());
    }
}
