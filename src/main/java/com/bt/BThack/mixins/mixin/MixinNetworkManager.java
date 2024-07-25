package com.bt.BThack.mixins.mixin;

import com.bt.BThack.System.Client;
import com.bt.BThack.impl.Events.PacketEvent;
import io.netty.channel.ChannelHandlerContext;
import java.io.IOException;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraftforge.common.MinecraftForge;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = NetworkManager.class, priority = Integer.MAX_VALUE)
public class MixinNetworkManager {
   @Inject(
      method = {"sendPacket(Lnet/minecraft/network/Packet;)V"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private void onSendPacket(Packet packet, CallbackInfo callbackInfo) {
      PacketEvent packetEvent = new PacketEvent.Send(packet);
      MinecraftForge.EVENT_BUS.post(packetEvent);
      if (packetEvent.isCanceled()) {
         callbackInfo.cancel();
      }

   }

   @Inject(
      method = {"channelRead0"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private void onChannelRead(ChannelHandlerContext channelHandlerContext, Packet packet, CallbackInfo callbackInfo) {
      PacketEvent packetEvent = new PacketEvent.Receive(packet);
      MinecraftForge.EVENT_BUS.post(packetEvent);
      if (packetEvent.isCanceled()) {
         callbackInfo.cancel();
      }

   }

   @Inject(method = "sendPacket(Lnet/minecraft/network/Packet;)V", at = @At("RETURN"), cancellable = true)
   private void sendPacketPost(Packet<?> packet, CallbackInfo callbackInfo) {
      PacketEvent event = new PacketEvent.Send.Post(packet);
      MinecraftForge.EVENT_BUS.post(event);

      if (event.isCanceled()) {
         callbackInfo.cancel();
      }
   }

   @Inject(
      method = {"exceptionCaught"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private void exceptionCaught(ChannelHandlerContext p_exceptionCaught_1_, Throwable p_exceptionCaught_2_, CallbackInfo callbackInfo) {
      if (p_exceptionCaught_2_ instanceof IOException && Client.getModuleByName("AntiPacketKick").isEnabled()) {
         callbackInfo.cancel();
      }

   }
}
