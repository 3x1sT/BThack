package com.bt.BThack.impl.CustomGui.Gui.Client.GuiUtils.component.components.setting.settings;

import java.awt.Color;

import com.bt.BThack.System.Client;
import com.bt.BThack.api.Module.Module;
import com.bt.BThack.api.Utils.Font.FontUtil;
import com.bt.BThack.api.Utils.Render.ColourUtils;
import com.bt.BThack.api.Utils.Render.RainbowUtils;
import com.bt.BThack.api.Utils.Render.RenderUtils;
import com.bt.BThack.api.Managers.Settings.Setting;

import com.bt.BThack.impl.CustomGui.Gui.Client.GuiUtils.component.components.setting.AbstractSetting;
import com.bt.BThack.impl.CustomGui.Gui.Client.GuiUtils.component.components.Button;

public class Checkbox extends AbstractSetting {
	
	public Checkbox(Setting option, Button button, int offset, Module module) {
		super(offset, button, module, option);

		this.x = button.parent.getX() + button.parent.getWidth();
		this.y = button.parent.getY() + button.offset;
	}

	@Override
	public void renderComponent() {
		if (!this.getVisible()) return;

		int rainbowType = (int) Module.getSlider("ClickGui", "Rainbow speed");
		float speed = RainbowUtils.getRainbowRectSpeed(rainbowType)[0];
		int delay = (int) RainbowUtils.getRainbowRectSpeed(rainbowType)[1];

		if (isCheck()) {
			RenderUtils.drawHorizontalGradientRect(parent.parent.getX(), parent.parent.getY() + offset, parent.parent.getX() + (parent.parent.getWidth() / 2), parent.parent.getY() + offset + 15, Module.getCheckbox("ClickGui", "Rainbow") ? ColourUtils.rainbow(delay, speed) : Module.getCheckbox("ClickGui", "Custom Color") ? new Color((int) Module.getSlider("ClickGui", "Red"), (int) Module.getSlider("ClickGui", "Green"), (int) Module.getSlider("ClickGui", "Blue")).getRGB() : new Color(Client.colourTheme.getFontColour()).hashCode(), new Color(0,0,0,0).hashCode());
		}

		FontUtil.drawText(this.op.getName(), parent.parent.getX() + 7, parent.parent.getY() + offset + (Client.getModuleByName("Font").isEnabled() ? 2 : 4), Client.colourTheme.getModuleDisabledColour());
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
			this.op.setValBoolean(!isCheck());
		}
	}

	public boolean isCheck() {
		return this.op.getValBoolean();
	}
}
