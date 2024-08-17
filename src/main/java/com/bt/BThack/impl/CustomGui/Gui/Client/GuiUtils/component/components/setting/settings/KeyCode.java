package com.bt.BThack.impl.CustomGui.Gui.Client.GuiUtils.component.components.setting.settings;

import com.bt.BThack.System.Client;
import com.bt.BThack.api.Module.Module;
import com.bt.BThack.api.Utils.Font.FontUtil;
import com.bt.BThack.api.Managers.Settings.Setting;
import com.bt.BThack.impl.CustomGui.Gui.Client.GuiUtils.component.components.setting.AbstractSetting;
import com.bt.BThack.impl.CustomGui.Gui.Client.GuiUtils.component.components.Button;
import org.lwjgl.input.Keyboard;

public class KeyCode extends AbstractSetting {

    private boolean binding;

    public KeyCode(Button button, int offset, Setting setting, Module module) {
        super(offset, button, module, setting);

        this.x = button.parent.getX() + button.parent.getWidth();
        this.y = button.parent.getY() + button.offset;
    }

    @Override
    public void renderComponent() {
        if (!this.getVisible()) return;

        FontUtil.drawText(binding ? "< PRESS KEY >" : (op.getName() + ": " + Keyboard.getKeyName(this.op.getKeyCodeVal())), parent.parent.getX() + 2, parent.parent.getY() + offset + 4, Client.colourTheme.getModuleDisabledColour());
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
        else if(this.binding) {
            this.op.setKeyCodeVal(0);
            this.binding = false;
        }
    }
}
