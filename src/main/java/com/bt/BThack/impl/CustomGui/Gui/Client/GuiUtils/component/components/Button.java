package com.bt.BThack.impl.CustomGui.Gui.Client.GuiUtils.component.components;

import com.bt.BThack.BThack;
import com.bt.BThack.System.Client;
import com.bt.BThack.api.Module.Module;
import com.bt.BThack.api.Utils.Font.FontUtil;
import com.bt.BThack.api.Utils.Render.ColourUtils;
import com.bt.BThack.api.Utils.Render.RainbowUtils;
import com.bt.BThack.api.Utils.Render.RenderUtils;
import com.bt.BThack.impl.CustomGui.Gui.Client.ClickGui.ClickGuiManager;
import com.bt.BThack.api.Managers.Settings.Setting;
import com.bt.BThack.impl.CustomGui.Gui.Client.GuiUtils.component.Component;
import com.bt.BThack.impl.CustomGui.Gui.Client.GuiUtils.component.Frame;
import com.bt.BThack.impl.CustomGui.Gui.Client.GuiUtils.component.components.setting.AbstractSetting;
import com.bt.BThack.impl.CustomGui.Gui.Client.GuiUtils.component.components.setting.settings.*;
import com.bt.BThack.impl.CustomGui.Gui.Client.GuiUtils.component.components.setting.settings.Checkbox;
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
	private final ArrayList<AbstractSetting> subcomponents;
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
					this.subcomponents.add(new ModeButton(s, this, opY, s.getIndex(), mod));
					opY += 12;
				}
				if(s.isSlider()){                             //mod deleted
					this.subcomponents.add(new Slider(s, this, opY, mod));
					opY += 12;
				}
				if(s.isCheck()){                              //mod deleted
					this.subcomponents.add(new Checkbox(s, this, opY, mod));
					opY += 12;
				}
				if (s.isKeyCode()){
					this.subcomponents.add(new KeyCode(this, opY, s, mod));
					opY += 12;
				}
				if (s.isGuiButton()){
					this.subcomponents.add(new OpenGuiButton(s, this, opY, mod));
					opY += 12;
				}
			}
		}

		this.subcomponents.add(new Visible(this, opY, mod));
		if (mod.allowRemapKeyCode) {
			opY += 12;
			this.subcomponents.add(new Keybind(this, opY));
		}
	}

	@Override
	public void setOff(int newOff) {
		offset = newOff;
		int opY = offset + 12;
		for(AbstractSetting comp : this.subcomponents) {
			comp.setOff(opY);
			if (comp.getVisible())
				opY += 15;
		}
	}

	@Override
	public void updateDependencies(int offset) {
		for (AbstractSetting comp : this.subcomponents) {
			comp.updateDependencies(0);
		}

		this.offset = offset;
		int opY = offset + 12;
		for(AbstractSetting comp : this.subcomponents) {
			comp.setOff(opY);
			if (comp.getVisible())
				opY += 15;
		}
	}

	@Override
	public void renderComponent() {
		int rainbowType = (int) Module.getSlider("ClickGui", "Rainbow speed");
		float speed = RainbowUtils.getRainbowRectSpeed(rainbowType)[0];
		int delay = (int) RainbowUtils.getRainbowRectSpeed(rainbowType)[1];

		//Gui.drawRect(parent.getX(), this.parent.getY() + this.offset, parent.getX() + parent.getWidth(), this.parent.getY() + 12 + this.offset, this.isHovered ? new Color(Client.colourTheme.getBackgroundFontHoveredColour()).hashCode() : new Color(Client.colourTheme.getBackgroundFontColour()).hashCode());
		FontUtil.drawTextWithAlphaCheck(this.mod.getName(), (parent.getX() + 5), (parent.getY() + offset + (Client.getModuleByName("Font").isEnabled() ? 0 : 2)), this.mod.isEnabled() ? Module.getCheckbox("ClickGui", "Rainbow") ? ColourUtils.integrateAlpha(ColourUtils.rainbow(delay, speed), effect.getColor().getAlpha()) : Module.getCheckbox("ClickGui", "Custom Color") ? ColourUtils.integrateAlpha(new Color((int) Module.getSlider("ClickGui", "Red"), (int) Module.getSlider("ClickGui", "Green"), (int) Module.getSlider("ClickGui", "Blue")).getRGB(), effect.getColor().getAlpha()) : ColourUtils.integrateAlpha(new Color(Client.colourTheme.getModuleEnabledColour()).hashCode(), effect.getColor().getAlpha()) : ColourUtils.integrateAlpha(new Color(Client.colourTheme.getModuleDisabledColour()).hashCode(), effect.getColor().getAlpha())); //0x999999

		if(this.subcomponents.size() >= 2) {
			FontUtil.drawTextWithAlphaCheck(this.open ? "-" : "+", (parent.getX()+parent.getWidth()-10), (parent.getY() + offset), ColourUtils.integrateAlpha(new Color(Client.colourTheme.getModuleDisabledColour()).hashCode(), effect.getColor().getAlpha()));
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
			length = (int) FontUtil.getStringWidth(this.mod.getDescription()) + 12;
			Gui.drawRect(1, ClickGuiManager.descriptionY - 5, length, (ClickGuiManager.descriptionY + 12), new Color(Client.colourTheme.getBackgroundFontColour()).hashCode());
			RenderUtils.drawOutlineRect(1, ClickGuiManager.descriptionY - 5, length, (ClickGuiManager.descriptionY + 12), 1, ColourUtils.rainbow(delay, speed));
			FontUtil.drawText(this.mod.getDescription(), 6, (ClickGuiManager.descriptionY - 1), Client.colourTheme.getModuleDisabledColour());
		}
	}

	@Override
	public void renderRect(BufferBuilder builder, Tessellator tessellator) {
		RenderUtils.drawNoGL11Rect(parent.getX(), this.parent.getY() + this.offset, parent.getX() + parent.getWidth(), this.parent.getY() + 12 + this.offset, this.isHovered ? ColourUtils.integrateAlpha(new Color(Client.colourTheme.getBackgroundFontHoveredColour()).hashCode(), (int) (effect.getColor().getAlpha() / 1.3)) : ColourUtils.integrateAlpha(new Color(Client.colourTheme.getBackgroundFontColour()).hashCode(), (int) (effect.getColor().getAlpha() / 1.3)), builder, tessellator);
	}

	@Override
	public int getHeight() {
		if(this.open) {
			int size = 0;
			for (AbstractSetting component : this.subcomponents) {
				if (component.getVisible()) {
					size++;
				}
			}
			return (15 * (size + 1)) - 3;
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
		if (this.open)
			this.parent.updateDependencies();
	}

	@Override
	public void mouseReleased(int mouseX, int mouseY, int mouseButton) {
		for(Component comp : this.subcomponents) {
			comp.mouseReleased(mouseX, mouseY, mouseButton);
		}
		if (this.open)
			this.parent.updateDependencies();
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
