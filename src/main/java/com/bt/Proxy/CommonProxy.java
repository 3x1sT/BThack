package com.bt.Proxy;

import com.bt.BTbot.BTbot;
import com.bt.BThack.BThack;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class CommonProxy {
   public void preInit(FMLPreInitializationEvent event) {
      BThack.logger = event.getModLog();
      BTbot.logger = event.getModLog();
   }

   public void init(FMLInitializationEvent event) {
   }

   public void postInit(FMLPostInitializationEvent event) {
   }
}
