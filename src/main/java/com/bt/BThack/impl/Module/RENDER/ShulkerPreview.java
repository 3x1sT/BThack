package com.bt.BThack.impl.Module.RENDER;

import com.bt.BThack.api.Module.Module;
import com.bt.BThack.api.Utils.Render.ColourUtils;
import com.bt.BThack.api.Utils.Render.RenderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.NonNullList;
import org.lwjgl.input.Keyboard;

import java.awt.*;

public class ShulkerPreview extends Module {

    public ShulkerPreview() {
        super("ShulkerPreview",
                "Shows the contents of the shulker.",
                Keyboard.KEY_NONE,
                Category.RENDER,
                false
        );
    }

    public static void renderShulkerTooltip(ItemStack itemStack, int x, int y) {
        Minecraft mc = Minecraft.getMinecraft();

        GlStateManager.disableLighting();
        GlStateManager.disableDepth();

        Gui.drawRect(x + 8, y - 21, x + 158, y - 6, ColourUtils.rainbow(100));
        Gui.drawRect(x + 8, y - 6, x + 158, y + 48, new Color(0, 0, 0, 225).getRGB());
        RenderUtils.drawOutlineRect(x + 7, y - 22, x + 159, y + 49, 1, Color.white.hashCode());

        mc.fontRenderer.drawString(itemStack.getDisplayName(), x + 10, y - 18, -1, true);

        //Font renderer doesn't work here for some reason :/
        //FontUtil.drawText(itemStack.getDisplayName(), x + 10, y - 18, Color.white.hashCode());

        GlStateManager.enableDepth();

        mc.getRenderItem().zLevel = 150.0f;

        RenderHelper.enableGUIStandardItemLighting();

        NonNullList<ItemStack> stacks = getShulkerItems(itemStack);

        for (int i = 0; i < 27; i++) {
            int offsetX = x + (i % 9) * 16 + 11;
            int offsetY = y + (i / 9) * 16 - 3;

            ItemStack stack = stacks.get(i);

            mc.getRenderItem().renderItemAndEffectIntoGUI(stack, offsetX, offsetY);
            mc.getRenderItem().renderItemOverlayIntoGUI(mc.fontRenderer, stack, offsetX, offsetY, null);
        }

        RenderHelper.disableStandardItemLighting();

        mc.getRenderItem().zLevel = 0.0f;

        GlStateManager.enableLighting();
    }


    private static NonNullList<ItemStack> getShulkerItems(ItemStack stack) {
        NonNullList<ItemStack> content = NonNullList.withSize(27, ItemStack.EMPTY);

        NBTTagCompound compound = stack.getTagCompound();
        if (compound != null && compound.hasKey("BlockEntityTag", 10)) {
            NBTTagCompound tags = compound.getCompoundTag("BlockEntityTag");
            if (tags.hasKey("Items", 9)) {
                ItemStackHelper.loadAllItems(tags, content);
            }
        }
        return content;
    }
}
