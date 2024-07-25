package com.bt.BThack.impl.MemoryCleaner;

import com.bt.BThack.System.Client;
import com.bt.BThack.api.Utils.ChatUtils;
import com.bt.BThack.api.Utils.Interfaces.Mc;
import com.mojang.realmsclient.gui.ChatFormatting;

public class MemoryManager implements Mc {

   public static void cleanMemory() {
      if (Client.getModuleByName("MemoryCleaner").isEnabled()) {
         Runnable runnable = new CleanerThread();
         Thread gcThread = new Thread(runnable, "MemoryCleaner GC Thread");
         gcThread.setDaemon(true);
         gcThread.start();
      } else {
         if (mc.player != null && mc.world != null) {
            ChatUtils.sendMessage(ChatFormatting.YELLOW + "Memory Cleaner module is disabled, please enable it before next use.");
            if (Client.getModuleByName("CleanMemory").isEnabled()) {
               Client.getModuleByName("CleanMemory").setToggled(false);
            }
         }
      }
   }
}
