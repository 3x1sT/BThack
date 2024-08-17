package com.bt.BThack.impl.CustomGui.Gui.Client.GuiUtils.component.components.setting.settings;

import com.bt.BThack.System.Client;
import com.bt.BThack.api.Module.Module;
import com.bt.BThack.api.Utils.Font.FontUtil;
import com.bt.BThack.api.Utils.Interfaces.Mc;
import com.bt.BThack.api.Managers.Settings.Setting;
import com.bt.BThack.impl.CustomGui.Gui.Client.GuiUtils.component.components.setting.AbstractSetting;
import com.bt.BThack.impl.CustomGui.Gui.Client.GuiUtils.component.components.Button;

public class OpenGuiButton extends AbstractSetting implements Mc {

    public OpenGuiButton(Setting option, Button button, int offset, Module module) {
        super(offset, button, module, option);

        this.x = button.parent.getX() + button.parent.getWidth();
        this.y = button.parent.getY() + button.offset;
    }

    @Override
    public void renderComponent() {
        if (!this.getVisible()) return;

        FontUtil.drawText(this.op.getName() + " ... ", parent.parent.getX() + 2, parent.parent.getY() + offset + 4, Client.colourTheme.getModuleDisabledColour());
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
}
