package com.bt.BThack.impl.CustomGui.Gui.Client.GuiUtils.Settings;

import com.bt.BThack.api.Module.Module;

import java.util.ArrayList;

public class Setting {
	//final added
	private final String name;
	//final added
	private final Module module;

	//final added
	private final String mode;
	
	private String sVal;
	private ArrayList<String> options;
	private String title;
	
	private boolean bVal;
	
	private double dVal;
	private double min;
	private double max;
	private boolean onlyInt = false;
	
	private String text;
	
	private int color;

	private int modeIndex;

	public Setting(String name, Module module, ArrayList<String> options, String title){
		this.name = name;
		this.module = module;
		this.options = options;
		this.title = title;
		this.mode = "Combo";
		this.modeIndex = 0;
	}

	public Setting(String name, Module module, boolean bVal){
		this.name = name;
		this.module = module;
		this.bVal = bVal;
		this.mode = "Check";
	}

	public Setting(String name, Module module, double dVal, double min, double max, boolean onlyInt){
		this.name = name;
		this.module = module;
		this.dVal = dVal;
		this.min = min;
		this.max = max;
		this.onlyInt = onlyInt;
		this.mode = "Slider";
	}

	
	public String getName(){
		return name;
	}
	
	public Module getModule(){
		return module;
	}
	
	public String getValString(){
		return this.sVal;
	}
	
	public void setValString(String in){
		this.sVal = in;
	}

	public int getIndex() {
		return this.modeIndex;
	}

	public void setIndex(int index) {
		this.modeIndex = index;
	}
	
	public ArrayList<String> getOptions(){
		return this.options;
	}
	
	public String getTitle(){
		return this.title;
	}
	
	public boolean getValBoolean(){
		return this.bVal;
	}
	
	public void setValBoolean(boolean in){
		this.bVal = in;
	}
	
	public double getValDouble(){
		if(this.onlyInt){
			this.dVal = (int)dVal;
		}
		return this.dVal;
	}

	public void setValDouble(double in){
		this.dVal = in;
	}
	
	public double getMin(){
		return this.min;
	}
	
	public double getMax(){
		return this.max;
	}
	
	public int getColor(){
		return this.color;
	}
	
	public String getString(){
		return this.text;
	}

	                                                                   //deleted      ? true : false
	public boolean isCombo(){
		return this.mode.equalsIgnoreCase("Combo");
	}
	
	public boolean isCheck(){
		return this.mode.equalsIgnoreCase("Check");
	}
	
	public boolean isSlider(){
		return this.mode.equalsIgnoreCase("Slider");
	}
	
	public boolean isMode(){
		return this.mode.equalsIgnoreCase("ModeButton");
	}
	
	//public boolean onlyInt(){return this.onlyInt;}
}
