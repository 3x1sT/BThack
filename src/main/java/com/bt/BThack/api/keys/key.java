package com.bt.BThack.api.keys;

import com.bt.BThack.System.Client;
import com.bt.BThack.api.CommandSystem.manager.CommandManager;
import com.bt.BThack.api.Utils.Interfaces.Mc;
import net.minecraftforge.client.event.ClientChatEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import org.lwjgl.input.Keyboard;

public class key implements Mc {

    @SubscribeEvent
    public void onKeyInput(InputEvent.KeyInputEvent e) {
        if (Keyboard.isKeyDown(Keyboard.getEventKey())) {
            if (Keyboard.getEventKey() != Keyboard.KEY_NONE) {
                Client.keyPress(Keyboard.getEventKey());
            }
        }
    }

    @SubscribeEvent
    public void onClientChat(ClientChatEvent event) {
        if (event.getMessage().startsWith(Client.ChatPrefix)) {
            event.setCanceled(true);

            mc.ingameGUI.getChatGUI().addToSentMessages(event.getOriginalMessage());

            CommandManager.parseCommand(event.getMessage().replace(Client.ChatPrefix, ""));
        } else {
            event.setMessage(event.getMessage());
        }
    }
}
