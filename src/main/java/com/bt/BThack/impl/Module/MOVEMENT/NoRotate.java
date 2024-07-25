package com.bt.BThack.impl.Module.MOVEMENT;

import com.bt.BThack.api.Module.Module;
import com.bt.BThack.api.Utils.Modules.NoRotateMathUtil;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Keyboard;

public class NoRotate extends Module {
    public NoRotate() {
        super("NoRotate",
                "Locks the camera along the nearest axis.",
                Keyboard.KEY_NONE,
                Category.MOVEMENT,
                false
        );

        addCheckbox("BlockPitchRotate", this, true);
    }

    @SubscribeEvent
    public void onUpdate(TickEvent.PlayerTickEvent e) {
        if (nullCheck()) return;

        mc.player.rotationYaw = NoRotateMathUtil.RotateYawMath(mc.player);
        boolean box = getCheckbox(this.name, "BlockPitchRotate");
        if (box)
            mc.player.rotationPitch = NoRotateMathUtil.RotatePitchMath(mc.player);
    }
}
