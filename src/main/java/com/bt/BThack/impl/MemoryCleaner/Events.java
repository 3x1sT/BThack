package com.bt.BThack.impl.MemoryCleaner;

import com.bt.BThack.System.Client;
import com.bt.BThack.api.Module.Module;
import com.bt.BThack.api.Utils.Interfaces.Mc;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.ClientTickEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.Phase;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.ArrayList;
import java.util.List;


public final class Events implements Mc {
   public static long lastCleanTime = 0L;
   public static List recognizedPlayers = new ArrayList();
   public static int idleTime = 0;

   @SideOnly(Side.CLIENT)
   @SubscribeEvent
   public static void onClientTick(ClientTickEvent event) {
      if (Client.getModuleByName("MemoryCleaner").isEnabled()) {
         EntityPlayerSP player = mc.player;
         if (!mc.isGamePaused() && event.phase == Phase.END && player != null && player.world.isRemote) {
            boolean doClean = false;
            if (System.currentTimeMillis() - lastCleanTime > (long) Module.getSlider("MemoryCleaner", "Min Interval") * 1000L) {
               if ((double) (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / (double) Runtime.getRuntime().totalMemory() > Module.getSlider("MemoryCleaner", "Force Clean %") / 100.0D) {
                  doClean = true;
               } else if (Module.getCheckbox("MemoryCleaner", "Auto Cleanup")) {
                  if (idleTime > Module.getSlider("MemoryCleaner", "MinAFKTime") * 20) {
                     doClean = true;
                  }

                  if (System.currentTimeMillis() - lastCleanTime > (long) Module.getSlider("MemoryCleaner", "Max Interval") * 1000L) {
                     doClean = true;
                  }
               }

               if (doClean) {
                  MemoryManager.cleanMemory();
                  lastCleanTime = System.currentTimeMillis();
                  idleTime = 0;
               }

               if (Module.getCheckbox("MemoryCleaner", "Auto Cleanup")) {
                  if (player.motionX < 0.001D && player.motionY < 0.001D && player.motionZ < 0.001D) {
                     ++idleTime;
                  } else {
                     idleTime = 0;
                  }
               }
            }
         }
      }
   }

   @SideOnly(Side.CLIENT)
   @SubscribeEvent
   public static void onPlayerLogin(EntityJoinWorldEvent event) {
      if (Client.getModuleByName("MemoryCleaner").isEnabled()) {
         if (event.getEntity() instanceof EntityPlayer && event.getWorld().isRemote) {
            EntityPlayer player = (EntityPlayer) event.getEntity();
            if (player.getUniqueID().equals(mc.player.getUniqueID()) && !recognizedPlayers.contains(player.getUniqueID())) {
               if (Module.getCheckbox("MemoryCleaner", "Clean On Join")) {
                  MemoryManager.cleanMemory();
               }

               lastCleanTime = System.currentTimeMillis();
               idleTime = 0;
               recognizedPlayers.add(player.getUniqueID());
            }
         }
      }
   }
}
