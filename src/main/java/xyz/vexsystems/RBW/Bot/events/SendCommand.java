package xyz.vexsystems.RBW.Bot.events;

import net.minecraft.client.Minecraft;
import xyz.vexsystems.RBW.Config.ConfigManager;
import xyz.vexsystems.RBW.Utils.utils;

import java.util.Random;

public class SendCommand {
    private Random random = new Random();
    private ConfigManager configManager = new ConfigManager();

    public void send() {
        int numberOfServers = configManager.getProperty("BotHandler", "servers", 18);
        int randomNumber = random.nextInt(numberOfServers) + 1;
        utils.sendChatMessage("&9Executing command...");
        utils.sendChatMessage("&aSending to BEDWARS LOBBY " + randomNumber);
        Minecraft.getMinecraft().thePlayer.sendChatMessage("/swaplobby " + randomNumber);
    }
}
