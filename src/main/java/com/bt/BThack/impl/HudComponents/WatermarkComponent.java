package com.bt.BThack.impl.HudComponents;

import com.bt.BThack.System.Client;
import com.bt.BThack.api.HudComponent.HudComponent;
import com.bt.BThack.api.Module.Module;
import com.bt.BThack.api.Utils.Font.FontUtil;
import com.bt.BThack.api.Utils.Render.RenderUtils;
import com.bt.BThack.impl.CustomGui.Menu.BThackMenu;
import net.minecraft.client.renderer.GlStateManager;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;

public class WatermarkComponent extends HudComponent {

    public WatermarkComponent(Module module) {
        super("Watermark",
                5,
                5,
                true,
                module
        );

        ArrayList<String> options = new ArrayList<>();
        options.add("Logo");
        options.add("Text");

        addMode("Logo Type", options);
    }

    @Override
    public void render(RenderType type) {
        if (nullCheck()) return;

        outOfBoundsCheck();

        if (type == RenderType.TEXT) {
            if (getMode("Logo Type").equals("Text")) {
                drawText(Client.cName, this.getX(), this.getY());
                this.width = FontUtil.getStringWidth(Client.cName);
                this.height = FontUtil.getStringHeight(Client.cName);
            }
        } else if (type == RenderType.IMAGE) {
            if (getMode("Logo Type").equals("Logo")) {
                GlStateManager.pushMatrix();
                mc.renderEngine.bindTexture(BThackMenu.BThackLogo);
                glEnable(GL_BLEND);
                glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
                RenderUtils.drawScaledCustomSizeModalRect(this.getX(), this.getY() - 18, 0, 0, 138, 72, 138, 72, 138, 72);
                glDisable(GL_BLEND);
                GL11.glBindTexture(GL_TEXTURE_2D, 0);
                GlStateManager.popMatrix();

                this.width = 138;
                this.height = 42;
            }
        }
    }
}
