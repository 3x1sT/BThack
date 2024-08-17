package com.bt.BThack.impl.CustomGui.Gui.Client.HudMover.Utils;

import com.bt.BThack.api.HudComponent.HudComponent;
import com.bt.BThack.api.Utils.Render.ColourUtils;
import com.bt.BThack.api.Utils.Render.RenderUtils;
import com.bt.BThack.api.Utils.System.Buttons.Button;
import net.minecraft.client.gui.ScaledResolution;

import java.awt.*;

public class HudComponentButton extends Button {
    public int x;
    public int y;

    private boolean selected = false;
    public final HudComponent hudComponent;


    public HudComponentButton(int id, HudComponent hudComponent) {
        super(id ,(int) (hudComponent.getX() + (hudComponent.width / 2)),(int) (hudComponent.getY() + (hudComponent.height / 2)),(int) (hudComponent.width / 2),(int) (hudComponent.height / 2), "");
        this.hudComponent = hudComponent;
    }

    short alpha = 255;
    boolean alphaIvert = true;

    @Override
    public void renderButton() {
        this.hudComponent.render(HudComponent.RenderType.TEXT);
        this.hudComponent.render(HudComponent.RenderType.IMAGE);

        if (selected) {
            if (this.hudComponent.width < 0) {
                RenderUtils.drawOutlineRect((int) ((this.x + this.hudComponent.width) - 1), (int) ((this.y + this.hudComponent.height) - 1), this.x + 1, this.y + 1, 1, ColourUtils.rainbow(100));
            } else {
                RenderUtils.drawOutlineRect(this.x - 1, this.y - 1, (int) (this.x + this.hudComponent.width + 1), (int) (this.y + this.hudComponent.height + 1), 1, ColourUtils.rainbow(100));
            }
        }

        if (hovered) {
            if (!alphaIvert) {
                if (alpha < 255) {
                    alpha += 3;
                } else {
                    alphaIvert = true;
                }
            } else {
                if (alpha > 1) {
                    alpha -= 3;
                } else {
                    alphaIvert = false;
                }
            }

            Color hoveredColor = new Color(255,255,255, alpha);

            if (!selected) {
                if (this.hudComponent.width < 0) {
                    RenderUtils.drawOutlineRect((int) ((this.x + this.hudComponent.width) - 1), (int) ((this.y + this.hudComponent.height) - 1), this.x + 1, this.y + 1, 1, hoveredColor.hashCode());
                } else {
                    RenderUtils.drawOutlineRect(this.x - 1, this.y - 1, (int) (this.x + this.hudComponent.width + 1), (int) (this.y + this.hudComponent.height + 1), 1, hoveredColor.hashCode());
                }
            } else {
                if (this.hudComponent.width < 0) {
                    RenderUtils.drawOutlineRect((int) ((this.x + this.hudComponent.width) - 2), (int) ((this.y + this.hudComponent.height) - 2), this.x + 2, this.y + 2, 1, hoveredColor.hashCode());
                } else {
                    RenderUtils.drawOutlineRect(this.x - 2, this.y - 2, (int) (this.x + this.hudComponent.width + 2), (int) (this.y + this.hudComponent.height + 2), 1, hoveredColor.hashCode());
                }
            }
        }
    }

    @Override
    public void updateButton(int mouseX, int mouseY) {
        super.updateButton(mouseX, mouseY);

        ScaledResolution sc = new ScaledResolution(mc);

        if (this.selected) {
            this.x = mouseX;
            this.y = mouseY;
        } else {
            this.x = (int) this.hudComponent.getX();
            this.y = (int) this.hudComponent.getY();
        }

        this.hudComponent.setX(this.x, sc.getScaledWidth());
        this.hudComponent.setY(this.y, sc.getScaledHeight());
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY) {
        if (this.isMouseOnButton(mouseX, mouseY)) {
            this.selected = !this.selected;
        }
    }

    @Override
    public boolean isMouseOnButton(int mouseX, int mouseY) {
        if (this.hudComponent.width < 0) {
            return mouseX <= this.x && mouseX >= this.x + this.hudComponent.width && mouseY >= this.y && mouseY <= this.y + this.hudComponent.height;
        } else {
            return this.x <= mouseX && mouseX <= this.x + this.hudComponent.width && mouseY >= this.y && mouseY <= this.y + this.hudComponent.height;
        }
    }
}
