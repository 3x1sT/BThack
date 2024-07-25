package com.bt.BThack.impl.CustomGui.Gui.Client.GuiUtils.component;

import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;

public class Component {
	private boolean visible = true;

	public boolean getVisible() {
		return this.visible;
	}

	public void setVisible(boolean in) {
		this.visible = in;
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
