package com.bt.BThack.impl.CustomGui.Gui.Client.GuiUtils.component.components.sub;

import com.bt.BThack.BThack;
import com.bt.BThack.System.Client;
import com.bt.BThack.api.Module.Module;
import com.bt.BThack.api.Utils.Font.FontUtil;
import com.bt.BThack.api.Utils.Interfaces.Mc;
import com.bt.BThack.api.Utils.Render.RenderUtils;
import com.bt.BThack.impl.CustomGui.Gui.Client.GuiUtils.Settings.Setting;
import com.bt.BThack.impl.CustomGui.Gui.Client.GuiUtils.component.Component;
import com.bt.BThack.impl.CustomGui.Gui.Client.GuiUtils.component.components.Button;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;

import java.awt.*;

public class OpenGuiButton extends Component implements Mc {


    private boolean hovered;
    private final Setting op;
    private final Button parent;
    private final Module module;
    private int offset;
    private int x;
    private int y;

    //private final Module mod;

    public OpenGuiButton(Setting option, Button button, int offset, Module module) {
        this.op = option;
        this.parent = button;
        this.module = module;
        this.x = button.parent.getX() + button.parent.getWidth();
        this.y = button.parent.getY() + button.offset;
        this.offset = offset;
    }

    @Override
    public void renderComponent() {
        if (!this.getVisible()) return;

        FontUtil.drawText(this.op.getName() + " ... ", parent.parent.getX() + 2, parent.parent.getY() + offset + 4, Client.colourTheme.getModuleDisabledColour());
    }

    @Override
    public void renderRect(BufferBuilder builder, Tessellator tessellator) {
        if (!this.getVisible()) return;

        RenderUtils.drawNoGL11Rect(parent.parent.getX(), parent.parent.getY() + offset, parent.parent.getX() + (parent.parent.getWidth()), parent.parent.getY() + offset + 15, this.hovered ? new Color(Client.colourTheme.getBackgroundFontHoveredColour()).hashCode() : new Color(Client.colourTheme.getBackgroundFontColour()).hashCode(), builder, tessellator);
    }

    @Override
    public void setOff(int newOff) {
        offset = newOff;
    }

    @Override
    public void updateDependencies(int offset) {
        if (op.dependenceType != null) {
            if (!BThack.instance.settingsManager.getModuleSettingByName(module.name, op.dependenceName).isVisible()) {
                this.setVisible(false);
                op.setVisible(false);
                return;
            }

            boolean visible = true;
            switch (op.dependenceType) {
                case "Combo":
                    visible = Module.getMode(module.name, op.dependenceName).equals(op.dependenceSVal);
                    break;
                case "Check":
                    visible = Module.getCheckbox(module.name, op.dependenceName) == op.dependenceBVal;
                    break;
                case "Slider":
                    visible = !(Module.getSlider(module.name, op.dependenceName) < op.dependenceDMin) && !(op.dependenceDMax < Module.getSlider(module.name, op.dependenceName));
                    break;
            }
            this.setVisible(visible);
            op.setVisible(visible);
        }
    }

    @Override
    public void updateComponent(int mouseX, int mouseY) {

        if (!this.getVisible()) return;

        this.hovered = isMouseOnButton(mouseX, mouseY);
        this.y = parent.parent.getY() + offset;
        this.x = parent.parent.getX();
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int button) {
        if (!this.getVisible()) return;

        if (isMouseOnButton(mouseX, mouseY) && button == 0 && this.parent.open) {
            mc.displayGuiScreen(op.getScreenVal());
        }
    }

    public boolean isMouseOnButton(int x, int y) {
        return x > this.x && x < this.x + 100 && y > this.y && y < this.y + 15;
    }
}
