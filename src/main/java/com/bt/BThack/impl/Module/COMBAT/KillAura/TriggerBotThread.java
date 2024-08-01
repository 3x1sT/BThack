package com.bt.BThack.impl.Module.COMBAT.KillAura;

import com.bt.BThack.api.Module.Module;
import com.bt.BThack.api.Social.Friends.FriendList;
import com.bt.BThack.api.Utils.Interfaces.Mc;
import com.bt.BThack.api.Utils.Modules.KillAuraUtils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.RayTraceResult;

public class TriggerBotThread extends Thread implements Mc {
    private int delay;

    public void run() {
        try {
            delay = (int) (Module.getSlider("KillAura", "Delay(Second)") * 1000);

            RayTraceResult objectMouseOver = mc.objectMouseOver;

            if (objectMouseOver != null) {
                if (objectMouseOver.typeOfHit == RayTraceResult.Type.ENTITY) {
                    Entity ent = objectMouseOver.entityHit;

                    if (Module.getCheckbox("KillAura", "Players") && ent != null) {
                        if (Module.getCheckbox("KillAura", "Teammates")) {
                            attack(ent);
                        } else {
                            if (!mc.player.isOnSameTeam(ent)) {
                                attack(ent);
                            }
                        }
                    }

                    if (Module.getCheckbox("KillAura", "Mobs")) {
                        if (KillAuraUtils.isCurrentMob(ent) && ent.isEntityAlive()) {
                            switch (Module.getMode("KillAura", "AttackMode")) {
                                case "CoolDown":
                                    KillAuraUtils.CoolDownAttack(ent);
                                    break;
                                case "Spam":
                                    KillAuraUtils.SpamAttack(ent);
                                    break;
                                case "Delay":
                                    KillAuraUtils.DelayAttack(ent);
                                    try {
                                        sleep(delay);
                                    } catch (InterruptedException ignored) {
                                    }
                                    break;
                            }
                        }
                    }
                }
            }
        } catch (Exception ignored) {}
    }

    private void attack(Entity ent) {
        if (Module.getCheckbox("KillAura", "Friends")) {
            if (ent instanceof EntityPlayer && ((EntityPlayer) ent).getHealth() > 0) {
                switch (Module.getMode("KillAura", "AttackMode")) {
                    case "CoolDown":
                        KillAuraUtils.CoolDownAttack(ent);
                        break;
                    case "Spam":
                        KillAuraUtils.SpamAttack(ent);
                        break;
                    case "Delay":
                        KillAuraUtils.DelayAttack(ent);
                        try {
                            sleep(delay);
                        } catch (InterruptedException ignored) {}
                        break;
                }
            }
        } else {
            if (ent instanceof EntityPlayer && ((EntityPlayer) ent).getHealth() > 0) {
                String name = ((EntityPlayer) ent).getDisplayNameString();

                if (!FriendList.Friends.contains(name)) {
                    switch (Module.getMode("KillAura", "AttackMode")) {
                        case "CoolDown":
                            KillAuraUtils.CoolDownAttack(ent);
                            break;
                        case "Spam":
                            KillAuraUtils.SpamAttack(ent);
                            break;
                        case "Delay":
                            KillAuraUtils.DelayAttack(ent);
                            try {
                                sleep(delay);
                            } catch (InterruptedException ignored) {}
                            break;
                    }
                }
            }
        }
    }
}
