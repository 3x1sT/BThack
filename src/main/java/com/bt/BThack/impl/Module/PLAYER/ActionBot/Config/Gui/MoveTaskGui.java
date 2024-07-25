package com.bt.BThack.impl.Module.PLAYER.ActionBot.Config.Gui;

import com.bt.BThack.api.Utils.Font.FontUtil;
import com.bt.BThack.api.Utils.System.BThackGui;
import com.bt.BThack.api.Utils.System.Buttons.Button;
import com.bt.BThack.api.Utils.System.Buttons.NumberFrameButton;
import com.bt.BThack.impl.Module.PLAYER.ActionBot.Config.ActionBotConfig;
import com.bt.BThack.impl.Module.PLAYER.ActionBot.Config.Gui.Utils.TaskButton;

import java.awt.*;
import java.io.IOException;

import static com.bt.BThack.impl.Module.PLAYER.ActionBot.Config.Gui.ActionBotConfigGui.*;
import static com.bt.BThack.impl.Module.PLAYER.ActionBot.Config.Gui.ActionBotConfigGui.heightFactor;

public class MoveTaskGui extends BThackGui {
    private final TaskButton taskButton;

    public MoveTaskGui(TaskButton taskButton) {
        this.taskButton = taskButton;
    }

    @Override
    public void initGui() {
        this.buttons.clear();

        this.buttons.add(new NumberFrameButton(1, (scaledResolution.getScaledWidth() / 2), (int) ((scaledResolution.getScaledHeight() / 2) - (heightFactor * 2.5)), (int) (widthFactor * 8.5), (int) heightFactor));
        this.buttons.add(new Button(-1, (scaledResolution.getScaledWidth() / 2), ((scaledResolution.getScaledHeight() / 2)), (int) (widthFactor * 8.5), (int) heightFactor, "Confirm"));
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        drawConfigBackGround();

        FontUtil.drawText("New task number", (int) ((scaledResolution.getScaledWidth() / 2) - (FontUtil.getStringWidth("New task number") / 2)), (int) ((scaledResolution.getScaledHeight() / 2) - (heightFactor * 4.5)), Color.white.hashCode());

        super.drawScreen(mouseX, mouseY, partialTicks);
    }


    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        super.mouseClicked(mouseX, mouseY, mouseButton);

        if (activeButton.getId() == -1) {
            NumberFrameButton numberButton = (NumberFrameButton) getButtonFromId(1);

            ActionBotConfig.tasks.remove(this.taskButton.getId());
            ActionBotConfig.tasks.add((int) Math.min(ActionBotConfig.tasks.size() - 2, numberButton.getNumber()), taskButton.task);

            mc.displayGuiScreen(new ActionBotConfigGui());
        }
    }
}
