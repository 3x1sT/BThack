package com.bt.BThack.impl.CustomGui.Gui.Client.GuiUtils.component.components.setting;

import com.bt.BThack.BThack;
import com.bt.BThack.System.Client;
import com.bt.BThack.api.Module.Module;
import com.bt.BThack.api.Utils.Render.ColourUtils;
import com.bt.BThack.api.Utils.Render.RenderUtils;
import com.bt.BThack.api.Managers.Settings.Setting;
import com.bt.BThack.impl.CustomGui.Gui.Client.GuiUtils.component.Component;
import com.bt.BThack.impl.CustomGui.Gui.Client.GuiUtils.component.components.Button;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;

import java.awt.*;

public abstract class AbstractSetting extends Component {

    public int x;
    public int y;

    public int offset;

    public boolean hovered;

    private boolean visible = true;

    public final com.bt.BThack.impl.CustomGui.Gui.Client.GuiUtils.component.components.Button parent;
    public final Module module;
    public final Setting op;

    public AbstractSetting(int offset, Button button, Module module, Setting op) {
        this.offset = offset;
        this.parent = button;
        this.module = module;
        this.op = op;
    }

    @Override
    public abstract void renderComponent();

    @Override
    public void renderRect(BufferBuilder builder, Tessellator tessellator) {
        if (!this.getVisible()) return;

        RenderUtils.drawNoGL11Rect(parent.parent.getX(), parent.parent.getY() + offset, parent.parent.getX() + (parent.parent.getWidth()), parent.parent.getY() + offset + 15, this.hovered ? ColourUtils.integrateAlpha(new Color(Client.colourTheme.getBackgroundFontHoveredColour()).hashCode(), (int) (parent.effect.getColor().getAlpha() / 1.17)) : ColourUtils.integrateAlpha(new Color(Client.colourTheme.getBackgroundFontColour()).hashCode(), (int) (parent.effect.getColor().getAlpha() / 1.17)), builder, tessellator);
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
    public abstract void updateComponent(int mouseX, int mouseY);

    @Override
    public abstract void mouseClicked(int mouseX, int mouseY, int button);

    public boolean isMouseOnButton(int x, int y) {
        return x > this.x && x < this.x + 100 && y > this.y && y < this.y + 15;
    }

    public boolean getVisible() {
        return this.visible;
    }

    public void setVisible(boolean in) {
        this.visible = in;
    }
}
