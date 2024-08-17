package com.bt.BThack.api.Managers.Settings;

import com.bt.BThack.api.Module.Module;
import net.minecraft.client.gui.GuiScreen;

import java.util.ArrayList;

public class Setting {
	private final String name;
	private final Module module;

	public final String dependenceName;
	public final String dependenceType;
	public boolean dependenceBVal;
	public String dependenceSVal;
	public double dependenceDMin;
	public double dependenceDMax;

	private final String mode;
	
	private String sVal;
	private ArrayList<String> options;
	
	private boolean bVal;

	private int keyCodeVal;

	private double dVal;
	private double min;
	private double max;
	public boolean onlyInt = false;

	private GuiScreen screenVal;
	
	//private String text;
	
	//private int color;

	private int modeIndex;

	private boolean visible = true;

	//Combo Setting
	public Setting(String name, Module module, ArrayList<String> options){
		this.dependenceName = null;
		this.dependenceType = null;

		this.name = name;
		this.module = module;
		this.options = options;
		this.mode = "Combo";
		this.modeIndex = 0;
	}

	public Setting(String dependenceName, String dependenceSVal, String name, Module module, ArrayList<String> options) {
		this.dependenceName = dependenceName;
		this.dependenceSVal = dependenceSVal;
		this.dependenceType = "Combo";

		this.name = name;
		this.module = module;
		this.options = options;
		this.mode = "Combo";
		this.modeIndex = 0;
	}

	public Setting(String dependenceName, boolean dependenceBVal, String name, Module module, ArrayList<String> options) {
		this.dependenceName = dependenceName;
		this.dependenceBVal = dependenceBVal;
		this.dependenceType = "Check";

		this.name = name;
		this.module = module;
		this.options = options;
		this.mode = "Combo";
		this.modeIndex = 0;
	}

	public Setting(String dependenceName, double dependenceDMin, double dependenceDMax, String name, Module module, ArrayList<String> options) {
		this.dependenceName = dependenceName;
		this.dependenceDMin = dependenceDMin;
		this.dependenceDMax = dependenceDMax;
		this.dependenceType = "Slider";

		this.name = name;
		this.module = module;
		this.options = options;
		this.mode = "Combo";
		this.modeIndex = 0;
	}
	/////////////

	//CheckBox Setting
	public Setting(String name, Module module, boolean bVal){
		this.dependenceName = null;
		this.dependenceType = null;

		this.name = name;
		this.module = module;
		this.bVal = bVal;
		this.mode = "Check";
	}

	public Setting(String dependenceName, String dependenceSVal, String name, Module module, boolean bVal) {
		this.dependenceName = dependenceName;
		this.dependenceSVal = dependenceSVal;
		this.dependenceType = "Combo";

		this.name = name;
		this.module = module;
		this.bVal = bVal;
		this.mode = "Check";
	}

	public Setting(String dependenceName, boolean dependenceBVal, String name, Module module, boolean bVal) {
		this.dependenceName = dependenceName;
		this.dependenceBVal = dependenceBVal;
		this.dependenceType = "Check";

		this.name = name;
		this.module = module;
		this.bVal = bVal;
		this.mode = "Check";
	}

	public Setting(String dependenceName, double dependenceDMin, double dependenceDMax, String name, Module module, boolean bVal) {
		this.dependenceName = dependenceName;
		this.dependenceDMin = dependenceDMin;
		this.dependenceDMax = dependenceDMax;
		this.dependenceType = "Slider";

		this.name = name;
		this.module = module;
		this.bVal = bVal;
		this.mode = "Check";
	}
	/////////////

	//Slider Setting
	public Setting(String name, Module module, double dVal, double min, double max, boolean onlyInt){
		this.dependenceName = null;
		this.dependenceType = null;

		this.name = name;
		this.module = module;
		this.dVal = dVal;
		this.min = min;
		this.max = max;
		this.onlyInt = onlyInt;
		this.mode = "Slider";
	}

	public Setting(String dependenceName, String dependenceSVal, String name, Module module, double dVal, double min, double max, boolean onlyInt) {
		this.dependenceName = dependenceName;
		this.dependenceSVal = dependenceSVal;
		this.dependenceType = "Combo";

		this.name = name;
		this.module = module;
		this.dVal = dVal;
		this.min = min;
		this.max = max;
		this.onlyInt = onlyInt;
		this.mode = "Slider";
	}

	public Setting(String dependenceName, boolean dependenceBVal, String name, Module module, double dVal, double min, double max, boolean onlyInt) {
		this.dependenceName = dependenceName;
		this.dependenceBVal = dependenceBVal;
		this.dependenceType = "Check";

		this.name = name;
		this.module = module;
		this.dVal = dVal;
		this.min = min;
		this.max = max;
		this.onlyInt = onlyInt;
		this.mode = "Slider";
	}

	public Setting(String dependenceName, double dependenceDMin, double dependenceDMax, String name, Module module, double dVal, double min, double max, boolean onlyInt) {
		this.dependenceName = dependenceName;
		this.dependenceDMin = dependenceDMin;
		this.dependenceDMax = dependenceDMax;
		this.dependenceType = "Slider";

		this.name = name;
		this.module = module;
		this.dVal = dVal;
		this.min = min;
		this.max = max;
		this.onlyInt = onlyInt;
		this.mode = "Slider";
	}
	/////////////

	//KeyCode Setting
	public Setting(String name, Module module) {
		this.dependenceName = null;
		this.dependenceType = null;

		this.name = name;
		this.module = module;
		this.mode = "KeyCode";
	}

	public Setting(String dependenceName, String dependenceSVal, String name, Module module) {
		this.dependenceName = dependenceName;
		this.dependenceSVal = dependenceSVal;
		this.dependenceType = "Combo";

		this.name = name;
		this.module = module;
		this.mode = "KeyCode";
	}

	public Setting(String dependenceName, boolean dependenceBVal, String name, Module module) {
		this.dependenceName = dependenceName;
		this.dependenceBVal = dependenceBVal;
		this.dependenceType = "Check";

		this.name = name;
		this.module = module;
		this.mode = "KeyCode";
	}

	public Setting(String dependenceName, double dependenceDMin, double dependenceDMax, String name, Module module) {
		this.dependenceName = dependenceName;
		this.dependenceDMin = dependenceDMin;
		this.dependenceDMax = dependenceDMax;
		this.dependenceType = "Slider";

		this.name = name;
		this.module = module;
		this.mode = "KeyCode";
	}
	/////////////

	//GuiButton Setting
	public Setting(String name, Module module, GuiScreen screen) {
		this.dependenceName = null;
		this.dependenceType = null;

		this.name = name;
		this.module = module;
		this.screenVal = screen;
		this.mode = "GuiButton";
	}

	public Setting(String dependenceName, String dependenceSVal, String name, Module module, GuiScreen screen) {
		this.dependenceName = dependenceName;
		this.dependenceSVal = dependenceSVal;
		this.dependenceType = "Combo";

		this.name = name;
		this.module = module;
		this.screenVal = screen;
		this.mode = "GuiButton";
	}

	public Setting(String dependenceName, boolean dependenceBVal, String name, Module module, GuiScreen screen) {
		this.dependenceName = dependenceName;
		this.dependenceBVal = dependenceBVal;
		this.dependenceType = "Check";

		this.name = name;
		this.module = module;
		this.screenVal = screen;
		this.mode = "GuiButton";
	}

	public Setting(String dependenceName, double dependenceDMin, double dependenceDMax, String name, Module module, GuiScreen screen) {
		this.dependenceName = dependenceName;
		this.dependenceDMin = dependenceDMin;
		this.dependenceDMax = dependenceDMax;
		this.dependenceType = "Slider";

		this.name = name;
		this.module = module;
		this.screenVal = screen;
		this.mode = "GuiButton";
	}
	////////

	
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

	public void setOptions(ArrayList<String> options) {
		this.options = options;
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

	public int getKeyCodeVal() {
		return this.keyCodeVal;
	}

	public GuiScreen getScreenVal() {
		return this.screenVal;
	}

	public void setScreenVal(GuiScreen in) {
		this.screenVal = in;
	}

	public void setKeyCodeVal(int in) {
		this.keyCodeVal = in;
	}

	public void setValDouble(double in){
		this.dVal = in;
	}

	public boolean isVisible() {
		return this.visible;
	}

	public void setVisible(boolean in) {
		this.visible = in;
	}
	
	public double getMin(){
		return this.min;
	}
	
	public double getMax(){
		return this.max;
	}

	/*
	public int getColor(){
		return this.color;
	}
	
	public String getString(){
		return this.text;
	}
	 */

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

	public boolean isKeyCode() {
		return this.mode.equalsIgnoreCase("KeyCode");
	}

	public boolean isGuiButton() {
		return this.mode.equalsIgnoreCase("GuiButton");
	}
	
	//public boolean onlyInt(){return this.onlyInt;}
}
