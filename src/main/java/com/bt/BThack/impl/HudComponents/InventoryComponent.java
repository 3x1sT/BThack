package com.bt.BThack.impl.HudComponents;

import com.bt.BThack.api.HudComponent.HudComponent;
import com.bt.BThack.api.Module.Module;
import com.bt.BThack.api.Utils.Render.RenderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.item.ItemStack;

import java.awt.*;

public class InventoryComponent extends HudComponent {

    public InventoryComponent(Module module) {
        super("Inventory",
                (new ScaledResolution(Minecraft.getMinecraft()).getScaledWidth() / 2f) + (new ScaledResolution(Minecraft.getMinecraft()).getScaledWidth() / 7.5f),
                new ScaledResolution(Minecraft.getMinecraft()).getScaledHeight() - 48,
                true,
                module
        );

        this.width = 144;
        this.height = 48;
    }

    @Override
    public void render(RenderType type) {
        if (nullCheck()) return;

        Gui.drawRect((int) getX() - 3, (int) getY() - 3, (int) (getX() + width) + 3, (int) (getY() + height) + 3, new Color(20, 20, 20, 135).getRGB());
        RenderUtils.drawOutlineRect((int) getX() - 4, (int) getY() - 4, (int) (getX() + width) + 4, (int) (getY() + height) + 4, 1, new Color(255, 255, 255, 125).getRGB());


        GlStateManager.pushMatrix();
        RenderHelper.enableGUIStandardItemLighting();

        for (int i = 0; i < 27; i++) {
            ItemStack itemStack = mc.player.inventory.mainInventory.get(i + 9);

            int offsetX = (int) getX() + (i % 9) * 16;
            int offsetY = (int) getY() + (i / 9) * 16;

            mc.getRenderItem().renderItemAndEffectIntoGUI(itemStack, offsetX, offsetY);
            mc.getRenderItem().renderItemOverlayIntoGUI(mc.fontRenderer, itemStack, offsetX, offsetY, null);
        }

        RenderHelper.disableStandardItemLighting();

        mc.getRenderItem().zLevel = 0.0f;

        GlStateManager.popMatrix();
    }
}
