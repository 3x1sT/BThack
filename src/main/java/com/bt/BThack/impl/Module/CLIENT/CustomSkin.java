package com.bt.BThack.impl.Module.CLIENT;

import com.bt.BThack.api.Module.Module;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.input.Keyboard;

import java.util.ArrayList;
import java.util.Arrays;

public class CustomSkin extends Module {
    public static final ResourceLocation popBobSkin = new ResourceLocation("bthack", "skins/popbob.png");
    public static final ResourceLocation hauseMasterSkin = new ResourceLocation("bthack", "skins/hausemaster.png");
    public static final ResourceLocation iTristanSkin = new ResourceLocation("bthack", "skins/itristan.png");
    public static final ResourceLocation jared2013Skin = new ResourceLocation("bthack", "skins/jared2013.png");
    public static final ResourceLocation theCampingRusherSkin = new ResourceLocation("bthack", "skins/thecampingrusher.png");
    public static final ResourceLocation xcc2Skin = new ResourceLocation("bthack", "skins/xcc2.png");

    public CustomSkin() {
        super("CustomSkin",
                "Allows you to change your skin.",
                Keyboard.KEY_NONE,
                Category.CLIENT,
                false
        );
        ArrayList<String> skins = new ArrayList<>(Arrays.asList(
                "PopBob",
                "HauseMaster",
                "ITristan",
                "Jared2013",
                "TheCampingRusher",
                "xcc2"
        ));

        addMode("Skin", this, skins);

        addCheckbox("No Slim Skin", this, true);
    }
}
