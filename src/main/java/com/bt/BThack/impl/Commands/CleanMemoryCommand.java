package com.bt.BThack.impl.Commands;

import com.bt.BThack.impl.MemoryCleaner.Events;
import com.bt.BThack.impl.MemoryCleaner.MemoryManager;
import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.client.IClientCommand;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class CleanMemoryCommand extends CommandBase implements IClientCommand {
   //public static final String NAME = "cleanmemory";

   public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
      MemoryManager.cleanMemory();
      Events.lastCleanTime = System.currentTimeMillis();
   }

   @MethodsReturnNonnullByDefault
   public String getName() {
      return "cleanmemory";
   }

   @MethodsReturnNonnullByDefault
   public String getUsage(ICommandSender sender) {
      return "/cleanmemory";
   }

   public int getRequiredPermissionLevel() {
      return 0;
   }

   public boolean allowUsageWithoutPrefix(ICommandSender sender, String message) {
      return false;
   }
}
