package com.bt.BThack.impl.Module.MOVEMENT.ElytraFlight;

import com.bt.BThack.BThack;
import com.bt.BThack.System.Client;
import com.bt.BThack.api.Managers.PlayerPacketManager;
import com.bt.BThack.api.Module.Module;
import com.bt.BThack.api.Utils.ChatUtils;
import com.bt.BThack.api.Utils.Entity.PlayerUtil;
import com.bt.BThack.api.Utils.WorldUtils;
import com.bt.BThack.impl.Events.PacketEvent;
import com.bt.BThack.impl.Events.PlayerTravelEvent;
import com.bt.BThack.mixins.mixin.AccessorEntity;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.CPacketEntityAction;
import net.minecraft.network.play.server.SPacketPlayerPosLook;
import net.minecraft.util.math.Vec2f;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Keyboard;

import java.util.ArrayList;

public class ElytraFlight extends Module {
    private final String chatName = "[ElytraFlight] ";

    public ElytraFlight() {
        super("ElytraFlight",
                "Allows infinite and way easier Elytra flying",
                Keyboard.KEY_NONE,
                Category.MOVEMENT,
                false
        );

        ArrayList<String> page = new ArrayList<>();
        page.add("Generic");
        page.add("Extra");

        addMode("Page", this, page);

        //Generic Settings
        addCheckbox("Page", "Generic","Durability Warning", this, true);
        addSlider("Page", "Generic","Warning Threshold", this, 5, 1, 50, true);
        addCheckbox("Page", "Generic","Auto Landing", this, false);

        addCheckbox("Page", "Generic","Easy Takeoff", this, true);
        addCheckbox("Page", "Generic","Takeoff Timer", this, true);
        addCheckbox("Page", "Generic","High Ping Optimize", this, false);
        addSlider("Page", "Generic","Min Takeoff Height", this, 0.5f, 0.0f, 1.5f, false);

        addSlider("Page", "Generic","Start Speed", this, 40, 0, 100, true);
        addSlider("Page", "Generic","Accelerate Time", this, 0.0f, 0.0f, 20.0f, false);

        addCheckbox("Page", "Generic","Spoof Pitch", this, true);
        addCheckbox("Page", "Generic","Block Interact", this, false);
        addSlider("Page", "Generic","Forward Pitch", this, -30, -90, 90, true);

        addCheckbox("Page", "Generic","Elytra Sounds", this, true);
        addSlider("Page", "Generic","Swing Speed", this, 0.2, 0.0f, 2.0f, false);
        addSlider("Page", "Generic","Swing Amount", this, 0.2, 0.0f, 2.0f, false);
        //////////

        //Extra Settings
        addSlider("Page", "Extra","Base Boost Pitch", this, 20, 0, 90, true);
        addCheckbox("Page", "Extra","NCP Strict", this, true);
        addCheckbox("Page", "Extra","Legacy Look Boost", this, false);
        addCheckbox("Page", "Extra","Auto Control Altitude", this, false);
        addCheckbox("Page", "Extra","Dynamic Down Speed", this, false);
        addSlider("Page", "Extra","Speed C", this, 1.75, 0.0f, 2.5f, false);
        addSlider("Page", "Extra","Fall Speed C", this, 0.0, 0.0f, 0.3f, false);
        addSlider("Page", "Extra","Down Speed C", this, 1.0f, 1.0f, 5.0f, false);
        addSlider("Page", "Extra","Dynamic Down Speed C", this, 2.0f, 1.0f, 5.0f, false);
    }


    /* Control mode states */
    private static double hoverTarget = -1.0;
    private static float packetYaw = 0.0f;
    public static float packetPitch = 0.0f;
    private static boolean hoverState = false;
    private static int boostingTick = 0;

    /* Generic states */
    private boolean elytraIsEquipped = false;
    private int elytraDurability = 0;
    private boolean outOfDurability = false;
    private boolean wasInLiquid = false;
    public static boolean isFlying = false;
    private boolean isStandingStillH = false;
    private boolean isStandingStill = false;
    private float speedPercentage = 0.0f;

    @Override
    public void onEnable() {
        super.onEnable();

        if (Client.getModuleByName("NewElytraFlight").isEnabled()) {
            Client.getModuleByName("NewElytraFlight").setToggled(false);
        }

        Client.getModuleByName("ElytraBoost").setToggled(false);

        BThack.instance.settingsManager.getModuleSettingByName(this.name, "Auto Landing").setValBoolean(false);
        speedPercentage = (float) getSlider(this.name, "Start Speed"); /* For acceleration */
        hoverTarget = -1.0; /* For control mode */
    }

    @Override
    public void onDisable() {
        super.onDisable();

        if (nullCheck()) return;

        reset(true);
    }


    @SubscribeEvent
    public void onPacketReceive(PacketEvent.Receive event) {
        if (mc.player.isSpectator() || !elytraIsEquipped || elytraDurability <= 1 || !isFlying) return;

        if (event.getPacket() instanceof SPacketPlayerPosLook) {
            SPacketPlayerPosLook packet = (SPacketPlayerPosLook) event.getPacket();
            packet.pitch = mc.player.rotationPitch;
        }
    }

    @SubscribeEvent
    public void onPlayerTravel(PlayerTravelEvent event) {
        if (mc.player.isSpectator()) return;
        stateUpdate(event);
        if (elytraIsEquipped && elytraDurability > 1) {
            if (getCheckbox(this.name, "Auto Landing")) {
                landing(event);
                return;
            }
            if (!isFlying) {
                takeoff(event);
            } else {
                mc.timer.tickLength = 50.0f;
                ((AccessorEntity)mc.player).invokeSetFlag(3, false);
                controlMode(event);
            }
            spoofRotation();
        } else if (!outOfDurability) {
            reset(true);
        }
    }





    private void stateUpdate(PlayerTravelEvent event) {
        /* Elytra Check */
        ItemStack armorSlot = mc.player.inventory.armorInventory.get(2);
        elytraIsEquipped = armorSlot.getItem() == Items.ELYTRA;

        /* Elytra Durability Check */
        if (elytraIsEquipped) {
            int oldDurability = elytraDurability;
            elytraDurability = armorSlot.getMaxDamage() - armorSlot.getItemDamage();

            /* Elytra Durability Warning, runs when player is in the air and durability changed */
            if (!mc.player.onGround && oldDurability != elytraDurability) {
                if (getCheckbox(this.name, "Durability Warning") && elytraDurability > 1 && elytraDurability < getSlider(this.name, "Warning Threshold") * armorSlot.getMaxDamage() / 100) {
                    mc.getSoundHandler().playSound(PositionedSoundRecord.getRecord(SoundEvents.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0f, 1.0f));
                    ChatUtils.sendMessage(chatName + " Warning: Elytra has " + (elytraDurability - 1) + " durability remaining");
                } else if (elytraDurability <= 1 && !outOfDurability) {
                    outOfDurability = true;
                    if (getCheckbox(this.name, "Durability Warning")) {
                        mc.getSoundHandler().playSound(PositionedSoundRecord.getRecord(SoundEvents.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0f, 1.0f));
                        ChatUtils.sendMessage(chatName + " Elytra is out of durability, holding player in the air");
                    }
                }
            }
        } else {
            elytraDurability = 0;
        }

        /* Holds player in the air if run out of durability */
        if (!mc.player.onGround && elytraDurability <= 1 && outOfDurability) {
            holdPlayer(event);
        } else if (outOfDurability) {
            outOfDurability = false; /* Reset if player is on the ground or replace with a new elytra */
        }

        /* wasInLiquid check */
        if (mc.player.isInWater() || mc.player.isInLava()) {
            wasInLiquid = true;
        } else if (mc.player.onGround || isFlying) {
            wasInLiquid = false;
        }

        /* Elytra flying status check */
        isFlying = mc.player.isElytraFlying();

        /* Movement input check */
        isStandingStillH = mc.player.movementInput.moveForward == 0f && mc.player.movementInput.moveStrafe == 0f;
        isStandingStill = isStandingStillH && !mc.player.movementInput.jump && !mc.player.movementInput.sneak;

        /* Reset acceleration */
        if (!isFlying || isStandingStill) {
            speedPercentage = (float) getSlider(this.name, "Start Speed");
        }

        /* Modify leg swing */
        if (shouldSwing()) {
            mc.player.prevLimbSwingAmount = mc.player.limbSwingAmount;
            mc.player.limbSwing += (float) getSlider(this.name, "Swing Speed");
            float speedRatio = (mc.player.getAIMoveSpeed() / (float) getSettingSpeed());
            mc.player.limbSwingAmount += ((speedRatio * (float) getSlider(this.name, "Swing Amount")) - mc.player.limbSwingAmount) * 0.4f;
        }
    }

    private void reset(boolean cancelFlying) {
        wasInLiquid = false;
        isFlying = false;
        //isPacketFlying = false;
        mc.timer.tickLength = 50.0f;
        mc.player.capabilities.setFlySpeed(0.05f);
        if (cancelFlying)
            mc.player.capabilities.isFlying = false;
    }

    /* Holds player in the air */
    private void holdPlayer(PlayerTravelEvent event) {
        event.setCanceled(true);
        mc.timer.tickLength = 50.0f;
        mc.player.setVelocity(0.0, -0.01, 0.0);
    }

    /* Auto landing */
    private void landing(PlayerTravelEvent event) {
        if (mc.player.onGround) {
            ChatUtils.sendMessage(chatName + " Landed!");
            BThack.instance.settingsManager.getModuleSettingByName(this.name, "Auto Landing").setValBoolean(false);
            return;
        } else if (WorldUtils.isLiquidBelow(mc.world, mc.player)) {
            ChatUtils.sendMessage(chatName + " Liquid below, disabling.");
            BThack.instance.settingsManager.getModuleSettingByName(this.name, "Auto Landing").setValBoolean(false);
        } else if (mc.player.capabilities.isFlying || !mc.player.isElytraFlying()) { //|| isPacketFlying) {
            reset(true);
            takeoff(event);
            return;
        } else {
            double groundY = WorldUtils.getGroundPos(mc.world, mc.player).y;
            if (mc.player.posY > groundY + 1.0) {
                mc.timer.tickLength = 50.0f;
                mc.player.motionY = Math.max(Math.min(-(mc.player.posY - groundY) / 20.0, -0.5), -5.0);
            } else if (mc.player.motionY != 0.0) {
                /* Pause falling to reset fall distance */
                if (!mc.isSingleplayer()) mc.timer.tickLength = 200.0f; /* Use timer to pause longer */
                mc.player.motionY = 0.0;
            } else {
                mc.player.motionY = -0.2;
            }
        }
        mc.player.setVelocity(0.0, mc.player.motionY, 0.0); /* Kills horizontal motion */
        event.setCanceled(true);
    }

    /* The best takeoff method <3 */
    private void takeoff(PlayerTravelEvent event) {
        /* Pause Takeoff if server is lagging, player is in water/lava, or player is on ground */
        float timerSpeed = getCheckbox(this.name, "High Ping Optimize") ? 400.0f : 200.0f;
        float height = getCheckbox(this.name, "High Ping Optimize") ? 0.0f : (float) getSlider(this.name, "Min Takeoff Height");
        boolean closeToGround = mc.player.posY <= WorldUtils.getGroundPos(mc.world, mc.player).y + height && !wasInLiquid && !mc.isSingleplayer();

        if (!getCheckbox(this.name, "Easy Takeoff") || mc.player.onGround) {
            reset(mc.player.onGround);
            return;
        }

        if (mc.player.motionY < 0 && !getCheckbox(this.name, "High Ping Optimize") || mc.player.motionY < -0.02) {
            if (closeToGround) {
                mc.timer.tickLength = 25.0f;
                return;
            }

            if (!getCheckbox(this.name, "High Ping Optimize") && !wasInLiquid && !mc.isSingleplayer()) { /* Cringe moment when you use elytra flight in single player world */
                event.setCanceled(true);
                mc.player.setVelocity(0.0, -0.02, 0.0);
            }

            if (getCheckbox(this.name, "Takeoff Timer") && !mc.isSingleplayer()) mc.timer.tickLength = timerSpeed * 2.0f;
            mc.player.connection.sendPacket(new CPacketEntityAction(mc.player, CPacketEntityAction.Action.START_FALL_FLYING));
            hoverTarget = mc.player.posY + 0.2;
        } else if (getCheckbox(this.name, "High Ping Optimize") && !closeToGround) {
            mc.timer.tickLength = timerSpeed;
        }
    }

    /**
     *  Calculate yaw for control and packet mode
     *
     *  @return Yaw in radians based on player rotation yaw and movement input
     */
    private double getYaw() {
        double yawRad = PlayerUtil.calcMoveYaw();
        packetYaw = (float) Math.toDegrees(yawRad);
        return yawRad;
    }

    /**
     * Calculate a speed with a non linear acceleration over time
     *
     * @return boostingSpeed if boosting is true, else return an accelerated speed.
     */
    private double getSpeed(boolean boosting) {
        if (boosting) {
            return getCheckbox(this.name, "NCP Strict") ? Math.min( getSlider(this.name, "Speed C"), 2.0f) : getSlider(this.name, "Speed C");
        } else if (getSlider(this.name, "Accelerate Time") != 0.0f && getSlider(this.name, "Start Speed") != 100) {
            speedPercentage = Math.min(speedPercentage + (100.0f - (float) getSlider(this.name, "Start Speed")) / ((float) getSlider(this.name, "Accelerate Time") * 20.0f), 100.0f);
            double speedMultiplier = speedPercentage / 100.0;

            return getSettingSpeed() * speedMultiplier * (Math.cos(speedMultiplier * Math.PI) * -0.5 + 0.5);
        } else {
            return getSettingSpeed();
        }
    }

    private double getSettingSpeed() {
        return getSlider(this.name, "Speed C");
    }

    private void setSpeed(double yaw, boolean boosting) {
        double acceleratedSpeed = getSpeed(boosting);
        mc.player.setVelocity(Math.sin(-yaw) * acceleratedSpeed, mc.player.motionY, Math.cos(yaw) * acceleratedSpeed);
    }
    /* End of Generic Functions */


    /* Control Mode */
    private void controlMode(PlayerTravelEvent event) {
        /* States and movement input */
        double currentSpeed = Math.sqrt(mc.player.motionX * mc.player.motionX + mc.player.motionZ * mc.player.motionZ);
        boolean moveUp;
        if (!getCheckbox(this.name, "Legacy Look Boost")) {
            moveUp = mc.player.movementInput.jump;
        } else {
            moveUp = mc.player.rotationPitch < -10.0f && !isStandingStillH;
        }

        boolean moveDown;
        if (Client.getModuleByName("GuiMove").isEnabled() && mc.currentScreen != null || moveUp) {
            moveDown = false;
        } else {
            moveDown = mc.player.movementInput.sneak;
        }

        /* Dynamic down speed */
        double calcDownSpeed;
        if (getCheckbox(this.name, "Dynamic Down Speed")) {
            double minDownSpeed = Math.min(getSlider(this.name, "Down Speed C"), getSlider(this.name, "Dynamic Down Speed C"));
            double maxDownSpeed = Math.max(getSlider(this.name, "Down Speed C"), getSlider(this.name, "Dynamic Down Speed C"));
            if (mc.player.rotationPitch > 0) {
                calcDownSpeed = mc.player.rotationPitch / 90.0 * (maxDownSpeed - minDownSpeed) + minDownSpeed;
            } else calcDownSpeed = minDownSpeed;
        } else
            calcDownSpeed = getSlider(this.name, "Down Speed C");

        /* Hover */
        if (hoverTarget < 0.0 || moveUp) {
            hoverTarget = mc.player.posY;
        } else if (moveDown) {
            hoverTarget = mc.player.posY - calcDownSpeed;
        }
        hoverState = hoverState ? mc.player.posY < hoverTarget : mc.player.posY < hoverTarget - 0.1
                && getCheckbox(this.name, "Auto Control Altitude");

        /* Set velocity */
        if (!isStandingStillH || moveUp) {
            if ((moveUp || hoverState) && (currentSpeed >= 0.8 || mc.player.motionY > 1.0)) {
                upwardFlight(currentSpeed, getYaw());
            } else { /* Runs when pressing wasd */
                packetPitch = (float) getSlider(this.name, "Forward Pitch");
                mc.player.motionY = getSlider(this.name, "Fall Speed C");
                setSpeed(getYaw(), moveUp);
                boostingTick = 0;
            }
        } else mc.player.setVelocity(0.0, 0.0, 0.0); /* Stop moving if no inputs are pressed */

        if (moveDown) mc.player.motionY = -calcDownSpeed; /* Runs when holding shift */

        event.setCanceled(true);
    }

    private void upwardFlight(double currentSpeed, double yaw) {
        double multipliedSpeed = 0.128 * Math.min(getSlider(this.name, "Speed C"), 2.0f);
        float strictPitch = (float) Math.toDegrees(Math.asin((multipliedSpeed - Math.sqrt(multipliedSpeed * multipliedSpeed - 0.0348)) / 0.12));
        float basePitch = getCheckbox(this.name, "NCP Strict") && strictPitch < getSlider(this.name, "Base Boost Pitch") && !Float.isNaN(strictPitch) ? -strictPitch
                : (float) -getSlider(this.name, "Base Boost Pitch");
        float targetPitch = mc.player.rotationPitch < 0.0f ?
                Math.max(mc.player.rotationPitch * (90.0f - (float) getSlider(this.name, "Base Boost Pitch")) / 90.0f - (float) getSlider(this.name, "Base Boost Pitch"), -90.0f) :
                (float) -getSlider(this.name, "Base Boost Pitch");

        if (packetPitch <= basePitch && boostingTick > 2) {
            if (packetPitch < targetPitch) packetPitch += 17.0f;
            if (packetPitch > targetPitch) packetPitch -= 17.0f;
            packetPitch = Math.max(packetPitch, targetPitch);
        } else {
            packetPitch = basePitch;
        }
        boostingTick++;

        /* These are actually the original Minecraft elytra fly code lol */
        double pitch = Math.toRadians(packetPitch);
        double targetMotionX = Math.sin(-yaw) * Math.sin(-pitch);
        double targetMotionZ = Math.cos(yaw) * Math.sin(-pitch);
        double targetSpeed = Math.sqrt(targetMotionX * targetMotionX + targetMotionZ * targetMotionZ);
        double upSpeed = currentSpeed * Math.sin(-pitch) * 0.04;
        double fallSpeed = Math.cos(pitch) * Math.cos(pitch) * 0.06 - 0.08;

        mc.player.motionX -= upSpeed * targetMotionX / targetSpeed - (targetMotionX / targetSpeed * currentSpeed - mc.player.motionX) * 0.1;
        mc.player.motionY += upSpeed * 3.2 + fallSpeed;
        mc.player.motionZ -= upSpeed * targetMotionZ / targetSpeed - (targetMotionZ / targetSpeed * currentSpeed - mc.player.motionZ) * 0.1;

        /* Passive motion loss */
        mc.player.motionX *= 0.99;
        mc.player.motionY *= 0.98;
        mc.player.motionZ *= 0.99;
    }
    /* End of Control Mode */

    public boolean shouldSwing() {
        return isEnabled()
                && isFlying
                && !getCheckbox(this.name, "Auto Landing");
    }

    private void spoofRotation() {
        if (mc.player.isSpectator() || !elytraIsEquipped || elytraDurability <= 1 || !isFlying)
            return;

        boolean cancelRotation = false;
        Vec2f rotation = new Vec2f(mc.player.rotationYaw, mc.player.rotationPitch);

        if (getCheckbox(this.name, "Auto Landing")) {
            rotation = new Vec2f(rotation.x, -20f);
        } else  {
            if (!isStandingStill) {
                rotation = new Vec2f(packetYaw, rotation.y);
            }
            if (getCheckbox(this.name, "Spoof Pitch")) {
                if (!isStandingStill)
                    rotation = new Vec2f(rotation.x, packetPitch);

                /* Cancels rotation packets if player is not moving and not clicking */
                cancelRotation = isStandingStill && ((!mc.gameSettings.keyBindUseItem.isKeyDown() && !mc.gameSettings.keyBindAttack.isKeyDown() && getCheckbox(this.name, "Block Interact")) || !getCheckbox(this.name, "Block Interact"));
            }
        }

        Packet.Builder builder = new Packet.Builder();

        if (cancelRotation) {
            builder.cancelRotate();
        } else {
            builder.rotate(rotation);
        }
        PlayerPacketManager.sendPlayerPacket(builder);
    }

    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent e) {
        if (nullCheck()) setToggled(false);
    }
}
