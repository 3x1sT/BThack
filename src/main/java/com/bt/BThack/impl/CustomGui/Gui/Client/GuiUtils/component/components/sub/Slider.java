package com.bt.BThack.impl.CustomGui.Gui.Client.GuiUtils.component.components.sub;

import java.awt.Color;
import java.math.BigDecimal;
import java.math.RoundingMode;

import com.bt.BThack.System.Client;
import com.bt.BThack.api.Module.Module;
import com.bt.BThack.api.Utils.Font.FontUtil;
import com.bt.BThack.api.Utils.Render.RenderUtils;
import com.bt.BThack.impl.CustomGui.Gui.Client.GuiUtils.Settings.Setting;
import com.bt.BThack.impl.CustomGui.Gui.Client.GuiUtils.component.Component;
import com.bt.BThack.impl.CustomGui.Gui.Client.GuiUtils.component.components.Button;

import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;

public class Slider extends Component {

	private final Setting op;
	private final Button parent;
	private final Module module;
	private int offset;
	private int x;
	private int y;
	private boolean dragging = false;

	private double renderWidth;
	
	public Slider(Setting value, Button button, int offset, Module module) {
		this.op = value;
		this.parent = button;
		this.module = module;
		this.x = button.parent.getX() + button.parent.getWidth();
		this.y = button.parent.getY() + button.offset;
		this.offset = offset;
	}
	
	@Override
	public void renderComponent() {
		if (!this.getVisible()) return;

		if (Module.getCheckbox("ClickGui", "Rainbow")) {
			int type = (int) Module.getSlider("ClickGui", "Rainbow speed");
			RenderUtils.drawGradientRainbowRect(parent.parent.getX(), parent.parent.getY() + offset + 11, parent.parent.getX() + (int) renderWidth, parent.parent.getY() + offset + 15, type);
		} else {
			Gui.drawRect(parent.parent.getX(), parent.parent.getY() + offset + 11, parent.parent.getX() + (int) renderWidth, parent.parent.getY() + offset + 15, Module.getCheckbox("ClickGui", "Custom Color") ? new Color((int) Module.getSlider("ClickGui", "Red"), (int) Module.getSlider("ClickGui", "Green"), (int) Module.getSlider("ClickGui", "Blue")).getRGB() : new Color(Client.colourTheme.getFontColour()).hashCode());
		}

		FontUtil.drawText(this.op.getName() + ": " + this.op.getValDouble(), parent.parent.getX() + 2, (parent.parent.getY() + offset + 1), Client.colourTheme.getModuleDisabledColour());
	}

	@Override
	public void renderRect(BufferBuilder builder, Tessellator tessellator) {
		if (!this.getVisible()) return;

		RenderUtils.drawNoGL11Rect(parent.parent.getX(), parent.parent.getY() + offset, parent.parent.getX() + parent.parent.getWidth(), parent.parent.getY() + offset + 15, new Color(Client.colourTheme.getBackgroundFontColour()).hashCode(), builder, tessellator);
		RenderUtils.drawNoGL11Rect(parent.parent.getX(), parent.parent.getY() + offset, parent.parent.getX(), parent.parent.getY() + offset + 15, new Color(Client.colourTheme.getBackgroundFontColour()).hashCode(), builder, tessellator);
		RenderUtils.drawNoGL11Rect(parent.parent.getX(), parent.parent.getY() + offset + 11, parent.parent.getX() + 100, parent.parent.getY() + offset + 15, Color.GRAY.darker().darker().darker().getRGB(), builder, tessellator);
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
		if (!this.getVisible() || !parent.open) return;

		this.y = parent.parent.getY() + offset;
		this.x = parent.parent.getX();

		double diff = Math.min(100, Math.max(0, mouseX - this.x));

		double min = op.getMin();
		double max = op.getMax();

		renderWidth = (100) * (op.getValDouble() - min) / (max - min);

		if (dragging) {
			if (diff == 0) {
				op.setValDouble(op.getMin());
			} else {
				double newValue = roundToPlace(((diff / 100) * (max - min) + min), 2);
				op.setValDouble(newValue);
			}
		}
	}
	
	private static double roundToPlace(double value, int places) {
		if (places < 0) {
			throw new IllegalArgumentException();
		}
		BigDecimal bd = new BigDecimal(value);
		bd = bd.setScale(places, RoundingMode.HALF_UP);
		return bd.doubleValue();
    }
	
	@Override
	public void mouseClicked(int mouseX, int mouseY, int button) {

		if (!this.getVisible() || !parent.open) return;

		if (isMouseOnButton(mouseX, mouseY))
			dragging = true;
		/*
		if (isMouseOnButtonD(mouseX, mouseY) && button == 0 && this.parent.open) {
			dragging = true;
		}
		if (isMouseOnButtonI(mouseX, mouseY) && button == 0 && this.parent.open) {
			dragging = true;
		}

		 */
	}
	
	@Override
	public void mouseReleased(int mouseX, int mouseY, int mouseButton) {
		dragging = false;
	}


	public boolean isMouseOnButton(int x, int y) {
        return isMouseOnButtonD(x, y) || isMouseOnButtonI(x, y);
	}
	
	private boolean isMouseOnButtonD(int x, int y) {
		return x > this.x && x < this.x + (parent.parent.getWidth() / 2 + 1) && y > this.y && y < this.y + 15;
	}
	
	private boolean isMouseOnButtonI(int x, int y) {
		return x > this.x + parent.parent.getWidth() / 2 && x < this.x + parent.parent.getWidth() && y > this.y && y < this.y + 15;
	}
}
