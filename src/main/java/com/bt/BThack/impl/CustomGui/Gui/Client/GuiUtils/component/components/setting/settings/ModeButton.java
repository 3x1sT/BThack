package com.bt.BThack.impl.CustomGui.Gui.Client.GuiUtils.component.components.setting.settings;

import com.bt.BThack.System.Client;
import com.bt.BThack.api.Module.Module;
import com.bt.BThack.api.Utils.Font.FontUtil;
import com.bt.BThack.api.Managers.Settings.Setting;
import com.bt.BThack.impl.CustomGui.Gui.Client.GuiUtils.component.components.setting.AbstractSetting;
import com.bt.BThack.impl.CustomGui.Gui.Client.GuiUtils.component.components.Button;

public class ModeButton extends AbstractSetting {

	public ModeButton(Setting set, Button button, int offset, int modeIndex, Module module) {
		super(offset, button, module, set);

		this.x = button.parent.getX() + button.parent.getWidth();
		this.y = button.parent.getY() + button.offset;

		set.setValString(set.getOptions().get(modeIndex));
	}
	
	@Override
	public void renderComponent() {
		if (!this.getVisible()) return;

		FontUtil.drawText(this.op.getName() + ": " + this.op.getOptions().get(this.op.getIndex()), parent.parent.getX() + 2, parent.parent.getY() + offset + 4, Client.colourTheme.getModuleDisabledColour());
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
}
