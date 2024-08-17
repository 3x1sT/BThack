package com.bt.BThack.impl.Module.CLIENT;

import com.bt.BThack.api.Module.Module;
import net.minecraftforge.client.event.ClientChatEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.input.Keyboard;

public class ChatCalculator extends Module {

    public ChatCalculator() {
        super("ChatCalculator",
                "Calculator in chat!",
                Keyboard.KEY_NONE,
                Category.CLIENT,
                true
        );
    }

    @SubscribeEvent
    public void chatCalc(ClientChatEvent e) {
        String[] calcArgs = {"*", "+", "-", "/"};

        try {
            for (String msg : e.getMessage().split(" ")) {
                for (String s : calcArgs) {
                    if (msg.contains(s)) {
                        if (s == "*") {
                            e.setMessage(e.getMessage().replace(msg,
                                    Integer.toString(Integer.parseInt(msg.split("\\*")[0])*Integer.parseInt(msg.split("\\*")[1]))));
                        } if (s == "+") {
                            e.setMessage(e.getMessage().replace(msg,
                                    Integer.toString(Integer.parseInt(msg.split("\\+")[0])+Integer.parseInt(msg.split("\\+")[1]))));
                        } if (s == "-") {
                            e.setMessage(e.getMessage().replace(msg,
                                    Integer.toString(Integer.parseInt(msg.split("-")[0])-Integer.parseInt(msg.split("-")[1]))));
                        } if (s == "/") {
                            e.setMessage(e.getMessage().replace(msg,
                                    Integer.toString(Integer.parseInt(msg.split("/")[0])/Integer.parseInt(msg.split("/")[1]))));
                        }
                    }
                }
            }
        } catch (Exception ignored) {}
    }
}
