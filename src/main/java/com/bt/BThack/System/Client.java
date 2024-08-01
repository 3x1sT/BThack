package com.bt.BThack.System;


import com.bt.BThack.BThack;
import com.bt.BThack.api.CommandSystem.manager.CommandManager;
import com.bt.BThack.api.HudComponent.HudComponent;
import com.bt.BThack.api.Managers.RenderManager.RenderManager;
import com.bt.BThack.api.Managers.TPSManager;
import com.bt.BThack.api.MusicManager.MainMenuMusicThread;
import com.bt.BThack.api.Storage.MusicStorage;
import com.bt.BThack.api.Themes.ColourThemes.ColourTheme;
import com.bt.BThack.api.Utils.Destroy.DestroyManager;
import com.bt.BThack.api.Utils.Interfaces.Mc;
import com.bt.BThack.impl.Commands.*;
import com.bt.BThack.impl.Commands.List.*;
import com.bt.BThack.impl.Commands.Social.Clans.ClanMembersCommand;
import com.bt.BThack.impl.Commands.Social.Clans.ClanStatusCommand;
import com.bt.BThack.impl.Commands.Social.Clans.ClansCommand;
import com.bt.BThack.impl.Commands.Social.EnemiesCommand;
import com.bt.BThack.impl.Commands.Social.FriendsCommand;
import com.bt.BThack.impl.CustomGui.Gui.Client.ClickGui.ClickGuiManager;
import com.bt.BThack.api.Module.Module;
import com.bt.BThack.impl.Module.CLIENT.CustomSkin;
import com.bt.BThack.impl.Module.COMBAT.AimBot;
import com.bt.BThack.impl.Module.EXPLOIT.PingSpoof;
import com.bt.BThack.impl.Module.EXPLOIT.Timer;
import com.bt.BThack.impl.Module.MOVEMENT.AntiAFK;
import com.bt.BThack.impl.Module.MOVEMENT.AutoJump;
import com.bt.BThack.impl.Module.MOVEMENT.CameraRotator;
import com.bt.BThack.impl.Module.MOVEMENT.ElytraFlight.ElytraFlight;
import com.bt.BThack.impl.Module.MOVEMENT.NewElytraFlight.NewElytraFlight;
import com.bt.BThack.api.Managers.PlayerPacketManager;
import com.bt.BThack.impl.Module.MOVEMENT.NinjaBridge.NinjaBridge;
import com.bt.BThack.impl.Module.MOVEMENT.NoFall;
import com.bt.BThack.impl.Module.MISC.EnemyRadar;
import com.bt.BThack.impl.Module.MISC.TopperRadar;
import com.bt.BThack.impl.Module.PLAYER.ActionBot.ActionBot;
import com.bt.BThack.impl.Module.PLAYER.AutoBuilder;
import com.bt.BThack.impl.Module.PLAYER.ElytraTakeOf.ElytraTakeOf;
import com.bt.BThack.impl.Module.PLAYER.FreeCam;
import com.bt.BThack.impl.Module.PLAYER.LagDetector.LagDetector;
import com.bt.BThack.impl.Module.PLAYER.PMSpammer.PMSpammer;
import com.bt.BThack.impl.Module.RENDER.Chams.Chams;
import com.bt.BThack.impl.Module.RENDER.Zoom;
import com.bt.BThack.impl.Module.CLIENT.HUD;
import com.google.common.collect.Sets;
import net.minecraftforge.common.MinecraftForge;
import org.lwjgl.opengl.Display;

import com.bt.BThack.impl.Module.COMBAT.*;
import com.bt.BThack.impl.Module.EXPLOIT.*;
import com.bt.BThack.impl.Module.MOVEMENT.*;
import com.bt.BThack.impl.Module.MISC.*;
import com.bt.BThack.impl.Module.PLAYER.*;
import com.bt.BThack.impl.Module.RENDER.*;
import com.bt.BThack.impl.Module.CLIENT.*;
import com.bt.BThack.impl.Module.WORLD.*;


import com.bt.BThack.impl.Module.COMBAT.AutoSword;
import com.bt.BThack.impl.Module.COMBAT.KillAura.KillAura;

import com.bt.BThack.impl.Module.EXPLOIT.PortalGod;
import com.bt.BThack.impl.Module.MOVEMENT.NoRotate;
import com.bt.BThack.impl.Module.MOVEMENT.NoSlow;
import com.bt.BThack.impl.Module.MOVEMENT.ShiftSpam;
import com.bt.BThack.impl.Module.MOVEMENT.Strafe;

import com.bt.BThack.impl.Module.PLAYER.AutoFirework;
import com.bt.BThack.impl.Module.PLAYER.AutoPearl;
import com.bt.BThack.impl.Module.PLAYER.ChestStealer;
import com.bt.BThack.impl.Module.PLAYER.Spammer.Spammer;

import com.bt.BThack.impl.Module.CLIENT.ClickGui;

import com.bt.BThack.impl.Module.WORLD.CustomDayTime;
import com.bt.BThack.impl.Module.WORLD.Lawnmower;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;

public class Client implements Mc {
    public static MainMenuMusicThread mainMenuMusicThread;

    public static String name = "BThack 1.12.2 | " + mc.getSession().getUsername();
    public static String cName = "BThack " + BThack.VERSION;
    public static final String ChatPrefix = "$";

    //Лист с модулями
    public static CopyOnWriteArrayList<Module> modules = new CopyOnWriteArrayList<>();
    public static CopyOnWriteArrayList<HudComponent> hudComponents = new CopyOnWriteArrayList<>();

    public static ClickGuiManager clickGuiManager;

    public static TPSManager tpsManager;


    public static ColourTheme colourTheme;

    public static boolean inited = false;


    private static final Set<Object> managers = Sets.newHashSet(
            new PlayerPacketManager(),
            new RenderManager(),
            new DestroyManager()
    );


    //Инициализирует все модули и компоненты
    public static void startup() {
        Display.setTitle(name);

        //Добавление всех модулей в список
        modules.addAll(Arrays.asList(
                //COMBAT
                new AimBot(),
                new AutoShield(),
                new AutoSword(),
                new Criticals(),
                new FastBow(),
                new FireBallAura(),
                new HitBox(),
                new KillAura(),
                new HitSound(),
                new NoEntityTrace(),
                new NoFriendDamage(),
                new SafeTrap(),
                new Surround(),

                //EXPLOIT
                new AntiHunger(),
                new AntiPacketKick(),
                new LiquidInteract(),
                new NoBreakReset(),
                new PingSpoof(),
                new PortalGod(),
                new ThunderHack(),
                new Timer(),

                //MISC
                new CleanMemory(),
                new GameCrasher(),
                new EnemyRadar(),
                new ItemRandomizer(),
                new PistonSoundDelay(),
                new TopperRadar(),

                //CLIENT
                new BThackCape(),
                new BThackMainMenu(),
                new ClickGui(),
                new CustomSkin(),
                new DiscordRPC(),
                new Font(),
                new HUD(),
                new HudEditor(),
                new MemoryCleaner(),
                new ModuleOnOffSound(),
                new Settings(),

                //MOVEMENT
                new AirJump(),
                new AntiAFK(),
                new AutoJump(),
                new Bhop(),
                new ElytraFastClose(),
                new ElytraFlight(),
                new NewElytraFlight(),
                new ElytraStrafe(),
                new CameraRotator(),
                new FastFall(),
                new Flip(),
                new Glide(),
                new IceSpeed(),
                new GuiMove(),
                new NoFall(),
                new NoSlow(),
                new Jesus(),
                new NinjaBridge(),
                new NoRotate(),
                new NoSRotations(),
                new Scaffold(),
                new Speed(),
                new Spider(),
                new Sprint(),
                new ShiftSpam(),
                new Step(),
                new Strafe(),

                //PLAYER
                new AutoBuilder(),
                new ActionBot(),
                new AutoDisconnect(),
                new AutoFirework(),
                new AutoPearl(),
                new AutoRespawn(),
                new AutoTool(),
                new ChildModel(),
                new ElytraReplace(),
                new ElytraSwap(),
                new ElytraTakeOf(),
                new ChestStealer(),
                new FakeCreative(),
                new FakePlayer(),
                new FastUse(),
                new FreeCam(),
                new Introvert(),
                new ItemSaver(),
                new LagDetector(),
                new MultiFakePlayer(),
                new PMSpammer(),
                new Spammer(),
                new NoJumpDelay(),
                new NoSwing(),
                new PacketPlace(),
                new Velocity(),
                new XCarry(),

                //RENDER
                new Africa(),
                new AntiHazard(),
                new AttackTrace(),
                new BlockHighlight(),
                new Caipirinha(),
                new CameraClip(),
                new CameraDistance(),
                new Chams(),
                new ChestESP(),
                new ChinaHat(),
                new CS_Crosshair(),
                new ESP(),
                new ExtraTab(),
                new FullBright(),
                new HoleESP(),
                new LargeNicknames(),
                new NoFog(),
                new NoOverlay(),
                new NoRender(),
                new Radar(),
                new ShulkerPreview(),
                new Tracers(),
                new Trajectories(),
                new ViewModel(),
                new Zoom(),
                new Xray(),

                //WORLD
                new BHMaximizer(),
                new Reach(),
                new CloudsColor(),
                new CustomDayTime(),
                new Fly(),
                new FogColor(),
                new Lawnmower(),
                new NoWeather(),
                new SkyColour(),
                new WorldElements()

                //COMPONENTS

        ));

        clickGuiManager = new ClickGuiManager();

        initCommands();

        initMusic();

        for (Object object : managers) {
            MinecraftForge.EVENT_BUS.register(object);
        }

        tpsManager = new TPSManager();

        inited = true;
    }


    //Получение модулей из определенной категории
    public static ArrayList<Module> getModulesInCategory(Module.Category c) {
        ArrayList<Module> mods = new ArrayList<>();
        for (Module m : modules) {
            if (m.getCategory().name().equalsIgnoreCase(c.name())) {
                mods.add(m);
            }
        }
        return mods;
    }

    //Получение модуля по его имени
    public static Module getModuleByName(String name) {
        return modules.stream()
                .filter(module -> module.getName().equalsIgnoreCase(name))
                .findFirst()
                .orElse(null);
    }

    //Переключает модуль, если нажатая кавиша соответствует его кейбинду
    public static void keyPress(int key) {
        for (Module m : modules) {
            if (m.getKey() == key) {
                m.toggle();
            }
        }
    }

    public static boolean isOptionActivated(String mod, String optionName) {
        return getModuleByName(mod).isEnabled() && Module.getCheckbox(mod, optionName);
    }

    //Добавляет тему с список тем
    public static void addCTheme(String name, int frontColour, int backgroundFontColour, int backgroundFontHoveredColour, int moduleEnabledColour, int moduleDisabledColour, int arrayListColour) {
        BThack.instance.colourThemeManager.addColourTheme(new ColourTheme(name, frontColour, backgroundFontColour, backgroundFontHoveredColour, moduleEnabledColour, moduleDisabledColour, arrayListColour));
    }


    private static void initCommands() {
        CommandManager.addCommands(
                new FriendsCommand(),
                new EnemiesCommand(),
                new CleanMemoryCommand(),


                new ClansCommand(),
                new ClanMembersCommand(),
                new ClanStatusCommand(),


                new RefreshCommand(),
                new RotateCommand(),
                new XrayCommand(),


                new CommandListCommand(),
                new FriendListCommand(),
                new EnemiesListCommand(),
                new clansListCommand(),
                new XrayListCommand()
        );
    }

    private static void initMusic() {
        MusicStorage.init();

        if (
                !Files.exists(Paths.get("BThack_Music/CosmicMusic1.wav")) ||
                        !Files.exists(Paths.get("BThack_Music/CosmicMusic2.wav")) ||
                        !Files.exists(Paths.get("BThack_Music/CosmicMusic3.wav")) ||
                        !Files.exists(Paths.get("BThack_Music/CosmicMusic4.wav"))
        ) {
            BThack.error("No music found for main menu! Further work with the main menu will be accompanied without sounds.");
        } else {
            mainMenuMusicThread = new MainMenuMusicThread();

            mainMenuMusicThread.init();

            BThack.log("The music is initialized and ready for use.");
        }
    }
}
