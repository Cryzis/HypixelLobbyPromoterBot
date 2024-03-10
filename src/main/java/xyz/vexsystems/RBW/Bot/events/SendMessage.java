package xyz.vexsystems.RBW.Bot.events;

import net.minecraft.client.Minecraft;
import xyz.vexsystems.RBW.Utils.utils;

import java.util.List;
import java.util.Random;

public class SendMessage {
    private List<String> messages;
    private Random random = new Random();
    private String lastSentMessage;

    public SendMessage(List<String> messages) {
        this.messages = messages;
    }

    public void send() {
        if (!messages.isEmpty()) {
            String message;
            if (messages.size() == 1) {
                message = messages.get(0);
            } else {
                do {
                    int index = random.nextInt(messages.size());
                    message = messages.get(index);
                } while (message.equals(lastSentMessage));
            }
            lastSentMessage = message;
            utils.sendChatMessage("&9Requesting message...");
            Minecraft.getMinecraft().thePlayer.sendChatMessage(message);
        } else {
            utils.sendChatMessage("&c[ERROR] Error sent to vexsystems support.");
            utils.sendChatMessage("&c[ERROR] Please contact Cryzis [cryzis.]");
        }
    }
}
