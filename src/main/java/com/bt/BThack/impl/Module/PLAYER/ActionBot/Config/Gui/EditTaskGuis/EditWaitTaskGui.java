package com.bt.BThack.impl.Module.PLAYER.ActionBot.Config.Gui.EditTaskGuis;

import com.bt.BThack.api.Storage.MusicStorage;
import com.bt.BThack.api.Utils.System.Buttons.Button;
import com.bt.BThack.api.Utils.System.Buttons.NumberFrameButton;
import com.bt.BThack.impl.Module.PLAYER.ActionBot.Config.ActionBotConfig;
import com.bt.BThack.impl.Module.PLAYER.ActionBot.Config.ActionBotTasks.WaitTask;
import com.bt.BThack.impl.Module.PLAYER.ActionBot.Config.Gui.ActionBotConfigGui;
import com.bt.BThack.impl.Module.PLAYER.ActionBot.Config.Gui.AddingTaskGuis.AddingWaitTaskGui;
import com.bt.BThack.impl.Module.PLAYER.ActionBot.Config.Gui.Utils.TaskButton;
import net.minecraft.util.SoundCategory;

import java.io.IOException;

public class EditWaitTaskGui extends AddingWaitTaskGui {
    private final TaskButton taskButton;

    public EditWaitTaskGui(TaskButton taskButton) {
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
            NumberFrameButton frameButton = (NumberFrameButton) getButtonFromId(1);

            ActionBotConfig.tasks.remove(taskButton.getId());
            ActionBotConfig.tasks.add(taskButton.getId(), new WaitTask(frameButton.getNumber()));

            mc.displayGuiScreen(new ActionBotConfigGui());
        }
    }
}
