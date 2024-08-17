package com.bt.BTbot;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.Logger;

@Mod(modid = BTbot.MODID, name = BTbot.NAME, version = BTbot.VERSION)
public class BTbot {
    public static final String MODID = "btbot";
    public static final String NAME = "BTbot Mod";
    public static final String VERSION = "0.1";

    public static final String ModName = "BTbot v" + VERSION;

    @Mod.Instance("bthack")
    public static BTbot INSTANCE;

    public static Logger logger;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {

    }

    @EventHandler
    public void init(FMLInitializationEvent event) {

    }

    @EventHandler
    public void postinit(FMLPostInitializationEvent event) {

    }
}
