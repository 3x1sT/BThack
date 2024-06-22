package com.bt.BThack;


import com.bt.BThack.api.Themes.ColourThemes.ColourTheme;
import com.bt.BThack.api.Utils.Interfaces.Mc;
import com.bt.BThack.impl.CustomGui.Gui.Client.ClickGui.ClickGuiManager;
import com.bt.BThack.api.Module.Module;
import com.bt.BThack.impl.Module.COMBAT.AimBot;
import com.bt.BThack.impl.Module.EXPLOIT.PingSpoof.PingSpoof;
import com.bt.BThack.impl.Module.EXPLOIT.Timer.Timer;
import com.bt.BThack.impl.Module.MOVEMENT.AntiAFK;
import com.bt.BThack.impl.Module.MOVEMENT.AutoJump.AutoJump;
import com.bt.BThack.impl.Module.MOVEMENT.CameraRotator.CameraRotator;
import com.bt.BThack.impl.Module.MOVEMENT.NinjaBridge.NinjaBridge;
import com.bt.BThack.impl.Module.MOVEMENT.NoFall;
import com.bt.BThack.impl.Module.OTHER.EnemyRadar.EnemyRadar;
import com.bt.BThack.impl.Module.PLAYER.FreeCam;
import com.bt.BThack.impl.Module.RENDER.Zoom.Zoom;
import com.bt.BThack.impl.Module.CLIENT.HUD;
import org.lwjgl.opengl.Display;

import com.bt.BThack.impl.Module.COMBAT.*;
import com.bt.BThack.impl.Module.EXPLOIT.*;
import com.bt.BThack.impl.Module.MOVEMENT.*;
import com.bt.BThack.impl.Module.OTHER.*;
import com.bt.BThack.impl.Module.PLAYER.*;
import com.bt.BThack.impl.Module.RENDER.*;
import com.bt.BThack.impl.Module.CLIENT.*;
import com.bt.BThack.impl.Module.WORLD.*;


import com.bt.BThack.impl.Module.COMBAT.AutoSword;
import com.bt.BThack.impl.Module.COMBAT.KillAura.KillAura;

import com.bt.BThack.impl.Module.EXPLOIT.PortalGod;
import com.bt.BThack.impl.Module.MOVEMENT.NoRotate;
import com.bt.BThack.impl.Module.MOVEMENT.NoSlow;
import com.bt.BThack.impl.Module.MOVEMENT.ShiftSpam.ShiftSpam;
import com.bt.BThack.impl.Module.MOVEMENT.Strafe;

import com.bt.BThack.impl.Module.PLAYER.AutoFirework;
import com.bt.BThack.impl.Module.PLAYER.AutoPearl;
import com.bt.BThack.impl.Module.PLAYER.ChestStealer.ChestStealer;
import com.bt.BThack.impl.Module.PLAYER.Spammer.Spammer;

import com.bt.BThack.impl.Module.CLIENT.ClickGui;

import com.bt.BThack.impl.Module.WORLD.CustomDayTime;
import com.bt.BThack.impl.Module.WORLD.Lawnmower;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.CopyOnWriteArrayList;

public class Client implements Mc {
    public static String name = "BThack 1.12.2 | " + mc.getSession().getUsername();
    public static String cName = "BThack " + BThack.VERSION;

    //Лист с модулями
    public static CopyOnWriteArrayList<Module> modules = new CopyOnWriteArrayList<>();

    public static ClickGuiManager clickGuiManager;


    public static ColourTheme colourTheme;


    //Инициализирует все модули и компоненты
    public static void startup() {
        Display.setTitle(name);

        //Добавление всех модулей в список
        modules.addAll(Arrays.asList(
                //COMBAT
                new AutoSword(),
                new AimBot(),
                new Criticals(),
                new FastBow(),
                new HitBox(),
                new KillAura(),
                new HitSound(),
                new NoEntityTrace(),
                new NoFriendDamage(),

                //EXPLOIT
                new AntiHunger(),
                new AntiPacketKick(),
                new NoBreakReset(),
                new PingSpoof(),
                new PortalGod(),
                new Timer(),

                //CLIENT
                new ClickGui(),
                new Font(),
                new HUD(),
                new MemoryCleaner(),
                new Settings(),

                //MOVEMENT
                new AirJump(),
                new AntiAFK(),
                new AutoJump(),
                new Bhop(),
                new ElytraFastClose(),
                new CameraRotator(),
                new FastFall(),
                new Flip(),
                new Glide(),
                new IceSpeed(),
                new InventoryMove(),
                new NoFall(),
                new NoSlow(),
                new Jesus(),
                new NinjaBridge(),
                new NoRotate(),
                new NoSRotations(),
                new Speed(),
                new Spider(),
                new Sprint(),
                new ShiftSpam(),
                new Strafe(),

                //OTHER
                new CleanMemory(),
                new EnemyRadar(),

                //PLAYER
                new AutoDisconnect(),
                new AutoFirework(),
                new AutoPearl(),
                new AutoRespawn(),
                new AutoTool(),
                //new AutoTPAccept(),
                new ChestStealer(),
                new FakeCreative(),
                new FakePlayer(),
                new FastUse(),
                new FreeCam(),
                new Spammer(),
                new NoJumpDelay(),
                new NoSwing(),
                new Velocity(),
                new XCarry(),

                //RENDER
                new AntiHazard(),
                new AttackTrace(),
                new ChestESP(),
                new ChinaHat(),
                new ESP(),
                new ExtraTab(),
                new FullBright(),
                new LargeNicknames(),
                new NoFog(),
                new NoOverlay(),
                new NoRender(),
                new Radar(),
                new Tracers(),
                new SkyColour(),
                new Zoom(),
                new Xray(),

                //WORLD
                new BHMaximizer(),
                new BlockReach(),
                new CustomDayTime(),
                new Fly(),
                new Lawnmower(),
                new NoWeather()

                //COMPONENTS

        ));

        clickGuiManager = new ClickGuiManager();
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

    //Добавляет тему с список тем
    public static void addCTheme(String name, int frontColour, int backgroundFontColour, int backgroundFontHoveredColour, int moduleEnabledColour, int moduleDisabledColour, int arrayListColour) {
        BThack.instance.colourThemeManager.addColourTheme(new ColourTheme(name, frontColour, backgroundFontColour, backgroundFontHoveredColour, moduleEnabledColour, moduleDisabledColour, arrayListColour));
    }
}
