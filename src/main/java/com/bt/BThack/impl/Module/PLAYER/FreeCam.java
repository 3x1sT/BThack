package com.bt.BThack.impl.Module.PLAYER;

import com.bt.BThack.api.Module.Module;
import com.bt.BThack.api.Utils.MathUtil;
import com.bt.BThack.impl.Events.PacketEvent;
import com.bt.BThack.impl.Events.PlayerMoveEvent;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.entity.Entity;
import net.minecraft.network.play.client.CPacketChatMessage;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.network.play.client.CPacketPlayerTryUseItem;
import net.minecraft.network.play.client.CPacketPlayerTryUseItemOnBlock;
import net.minecraft.network.play.client.CPacketUseEntity;
import net.minecraft.network.play.client.CPacketVehicleMove;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.ClientTickEvent;
import org.lwjgl.input.Keyboard;

public class FreeCam extends Module {
    public static boolean active = false;
    private Entity riding;
    private EntityOtherPlayerMP Camera;
    private Vec3d position;
    private float yaw;
    private float pitch;

    public FreeCam() {
        super("FreeCam",
                "Separates the player's camera from the body.",
                Keyboard.KEY_NONE,
                Category.PLAYER,
                false
        );

        addSlider("Speed", this, 0.8, 0.5, 2.5, false);
    }

    public void onEnable() {
        super.onEnable();
        if (!nullCheck()) {
            this.riding = null;
            if (mc.player.getRidingEntity() != null) {
                this.riding = mc.player.getRidingEntity();
                mc.player.dismountRidingEntity();
            }

            this.Camera = new EntityOtherPlayerMP(mc.world, mc.getSession().getProfile());
            this.Camera.copyLocationAndAnglesFrom(mc.player);
            this.Camera.prevRotationYaw = mc.player.rotationYaw;
            this.Camera.rotationYawHead = mc.player.rotationYawHead;
            this.Camera.inventory.copyInventory(mc.player.inventory);
            mc.world.addEntityToWorld(-69, this.Camera);
            this.position = mc.player.getPositionVector();
            this.yaw = mc.player.rotationYaw;
            this.pitch = mc.player.rotationPitch;
            mc.player.noClip = true;
        }
    }

    public void onDisable() {
        super.onDisable();
        if (!nullCheck()) {
            if (this.riding != null) {
                mc.player.startRiding(this.riding, true);
                this.riding = null;
            }

            if (this.Camera != null) {
                mc.world.removeEntity(this.Camera);
            }

            if (this.position != null) {
                mc.player.setPosition(this.position.x, this.position.y, this.position.z);
            }

            mc.player.rotationYaw = this.yaw;
            mc.player.rotationPitch = this.pitch;
            mc.player.noClip = false;
            mc.player.setVelocity(0.0D, 0.0D, 0.0D);
        }

    }

    @SubscribeEvent
    public void onPlayerMove(PlayerMoveEvent e) {
        mc.player.noClip = true;
        mc.player.collidedHorizontally = false;
        mc.player.collidedVertically = false;
        mc.player.collided = false;
    }

    @SubscribeEvent
    public void onSendPacket(PacketEvent.Send e) {
        if (nullCheck()) return;
        if (e.getPacket() instanceof CPacketUseEntity ||
                e.getPacket() instanceof CPacketPlayerTryUseItem ||
                e.getPacket() instanceof CPacketPlayerTryUseItemOnBlock ||
                e.getPacket() instanceof CPacketPlayer ||
                e.getPacket() instanceof CPacketVehicleMove ||
                e.getPacket() instanceof CPacketChatMessage
        ) {
            e.setCanceled(true);
        }

    }

    @SubscribeEvent
    public void onClientTick(ClientTickEvent e) {
        if (nullCheck()) return;

        arrayListInfo = "" + getSlider(this.name, "Speed");

        mc.player.noClip = true;
        mc.player.setVelocity(0.0D, 0.0D, 0.0D);
        double[] dir = MathUtil.directionSpeed(getSlider(this.getName(), "Speed"));
        if (mc.player.movementInput.moveStrafe == 0.0F && mc.player.movementInput.moveForward == 0.0F) {
            mc.player.motionX = 0.0D;
            mc.player.motionZ = 0.0D;
        } else {
            mc.player.motionX = dir[0];
            mc.player.motionZ = dir[1];
        }

        mc.player.setSprinting(false);
        if (mc.gameSettings.keyBindJump.isKeyDown()) {
            mc.player.motionY += getSlider(this.getName(), "Speed");
        }

        if (mc.gameSettings.keyBindSneak.isKeyDown()) {
            mc.player.motionY -= getSlider(this.getName(), "Speed");
        }

    }

    @SubscribeEvent
    public void onJoinWorld(EntityJoinWorldEvent e) {
        if (e.getEntity() == mc.player) {
            this.toggle();
        }

    }
}
