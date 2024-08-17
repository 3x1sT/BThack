package com.bt.BThack.impl.Module.PLAYER.ActionBot.Config.Gui;

import com.bt.BThack.api.Utils.Font.FontUtil;
import com.bt.BThack.api.Utils.System.BThackGui;
import com.bt.BThack.api.Utils.System.Buttons.Button;
import com.bt.BThack.impl.Module.PLAYER.ActionBot.Config.ActionBotConfig;
import com.bt.BThack.impl.Module.PLAYER.ActionBot.Config.ActionBotTask;
import com.bt.BThack.impl.Module.PLAYER.ActionBot.Config.ActionBotTasks.*;
import com.bt.BThack.impl.Module.PLAYER.ActionBot.Config.Gui.AddingTaskGuis.AddingMoveTaskGui;
import com.bt.BThack.impl.Module.PLAYER.ActionBot.Config.Gui.AddingTaskGuis.AddingSendMessageTask;
import com.bt.BThack.impl.Module.PLAYER.ActionBot.Config.Gui.AddingTaskGuis.AddingTunnelTaskGui;
import com.bt.BThack.impl.Module.PLAYER.ActionBot.Config.Gui.AddingTaskGuis.AddingWaitTaskGui;
import com.bt.BThack.impl.Module.PLAYER.ActionBot.Config.Gui.Utils.TaskButton;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;

import java.awt.*;
import java.io.IOException;

import static com.bt.BThack.impl.Module.PLAYER.ActionBot.Config.Gui.ActionBotConfigGui.*;

public class AddingTaskGui extends BThackGui {
    private String taskName = "Select the desired task";
    private ActionBotTask currentTask = new ActionBotTask("NULL");



    protected static TaskButton substituteTask;



    @Override
    public void initGui() {
        super.initGui();

        buttons.clear();

        buttons.add(new Button(1 ,(int) (widthFactor * 5), (int) (heightFactor * 3), (int) (widthFactor * 4), (int) heightFactor, "Move Task"));
        buttons.add(new Button(2, (int) (widthFactor * 5), (int) (heightFactor * 6), (int) (widthFactor * 4), (int) heightFactor, "Jump Task"));
        buttons.add(new Button(3, (int) (widthFactor * 5), (int) (heightFactor * 9), (int) (widthFactor * 4), (int) heightFactor, "SendMessage Task"));
        buttons.add(new Button(4, (int) (widthFactor * 5), (int) (heightFactor * 12), (int) (widthFactor * 4), (int) heightFactor, "Tunnel Task"));
        buttons.add(new Button(5, (int) (widthFactor * 5), (int) (heightFactor * 15), (int) (widthFactor * 4), (int) heightFactor, "Wait Task"));

        buttons.add(new Button(-1, (int) (scaledResolution.getScaledWidth() - (widthFactor * 5)), (int) (scaledResolution.getScaledHeight() - (heightFactor * 3)), (int) (widthFactor * 4), (int) heightFactor, "Setup"));
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        drawConfigBackGround();

        Gui.drawRect((int) (widthFactor * 11), (int) heightFactor, (int) (scaledResolution.getScaledWidth() - widthFactor), (int) (scaledResolution.getScaledHeight() - (heightFactor * 5)), new Color(0,0,0,40).hashCode());
        FontUtil.drawText(taskName, (int) ((scaledResolution.getScaledWidth() - (widthFactor + widthFactor)) - FontUtil.getStringWidth(taskName)), (int) (heightFactor * 1.5), Color.white.hashCode());

        short offset = 0;

        for (String text : this.currentTask.getTaskDescription()) {
            if (!text.equals("NULL")) {
                FontUtil.drawText(text, (int) (widthFactor * 12), (int) ((heightFactor * 4) + ((heightFactor * 0.5) * offset)), Color.white.hashCode());
                offset++;
            }
        }


        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        super.mouseClicked(mouseX, mouseY, mouseButton);

        if (activeButton.getId() == 1) {
            this.currentTask = new MoveTask(0,0, false, MoveTask.Type.Default);
            updateTaskName();
        }
        if (activeButton.getId() == 2) {
            this.currentTask = new JumpTask();
            updateTaskName();
        }
        if (activeButton.getId() == 3) {
            this.currentTask = new SendMessageTask("");
            updateTaskName();
        }
        if (activeButton.getId() == 4) {
            this.currentTask = new TunnelTask(TunnelTask.Direction.X_MINUS, 0);
            updateTaskName();
        }
        if (activeButton.getId() == 5) {
            this.currentTask = new WaitTask(0);
            updateTaskName();
        }


        if (activeButton.getId() == -1) {
            if (currentTask instanceof  MoveTask) {
                mc.displayGuiScreen(new AddingMoveTaskGui());
            }
            if (currentTask instanceof JumpTask) {
                ActionBotConfig.addActionTask(new JumpTask());
                mc.displayGuiScreen(new ActionBotConfigGui());
            }
            if (currentTask instanceof SendMessageTask) {
                mc.displayGuiScreen(new AddingSendMessageTask());
            }
            if (currentTask instanceof TunnelTask) {
                mc.displayGuiScreen(new AddingTunnelTaskGui());
            }
            if (currentTask instanceof WaitTask) {
                mc.displayGuiScreen(new AddingWaitTaskGui());
            }
        }
    }

    private void updateTaskName() {
        this.taskName = this.currentTask.getName() + " Task";
    }


    public static void addTask(ActionBotTask task) {
        if (substituteTask != null) {
            ActionBotConfig.tasks.add(AddingTaskGui.substituteTask.getId(), task);
        } else {
            ActionBotConfig.addActionTask(task);
        }

        Minecraft.getMinecraft().displayGuiScreen(new ActionBotConfigGui());
    }
}
