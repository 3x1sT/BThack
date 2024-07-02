package com.bt.BThack.api.Social.Allies;

public class Ally {
    private final String name;
    private final String clanName;
    private final float r;
    private final float g;
    private final float b;

    public Ally(String name, String clanName, float r, float g, float b) {
        this.name = name;
        this.clanName = clanName;
        this.r = r;
        this.g = g;
        this.b = b;
    }

    public Ally(String name, String clanName, String color) {
        this.name = name;
        this.clanName = clanName;
        String colour = color.toLowerCase();
        this.r = colour.equals("green") || colour.equals("blue") ? 0 : colour.equals("violet") ? 0.63f : 1;
        this.g = colour.equals("orange") ? 0.4f : colour.equals("red") || colour.equals("blue") || colour.equals("violet") ? 0 : 1;
        this.b = colour.equals("red") || colour.equals("orange") || colour.equals("yellow") || colour.equals("green") ? 0 : 1;
        //this.color = colour.equals("red") ? new Color(255, 0, 0).hashCode() : colour.equals("orange") ? new Color(255, 127, 0).hashCode() : colour.equals("yellow") ? new Color(255,255,0).hashCode() : colour.equals("green") ? new Color(0, 255, 0).hashCode() : colour.equals("blue") ? new Color(0,0,255).hashCode() : colour.equals("violet") ? new Color(161, 0, 255).hashCode() : new Color(255, 255, 255).hashCode();
    }

    public String getName() {
        return this.name;
    }

    public String getClanName() {
        return this.clanName;
    }

    public float getR() {
        return this.r;
    }

    public float getG() {
        return this.g;
    }

    public float getB() {
        return this.b;
    }
}
