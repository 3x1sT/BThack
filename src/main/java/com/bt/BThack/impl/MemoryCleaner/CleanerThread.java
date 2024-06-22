package com.bt.BThack.impl.MemoryCleaner;

import com.bt.BThack.BThack;
import com.bt.BThack.Client;
import com.bt.BThack.api.Module.Module;
import com.bt.BThack.api.Utils.ChatUtils;
import com.bt.BThack.api.Utils.Interfaces.Mc;
import com.mojang.realmsclient.gui.ChatFormatting;

public class CleanerThread implements Runnable, Mc {

   public CleanerThread() {
   }

   public void run() {
      BThack.log("Memory cleaner thread started!");
      if (Module.getCheckbox("MemoryCleaner", "Show Messages") && mc.player != null && mc.world != null) {
         ChatUtils.sendMessage(ChatFormatting.LIGHT_PURPLE + "Starting memory cleaning, please wait...");
      }

      System.gc();

      try {
         Thread.sleep(1000L);
      } catch (InterruptedException ignored) {}

      System.gc();
      if (Module.getCheckbox("MemoryCleaner", "Show Messages")) {
         ChatUtils.sendMessage(ChatFormatting.LIGHT_PURPLE + "Memory clearing completed successfully!");
      }

      BThack.log("Memory cleaner thread finished!");

      if (Client.getModuleByName("CleanMemory").isEnabled()) {
         Client.getModuleByName("CleanMemory").setToggled(false);
      }
   }
}
