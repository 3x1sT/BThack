package com.bt.BThack.impl.Module.RENDER;

import com.bt.BThack.api.Module.Module;
import com.bt.BThack.impl.Events.TransformFirstPersonEvent;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.EnumHandSide;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.input.Keyboard;

public class ViewModel extends Module {

    public ViewModel() {
        super("ViewModel",
                "Changes the way you look in first person.",
                Keyboard.KEY_NONE,
                Category.RENDER,
                false
        );

        addCheckbox("No Eat Anim", this, false);

        addSlider("Left Hand X", this, 0, -2.0, 2.0, false);
        addSlider("Left Hand Y", this, 0, -2.0, 2.0, false);
        addSlider("Left Hand Z", this, -1.2, -2.0, 2.0, false);

        addSlider("Left Hand Yaw", this, 0, -100, 100, true);
        addSlider("Left Hand Pitch", this, 0, -100, 100, true);
        addSlider("Left Hand Roll", this, 0, -100, 100, true);



        addSlider("Right Hand X", this, 0, -2.0, 2.0, false);
        addSlider("Right Hand Y", this, 0, -2.0, 2.0, false);
        addSlider("Right Hand Z", this, -1.2, -2.0, 2.0, false);

        addSlider("Right Hand Yaw", this, 0, -100, 100, true);
        addSlider("Right Hand Pitch", this, 0, -100, 100, true);
        addSlider("Right Hand Roll", this, 0, -100, 100, true);
    }

    @SubscribeEvent
    public void onTransformSideFirstPerson(TransformFirstPersonEvent.Pre e) {
        if (nullCheck()) return;

        if (e.getEnumHandSide() == EnumHandSide.LEFT) {
            GlStateManager.translate(getSlider(this.name, "Left Hand X"), getSlider(this.name, "Left Hand Y"), getSlider(this.name, "Left Hand Z"));
        }
        if (e.getEnumHandSide() == EnumHandSide.RIGHT) {
            GlStateManager.translate(getSlider(this.name, "Right Hand X"), getSlider(this.name, "Right Hand Y"), getSlider(this.name, "Right Hand Z"));
        }
    }

    @SubscribeEvent
    public void onTransFormPost(TransformFirstPersonEvent.Post e) {
        if (nullCheck()) return;

        if (e.getEnumHandSide() == EnumHandSide.LEFT) {
            GlStateManager.rotate((float)getSlider(this.name, "Left Hand Yaw"),0,1,0);
            GlStateManager.rotate((float)getSlider(this.name, "Left Hand Pitch"),1,0,0);
            GlStateManager.rotate((float)getSlider(this.name, "Left Hand Roll"),0,0,1);
        }

        if (e.getEnumHandSide() == EnumHandSide.RIGHT) {
            GlStateManager.rotate((float)getSlider(this.name, "Right Hand Yaw"),0,1,0);
            GlStateManager.rotate((float)getSlider(this.name, "Right Hand Pitch"),1,0,0);
            GlStateManager.rotate((float)getSlider(this.name, "Right Hand Roll"),0,0,1);
        }
    }
}
