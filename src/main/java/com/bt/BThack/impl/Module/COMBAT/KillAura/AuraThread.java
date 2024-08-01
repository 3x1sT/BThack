package com.bt.BThack.impl.Module.COMBAT.KillAura;

import com.bt.BThack.api.Module.Module;
import com.bt.BThack.api.Utils.Interfaces.Mc;
import com.bt.BThack.api.Utils.Modules.KillAuraUtils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;

public class AuraThread extends Thread implements Mc {
    private int delay;

    public void run() {
        try {
            delay = (int) (Module.getSlider("KillAura", "Delay(Second)") * 1000);

            EntityPlayer target = KillAuraUtils.filterPlayers("KillAura");
            Entity entity = KillAuraUtils.filterEntity(Module.getSlider("KillAura", "Range"));

            if (Module.getCheckbox("KillAura", "Players") && target != null && KillAuraUtils.canBeSeeTarget("KillAura", target)) {
                attack(target);
            }


            if (Module.getCheckbox("KillAura", "Mobs")) {
                if (entity != null) {
                    if (KillAuraUtils.canBeSeeTarget("KillAura", entity) && !entity.isDead && KillAuraUtils.isCurrentMob(entity)) {
                        switch (Module.getMode("KillAura", "AttackMode")) {
                            case "CoolDown":
                                KillAuraUtils.CoolDownAttack(entity);
                                break;
                            case "Spam":
                                KillAuraUtils.SpamAttack(entity);
                                break;
                            case "Delay":
                                KillAuraUtils.DelayAttack(entity);
                                try {
                                    sleep(delay);
                                } catch (InterruptedException ignored) {
                                }
                                break;
                        }
                    }
                }
            }
        } catch (Exception ignored) {}
    }

    private void attack(EntityPlayer target) {
        if (target.getHealth() > 0) {
            switch (Module.getMode("KillAura", "AttackMode")) {
                case "CoolDown":
                    KillAuraUtils.CoolDownAttack(target);
                    break;
                case "Spam":
                    KillAuraUtils.SpamAttack(target);
                    break;
                case "Delay":
                    KillAuraUtils.DelayAttack(target);
                    try {
                        sleep(delay);
                    } catch (InterruptedException ignored) {}
                    break;
            }
        }
    }
}
