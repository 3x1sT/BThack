package com.bt.BThack.impl.CustomGui.Gui.Client.GuiUtils.component;

import java.awt.Color;
import java.util.ArrayList;

import com.bt.BThack.System.Client;
import com.bt.BThack.api.Effect.Color.ColorEffects.SmoothEmergenceEffect;
import com.bt.BThack.api.Module.Module;
import com.bt.BThack.api.Utils.Font.FontUtil;


import com.bt.BThack.api.Utils.Render.RenderUtils;
import net.minecraft.client.gui.*;
import com.bt.BThack.impl.CustomGui.Gui.Client.GuiUtils.component.components.Button;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import org.lwjgl.input.Keyboard;

public class Frame {

	public ArrayList<Button> buttons;
	public Module.Category category;
	private boolean open;
	private final int width;
	private int y;
	private int x;
	private final int barHeight;
	private boolean isDragging;
	public int dragX;
	public int dragY;
	public static int color;
	
	public Frame(Module.Category cat) {
		this.buttons = new ArrayList<>();
		this.category = cat;
		this.width = 100;
		this.x = 0;
		this.y = 60;
		this.dragX = 0;
		this.barHeight = 12;
		this.open = true;
		this.isDragging = false;
		int tY = this.barHeight;

		for(Module mod : Client.getModulesInCategory(category)) {
			Button button = new Button(mod, this, tY);
			button.effect = new SmoothEmergenceEffect(new Color(0,0,0,255), buttons.size() * 2, 10);
			this.buttons.add(button);
			tY += 12;
		}
	}
	
	public ArrayList<Button> getButtons() {
		return buttons;
	}
	
	public void setX(int newX) {
		this.x = newX;
	}
	
	public void setY(int newY) {
		this.y = newY;
	}
	
	public void setDrag(boolean drag) {
		this.isDragging = drag;
	}
	
	public boolean isOpen() {
		return open;
	}
	
	public void setOpen(boolean open) {
		this.open = open;
	}

	public Module.Category getCategory() {
		return this.category;
	}
	
	public void renderFrame() {
		if (Module.getCheckbox("ClickGui", "Rainbow")) {
			int type = (int) Module.getSlider("ClickGui", "Rainbow speed");
			RenderUtils.drawGradientRainbowRect(this.x, this.y - 1, this.x + this.width, this.y, type);
			RenderUtils.drawGradientRainbowRect(this.x, this.y, this.x + this.width, this.y + 12, type);
		} else {
			Gui.drawRect(this.x, this.y - 1, this.x + this.width, this.y, Module.getCheckbox("ClickGui", "Custom Color") ? new Color((int) Module.getSlider("ClickGui", "Red"), (int) Module.getSlider("ClickGui", "Green"), (int) Module.getSlider("ClickGui", "Blue")).getRGB() : new Color(Client.colourTheme.getFontColour()).hashCode());
			Gui.drawRect(this.x, this.y, this.x + this.width, this.y + 12, Module.getCheckbox("ClickGui", "Custom Color") ? new Color((int) Module.getSlider("ClickGui", "Red"), (int) Module.getSlider("ClickGui", "Green"), (int) Module.getSlider("ClickGui", "Blue")).getRGB() : new Color(Client.colourTheme.getFontColour()).hashCode());
		}
		FontUtil.drawText(this.category.name(), this.x + 5, this.y + 2, Client.colourTheme.getModuleDisabledColour());

		if(this.open) {
			if(!this.buttons.isEmpty()) {
				Tessellator tessellator = Tessellator.getInstance();
				BufferBuilder bufferbuilder = tessellator.getBuffer();

				GlStateManager.enableBlend();
				GlStateManager.disableTexture2D();
				GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
				for(Component component : buttons) {
					component.renderRect(bufferbuilder, tessellator);
				}
				GlStateManager.enableTexture2D();
				GlStateManager.disableBlend();

				for(Component component : buttons) {
					component.renderComponent();
				}
			}
		}
	}
	
	public void refresh() {
		int off = this.barHeight;
		for(Component comp : buttons) {
			comp.setOff(off);
			off += comp.getHeight();
		}
	}

	public void updateDependencies() {
		int off = this.barHeight;
		for(Component comp : buttons) {
			comp.updateDependencies(off);
			off += comp.getHeight();
		}
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public int getWidth() {
		return width;
	}
	
	public void updatePosition(int mouseX, int mouseY) {
		if(this.isDragging) {
			this.setX(mouseX - dragX);
			this.setY(mouseY - dragY);
		}
	}

	public void tick() {
		for (Component component : buttons) {
			component.tick();
		}
	}

	public void moveFrame(int keyCode) {
		switch (keyCode) {
			case Keyboard.KEY_LEFT:
				this.x-= 10;
				break;
			case Keyboard.KEY_RIGHT:
				this.x+= 10;
				break;
			case Keyboard.KEY_UP:
				this.y-= 10;
				break;
			case Keyboard.KEY_DOWN:
				this.y+= 10;
		}
	}
	
	public boolean isWithinHeader(int x, int y) {
        return x >= this.x && x <= this.x + this.width && y >= this.y && y <= this.y + this.barHeight;
    }

	public void refreshButtonEffects() {
		int size = 0;
		for (Button button : this.buttons) {
			button.effect = new SmoothEmergenceEffect(new Color(0,0,0,255), (int) (size * Module.getSlider("ClickGui", "Effect DelayS")), (int) Module.getSlider("ClickGui", "Effect Delay"));
			size++;
		}
	}
	
}
