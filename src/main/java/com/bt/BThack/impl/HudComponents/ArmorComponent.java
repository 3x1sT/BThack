package com.bt.BThack.impl.HudComponents;

import com.bt.BThack.api.HudComponent.HudComponent;
import com.bt.BThack.api.Module.Module;
import com.bt.BThack.api.Utils.Font.FontUtil;
import com.bt.BThack.api.Utils.ItemsUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

import java.awt.*;

public class ArmorComponent extends HudComponent {

    public ArmorComponent(Module module) {
        super("Armor",
                (new ScaledResolution(Minecraft.getMinecraft()).getScaledWidth() / 2f) + (new ScaledResolution(Minecraft.getMinecraft()).getScaledWidth() / 7.5f),
                new ScaledResolution(Minecraft.getMinecraft()).getScaledHeight() - 128,
                true,
                module
        );
    }

    @Override
    public void render(RenderType type) {
        if (nullCheck()) return;

        GlStateManager.pushMatrix();
        RenderHelper.enableGUIStandardItemLighting();

        int y = 0;

        float maxWidth = 0;

        for (int i = 3; i > -1; i--) {
            ItemStack armorStack = mc.player.inventory.armorInventory.get(i);

            if (armorStack.getItem() != Items.AIR) {
                String text = ItemsUtil.getItemDurability(armorStack) + "/" + ItemsUtil.getItemMaxDurability(armorStack) + " (" + ItemsUtil.getItemDurabilityInPercentages(armorStack) + ")";

                if (FontUtil.getStringWidth(text) > maxWidth) {
                    maxWidth = FontUtil.getStringWidth(text);
                }

                mc.getRenderItem().renderItemAndEffectIntoGUI(armorStack, (int) getX(), (int) getY() + y);
                FontUtil.drawText(text, (int) getX() + 20, (int) getY() + y, armorStack.getItem().getRGBDurabilityForDisplay(armorStack));
                Gui.drawRect((int) getX() + 20, (int) (getY() + y + FontUtil.getStringHeight(text)), (int) getX() + 20 + 50, (int) (getY() + y + FontUtil.getStringHeight(text) + 3), Color.black.hashCode());
                Gui.drawRect((int) getX() + 20, (int) (getY() + y + FontUtil.getStringHeight(text)), (int) (getX() + 20 + (50 * (ItemsUtil.getItemDurabilityInPercentages(armorStack) / 100f))), (int) (getY() + y + FontUtil.getStringHeight(text) + 3), new Color(armorStack.getItem().getRGBDurabilityForDisplay(armorStack)).hashCode());
                //mc.getRenderItem().renderItemOverlayIntoGUI(mc.fontRenderer, armorStack, (int) getX(), (int) getY() + y, null);
            }

            y += 20;
        }

        this.height = y;
        this.width = 20 + maxWidth;


        RenderHelper.disableStandardItemLighting();

        mc.getRenderItem().zLevel = 0.0f;

        GlStateManager.popMatrix();
    }
}
