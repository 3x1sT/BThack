package com.bt.BThack.impl.Module.OTHER;

import com.bt.BThack.api.Module.Module;
import org.lwjgl.input.Keyboard;
import com.bt.BThack.impl.MemoryCleaner.MemoryManager;

public class CleanMemory extends Module {
    public CleanMemory() {
        super("CleanMemory",
                "Clears RAM when turned on.",
                Keyboard.KEY_NONE,
                Category.OTHER,
                false
        );
    }

    @Override
    public void onEnable() {
        MemoryManager.cleanMemory();
    }
}
