package com.bt.BThack;

import com.bt.BThack.System.Client;
import com.bt.BThack.System.ConfigInit;
import com.bt.BThack.System.CreateBThackFolders;
import com.bt.BThack.api.Social.Enemies.EnemyList;
import com.bt.BThack.api.Social.Friends.FriendList;
import com.bt.BThack.api.SoundSystem.TinySound;
import com.bt.BThack.api.Themes.ColourThemes.ColourThemeManager;
import com.bt.BThack.api.Themes.ColourThemes.ColourThemeUpdate;
import com.bt.BThack.api.Utils.Interfaces.Mc;
import com.bt.BThack.api.Utils.Render.RenderUtils;
import com.bt.BThack.api.keys.key;
import com.bt.BThack.impl.CustomGui.Gui.Client.ClickGui.ClickGuiManager;
import com.bt.BThack.impl.CustomGui.Gui.Client.GuiUtils.Settings.SettingsManager;
import com.bt.BThack.impl.CustomGui.Menu.onGuiOpenEvent;
import com.bt.BThack.impl.MemoryCleaner.Events;
import com.bt.Proxy.CommonProxy;
import net.minecraft.client.Minecraft;
import net.minecraft.util.Session;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.Logger;
import org.lwjgl.opengl.Display;

import java.io.IOException;
import java.lang.reflect.Field;

@Mod(modid = BThack.MODID, name = BThack.NAME, version = BThack.VERSION)
public class BThack implements Mc {

    /*
    Самая первая версия BThack была создана 27.02.2024 в 23:41
     */
    public static final String MODID = "bthack";
    public static final String NAME = "BThack Mod";
    public static final String VERSION = "1.4.2";

    //public static final String CLIENT_PROXY = "com.bt.Proxy.ClientProxy";
    //public static final String COMMON_PROXY = "com.bt.Proxy.CommonProxy";

    //Общий прокси для всех модов в чите, все фукции из других прокси должны быть перенесены в этот
    @SidedProxy(
            clientSide = "com.bt.Proxy.ClientProxy",
            serverSide = "com.bt.Proxy.CommonProxy"
    )
    public static CommonProxy proxy;

    public static BThack instance;
    public SettingsManager settingsManager;
    public ClickGuiManager clickGui;
    public ColourThemeManager colourThemeManager;

    public static Logger logger;

    public static void log(String message) {
        BThack.logger.info(message);
    }

    public static void error(String message) {
        BThack.logger.error(message);
    }


    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {

        proxy.preInit(event);

        try {
            CreateBThackFolders.start();
            CreateBThackFolders.createTutorialJsonTheme();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        TinySound.init();
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {
        clickGui = new ClickGuiManager();
        settingsManager = new SettingsManager();
        colourThemeManager = new ColourThemeManager();

        MinecraftForge.EVENT_BUS.register(new key());
        MinecraftForge.EVENT_BUS.register(new onGuiOpenEvent());
        MinecraftForge.EVENT_BUS.register(new Events());

        instance = this;

        try {
            ConfigInit.loadColourThemes();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Client.startup();
        ConfigInit.loadConfig();
        log("Config Loaded!");

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            Client.getModuleByName("FakePlayer").setToggled(false);
            ConfigInit.saveConfig();
            log("Config Saved!");
        }));

        MinecraftForge.EVENT_BUS.register(new ColourThemeUpdate());

        RenderUtils.init();

        proxy.init(event);
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        proxy.postInit(event);
        FriendList.loadFriends();
        EnemyList.loadEnemies();
    }

    public static void setSession(Session s) {
        Class<? extends Minecraft> mc = Minecraft.getMinecraft().getClass();

        try {
            Field session = null;

            for (Field f : mc.getDeclaredFields()) {
                if (f.getType().isInstance(s)) {
                    session = f;
                }
            }

            if (session == null) {
                throw new IllegalStateException("Session Null");
            }

            session.setAccessible(true);
            session.set(Minecraft.getMinecraft(), s);
            session.setAccessible(false);

            Client.name = "BThack " + VERSION + " | " + Minecraft.getMinecraft().getSession().getUsername();
            Display.setTitle(Client.name);
        } catch (Exception e) {
            error(e.getMessage());
        }
    }
}
