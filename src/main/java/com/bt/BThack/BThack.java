package com.bt.BThack;

import com.bt.BTbot.BTbot;
import com.bt.BThack.System.Client;
import com.bt.BThack.System.ConfigInit.ConfigInit;
import com.bt.BThack.System.BThackFileUtils;
import com.bt.BThack.api.Managers.Thread.ThreadManager;
import com.bt.BThack.api.Social.Enemies.EnemyList;
import com.bt.BThack.api.Social.Friends.FriendList;
import com.bt.BThack.api.SoundSystem.TinySound;
import com.bt.BThack.api.Themes.ColourThemes.ColourThemeManager;
import com.bt.BThack.api.Themes.ColourThemes.ColourThemeUpdate;
import com.bt.BThack.api.Utils.Interfaces.Mc;
import com.bt.BThack.api.Utils.Render.RenderUtils;
import com.bt.BThack.api.keys.key;
import com.bt.BThack.impl.CustomGui.Gui.Client.ClickGui.ClickGuiManager;
import com.bt.BThack.api.Managers.Settings.SettingsManager;
import com.bt.BThack.impl.CustomGui.Menu.BThackMenu;
import com.bt.BThack.impl.CustomGui.Menu.onGuiOpenEvent;
import com.bt.BThack.impl.MemoryCleaner.Events;
import net.minecraft.client.Minecraft;
import net.minecraft.util.Session;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.LogManager;
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
    public static final String NAME = "BThack Client";
    public static final String VERSION = "1.5.1";
    public static final String APP_ID = "1221431287852826676";

    public static BThack instance;
    public SettingsManager settingsManager;
    public ClickGuiManager clickGui;
    public BThackMenu mainMenu;
    public ColourThemeManager colourThemeManager;

    public static Logger logger;

    public static void log(String message) {
        BThack.logger.info("[BThack] {}", message);
    }

    public static void error(String message) {
        BThack.logger.error("[BThack] {}", message);
    }


    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        BThack.logger = LogManager.getLogger("BThack");
        BTbot.logger = LogManager.getLogger("BTbot");

        logBThackLogo();

        initLog("BThack initialization has begun. Your nickname: " + Minecraft.getMinecraft().getSession().getUsername());

        try {
            initLog("Starting to create BThack directory...");
            BThackFileUtils.start();
            BThackFileUtils.createTutorialJsonTheme();
            initLog("BThack directory successfully created!");
        } catch (IOException e) {
            initErr("There was an error when creating the BThack directory.");
            throw new RuntimeException(e);
        }

        initLog("Starting initialization of the sound engine...");
        TinySound.init();
        if (TinySound.isInitialized()) {
            initLog("The sound engine has been successfully initialized!");
        } else {
            initErr("The sound engine is not initialized!");
        }
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {
        clickGui = new ClickGuiManager();
        mainMenu = new BThackMenu();
        settingsManager = new SettingsManager();
        colourThemeManager = new ColourThemeManager();

        MinecraftForge.EVENT_BUS.register(new key());
        MinecraftForge.EVENT_BUS.register(new onGuiOpenEvent());
        MinecraftForge.EVENT_BUS.register(new Events());

        instance = this;

        initLog("Starting to upload color themes....");
        try {
            ConfigInit.loadColourThemes();
            initLog("Color themes has loaded!");
        } catch (IOException e) {
            initErr("There was a error when loading color themes!");
            throw new RuntimeException(e);
        }

        initLog("Starting client initialization...");
        Client.startup();
        if (Client.inited) {
            initLog("Client initialized!");
        } else {
            initErr("There was an error during client initialization! Further work is impossible!");
            throw new RuntimeException();
        }

        initLog("Starting loading the config...");
        try {
            ConfigInit.loadConfig();

            initLog("Config successfully uploaded!");
        } catch (Exception e) {
            initErr("There was an error when loading the config. Further work may occur with failures.");
            e.printStackTrace();
        }

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            Client.getModuleByName("FakePlayer").setToggled(false);
            ConfigInit.saveConfig();
            try {
                ConfigInit.saveHudComponents();
            } catch (IOException ignored) {}
            log("Config Saved!");
        }));

        MinecraftForge.EVENT_BUS.register(new ColourThemeUpdate());

        RenderUtils.init();
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        initLog("Starting to upload friends and enemies...");
        try {
            FriendList.loadFriends();
            EnemyList.loadEnemies();
        } catch (Exception e) {
            initErr("There was an error loading friends and enemies!");
            e.printStackTrace();
        }

        ThreadManager.startNewThread(thread -> {
            while (true) {
                if (mc.currentScreen == BThack.instance.clickGui) {
                    BThack.instance.clickGui.tick();
                }
                if (mc.currentScreen == BThack.instance.mainMenu) {
                    BThack.instance.mainMenu.tick();
                }
                try {
                    thread.sleep(10);
                } catch (InterruptedException ignored) {
                }
            }
        });

        initLog("BThack is fully initialized and ready for further work. Enjoy your game!");
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



    private void logBThackLogo() {
        log(" ---------------------------------------------------------------------------------------------------------------------------------------- ");
        log("|    ##############        ###################    ####                                                               ####                |");
        log("|    ##############        ###################    ####                                                               ####                |");
        log("|    ####          ####            ####           ####                       ##########            ##########        ####        ####    |");
        log("|    ####          ####            ####           ####    #######            ##########            ##########        ####        ####    |");
        log("|    ##############                ####           ###################                  ####    ####          ####    ####    ####        |");
        log("|    ##############                ####           ########       ####                  ####    ####          ####    ####    ####        |");
        log("|    ####          ####            ####           ####           ####        ##############    ####                  ########            |");
        log("|    ####          ####            ####           ####           ####        ##############    ####                  ########            |");
        log("|    ####          ####            ####           ####           ####    ####          ####    ####          ####    ####    ####        |");
        log("|    ####          ####            ####           ####           ####    ####          ####    ####          ####    ####    ####        |");
        log("|    ##############                ####           ####           ####        ##############        ##########        ####        ####    |");
        log("|    ##############                ####           ####           ####        ##############        ##########        ####        ####    |");
        log(" ---------------------------------------------------------------------------------------------------------------------------------------- ");
    }

    public static void initLog(CharSequence message) {
        String line = " ";
        for (int i = 2; i < message.length(); i++) {
            line = line + "-";
        }

        log(line);
        log(message.toString());
        log(line);
    }

    public static void initErr(CharSequence message) {
        String messageText = "ERROR: " + message;
        String line = " ";
        for (int i = 2; i < messageText.length(); i++) {
            line = line + "-";
        }

        error(line);
        error(message.toString());
        error(line);
    }
}
