package com.bt.BThack.impl.Module.WORLD;

import com.bt.BThack.api.Managers.PlayerPacketManager;
import com.bt.BThack.api.Module.Module;
import com.bt.BThack.api.Utils.BlockUtil;
import com.bt.BThack.api.Utils.Entity.EntityUtil;
import com.bt.BThack.api.Utils.Entity.PlayerUtil;
import com.bt.BThack.impl.Events.PlayerTravelEvent;
import com.bt.BThack.impl.Module.MOVEMENT.ElytraFlight.Packet;
import net.minecraft.block.BlockDoublePlant;
import net.minecraft.block.BlockFlower;
import net.minecraft.block.BlockTallGrass;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec2f;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Keyboard;

import java.util.Comparator;

public class Lawnmower extends Module {
    private double[] lastPos;

    public Lawnmower() {
        super("Lawnmower",
                "Breaks grass and flowers.",
                Keyboard.KEY_NONE,
                Category.WORLD,
                false
        );

        addSlider("Radius", this, 4.0,1,7,false);
        addCheckbox("Flowers", this, true);
    }

    @SubscribeEvent
    public void onUpdate(TickEvent.PlayerTickEvent e) {
        if (nullCheck()) return;

        arrayListInfo = "" + getSlider(this.name, "Radius");

        double radius = getSlider(this.name, "Radius");
        BlockPos positions = BlockUtil.getSphere(PlayerUtil.getLocalPlayerPosFloored(), (float) radius, (int) radius, false, true, 0).stream()
                .filter(this::isValidBlockPos)
                .min(Comparator.comparing(p_Pos -> EntityUtil.GetDistanceOfEntityToBlock(mc.player, p_Pos)))
                .orElse(null);

        if (positions != null) {
            lastPos =  EntityUtil.calculateLookAt(positions.getX() + 0.5, positions.getY() - 0.5, positions.getZ() + 0.5, mc.player);

            Packet.Builder builder = new Packet.Builder();

            builder.rotate(new Vec2f((float) lastPos[0], (float) lastPos[1]));

            PlayerPacketManager.sendPlayerPacket(builder);
            lastRotateTick = 5;

            mc.player.swingArm(EnumHand.MAIN_HAND);
            mc.playerController.clickBlock(positions, EnumFacing.UP);
        }
    }

    private int lastRotateTick = 0;

    @SubscribeEvent
    public void onTravel(PlayerTravelEvent e) {
        if (nullCheck()) return;

        if (lastPos == null) return;

        if (lastRotateTick > 0) {

            Packet.Builder builder = new Packet.Builder();

            builder.rotate(new Vec2f((float) lastPos[0], (float) lastPos[1]));

            PlayerPacketManager.sendPlayerPacket(builder);
            lastRotateTick--;
        }
    }

    private boolean isValidBlockPos(final BlockPos p_Pos)
    {
        boolean flowers = Module.getCheckbox(this.name, "Flowers");
        IBlockState state = mc.world.getBlockState(p_Pos);

        if (state.getBlock() instanceof BlockTallGrass || state.getBlock() instanceof BlockDoublePlant)
            return true;

        return flowers && state.getBlock() instanceof BlockFlower;
    }
}
