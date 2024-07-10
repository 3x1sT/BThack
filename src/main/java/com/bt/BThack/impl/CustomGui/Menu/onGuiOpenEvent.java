package com.bt.BThack.impl.CustomGui.Menu;

import com.bt.BThack.BThack;
import com.bt.BThack.api.Utils.Interfaces.Mc;
import com.bt.BThack.impl.Module.PLAYER.ActionBot.Config.Gui.ActionBotConfigGui;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class onGuiOpenEvent implements Mc {
    private boolean guiButtonsInited = false;

    private boolean guiOverwritten = false;

    private boolean firstOpened = true;

    @SubscribeEvent
    public void onGuiOpen(GuiOpenEvent e) {
        if (e.getGui() instanceof GuiMainMenu) {

            if (!guiButtonsInited) {
                mc.gameSettings.guiScale = 2;

                BThack.instance.settingsManager.getModuleSettingByName("ActionBot", "Open Config").setScreenVal(new ActionBotConfigGui());



                guiButtonsInited = true;
            }
            if (!guiOverwritten) {
                mc.gameSettings.guiScale = 2;
                guiOverwritten = true;
            }

            BThackMenu menu = new BThackMenu();

            e.setGui(menu);

            //I don't know what the fuck, but without that shit the button rendering breaks.  :/
            if (firstOpened) {
                ScaledResolution scaledresolution = new ScaledResolution(mc);
                int j = scaledresolution.getScaledWidth();
                int k = scaledresolution.getScaledHeight();
                menu.setWorldAndResolution(mc, j, k);

                firstOpened = false;
            }
        }
    }
}
