package com.bt.BThack.impl.Module.PLAYER.ActionBot.Config.Gui.AddingTaskGuis;

import com.bt.BThack.api.Utils.Font.FontUtil;
import com.bt.BThack.api.Utils.System.BThackGui;
import com.bt.BThack.api.Utils.System.Buttons.Button;
import com.bt.BThack.api.Utils.System.Buttons.ModeButton;
import com.bt.BThack.api.Utils.System.Buttons.NumberFrameButton;
import com.bt.BThack.impl.Module.PLAYER.ActionBot.Config.ActionBotTasks.TunnelTask;
import com.bt.BThack.impl.Module.PLAYER.ActionBot.Config.Gui.AddingTaskGui;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import static com.bt.BThack.impl.Module.PLAYER.ActionBot.Config.Gui.ActionBotConfigGui.*;
import static com.bt.BThack.impl.Module.PLAYER.ActionBot.Config.Gui.ActionBotConfigGui.heightFactor;

public class AddingTunnelTaskGui extends BThackGui {

    @Override
    public void initGui() {
        this.buttons.clear();

        this.buttons.add(new ModeButton(1,(int) ((scaledResolution.getScaledWidth() / 2) - (widthFactor * 5)), (int) ((scaledResolution.getScaledHeight() / 2) - (heightFactor * 2.5)), (int) (widthFactor * 4), (int) heightFactor, "Direction",
                new ArrayList<>(Arrays.asList(
                        "X+",
                        "X-",
                        "Z+",
                        "Z-"
                ))));
        this.buttons.add(new NumberFrameButton(2, (int) ((scaledResolution.getScaledWidth() / 2) + (widthFactor * 5)), (int) ((scaledResolution.getScaledHeight() / 2) - (heightFactor * 2.5)), (int) (widthFactor * 4), (int) heightFactor));

        this.buttons.add(new Button(-1, (scaledResolution.getScaledWidth() / 2), ((scaledResolution.getScaledHeight() / 2)), (int) (widthFactor * 8.5), (int) heightFactor, "Confirm"));
    }

    @Override
    public void drawScreen(int p_drawScreen_1_, int p_drawScreen_2_, float p_drawScreen_3_) {
        drawConfigBackGround();

        //FontUtil.drawText("Direction: ", (int) ((scaledResolution.getScaledWidth() / 2) - (widthFactor * 5) - (FontUtil.getStringWidth("Direction: ") / 2)), (int) ((scaledResolution.getScaledHeight() / 2) - (heightFactor * 4.5)), Color.white.hashCode());
        FontUtil.drawText("Length: ", (int) ((scaledResolution.getScaledWidth() / 2) + (widthFactor * 5) - (FontUtil.getStringWidth("Length: ") / 2)), (int) ((scaledResolution.getScaledHeight() / 2) - (heightFactor * 4.5)), Color.white.hashCode());

        super.drawScreen(p_drawScreen_1_, p_drawScreen_2_, p_drawScreen_3_);
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        super.mouseClicked(mouseX, mouseY, mouseButton);

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

            AddingTaskGui.addTask(new TunnelTask(direction, lengthButton.getTextInNumber()));
        }
    }
}
