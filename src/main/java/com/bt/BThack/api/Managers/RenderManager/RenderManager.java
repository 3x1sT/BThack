package com.bt.BThack.api.Managers.RenderManager;

import com.bt.BThack.api.Utils.Destroy.DestroyManager;
import com.bt.BThack.api.Utils.Interfaces.Mc;
import com.bt.BThack.api.Utils.Render.RenderUtils;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import java.util.HashMap;

public class RenderManager implements Mc {

    private static boolean isRendered = false;

    private static Phase currentRenderingPhase;

    private static final HashMap<BlockPos, BoxColor> needRenderedBoxes = new HashMap<>();


    public static void addRenderBox(BlockPos pos, BoxColor color) {
        if (!isRendered || !needRenderedBoxes.containsKey(pos)) {
            needRenderedBoxes.put(pos, color);
        }
    }



    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void onWorld(RenderWorldLastEvent e) {
        if (mc.player == null || mc.world == null || isRendered) return;

        isRendered = true;

        currentRenderingPhase = Phase.BOX;
        needRenderedBoxes.forEach(((blockPos, boxColor) -> {

            RenderUtils.drawBox(blockPos,
                    boxColor.lineRed, boxColor.lineGreen, boxColor.lineBlue, boxColor.lineAlpha,
                    boxColor.boxRed, boxColor.boxGreen, boxColor.boxBlue, boxColor.boxAlpha
                    );

        }));

        if (DestroyManager.isDestroying) {
            try {
                RenderUtils.drawBox(DestroyManager.currentBlockPos, 1,0,0, 1, 1,0,0, 0.5f);
            } catch (Exception ignored) {}
        }

        needRenderedBoxes.clear();
        currentRenderingPhase = Phase.NONE;


        isRendered = false;
    }


    public Phase getRenderPhase() {
        return currentRenderingPhase;
    }



    public enum Phase {
        BOX,
        NONE
    }
}
