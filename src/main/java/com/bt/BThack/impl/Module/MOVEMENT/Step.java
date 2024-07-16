package com.bt.BThack.impl.Module.MOVEMENT;

import com.bt.BThack.api.Module.Module;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Keyboard;

import java.util.ArrayList;

public class Step extends Module {
    public Step() {
        super("Step",
                "Allows you to step up blocks.",
                Keyboard.KEY_NONE,
                Category.MOVEMENT,
                false
        );

        ArrayList<String> stepOptions = new ArrayList<>();
        stepOptions.add("Vanilla");
        stepOptions.add("Packet");

        ArrayList<String> packetOptions = new ArrayList<>();
        packetOptions.add("One");
        packetOptions.add("Two");


        addMode("Step Mode", this, stepOptions);
        addMode("Step Mode", "Packet","Packet Mode", this, packetOptions);

        addSlider("Step Mode", "Vanilla","Vanilla Step Max", this, 4, 1, 10, true);

        addCheckbox("Allow Reverse", this, true);
    }

    private final double[] oneBlockOffset = {0.42, 0.753};
    private final double[] twoBlockOffset = {0.42, 0.78, 0.63, 0.51, 0.9, 1.21, 1.45, 1.43};


    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent e) {
        if (nullCheck()) return;

        if (mc.player.onGround && mc.player.collidedHorizontally && !mc.player.isInWater() && !mc.player.isInLava() && !mc.player.isOnLadder()) {
            switch (getMode(this.name, "Step Mode")) {
                case "Packet":
                    switch (getMode(this.name, "Packet Mode")) {
                        case "One":
                            for (double value : oneBlockOffset) {
                                mc.player.connection.sendPacket(new CPacketPlayer.Position(mc.player.posX, mc.player.posY + value, mc.player.posZ, mc.player.onGround));
                            }

                            mc.player.setPosition(mc.player.posX, mc.player.posY + 1.0, mc.player.posZ);
                            break;
                        case "Two":
                            for (double value : twoBlockOffset) {
                                mc.player.connection.sendPacket(new CPacketPlayer.Position(mc.player.posX, mc.player.posY + value, mc.player.posZ, mc.player.onGround));
                            }

                            mc.player.setPosition(mc.player.posX, mc.player.posY + 2.0, mc.player.posZ);
                            break;
                    }
                    break;
                case "Vanilla":
                    byte stepHeight = (byte) getSlider(this.name, "Vanilla Step Max");
                    if (mc.player.stepHeight != stepHeight) {
                        mc.player.stepHeight = stepHeight;
                    }
            }
        }

        if (getCheckbox(this.name, "Allow Reverse")) {
            if (!mc.player.collidedVertically && !mc.player.isInWater() && !mc.player.isInLava() && !mc.player.isOnLadder()) {
                mc.player.motionY--;
            }
        }
    }

    @Override
    public void onDisable() {
        super.onDisable();

        if (nullCheck()) return;

        mc.player.stepHeight = 0.5f;
    }
}
