package com.bt.BThack.impl.Module.PLAYER.ActionBot.Config.Gui.EditTaskGuis;

import com.bt.BThack.api.Storage.MusicStorage;
import com.bt.BThack.api.Utils.System.Buttons.Button;
import com.bt.BThack.api.Utils.System.Buttons.ModeButton;
import com.bt.BThack.api.Utils.System.Buttons.NumberFrameButton;
import com.bt.BThack.impl.Module.PLAYER.ActionBot.Config.ActionBotConfig;
import com.bt.BThack.impl.Module.PLAYER.ActionBot.Config.ActionBotTasks.TunnelTask;
import com.bt.BThack.impl.Module.PLAYER.ActionBot.Config.Gui.ActionBotConfigGui;
import com.bt.BThack.impl.Module.PLAYER.ActionBot.Config.Gui.AddingTaskGuis.AddingTunnelTaskGui;
import com.bt.BThack.impl.Module.PLAYER.ActionBot.Config.Gui.Utils.TaskButton;
import net.minecraft.util.SoundCategory;

import java.io.IOException;

public class EditTunnelTaskGui extends AddingTunnelTaskGui {
   private final TaskButton taskButton;

    public EditTunnelTaskGui(TaskButton taskButton) {
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
            ModeButton directionButton = (ModeButton) getButtonFromId(1);
            NumberFrameButton lengthButton = (NumberFrameButton) getButtonFromId(2);

            TunnelTask.Direction direction;

            switch (directionButton.getsVal()) {
                case "X+":
                    direction = TunnelTask.Direction.X_PLUS;
                    break;
                case "X-":
                    direction = TunnelTask.Direction.X_MINUS;
                    break;
                case "Z+":
                    direction = TunnelTask.Direction.Z_PLUS;
                    break;
                case "Z-":
                default:
                    direction = TunnelTask.Direction.Z_MINUS;
                    break;
            }

            ActionBotConfig.tasks.remove(taskButton.getId());
            ActionBotConfig.tasks.add(taskButton.getId(), new TunnelTask(direction, lengthButton.getTextInNumber()));

            mc.displayGuiScreen(new ActionBotConfigGui());
        }
    }
}
