package com.bt.BThack.impl.HudComponents;

import com.bt.BThack.System.Client;
import com.bt.BThack.api.HudComponent.HudComponent;
import com.bt.BThack.api.Module.Module;
import com.bt.BThack.api.Utils.Font.FontUtil;
import com.bt.BThack.api.Utils.Render.ColourUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;

import java.awt.*;
import java.util.ArrayList;

public class ArrayListComponent extends HudComponent {

    public ArrayListComponent(Module module) {
        super("ArrayList",
                new ScaledResolution(Minecraft.getMinecraft()).getScaledWidth(),
                5,
                true,
                module
        );

        addCheckbox("Draw Rects", true);
        addCheckbox("ArrayList rainbow", true);
        addSlider("Rainbow type", 3, 1, 8, true);
    }

    @Override
    public void render(RenderType t) {
        if (nullCheck()) return;

        int y = (int) this.getY();
        int yHeight = 0;

        int type = (int) getSlider("Rainbow type");
        final int[] counter = {1};

        //if (this.x > sc.getScaledWidth())
        //    this.x = sc.getScaledWidth();

        //if (this.y > sc.getScaledHeight() - 5)
        //    this.y = sc.getScaledHeight() - 5;



        ArrayList<Module> enabledModules = new ArrayList<>();

        for (Module module : Client.modules) {
            if (module.toggled) {
                enabledModules.add(module);
            }
        }

        enabledModules.sort((module1, module2) -> (int) FontUtil.getStringWidth(module2.getName()) - (int)FontUtil.getStringWidth(module1.getName()));

        float maxLength = 0;

        for (Module module : enabledModules) {
            if (module.toggled && module.visible) {
                if (maxLength < FontUtil.getStringWidth(module.name)) {
                    maxLength = FontUtil.getStringWidth(module.name);
                }

                if (getCheckbox("ArrayList rainbow")) {
                    if (getCheckbox("Draw Rects"))
                        Gui.drawRect((int) this.getX(), y, (int) this.getX() - 2, y + 10, ColourUtils.rainbowType(type, counter[0]));
                    FontUtil.drawText(module.name, (this.getX() - 4 - FontUtil.getStringWidth(module.name)), y, ColourUtils.rainbowType(type, counter[0]));
                } else {
                    if (getCheckbox("Draw Rects"))
                        Gui.drawRect((int) this.getX(), y, (int) this.getX() - 2, y + 10, (new Color(Client.colourTheme.getArrayListColour())).hashCode());
                    FontUtil.drawText(module.name, ((int) this.getX() - 4 - FontUtil.getStringWidth(module.name)), y, (new Color(Client.colourTheme.getArrayListColour())).hashCode());
                }

                y += 10;
                yHeight += 10;
                counter[0]++;
            }
        }

        this.width = -(7 + maxLength);
        this.height = yHeight;
    }
}
