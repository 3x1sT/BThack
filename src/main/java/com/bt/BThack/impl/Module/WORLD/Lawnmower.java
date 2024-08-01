package com.bt.BThack.impl.Module.WORLD;

import com.bt.BThack.api.Managers.PlayerPacketManager;
import com.bt.BThack.api.Module.Module;
import com.bt.BThack.api.Utils.BlockInteractionHelper;
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
        BlockPos l_ClosestPos = BlockInteractionHelper.getSphere(PlayerUtil.GetLocalPlayerPosFloored(), (float) radius, (int) radius, false, true, 0).stream()
                .filter(this::IsValidBlockPos)
                .min(Comparator.comparing(p_Pos -> EntityUtil.GetDistanceOfEntityToBlock(mc.player, p_Pos)))
                .orElse(null);
        if (l_ClosestPos != null)
        {
            lastPos =  EntityUtil.calculateLookAt(
                    l_ClosestPos.getX() + 0.5,
                    l_ClosestPos.getY() - 0.5,
                    l_ClosestPos.getZ() + 0.5,
                    mc.player);

            Packet.Builder builder = new Packet.Builder();

            builder.rotate(new Vec2f((float) lastPos[0], (float) lastPos[1]));

            PlayerPacketManager.sendPlayerPacket(builder);
            lastRotateTick = 5;

            mc.player.swingArm(EnumHand.MAIN_HAND);
            mc.playerController.clickBlock(l_ClosestPos, EnumFacing.UP);
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

    private boolean IsValidBlockPos(final BlockPos p_Pos)
    {
        boolean flowers = Module.getCheckbox(this.name, "Flowers");
        IBlockState l_State = mc.world.getBlockState(p_Pos);

        if (l_State.getBlock() instanceof BlockTallGrass || l_State.getBlock() instanceof BlockDoublePlant)
            return true;

        if (flowers && l_State.getBlock() instanceof BlockFlower)
            return true;

        return false;
    }
}
