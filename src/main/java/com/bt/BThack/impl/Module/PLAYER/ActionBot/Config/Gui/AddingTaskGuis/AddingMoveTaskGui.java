package com.bt.BThack.impl.Module.PLAYER.ActionBot.Config.Gui.AddingTaskGuis;

import com.bt.BThack.api.Utils.Font.FontUtil;
import com.bt.BThack.api.Utils.Render.ColourUtils;
import com.bt.BThack.api.Utils.Render.RenderUtils;
import com.bt.BThack.api.Utils.System.BThackGui;
import com.bt.BThack.api.Utils.System.Buttons.Button;
import com.bt.BThack.api.Utils.System.Buttons.ModeButton;
import com.bt.BThack.api.Utils.System.Buttons.NumberFrameButton;
import com.bt.BThack.api.Utils.System.Buttons.SwitchButton;
import com.bt.BThack.impl.Module.PLAYER.ActionBot.Config.ActionBotTasks.MoveTask;
import com.bt.BThack.impl.Module.PLAYER.ActionBot.Config.Gui.AddingTaskGui;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import static com.bt.BThack.impl.Module.PLAYER.ActionBot.Config.Gui.ActionBotConfigGui.*;

public class AddingMoveTaskGui extends BThackGui {

    @Override
    public void initGui() {
        super.initGui();

        buttons.clear();

        buttons.add(new NumberFrameButton(1, (int) (widthFactor * 13), (int) (heightFactor * 9.75), (int) (widthFactor + widthFactor), (int) (heightFactor * 0.75)));
        buttons.add(new NumberFrameButton(2, (int) (widthFactor * 13), (int) (heightFactor * 11.75), (int) (widthFactor + widthFactor), (int) (heightFactor * 0.75)));
        buttons.add(new SwitchButton(3, (int) (widthFactor * 13), (int) (heightFactor * 14), (int) (widthFactor + widthFactor), (int) heightFactor, false, "Scaffold"));
        buttons.add(new ModeButton(4, (int) (widthFactor * 15), (int) (heightFactor * 17), (int) (widthFactor * 4), (int) heightFactor, "Mode", new ArrayList<>(Arrays.asList("Default", "With AutoJump", "Through Obstacles"))));


        buttons.add(new Button(-1, (int) (widthFactor * 18.5), (int) (scaledResolution.getScaledHeight() - (heightFactor * 7)), (int) (widthFactor * 9.5), (int) heightFactor, "Confirm"));
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        drawConfigBackGround();

        RenderUtils.drawOutlineRect((int) (widthFactor * 9) - 1, (int) (heightFactor * 8) - 1, (int) (scaledResolution.getScaledWidth() - (widthFactor * 8)) + 1, (int) (scaledResolution.getScaledHeight() - (heightFactor * 9)) + 1, 1, ColourUtils.rainbow(100));
        Gui.drawRect((int) (widthFactor * 9), (int) (heightFactor * 8), (int) (scaledResolution.getScaledWidth() - (widthFactor * 8)), (int) (scaledResolution.getScaledHeight() - (heightFactor * 9)), menuColor);

        GlStateManager.scale(1.5, 1.5, 0);
        FontUtil.drawText("X:", (int) ((widthFactor * 10) * 0.66666666666666666666666666666667), (int) ((heightFactor * 9.3) * 0.66666666666666666666666666666667), Color.white.hashCode());
        FontUtil.drawText("Z:", (int) ((widthFactor * 10) * 0.66666666666666666666666666666667), (int) ((heightFactor * 11.3) * 0.66666666666666666666666666666667), Color.white.hashCode());
        GlStateManager.scale(0.66666666666666666666666666666667, 0.66666666666666666666666666666667, 0);

        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        super.mouseClicked(mouseX, mouseY, mouseButton);

        if (getButtonFromId(-1).isMouseOnButton(mouseX, mouseY)) {
            NumberFrameButton xFrame = (NumberFrameButton) getButtonFromId(1);
            NumberFrameButton zFrame = (NumberFrameButton) getButtonFromId(2);

            SwitchButton scaffoldButton = (SwitchButton) getButtonFromId(3);
            ModeButton modeButton = (ModeButton) getButtonFromId(4);

            MoveTask.Type moveType = modeButton.getsVal().equals("Default") ? MoveTask.Type.Default : modeButton.getsVal().equals("With AutoJump") ? MoveTask.Type.AutoJump : MoveTask.Type.Through_Obstacles;


            AddingTaskGui.addTask(new MoveTask(xFrame.getNumber(), zFrame.getNumber(), scaffoldButton.getBVal(), moveType));
        }
    }
}
