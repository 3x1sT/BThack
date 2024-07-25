package com.bt.BThack.impl.Module.COMBAT;

import com.bt.BThack.api.Module.Module;
import com.bt.BThack.api.Social.Clans.ClansUtils;
import com.bt.BThack.api.Utils.Modules.AimBotUtils;
import com.bt.BThack.api.Utils.Modules.KillAuraUtils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Keyboard;

public class AimBot extends Module {

    public AimBot() {
        super(
                "AimBot",
                "Automatic entity targeting.",
                Keyboard.KEY_NONE,
                Category.COMBAT,
                false
        );

        addSlider("Range", this, 4.0,1,5,false);
        addCheckbox("Players", this, true);
        addCheckbox("Mobs", this, true);
        addCheckbox("Teammates", this, false);
        addCheckbox("Friends", this, false);
        addCheckbox("Ignore Walls", this, false);

        ClansUtils.addClanManagerInModule(this);
    }

    @SubscribeEvent
    public void onUpdate(RenderWorldLastEvent e) {
        if (nullCheck()) return;

        rotate(this.name);
    }

    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent e) {
        if (nullCheck()) return;

        arrayListInfo = "" + getSlider(this.name, "Range");
    }

    public static void rotate(String mod) {

        EntityPlayer target = KillAuraUtils.filterPlayers(mod);

        Entity entity = KillAuraUtils.filterEntity(getSlider("AimBot", "Range"));

        if (getCheckbox(mod, "Players") && target != null && KillAuraUtils.canBeSeeTarget(mod, target)) {
            AimBotUtils.RotateToEntity(target);
        }
        if (getCheckbox(mod, "Mobs")) {
            if (entity != null && KillAuraUtils.canBeSeeTarget(mod, entity) && entity.isEntityAlive()) {
                AimBotUtils.RotateToEntity(entity);
            }
        }
    }
}
