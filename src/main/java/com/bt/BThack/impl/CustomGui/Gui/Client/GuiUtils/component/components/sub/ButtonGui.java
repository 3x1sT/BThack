package com.bt.BThack.impl.CustomGui.Gui.Client.GuiUtils.component.components.sub;

import com.bt.BThack.System.Client;
import com.bt.BThack.api.Module.Module;
import com.bt.BThack.api.Utils.Font.FontUtil;
import com.bt.BThack.api.Utils.Render.ColourUtils;
import com.bt.BThack.api.Utils.Render.RainbowUtils;
import com.bt.BThack.api.Utils.Render.RenderUtils;
import com.bt.BThack.impl.CustomGui.Gui.Client.GuiUtils.Settings.Setting;
import com.bt.BThack.impl.CustomGui.Gui.Client.GuiUtils.component.Component;
import com.bt.BThack.impl.CustomGui.Gui.Client.GuiUtils.component.components.Button;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;

import java.awt.*;

public class ButtonGui extends Component {


    private boolean hovered;
    private final Setting op;
    private final Button parent;
    private final Module module;
    private int offset;
    private int x;
    private int y;

    //private final Module mod;

    public ButtonGui(Setting option, Button button, int offset, Module module) {
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

        int rainbowType = (int) Module.getSlider("ClickGui", "Rainbow speed");
        float speed = RainbowUtils.getRainbowRectSpeed(rainbowType)[0];
        int delay = (int) RainbowUtils.getRainbowRectSpeed(rainbowType)[1];

        FontUtil.drawText(this.op.getName(), parent.parent.getX() + 14, parent.parent.getY() + offset + 4, Client.colourTheme.getModuleDisabledColour());

        if (this.op.getValBoolean()) {
            Gui.drawRect(parent.parent.getX() + 4 + 4, parent.parent.getY() + offset + 7, parent.parent.getX() + 8 + 4, parent.parent.getY() + offset + 11, Module.getCheckbox("ClickGui", "Rainbow") ? ColourUtils.rainbow(delay, speed) : Module.getCheckbox("ClickGui", "Custom Color") ? new Color((int) Module.getSlider("ClickGui", "Red"), (int) Module.getSlider("ClickGui", "Green"), (int) Module.getSlider("ClickGui", "Blue")).getRGB() : new Color(Client.colourTheme.getFontColour()).hashCode());
        } else {
            Gui.drawRect(parent.parent.getX() + 4 + 4, parent.parent.getY() + offset + 7, parent.parent.getX() + 8 + 4, parent.parent.getY() + offset + 11, Module.getCheckbox("ClickGui", "Rainbow") ? ColourUtils.rainbow(delay, speed) : Module.getCheckbox("ClickGui", "Custom Color") ? new Color((int) Module.getSlider("ClickGui", "Red"), (int) Module.getSlider("ClickGui", "Green"), (int) Module.getSlider("ClickGui", "Blue")).getRGB() : new Color(Client.colourTheme.getFontColour()).hashCode());
            Gui.drawRect(parent.parent.getX() + 5 + 4, parent.parent.getY() + offset + 8, parent.parent.getX() + 7 + 4, parent.parent.getY() + offset + 10, new Color(Client.colourTheme.getBackgroundFontHoveredColour()).hashCode());
        }
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
            switch (op.dependenceType) {
                case "Combo":
                    this.setVisible(Module.getMode(module.name, op.dependenceName).equals(op.dependenceSVal));
                    break;
                case "Check":
                    this.setVisible(Module.getCheckbox(module.name, op.dependenceName) == op.dependenceBVal);
                    break;
                case "Slider":
                    this.setVisible(!(Module.getSlider(module.name, op.dependenceName) < op.dependenceDMin) && !(op.dependenceDMax < Module.getSlider(module.name, op.dependenceName)));
                    break;
            }
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
            this.op.setValBoolean(!op.getValBoolean());
        }
    }

    public boolean isMouseOnButton(int x, int y) {
        return x > this.x && x < this.x + 100 && y > this.y && y < this.y + 15;
    }
}
