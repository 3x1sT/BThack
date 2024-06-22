package com.bt.BThack.impl.Module;

import com.bt.BThack.api.Module.Module;
import org.lwjgl.input.Keyboard;

import java.util.ArrayList;

public class SettingsGuide extends Module {
    public SettingsGuide() {
        super("",
                "",
                Keyboard.KEY_NONE,
                Category.CLIENT,
                false
        );

        //Лист с режимами функции
        ArrayList<String> options = new ArrayList<>();

        options.add("Test1");
        options.add("Test2");
        options.add("Test3");

        //Режим функции, например Packet, Bypass, Vanilla и тд.
        //               (Название настройки, хз, название листа с режимами, отображаемое название настройки)
        addMode("Mode", this, options, "mode");

                /*
                Ползунок с выбором значений
        dval = дефолтное значение
        min = минемальное значение
        max = максимальное значение
        onlyint :  false = детальная настройка(1.1-1.2-1.3 и тд.)
                   true = грубая настройка(1-2-3 и тд.)
         */
        addSlider("Range", this, 4.0,1,5,false);

        //CheckBox = обычный выбор включено/выключено                                  (вкл/выкл по умолчанию)
        addCheckbox("Box", this, true);
    }

    @Override
    public void onEnable() {
        //Получает значение режима функции с настройки          (Название функции, название настройки)
        String Mode = getMode(this.name, "Mode");

        //Получает значение ползунка с настройки                (Название функции, название настройки)
        double range = getSlider(this.name, "Range");

        //Получает значение включено/выключено с настройки      (Название функции, название настройки)
        boolean box = getCheckbox(this.name, "Box");
    }
}
