package com.bt.Proxy;

import com.bt.BTbot.BTbot;
import com.bt.BTbot.impl.Commands.AntiAFKCommand;
import com.bt.BThack.BThack;
import com.bt.BThack.api.Module.Module;
import com.bt.BThack.impl.Commands.*;
import net.minecraft.command.ICommand;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import com.bt.BThack.impl.Commands.CleanMemoryCommand;
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
      registerC("Memory cleaning", new CleanMemoryCommand());

      BTbot.logger.info("Register AntiAFK command...");
      ClientCommandHandler.instance.registerCommand(new AntiAFKCommand());

      registerC("Friends", new FriendsCommand());
      registerC("Enemies", new EnemiesCommand());
      registerC("Allies", new AlliesCommand());
      registerC("Refresh", new RefreshCommand());
      registerC("Rotate", new RotateCommand());
      registerC("Xray", new XrayCommand());


      super.postInit(event);
      if (Module.getCheckbox("MemoryCleaner", "Clean On Init")) {
         MemoryManager.cleanMemory();
      }

   }

   private void registerC(String name, ICommand command) {
      BThack.log("Register " + name + " command...");
      ClientCommandHandler.instance.registerCommand(command);
      BThack.log(name + " command registered!");
   }
}
