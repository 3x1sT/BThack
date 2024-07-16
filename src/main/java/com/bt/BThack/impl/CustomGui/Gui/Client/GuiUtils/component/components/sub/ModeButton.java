package com.bt.BThack.impl.CustomGui.Gui.Client.GuiUtils.component.components.sub;

//import com.bt.BThack.api.Module.Module;

import com.bt.BThack.System.Client;
import com.bt.BThack.api.Module.Module;
import com.bt.BThack.api.Utils.Font.FontUtil;
import com.bt.BThack.api.Utils.Render.RenderUtils;
import com.bt.BThack.impl.CustomGui.Gui.Client.GuiUtils.Settings.Setting;
import com.bt.BThack.impl.CustomGui.Gui.Client.GuiUtils.component.Component;
import com.bt.BThack.impl.CustomGui.Gui.Client.GuiUtils.component.components.Button;


import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;

import java.awt.*;

public class ModeButton extends Component {

	private boolean hovered;
	private final Button parent;
	private final Setting op;
	private int offset;
	private int x;
	private int y;
	private final Module module;

	public ModeButton(Setting set, Button button, int offset, int modeIndex, Module module) {
		this.op = set;
		this.parent = button;
		this.module = module;
		this.x = button.parent.getX() + button.parent.getWidth();
		this.y = button.parent.getY() + button.offset;
		this.offset = offset;

		set.setValString(set.getOptions().get(modeIndex));
	}
	
	@Override
	public void setOff(int newOff) {
		offset = newOff;
	}
	
	@Override
	public void renderComponent() {
		if (!this.getVisible()) return;

		FontUtil.drawText(this.op.getName() + ": " + this.op.getOptions().get(this.op.getIndex()), parent.parent.getX() + 2, parent.parent.getY() + offset + 4, Client.colourTheme.getModuleDisabledColour());
	}

	@Override
	public void renderRect(BufferBuilder builder, Tessellator tessellator) {
		if (!this.getVisible()) return;

		RenderUtils.drawNoGL11Rect(parent.parent.getX(), parent.parent.getY() + offset, parent.parent.getX() + parent.parent.getWidth(), parent.parent.getY() + offset + 15, this.hovered ? new Color(Client.colourTheme.getBackgroundFontHoveredColour()).hashCode() : new Color(Client.colourTheme.getBackgroundFontColour()).hashCode(), builder, tessellator);
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

		this.hovered = isMouseOnButton(mouseX, mouseY);
		this.y = parent.parent.getY() + offset;
		this.x = parent.parent.getX();
	}
	
	@Override
	public void mouseClicked(int mouseX, int mouseY, int button) {
		if (!this.getVisible() || !parent.open) return;

		if (isMouseOnButton(mouseX, mouseY) && button == 0 && this.parent.open) {
			int maxIndex = op.getOptions().size();

			if (op.getIndex() + 1 >= maxIndex)
				op.setIndex(0);
			else {
				int currentIndex = op.getIndex();
				op.setIndex(currentIndex + 1);
			}

			op.setValString(op.getOptions().get(op.getIndex()));
		}
	}

	public boolean isMouseOnButton(int x, int y) {
		return x > this.x && x < this.x + 100 && y > this.y && y < this.y + 15;
	}
}
