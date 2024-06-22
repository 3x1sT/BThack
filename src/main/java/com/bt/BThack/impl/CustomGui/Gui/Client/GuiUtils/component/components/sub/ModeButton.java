package com.bt.BThack.impl.CustomGui.Gui.Client.GuiUtils.component.components.sub;

//import com.bt.BThack.api.Module.Module;

import com.bt.BThack.Client;
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
	//final added
	private final Button parent;
	//final added
	private final Setting set;
	private int offset;
	private int x;
	private int y;
	//final added
	//private final Module mod;

	public ModeButton(Setting set, Button button, int offset, int modeIndex) {
		this.set = set;
		this.parent = button;
		//this.mod = mod;
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
		//Gui.drawRect(parent.parent.getX(), parent.parent.getY() + offset, parent.parent.getX() + parent.parent.getWidth(), parent.parent.getY() + offset + 15, this.hovered ? new Color(Client.colourTheme.getBackgroundFontHoveredColour()).hashCode() : new Color(Client.colourTheme.getBackgroundFontColour()).hashCode());

		//glPushMatrix();
		//glScalef(0.7f, 0.7f, 0.5f);
		FontUtil.drawText(this.set.getTitle() + ": " + this.set.getOptions().get(this.set.getIndex()), parent.parent.getX() + 2, parent.parent.getY() + offset + 4, Client.colourTheme.getModuleDisabledColour());
		//glPopMatrix();
	}

	@Override
	public void renderRect(BufferBuilder builder, Tessellator tessellator) {
		RenderUtils.drawNoGL11Rect(parent.parent.getX(), parent.parent.getY() + offset, parent.parent.getX() + parent.parent.getWidth(), parent.parent.getY() + offset + 15, this.hovered ? new Color(Client.colourTheme.getBackgroundFontHoveredColour()).hashCode() : new Color(Client.colourTheme.getBackgroundFontColour()).hashCode(), builder, tessellator);
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
			int maxIndex = set.getOptions().size();

			if (set.getIndex() + 1 >= maxIndex)
				set.setIndex(0);
			else {
				int currentIndex = set.getIndex();
				set.setIndex(currentIndex + 1);
			}

			set.setValString(set.getOptions().get(set.getIndex()));
		}
	}
	
	public boolean isMouseOnButton(int x, int y) {
		return x > this.x && x < this.x + 100 && y > this.y && y < this.y + 15;
	}
}
