package com.bt.BThack.impl.Commands;

import com.bt.BThack.api.CommandSystem.Exception.VentureException;
import com.bt.BThack.api.CommandSystem.command.AbstractCommand;
import com.bt.BThack.impl.MemoryCleaner.Events;
import com.bt.BThack.impl.MemoryCleaner.MemoryManager;

public class CleanMemoryCommand extends AbstractCommand {

    public CleanMemoryCommand() {
        super("Clears RAM", "cleanmemory", "cleanmemory"
        );
    }

    @Override
    public void execute(String[] args) throws VentureException {
        MemoryManager.cleanMemory();
        Events.lastCleanTime = System.currentTimeMillis();
    }
}
