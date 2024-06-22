package com.bt.BThack.impl.CustomGui.Gui.Client.GuiUtils.component.components;

import com.bt.BThack.BThack;
import com.bt.BThack.Client;
import com.bt.BThack.api.Module.Module;
import com.bt.BThack.api.Utils.Font.FontUtil;
import com.bt.BThack.api.Utils.Render.ColourUtils;
import com.bt.BThack.api.Utils.Render.RainbowUtils;
import com.bt.BThack.api.Utils.Render.RenderUtils;
import com.bt.BThack.impl.CustomGui.Gui.Client.ClickGui.ClickGuiManager;
import com.bt.BThack.impl.CustomGui.Gui.Client.GuiUtils.Settings.Setting;
import com.bt.BThack.impl.CustomGui.Gui.Client.GuiUtils.component.Component;
import com.bt.BThack.impl.CustomGui.Gui.Client.GuiUtils.component.Frame;
import com.bt.BThack.impl.CustomGui.Gui.Client.GuiUtils.component.components.sub.Checkbox;
import com.bt.BThack.impl.CustomGui.Gui.Client.GuiUtils.component.components.sub.Keybind;
import com.bt.BThack.impl.CustomGui.Gui.Client.GuiUtils.component.components.sub.ModeButton;
import com.bt.BThack.impl.CustomGui.Gui.Client.GuiUtils.component.components.sub.Slider;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;

import java.awt.*;
import java.util.ArrayList;

public class Button extends Component {

	public Module mod;
	public Frame parent;
	public int offset;
	private boolean isHovered;
	       //final added
	private final ArrayList<Component> subcomponents;
	public boolean open;
	public int height;
	public Button(Module mod, Frame parent, int offset) {
		this.mod = mod;
		this.parent = parent;
		this.offset = offset;
		this.height = 12;
		this.subcomponents = new ArrayList<>();
		this.open = false;
		int opY = offset + 12;
		if(BThack.instance.settingsManager.getSettingsByMod(mod) != null) {
			for(Setting s : BThack.instance.settingsManager.getSettingsByMod(mod)){
				if(s.isCombo()){                              //mod deleted
					this.subcomponents.add(new ModeButton(s, this, opY, s.getIndex()));
					opY += 12;
				}
				if(s.isSlider()){                             //mod deleted
					this.subcomponents.add(new Slider(s, this, opY));
					opY += 12;
				}
				if(s.isCheck()){                              //mod deleted
					this.subcomponents.add(new Checkbox(s, this, opY));
					opY += 12;
				}
			}
		}
		this.subcomponents.add(new Keybind(this, opY));
	}

	@Override
	public void setOff(int newOff) {
		offset = newOff;
		int opY = offset + 12;
		for(Component comp : this.subcomponents) {
			comp.setOff(opY);
			opY += 15;
		}
	}

	@Override
	public void renderComponent() {
		int rainbowType = (int) Module.getSlider("ClickGui", "Rainbow speed");
		float speed = RainbowUtils.getRainbowRectSpeed(rainbowType)[0];
		int delay = (int) RainbowUtils.getRainbowRectSpeed(rainbowType)[1];

		//Gui.drawRect(parent.getX(), this.parent.getY() + this.offset, parent.getX() + parent.getWidth(), this.parent.getY() + 12 + this.offset, this.isHovered ? new Color(Client.colourTheme.getBackgroundFontHoveredColour()).hashCode() : new Color(Client.colourTheme.getBackgroundFontColour()).hashCode());
		FontUtil.drawText(this.mod.getName(), (parent.getX() + 5), (parent.getY() + offset), this.mod.isEnabled() ? Module.getCheckbox("ClickGui", "Rainbow") ? ColourUtils.rainbow(delay, speed) : Module.getCheckbox("ClickGui", "Custom Color") ? new Color((int) Module.getSlider("ClickGui", "Red"), (int) Module.getSlider("ClickGui", "Green"), (int) Module.getSlider("ClickGui", "Blue")).getRGB() : Client.colourTheme.getModuleEnabledColour() : Client.colourTheme.getModuleDisabledColour()); //0x999999
		if(this.subcomponents.size() >= 2) {
			FontUtil.drawText(this.open ? "-" : "+", (parent.getX()+parent.getWidth()-10), (parent.getY() + offset + 2), Client.colourTheme.getModuleDisabledColour());
		}
		if(this.open) {
			if(!this.subcomponents.isEmpty()) {
				Tessellator tessellator = Tessellator.getInstance();
				BufferBuilder bufferbuilder = tessellator.getBuffer();

				GlStateManager.enableBlend();
				GlStateManager.disableTexture2D();
				GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
				for(Component comp : this.subcomponents) {
					comp.renderRect(bufferbuilder, tessellator);
				}
				GlStateManager.enableTexture2D();
				GlStateManager.disableBlend();

				for(Component comp : this.subcomponents) {
					comp.renderComponent();
				}
			}
		}
		if (isHovered) {
			int length;
			length = (this.mod.getDescription().length()) * 5;
			Gui.drawRect(1, ClickGuiManager.descriptionY - 5, length, (ClickGuiManager.descriptionY + 12), new Color(Client.colourTheme.getBackgroundFontColour()).hashCode());
			RenderUtils.drawOutlineRect(1, ClickGuiManager.descriptionY - 5, length, (ClickGuiManager.descriptionY + 12), 1, ColourUtils.rainbow(delay, speed));
			FontUtil.drawText(this.mod.getDescription(), 6, (ClickGuiManager.descriptionY - 1), Client.colourTheme.getModuleDisabledColour());
		}
	}

	@Override
	public void renderRect(BufferBuilder builder, Tessellator tessellator) {
		RenderUtils.drawNoGL11Rect(parent.getX(), this.parent.getY() + this.offset, parent.getX() + parent.getWidth(), this.parent.getY() + 12 + this.offset, this.isHovered ? new Color(Client.colourTheme.getBackgroundFontHoveredColour()).hashCode() : new Color(Client.colourTheme.getBackgroundFontColour()).hashCode(), builder, tessellator);
	}

	@Override
	public int getHeight() {
		if(this.open) {
			return (15 * (this.subcomponents.size() + 1)) - 4;
		}
		return 12;
	}

	@Override
	public void updateComponent(int mouseX, int mouseY) {
		this.isHovered = isMouseOnButton(mouseX, mouseY);
		if(!this.subcomponents.isEmpty()) {
			for(Component comp : this.subcomponents) {
				comp.updateComponent(mouseX, mouseY);
			}
		}
	}

	@Override
	public void mouseClicked(int mouseX, int mouseY, int button) {
		if(isMouseOnButton(mouseX, mouseY) && button == 0) {
			this.mod.toggle();
		}
		if(isMouseOnButton(mouseX, mouseY) && button == 1) {
			this.open = !this.open;
			this.parent.refresh();
		}
		for(Component comp : this.subcomponents) {
			comp.mouseClicked(mouseX, mouseY, button);
		}
	}

	@Override
	public void mouseReleased(int mouseX, int mouseY, int mouseButton) {
		for(Component comp : this.subcomponents) {
			comp.mouseReleased(mouseX, mouseY, mouseButton);
		}
	}

	@Override
	public void keyTyped(char typedChar, int key) {
		for(Component comp : this.subcomponents) {
			comp.keyTyped(typedChar, key);
		}
	}

	public boolean isMouseOnButton(int x, int y) {
        return x > parent.getX() && x < parent.getX() + parent.getWidth() && y > this.parent.getY() + this.offset && y < this.parent.getY() + 12 + this.offset;
    }
}
