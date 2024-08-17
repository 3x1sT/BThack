package com.bt.BThack.impl.CustomGui.Gui.Client.GuiUtils.component.components.setting.settings;

import com.bt.BThack.System.Client;
import com.bt.BThack.api.Utils.Font.FontUtil;
import com.bt.BThack.api.Utils.Render.ColourUtils;
import com.bt.BThack.api.Utils.Render.RenderUtils;
import com.bt.BThack.impl.CustomGui.Gui.Client.GuiUtils.component.components.setting.AbstractSetting;
import com.bt.BThack.impl.CustomGui.Gui.Client.GuiUtils.component.components.Button;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import org.lwjgl.input.Keyboard;

import java.awt.*;

public class Keybind extends AbstractSetting {

	private boolean binding;
	
	public Keybind(Button button, int offset) {
		super(offset, button, null, null);

		this.x = button.parent.getX() + button.parent.getWidth();
		this.y = button.parent.getY() + button.offset;
	}
	
	@Override
	public void renderComponent() {
		//Gui.drawRect(parent.parent.getX(), parent.parent.getY() + offset, parent.parent.getX() + parent.parent.getWidth(), parent.parent.getY() + offset + 15, this.hovered ? new Color(Client.colourTheme.getBackgroundFontHoveredColour()).hashCode() : new Color(Client.colourTheme.getBackgroundFontColour()).hashCode());
		//glPushMatrix();
		//glScalef(0.7f,0.7f, 0.5f);
		FontUtil.drawText(binding ? "< PRESS KEY >" : ("Key: " + Keyboard.getKeyName(this.parent.mod.getKey())), parent.parent.getX() + 2, parent.parent.getY() + offset + 4, Client.colourTheme.getModuleDisabledColour());
		//glPopMatrix();
	}

	@Override
	public void renderRect(BufferBuilder builder, Tessellator tessellator) {
		RenderUtils.drawNoGL11Rect(parent.parent.getX(), parent.parent.getY() + offset, parent.parent.getX() + parent.parent.getWidth(), parent.parent.getY() + offset + 15, this.hovered ? ColourUtils.integrateAlpha(new Color(Client.colourTheme.getBackgroundFontHoveredColour()).hashCode(), (int) (parent.effect.getColor().getAlpha() / 1.17)) : ColourUtils.integrateAlpha(new Color(Client.colourTheme.getBackgroundFontColour()).hashCode(), (int) (parent.effect.getColor().getAlpha() / 1.17)), builder, tessellator);
	}

	@Override
	public void updateDependencies(int offset) {
		//There should be no action here
	}

	@Override
	public void updateComponent(int mouseX, int mouseY) {
		this.hovered = isMouseOnButton(mouseX, mouseY);
		this.y = parent.parent.getY() + offset;
		this.x = parent.parent.getX();
	}
	
	@Override
	public void mouseClicked(int mouseX, int mouseY, int button) {
		if(isMouseOnButton(mouseX, mouseY) && button == 0 && this.parent.open) {
			this.binding = !this.binding;
		}
	}
	
	@Override
	public void keyTyped(char typedChar, int key) {
		if(this.binding && key != Keyboard.KEY_DELETE) {
			this.parent.mod.setKey(key);
			this.binding = false;
		}
		else if(this.binding) {
			this.parent.mod.setKey(0);
			this.binding = false;
		}
	}
}
