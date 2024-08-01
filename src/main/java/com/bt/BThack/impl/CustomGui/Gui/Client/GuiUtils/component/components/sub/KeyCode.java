package com.bt.BThack.impl.CustomGui.Gui.Client.GuiUtils.component.components.sub;

import com.bt.BThack.BThack;
import com.bt.BThack.System.Client;
import com.bt.BThack.api.Module.Module;
import com.bt.BThack.api.Utils.Font.FontUtil;
import com.bt.BThack.api.Utils.Render.RenderUtils;
import com.bt.BThack.impl.CustomGui.Gui.Client.GuiUtils.Settings.Setting;
import com.bt.BThack.impl.CustomGui.Gui.Client.GuiUtils.component.Component;
import com.bt.BThack.impl.CustomGui.Gui.Client.GuiUtils.component.components.Button;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import org.lwjgl.input.Keyboard;

import java.awt.*;

public class KeyCode extends Component {

    private boolean hovered;
    private boolean binding;
    private final Button parent;
    private final Setting op;
    private final Module module;
    private int offset;
    private int x;
    private int y;

    public KeyCode(Button button, int offset, Setting setting, Module module) {
        this.parent = button;
        this.module = module;
        this.x = button.parent.getX() + button.parent.getWidth();
        this.y = button.parent.getY() + button.offset;
        this.offset = offset;
        this.op = setting;
    }

    @Override
    public void setOff(int newOff) {
        offset = newOff;
    }

    @Override
    public void renderComponent() {
        if (!this.getVisible()) return;

        FontUtil.drawText(binding ? "< PRESS KEY >" : (op.getName() + ": " + Keyboard.getKeyName(this.op.getKeyCodeVal())), parent.parent.getX() + 2, parent.parent.getY() + offset + 4, Client.colourTheme.getModuleDisabledColour());
    }

    @Override
    public void renderRect(BufferBuilder builder, Tessellator tessellator) {
        if (!this.getVisible()) return;

        RenderUtils.drawNoGL11Rect(parent.parent.getX(), parent.parent.getY() + offset, parent.parent.getX() + parent.parent.getWidth(), parent.parent.getY() + offset + 15, this.hovered ? new Color(Client.colourTheme.getBackgroundFontHoveredColour()).hashCode() : new Color(Client.colourTheme.getBackgroundFontColour()).hashCode(), builder, tessellator);
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
        if (!this.getVisible() || !parent.open) return;

        this.hovered = isMouseOnButton(mouseX, mouseY);
        this.y = parent.parent.getY() + offset;
        this.x = parent.parent.getX();
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int button) {
        if (!this.getVisible() || !parent.open) return;

        if(isMouseOnButton(mouseX, mouseY) && button == 0 && this.parent.open) {
            this.binding = !this.binding;
        }
    }

    @Override
    public void keyTyped(char typedChar, int key) {
        if (!this.getVisible()) return;

        if(this.binding && key != Keyboard.KEY_DELETE) {
            this.op.setKeyCodeVal(key);
            this.binding = false;
        }
        else if(this.binding && key == Keyboard.KEY_DELETE) {
            this.op.setKeyCodeVal(0);
            this.binding = false;
        }
    }

    public boolean isMouseOnButton(int x, int y) {
        return x > this.x && x < this.x + 100 && y > this.y && y < this.y + 15;
    }
}
