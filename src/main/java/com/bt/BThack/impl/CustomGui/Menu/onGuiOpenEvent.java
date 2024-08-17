package com.bt.BThack.impl.CustomGui.Menu;

import com.bt.BThack.BThack;
import com.bt.BThack.System.Client;
import com.bt.BThack.api.Utils.Interfaces.Mc;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class onGuiOpenEvent implements Mc {

    private boolean guiOverwritten = false;

    private boolean firstOpened = true;

    @SubscribeEvent
    public void onGuiOpen(GuiOpenEvent e) {
        if (e.getGui() instanceof GuiMainMenu) {


            if (!guiOverwritten) {
                mc.gameSettings.guiScale = 2;
                guiOverwritten = true;
            }

            if (Client.getModuleByName("BThackMainMenu").isEnabled()) {
                e.setGui(BThack.instance.mainMenu);

                //I don't know what the fuck, but without that shit the button rendering breaks.  :/
                if (firstOpened) {
                    ScaledResolution scaledresolution = new ScaledResolution(mc);
                    int j = scaledresolution.getScaledWidth();
                    int k = scaledresolution.getScaledHeight();
                    BThack.instance.mainMenu.setWorldAndResolution(mc, j, k);

                    firstOpened = false;
                }
            }
        }
    }
}
