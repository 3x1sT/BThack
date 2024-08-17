package com.bt.BThack.impl.Module.PLAYER.ActionBot.Config.Gui.AddingTaskGuis;

import com.bt.BThack.api.Utils.Font.FontUtil;
import com.bt.BThack.api.Utils.System.BThackGui;
import com.bt.BThack.api.Utils.System.Buttons.Button;
import com.bt.BThack.api.Utils.System.Buttons.NumberFrameButton;
import com.bt.BThack.impl.Module.PLAYER.ActionBot.Config.ActionBotTasks.WaitTask;
import com.bt.BThack.impl.Module.PLAYER.ActionBot.Config.Gui.AddingTaskGui;

import java.awt.*;
import java.io.IOException;

import static com.bt.BThack.impl.Module.PLAYER.ActionBot.Config.Gui.ActionBotConfigGui.*;

public class AddingWaitTaskGui extends BThackGui {


    @Override
    public void initGui() {
        this.buttons.clear();

        this.buttons.add(new NumberFrameButton(1, (scaledResolution.getScaledWidth() / 2), (int) ((scaledResolution.getScaledHeight() / 2) - (heightFactor * 2.5)), (int) (widthFactor * 8.5), (int) heightFactor));
        this.buttons.add(new Button(-1, (scaledResolution.getScaledWidth() / 2), ((scaledResolution.getScaledHeight() / 2)), (int) (widthFactor * 8.5), (int) heightFactor, "Confirm"));
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        drawConfigBackGround();

        FontUtil.drawText("Seconds", (int) ((scaledResolution.getScaledWidth() / 2) - (FontUtil.getStringWidth("Seconds") / 2)), (int) ((scaledResolution.getScaledHeight() / 2) - (heightFactor * 4.5)), Color.white.hashCode());


        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        super.mouseClicked(mouseX, mouseY, mouseButton);

        if (activeButton.getId() == -1) {
            NumberFrameButton frameButton = (NumberFrameButton) getButtonFromId(1);

            AddingTaskGui.addTask(new WaitTask(frameButton.getNumber()));
        }
    }
}
