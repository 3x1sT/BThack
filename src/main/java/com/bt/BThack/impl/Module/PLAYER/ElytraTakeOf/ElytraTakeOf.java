package com.bt.BThack.impl.Module.PLAYER.ElytraTakeOf;

import com.bt.BThack.System.Client;
import com.bt.BThack.api.Managers.Thread.IThread;
import com.bt.BThack.api.Managers.Thread.ThreadManager;
import com.bt.BThack.api.Module.Module;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.ClickType;
import net.minecraft.network.play.client.CPacketPlayer;
import org.lwjgl.input.Keyboard;

//TODO : Rewrite in the future
public class ElytraTakeOf extends Module {

    public ElytraTakeOf() {
        super("ElytraTakeOf",
                "When enabled, changes elytra if the player is flying.",
                Keyboard.KEY_NONE,
                Category.PLAYER,
                false
        );
    }

    @Override
    public void onEnable() {
        if (nullCheck()) {
            setToggled(false);
            return;
        }

        if (mc.player.isElytraFlying()) {
            boolean reEquiped = false;
            /*
            for (int needSlot = 0; needSlot < 36; needSlot++) {
                if (mc.player.inventory.getStackInSlot(needSlot).getItem() instanceof ItemArmor) {
                    reEquipElytra(needSlot);
                    reEquiped = true;
                    break;
                }
            }

             */
            if (!reEquiped) {
                for (int needSlot = 0; needSlot < 36; needSlot++) {
                    if (mc.player.inventory.getStackInSlot(needSlot).getItem() == Items.AIR) {
                        reEquipElytra(needSlot);
                        break;
                    }
                }
            }

            if (!Client.getModuleByName("ElytraFlight").isEnabled()) {
                ThreadManager.startNewThread(new IThread() {
                    public int needItem;

                    public boolean elytraFlying;

                    @Override
                    public void start(Thread thread) {
                        if (!elytraFlying) {

                            while (!mc.player.isElytraFlying()) {
                                mc.gameSettings.keyBindJump.pressed = true;

                                try {
                                    thread.sleep(100);
                                } catch (InterruptedException e) {
                                }
                                mc.gameSettings.keyBindJump.pressed = false;

                                if (mc.player.collidedVertically)
                                    thread.stop();

                                if (mc.player.isElytraFlying())
                                    mc.player.playSound(SoundEvents.ENTITY_EXPERIENCE_ORB_PICKUP, 1, 1);
                            }
                        } else {
                            mc.player.connection.sendPacket(new CPacketPlayer(mc.player.onGround));

                            mc.playerController.windowClick(0, 6, 1, ClickType.PICKUP, mc.player);
                            try {
                                thread.sleep(70);
                            } catch (InterruptedException e) {}
                            mc.playerController.windowClick(0, needItem, 1, ClickType.PICKUP, mc.player);
                            try {
                                thread.sleep(70);
                            } catch (InterruptedException e) {}

                            mc.player.connection.sendPacket(new CPacketPlayer(mc.player.onGround));
                        }
                    }
                });
                mc.player.connection.sendPacket(new CPacketPlayer(mc.player.onGround));
            }
        }
        toggle();
    }

    /*
    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent e) {
        if (nullCheck()) return;


        Item armor = null;
        byte a = 0;
        for (ItemStack stack : mc.player.getEquipmentAndArmor()) {
            if (a == 4) {
                armor = stack.getItem();
                break;
            } else
                a++;
        }

        if (mc.player.collidedVertically) {
            if (!mc.player.isElytraFlying()) {
                if (!(armor instanceof ItemArmor)) {
                    for (int needSlot = 0; needSlot < 36; needSlot++) {
                        if (mc.player.inventory.getStackInSlot(needSlot).getItem() instanceof ItemArmor) {
                            int item;
                            if (needSlot < 9)
                                item = needSlot + 36;
                            else
                                item = needSlot;

                            mc.playerController.windowClick(0, 6, 1, ClickType.PICKUP, mc.player);
                            mc.playerController.windowClick(0, item, 1, ClickType.PICKUP, mc.player);
                            mc.playerController.windowClick(0, 6, 1, ClickType.PICKUP, mc.player);
                            mc.playerController.updateController();
                            break;
                        }
                    }
                }
            }
        } else {
            if (mc.player.fallDistance > getSlider(this.name, "Fall Distance")) {
                if (!(armor instanceof  ItemElytra)) {
                    for (int needSlot = 0; needSlot < 36; needSlot++) {
                        if (mc.player.inventory.getStackInSlot(needSlot).getItem() instanceof ItemElytra) {
                            if (!Client.getModuleByName("ElytraFlight").isEnabled()) {

                                int item;
                                if (needSlot < 9)
                                    item = needSlot + 36;
                                else
                                    item = needSlot;

                                mc.playerController.windowClick(0, 6, 1, ClickType.PICKUP, mc.player);
                                mc.playerController.windowClick(0, item, 1, ClickType.PICKUP, mc.player);
                                mc.playerController.windowClick(0, 6, 1, ClickType.PICKUP, mc.player);
                                mc.playerController.updateController();

                                ElytraTakeOfActivateElytraThread thread = new ElytraTakeOfActivateElytraThread();
                                thread.elytraFlying = false;
                                thread.start();
                                mc.player.connection.sendPacket(new CPacketPlayer(mc.player.onGround));
                                break;
                            } else {
                                int item;
                                if (needSlot < 9)
                                    item = needSlot + 36;
                                else
                                    item = needSlot;

                                ElytraTakeOfActivateElytraThread thread = new ElytraTakeOfActivateElytraThread();
                                thread.elytraFlying = true;
                                thread.needItem = item;
                                thread.start();
                            }
                        }
                    }
                } else {
                    if (!mc.player.isElytraFlying()) {
                        if (!Client.getModuleByName("ElytraFlight").isEnabled())
                            new ElytraTakeOfActivateElytraThread().start();
                    }
                }
            }
        }


    }

     */


    /*
    @SubscribeEvent
    public void onKey(InputEvent.KeyInputEvent e) {
        if (nullCheck()) return;

        if (isKeyCodePressed(this.name, "ReEquip Elytra")) {
            if (mc.player.isElytraFlying()) {
                boolean reEquiped = false;
                for (int needSlot = 0; needSlot < 36; needSlot++) {
                    if (mc.player.inventory.getStackInSlot(needSlot).getItem() instanceof ItemArmor) {
                        reEquipElytra(needSlot);
                        reEquiped = true;
                        LagDetector.rubberBandDetected = false;
                        break;
                    }
                }
                if (!reEquiped) {
                    for (int needSlot = 0; needSlot < 36; needSlot++) {
                        if (mc.player.inventory.getStackInSlot(needSlot).getItem() == Items.AIR) {
                            reEquipElytra(needSlot);
                            LagDetector.rubberBandDetected = false;
                            break;
                        }
                    }
                }

                if (!Client.getModuleByName("ElytraFlight").isEnabled()) {
                    new ElytraTakeOfActivateElytraThread().start();
                    mc.player.connection.sendPacket(new CPacketPlayer(mc.player.onGround));
                }
            }
        }
    }

     */



    public static void reEquipElytra(int needSlot) {
        ElytraTeakeOfReEquipElytraThread thread = new ElytraTeakeOfReEquipElytraThread();

        thread.needSlot = needSlot;

        thread.start();
    }
}
