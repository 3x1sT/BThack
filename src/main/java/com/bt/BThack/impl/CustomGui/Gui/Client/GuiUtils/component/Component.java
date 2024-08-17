package com.bt.BThack.impl.CustomGui.Gui.Client.GuiUtils.component;

import com.bt.BThack.api.Effect.Color.ColorEffectBase;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;

public class Component {
	public ColorEffectBase effect;

	public void tick() {
		effect.tick();
	}


	public void renderComponent() {
	}

	public void renderRect(BufferBuilder builder, Tessellator tessellator) {

	}
	
	public void updateComponent(int mouseX, int mouseY) {
		
	}
	
	public void mouseClicked(int mouseX, int mouseY, int button) {
		
	}
	
	public void mouseReleased(int mouseX, int mouseY, int mouseButton) {
	}
	
	public int getParentHeight() {
		return 0;
	}
	
	public void keyTyped(char typedChar, int key) {
		
	}
	
	public void setOff(int newOff) {
		
	}
	
	public int getHeight() {
		return 0;
	}




	public void updateDependencies(int offset) {}
}
