package com.bt.BThack.impl.Module.PLAYER;

import com.bt.BThack.api.Module.Module;
import com.bt.BThack.api.Utils.ChatUtils;
import com.bt.BThack.api.Utils.Font.FontUtil;
import com.bt.BThack.api.Utils.TickTimer;
import com.bt.BThack.impl.Events.PacketEvent;
import com.google.common.annotations.Beta;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.network.play.server.SPacketPlayerPosLook;
import net.minecraft.util.math.Vec2f;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.common.network.FMLNetworkEvent;
import org.lwjgl.input.Keyboard;

import java.awt.*;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

@Beta
public class LagDetector extends Module {
    public static boolean rubberBandDetected = false;


    int red1 = new Color(255,0,0).hashCode();
    int red2 = new Color(255,150,150).hashCode();
    int red = 0;
    int changeColorTimeout = 0;

    private String lagText = "";
    long timeoutMillis = (long)(3 * 1000.0f);

    private final TickTimer lastPacketTimer = new TickTimer(TickTimer.TimeUnit.MILLISECONDS);
    public static final TickTimer lastRubberBandTimer = new TickTimer(TickTimer.TimeUnit.MILLISECONDS);


    public LagDetector() {
        super("LagDetector",
                "Informs you of various server lags.",
                Keyboard.KEY_NONE,
                Category.PLAYER,
                false
        );
    }


    @SubscribeEvent
    public void onOverlay(RenderGameOverlayEvent e) {
        if (e.getType() == RenderGameOverlayEvent.ElementType.TEXT && !mc.isSingleplayer()) {
            if (lagText.isEmpty())
                return;

            ScaledResolution sc = new ScaledResolution(mc);

            FontUtil.drawText(lagText, (sc.getScaledWidth() / 2f) - (FontUtil.getStringWidth(lagText) / 2), (sc.getScaledHeight() / 2f) + 20, red);
        }
    }

    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent e) {
        if (nullCheck()) return;
        if (mc.isSingleplayer()) return;

        if (lastPacketTimer.tick(timeoutMillis, false)) {
            if (isOffline()) {
                lagText = "Your internet is offline!";
            } else {
                lagText = "Server Not Responding!";
            }
        } else {
            if (!lagText.isEmpty()) lagText = "";
        }
        if (!lastRubberBandTimer.tick(50, false)) {
            if (!rubberBandDetected)
                rubberBandDetected = true;

            ChatUtils.sendMessage("RubberBand Detected!");
        }

        if (changeColorTimeout >= 45) {
            red = red == red1 ? red2 : red1;
            changeColorTimeout = 0;
        } else changeColorTimeout++;
    }

    @SubscribeEvent
    public void onReceivePackets(PacketEvent.Receive e) {
        lastPacketTimer.reset(0);

        if (!rubberBandDetected || !(e.getPacket() instanceof SPacketPlayerPosLook) || mc.player.ticksExisted < 20)
            return;

        SPacketPlayerPosLook packet = (SPacketPlayerPosLook) e.getPacket();

        double dist = new Vec3d(packet.x, packet.y, packet.z).subtract(mc.player.getPositionVector()).length();
        Vec2f rotVec =  new Vec2f(packet.yaw - mc.player.rotationYaw, packet.pitch - mc.player.rotationPitch);
        double rotationDiff = Math.sqrt(rotVec.x * rotVec.x + rotVec.y * rotVec.y);

        if (0.5 <= dist && dist <= 64 || rotationDiff > 1.0)
            lastRubberBandTimer.reset(0);
    }

    @SubscribeEvent
    public void onConnectToServer(FMLNetworkEvent.ClientConnectedToServerEvent e) {
        lastPacketTimer.reset(69420L);
        lastRubberBandTimer.reset(-69420L);
    }



    private long timeOut;
    private boolean lastReturn = false;

    private boolean isOffline() {
        if (timeOut != 0) {
            timeOut--;
            return lastReturn;
        }
        try (Socket socket = new Socket()) {
            socket.connect(new InetSocketAddress("1.1.1.1", 80), 100);
            lastReturn = false;
            timeOut = 20;
            return false;
        } catch (IOException e) {
            lastReturn = true;
            timeOut = 20;
            return true; // Either timeout or unreachable or failed DNS lookup.
        }
    }
}
