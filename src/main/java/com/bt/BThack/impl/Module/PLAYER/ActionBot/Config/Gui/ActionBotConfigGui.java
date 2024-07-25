package com.bt.BThack.impl.Module.PLAYER.ActionBot.Config.Gui;

import com.bt.BThack.BThack;
import com.bt.BThack.api.Utils.Font.FontUtil;
import com.bt.BThack.api.Utils.Render.ColourUtils;
import com.bt.BThack.api.Utils.Render.RenderUtils;
import com.bt.BThack.api.Utils.System.BThackGui;
import com.bt.BThack.api.Utils.System.Buttons.Button;
import com.bt.BThack.impl.Module.PLAYER.ActionBot.Config.ActionBotConfig;
import com.bt.BThack.impl.Module.PLAYER.ActionBot.Config.ActionBotTask;
import com.bt.BThack.impl.Module.PLAYER.ActionBot.Config.ActionBotTasks.MoveTask;
import com.bt.BThack.impl.Module.PLAYER.ActionBot.Config.ActionBotTasks.SendMessageTask;
import com.bt.BThack.impl.Module.PLAYER.ActionBot.Config.ActionBotTasks.TunnelTask;
import com.bt.BThack.impl.Module.PLAYER.ActionBot.Config.ActionBotTasks.WaitTask;
import com.bt.BThack.impl.Module.PLAYER.ActionBot.Config.Gui.EditTaskGuis.EditMoveTaskGui;
import com.bt.BThack.impl.Module.PLAYER.ActionBot.Config.Gui.EditTaskGuis.EditSendMessageTaskGui;
import com.bt.BThack.impl.Module.PLAYER.ActionBot.Config.Gui.EditTaskGuis.EditTunnelTaskGui;
import com.bt.BThack.impl.Module.PLAYER.ActionBot.Config.Gui.EditTaskGuis.EditWaitTaskGui;
import com.bt.BThack.impl.Module.PLAYER.ActionBot.Config.Gui.Utils.TaskButton;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

public class ActionBotConfigGui extends BThackGui {
    public static double widthFactor = 0;
    public static double heightFactor = 0;

    public static final int menuColor = new Color(85, 85, 85).hashCode();


    public static final double offsetFactor = 1.5;

    public static ScaledResolution scaledResolution;


    public static final ArrayList<TaskButton> taskButtons = new ArrayList<>();


    protected static short page = 0;

    protected static TaskButton selectedTask = null;

    @Override
    public void initGui() {
        buttons.clear();
        selectedTask = null;

        scaledResolution = new ScaledResolution(mc);

        widthFactor = scaledResolution.getScaledWidth() / 37D;
        heightFactor = scaledResolution.getScaledHeight() / 30D;

        buttons.add(new Button(1, (int) (scaledResolution.getScaledWidth() - (widthFactor * 4)), (int) (scaledResolution.getScaledHeight() - (heightFactor + heightFactor)), (int) widthFactor * 3, (int) heightFactor, "Quit"));
        buttons.add(new Button(2, (int) (scaledResolution.getScaledWidth() - (widthFactor * 4)), (int) (scaledResolution.getScaledHeight() - (heightFactor * 5)), (int) widthFactor * 3, (int) heightFactor, "Other Options"));
        buttons.add(new Button(3, (int) (scaledResolution.getScaledWidth() - (widthFactor * 4)), (int) (scaledResolution.getScaledHeight() - (heightFactor * 8)), (int) widthFactor * 3, (int) heightFactor, "Move"));
        buttons.add(new Button(4, (int) (scaledResolution.getScaledWidth() - (widthFactor * 4)), (int) (scaledResolution.getScaledHeight() - (heightFactor * 11)), (int) widthFactor * 3, (int) heightFactor, "Edit"));
        buttons.add(new Button(5, (int) (scaledResolution.getScaledWidth() - (widthFactor * 4)), (int) (scaledResolution.getScaledHeight() - (heightFactor * 14)), (int) widthFactor * 3, (int) heightFactor, "Delete"));
        buttons.add(new Button(6, (int) (scaledResolution.getScaledWidth() - (widthFactor * 4)), (int) (scaledResolution.getScaledHeight() - (heightFactor * 17)), (int) widthFactor * 3, (int) heightFactor, "Add"));

        //Кнопки для пролистывания меню
        buttons.add(new Button(-1, (int) (widthFactor * 1.75), (int) (heightFactor * 7.75), (int) (widthFactor * 0.75), (int) (heightFactor * 0.75), "Up"));
        buttons.add(new Button(-2, (int) (widthFactor + widthFactor), (int) (scaledResolution.getScaledHeight() - (heightFactor * 4.75)), (int) widthFactor, (int) (heightFactor * 0.75), "Down"));
        //////

        updateTaskList();
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        drawConfigBackGround();

        //Menu
        RenderUtils.drawOutlineRect((int) widthFactor - 1, (int) (heightFactor * 7) - 1, (int) (widthFactor * 27) + 1, (int) (scaledResolution.getScaledHeight() - (heightFactor * 4)) + 1, 1, Color.white.hashCode());
        Gui.drawRect((int) widthFactor, (int) (heightFactor * 7), (int) (widthFactor * 27), (int) (scaledResolution.getScaledHeight() - (heightFactor * 4)), menuColor);

        int offset = 0;

        //Текст с текущей страницей
        FontUtil.drawText("Page: " + (page + 1), (int) widthFactor * 3, (int) (heightFactor * 7.2), Color.white.hashCode());


        //Рисует все таски, которые на данный момент видны
        for (int i = 0; i < 12; i++) {
            if (i + (page * 12) >= ActionBotConfig.tasks.size()) {
                break;
            }
            ActionBotTask task = ActionBotConfig.tasks.get(i + (page * 12));
            if (i < 11 && !task.getName().equals("End"))
                Gui.drawRect((int) ((widthFactor * 9) - 1), (int) (heightFactor * 8  + ((heightFactor * offsetFactor) * offset)), (int) ((widthFactor * 9) + 1), (int) (((heightFactor * 8) + ((heightFactor * offsetFactor) * offset)) + ((heightFactor * offsetFactor))), Color.white.hashCode());


            TaskButton taskButton = taskButtons.get(i + (page * 12));
            taskButton.setOffset(offset);
            if (!task.isStartOrEndTask())
                taskButton.updateButton(mouseX, mouseY);
            taskButton.renderButton();
            offset++;
        }
        //////


        //Description
        RenderUtils.drawOutlineRect((int) widthFactor - 1, (int) (scaledResolution.getScaledHeight() - (heightFactor * 3)) - 1, (int) (scaledResolution.getScaledWidth() - (widthFactor * 10)) + 1, (int) (scaledResolution.getScaledHeight() - heightFactor) + 1, 1, ColourUtils.rainbow(100));
        Gui.drawRect((int) widthFactor, (int) (scaledResolution.getScaledHeight() - (heightFactor * 3)), (int) (scaledResolution.getScaledWidth() - (widthFactor * 10)), (int) (scaledResolution.getScaledHeight() - heightFactor), Color.black.hashCode());

        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    private static String getTaskName(ActionBotTask task) {
        String taskName;

        if (task.isMoveTask()) {
            MoveTask moveTask = (MoveTask) task;

            taskName = moveTask.getName() + ":  X: " + moveTask.getNeedX() + "  Z: " + moveTask.getNeedZ() + "  Scaffold: " + moveTask.isScaffold() + "  Type: " + moveTask.getType().name();
        } else if (task.isSendMessageTask()) {
            SendMessageTask messageTask = (SendMessageTask) task;

            taskName = messageTask.getName() + ": " + messageTask.getMessage();
        } else if (task.isTunnelTask()) {
            TunnelTask tunnelTask = (TunnelTask) task;

            taskName = tunnelTask.getName() + ":  Direction: " + tunnelTask.getDirection().name() + "  Length: " + tunnelTask.getLength();
        } else if (task.isWaitTask()) {
            WaitTask waitTask = (WaitTask) task;

            taskName = waitTask.getName() + ": " + waitTask.getSeconds() + "s.";
        } else  {
            taskName = task.getName();
        }
        return taskName;
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        super.mouseClicked(mouseX, mouseY, mouseButton);

        if (activeButton.getId() == 1) {
            mc.displayGuiScreen(null);
        }
        if (activeButton.getId() == 3) {
            if (selectedTask != null) {
                mc.displayGuiScreen(new MoveTaskGui(selectedTask));
            }
        }
        if (activeButton.getId() == 4) {
            if (ActionBotConfig.tasks.size() > 2) {
                if (selectedTask != null) {
                    if (selectedTask.task.isMoveTask()) {
                        mc.displayGuiScreen(new EditMoveTaskGui(selectedTask));
                    } else if (selectedTask.task.isSendMessageTask()) {
                        mc.displayGuiScreen(new EditSendMessageTaskGui(selectedTask));
                    } else if (selectedTask.task.isTunnelTask()) {
                        mc.displayGuiScreen(new EditTunnelTaskGui(selectedTask));
                    } else if (selectedTask.task.isWaitTask()) {
                        mc.displayGuiScreen(new EditWaitTaskGui(selectedTask));
                    }
                }
            }
        }
        if (activeButton.getId() == 5) {
            if (ActionBotConfig.tasks.size() > 2) {
                if (selectedTask == null) {
                    ActionBotConfig.removeActionTask();
                    mc.displayGuiScreen(new ActionBotConfigGui());
                } else {
                    removeTaskFromList(
                            selectedTask.getId()
                    );
                }
            }
        }
        if (activeButton.getId() == 6) {
            if (selectedTask != null)
                AddingTaskGui.substituteTask = selectedTask;
            else
                AddingTaskGui.substituteTask = null;
            mc.displayGuiScreen(new AddingTaskGui());
        }





        if (activeButton.getId() == -2) {
            page++;
        }
        if (activeButton.getId() == -1) {
            if (page > 0)
                page--;
        }







        int offset = 0;

        selectedTask = null;

        for (int i = 0; i < 12; i++) {
            if (i + (page * 12) >= ActionBotConfig.tasks.size()) {
                break;
            }
            ActionBotTask task = ActionBotConfig.tasks.get(i + (page * 12));

            TaskButton taskButton = taskButtons.get(i + (page * 12));
            taskButton.setOffset(offset);

            if (!task.isStartOrEndTask()) {
                taskButton.mouseClicked(mouseX, mouseY);

                if (taskButton.isSelected()) {
                    selectedTask = taskButton;
                }
            }


            offset++;
        }
    }

    @Override
    public void onGuiClosed() {
        BThack.instance.settingsManager.getModuleSettingByName("ActionBot", "Open Config").setScreenVal(new ActionBotConfigGui());
    }

    public static void drawConfigBackGround() {
        RenderUtils.draw4ColorRect( 0, 0, scaledResolution.getScaledWidth(), scaledResolution.getScaledHeight(), new Color(0,0,0, 128).hashCode(), new Color(0,0,0, 128).hashCode(), new Color(161,0, 255, 255).hashCode(), new Color(255, 0, 0, 255).hashCode());
    }



    public static void updateTaskList() {
        taskButtons.clear();

        for (int i = 0; i < ActionBotConfig.tasks.size(); i++) {
            ActionBotTask task = ActionBotConfig.tasks.get(i);

            if (task.isStartOrEndTask()) {
                taskButtons.add(new TaskButton(i, getTaskName(task), 0, task));
            } else {
                taskButtons.add(new TaskButton(i, i + ". " + getTaskName(task), 0, task));
            }
        }
    }

    public static void removeTaskFromList(int index) {
        Minecraft mc = Minecraft.getMinecraft();

        ActionBotConfig.tasks.remove(index);

        mc.displayGuiScreen(new ActionBotConfigGui());
    }
}
