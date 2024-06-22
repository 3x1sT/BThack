package com.bt.BThack.impl.CustomGui.Gui.Client.GuiUtils.component.components.sub;

import java.awt.Color;

import com.bt.BThack.Client;
import com.bt.BThack.api.Module.Module;
import com.bt.BThack.api.Utils.Font.FontUtil;
import com.bt.BThack.api.Utils.Render.ColourUtils;
import com.bt.BThack.api.Utils.Render.RainbowUtils;
import com.bt.BThack.api.Utils.Render.RenderUtils;
import com.bt.BThack.impl.CustomGui.Gui.Client.GuiUtils.Settings.Setting;
import com.bt.BThack.impl.CustomGui.Gui.Client.GuiUtils.component.Component;

import net.minecraft.client.gui.Gui;
import com.bt.BThack.impl.CustomGui.Gui.Client.GuiUtils.component.components.Button;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;

public class Checkbox extends Component {

	private boolean hovered;
	private final Setting op;
	private final Button parent;
	private int offset;
	private int x;
	private int y;

	//private final Module mod;
	
	public Checkbox(Setting option, Button button, int offset) {
		this.op = option;
		this.parent = button;
		//this.mod = mod;
		this.x = button.parent.getX() + button.parent.getWidth();
		this.y = button.parent.getY() + button.offset;
		this.offset = offset;
	}

	@Override
	public void renderComponent() {
		int rainbowType = (int) Module.getSlider("ClickGui", "Rainbow speed");
		float speed = RainbowUtils.getRainbowRectSpeed(rainbowType)[0];
		int delay = (int) RainbowUtils.getRainbowRectSpeed(rainbowType)[1];

		//Gui.drawRect(parent.parent.getX(), parent.parent.getY() + offset, parent.parent.getX() + (parent.parent.getWidth()), parent.parent.getY() + offset + 15, this.hovered ? new Color(Client.colourTheme.getBackgroundFontHoveredColour()).hashCode() : new Color(Client.colourTheme.getBackgroundFontColour()).hashCode());
		//glPushMatrix();
		//glScalef(0.7f, 0.7f, 0.5f);
		FontUtil.drawText(this.op.getName(), parent.parent.getX() + 14, parent.parent.getY() + offset + 4, Client.colourTheme.getModuleDisabledColour());
		//glPopMatrix();
		if (this.op.getValBoolean()) {
			Gui.drawRect(parent.parent.getX() + 4 + 4, parent.parent.getY() + offset + 7, parent.parent.getX() + 8 + 4, parent.parent.getY() + offset + 11, Module.getCheckbox("ClickGui", "Rainbow") ? ColourUtils.rainbow(delay, speed) : Module.getCheckbox("ClickGui", "Custom Color") ? new Color((int) Module.getSlider("ClickGui", "Red"), (int) Module.getSlider("ClickGui", "Green"), (int) Module.getSlider("ClickGui", "Blue")).getRGB() : new Color(Client.colourTheme.getFontColour()).hashCode());
		} else {
			Gui.drawRect(parent.parent.getX() + 4 + 4, parent.parent.getY() + offset + 7, parent.parent.getX() + 8 + 4, parent.parent.getY() + offset + 11, Module.getCheckbox("ClickGui", "Rainbow") ? ColourUtils.rainbow(delay, speed) : Module.getCheckbox("ClickGui", "Custom Color") ? new Color((int) Module.getSlider("ClickGui", "Red"), (int) Module.getSlider("ClickGui", "Green"), (int) Module.getSlider("ClickGui", "Blue")).getRGB() : new Color(Client.colourTheme.getFontColour()).hashCode());
			Gui.drawRect(parent.parent.getX() + 5 + 4, parent.parent.getY() + offset + 8, parent.parent.getX() + 7 + 4, parent.parent.getY() + offset + 10, new Color(Client.colourTheme.getBackgroundFontHoveredColour()).hashCode());
		}
	}

	@Override
	public void renderRect(BufferBuilder builder, Tessellator tessellator) {
		RenderUtils.drawNoGL11Rect(parent.parent.getX(), parent.parent.getY() + offset, parent.parent.getX() + (parent.parent.getWidth()), parent.parent.getY() + offset + 15, this.hovered ? new Color(Client.colourTheme.getBackgroundFontHoveredColour()).hashCode() : new Color(Client.colourTheme.getBackgroundFontColour()).hashCode(), builder, tessellator);
	}
	
	@Override
	public void setOff(int newOff) {
		offset = newOff;
	}
	
	@Override
	public void updateComponent(int mouseX, int mouseY) {
		this.hovered = isMouseOnButton(mouseX, mouseY);
		this.y = parent.parent.getY() + offset;
		this.x = parent.parent.getX();
	}
	
	@Override
	public void mouseClicked(int mouseX, int mouseY, int button) {
		if (isMouseOnButton(mouseX, mouseY) && button == 0 && this.parent.open) {
			this.op.setValBoolean(!op.getValBoolean());
		}
	}
	
	public boolean isMouseOnButton(int x, int y) {
		return x > this.x && x < this.x + 100 && y > this.y && y < this.y + 15;
    }
}
