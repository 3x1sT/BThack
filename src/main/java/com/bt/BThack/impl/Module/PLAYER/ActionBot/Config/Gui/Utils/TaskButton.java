package com.bt.BThack.impl.Module.PLAYER.ActionBot.Config.Gui.Utils;

import com.bt.BThack.api.Utils.Font.FontUtil;
import com.bt.BThack.api.Utils.Render.ColourUtils;
import com.bt.BThack.api.Utils.Render.RenderUtils;
import com.bt.BThack.api.Utils.System.Buttons.Button;
import com.bt.BThack.impl.Module.PLAYER.ActionBot.Config.ActionBotTask;
import net.minecraft.client.gui.Gui;

import java.awt.*;

import static com.bt.BThack.impl.Module.PLAYER.ActionBot.Config.Gui.ActionBotConfigGui.*;

public class TaskButton extends Button {
    private boolean selected;
    private int offset;
    public final ActionBotTask task;


    public TaskButton(int id, String taskName, int offset, ActionBotTask task) {
        super(id ,(int) (widthFactor * 9), (int) (((heightFactor * 8) + ((heightFactor * offsetFactor) * offset)) + (heightFactor / 2)), (int) ((FontUtil.getStringWidth(taskName) / 2) * 1.2), (int) (heightFactor / 2), taskName);

        this.offset = offset;
        this.task = task;
    }

    short alpha = 255;
    boolean alphaIvert = true;

    @Override
    public void renderButton() {
        float stringWidth = (FontUtil.getStringWidth(this.text) / 2);

        Gui.drawRect((int) (this.getCenterX() - (stringWidth * 1.2)), (int) (this.getCenterY() - (heightFactor / 2)), (int) (this.getCenterX() + (stringWidth * 1.2)), (int) (this.getCenterY() + (heightFactor / 2)), Color.black.hashCode());
        FontUtil.drawText(this.text, (int) (this.getCenterX() - stringWidth), (int) ((heightFactor * 8) + ((heightFactor * offsetFactor) * offset) + (((heightFactor * offsetFactor) / 2) - (FontUtil.getStringHeight(this.text)))), Color.white.hashCode());

        if (selected) {
            RenderUtils.drawOutlineRect((int) (this.getCenterX() - (stringWidth * 1.2) - 1), (int) (this.getCenterY() - (heightFactor / 2) - 1), (int) (this.getCenterX() + (stringWidth * 1.2) + 1), (int) (this.getCenterY() + (heightFactor / 2) + 1), 1, ColourUtils.rainbow(100));
        }

        if (hovered) {
            if (!alphaIvert) {
                if (alpha < 255) {
                    alpha += 3;
                } else {
                    alphaIvert = true;
                }
            } else {
                if (alpha > 1) {
                    alpha -= 3;
                } else {
                    alphaIvert = false;
                }
            }

            Color hoveredColor = new Color(255,255,255, alpha);

            if (!selected) {
                RenderUtils.drawOutlineRect((int) (this.getCenterX() - (stringWidth * 1.2) - 1), (int) (this.getCenterY() - (heightFactor / 2) - 1), (int) (this.getCenterX() + (stringWidth * 1.2) + 1), (int) (this.getCenterY() + (heightFactor / 2) + 1), 1, hoveredColor.hashCode());
            } else {
                RenderUtils.drawOutlineRect((int) (this.getCenterX() - (stringWidth * 1.2) - 2), (int) (this.getCenterY() - (heightFactor / 2) - 2), (int) (this.getCenterX() + (stringWidth * 1.2) + 2), (int) (this.getCenterY() + (heightFactor / 2) + 2), 1, hoveredColor.hashCode());
            }
        }
    }

    @Override
    public void updateButton(int mouseX, int mouseY) {
        super.updateButton(mouseX, mouseY);


    }

    @Override
    public void mouseClicked(int mouseX, int mouseY) {
        this.selected = this.isMouseOnButton(mouseX, mouseY);
    }


    public void setOffset(int offset) {
        this.offset = offset;

        this.setCenterY((int) (((heightFactor * 8) + ((heightFactor * offsetFactor) * offset)) + (heightFactor / 2)));
    }

    public boolean isSelected() {
        return this.selected;
    }
}
