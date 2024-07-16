package com.bt.BThack.impl.Module.PLAYER.ActionBot.Config.Gui.EditTaskGuis;

import com.bt.BThack.api.Storage.MusicStorage;
import com.bt.BThack.api.Utils.System.Buttons.Button;
import com.bt.BThack.api.Utils.System.Buttons.ModeButton;
import com.bt.BThack.api.Utils.System.Buttons.NumberFrameButton;
import com.bt.BThack.api.Utils.System.Buttons.SwitchButton;
import com.bt.BThack.impl.Module.PLAYER.ActionBot.Config.ActionBotConfig;
import com.bt.BThack.impl.Module.PLAYER.ActionBot.Config.ActionBotTasks.MoveTask;
import com.bt.BThack.impl.Module.PLAYER.ActionBot.Config.Gui.ActionBotConfigGui;
import com.bt.BThack.impl.Module.PLAYER.ActionBot.Config.Gui.AddingTaskGuis.AddingMoveTaskGui;
import com.bt.BThack.impl.Module.PLAYER.ActionBot.Config.Gui.Utils.TaskButton;
import net.minecraft.util.SoundCategory;

import java.io.IOException;

public class EditMoveTaskGui extends AddingMoveTaskGui {
    private final TaskButton taskButton;

    public EditMoveTaskGui(TaskButton taskButton) {
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

        if (getButtonFromId(-1).isMouseOnButton(mouseX, mouseY)) {
            NumberFrameButton xFrame = (NumberFrameButton) getButtonFromId(1);
            NumberFrameButton zFrame = (NumberFrameButton) getButtonFromId(2);

            SwitchButton scaffoldButton = (SwitchButton) getButtonFromId(3);
            ModeButton modeButton = (ModeButton) getButtonFromId(4);

            MoveTask.Type moveType = modeButton.getsVal().equals("Default") ? MoveTask.Type.Default : modeButton.getsVal().equals("With AutoJump") ? MoveTask.Type.AutoJump : MoveTask.Type.Through_Obstacles;


            ActionBotConfig.tasks.remove(taskButton.getId());
            ActionBotConfig.tasks.add(taskButton.getId(), new MoveTask(xFrame.getTextInNumber(), zFrame.getTextInNumber(), scaffoldButton.getBVal(), moveType));

            mc.displayGuiScreen(new ActionBotConfigGui());
        }
    }
}
