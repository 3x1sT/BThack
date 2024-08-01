package com.bt.Proxy;

import com.bt.BThack.api.Module.Module;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import com.bt.BThack.impl.MemoryCleaner.MemoryManager;

public class ClientProxy extends CommonProxy {
   public void preInit(FMLPreInitializationEvent event) {
      super.preInit(event);
   }

   public void init(FMLInitializationEvent event) {
      super.init(event);
      if (Module.getCheckbox("MemoryCleaner", "Clean On Init")) {
         MemoryManager.cleanMemory();
      }

   }

   public void postInit(FMLPostInitializationEvent event) {


      super.postInit(event);
      if (Module.getCheckbox("MemoryCleaner", "Clean On Init")) {
         MemoryManager.cleanMemory();
      }

   }
}
