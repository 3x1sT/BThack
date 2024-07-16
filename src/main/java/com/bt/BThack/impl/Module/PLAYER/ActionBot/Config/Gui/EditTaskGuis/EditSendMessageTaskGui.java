package com.bt.BThack.impl.Module.PLAYER.ActionBot.Config.Gui.EditTaskGuis;

import com.bt.BThack.api.Storage.MusicStorage;
import com.bt.BThack.api.Utils.System.Buttons.Button;
import com.bt.BThack.api.Utils.System.Buttons.TextFrameButton;
import com.bt.BThack.impl.Module.PLAYER.ActionBot.Config.ActionBotConfig;
import com.bt.BThack.impl.Module.PLAYER.ActionBot.Config.ActionBotTasks.SendMessageTask;
import com.bt.BThack.impl.Module.PLAYER.ActionBot.Config.Gui.ActionBotConfigGui;
import com.bt.BThack.impl.Module.PLAYER.ActionBot.Config.Gui.AddingTaskGuis.AddingSendMessageTask;
import com.bt.BThack.impl.Module.PLAYER.ActionBot.Config.Gui.Utils.TaskButton;
import net.minecraft.util.SoundCategory;

import java.io.IOException;

public class EditSendMessageTaskGui extends AddingSendMessageTask {
    private final TaskButton taskButton;

    public EditSendMessageTaskGui(TaskButton taskButton) {
        super();

        this.taskButton = taskButton;
    }


    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        for (Button button : buttons) {
            button.mouseClicked(mouseX, mouseY);

            if (button.isMouseOnButton(mouseX, mouseY)) {
                activeButton = button;
                MusicStorage.buttonClicked.play(mc.gameSettings.getSoundLevel(SoundCategory.MASTER) / 2);
            }
        }

        if (activeButton.getId() == -1) {
            TextFrameButton frameButton = (TextFrameButton) getButtonFromId(1);

            ActionBotConfig.tasks.remove(taskButton.getId());
            ActionBotConfig.tasks.add(taskButton.getId(), new SendMessageTask(frameButton.getText()));

            mc.displayGuiScreen(new ActionBotConfigGui());
        }
    }
}
