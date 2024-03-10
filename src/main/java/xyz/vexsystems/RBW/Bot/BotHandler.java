package xyz.vexsystems.RBW.Bot;

import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import xyz.vexsystems.RBW.Bot.events.SendCommand;
import xyz.vexsystems.RBW.Bot.events.SendMessage;
import xyz.vexsystems.RBW.Config.ConfigManager;
import xyz.vexsystems.RBW.Utils.utils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class BotHandler {
    private static final String MESSAGE_FILE_PATH = "config/RBW/messages.txt";
    private List<String> messages = new ArrayList<>();
    private SendMessage sendMessage;
    private SendCommand sendCommand;
    private int ticksElapsed = 0;
    private int commandTicks = 0;
    private boolean commandScheduled = false;
    private BotMasterControl masterControl;
    private int messageDelay;
    private int commandDelay;

    public BotHandler(BotMasterControl masterControl) {
        this.masterControl = masterControl;
        loadMessages();
        sendMessage = new SendMessage(messages);
        sendCommand = new SendCommand();
        loadConfig();
    }

    private void loadConfig() {
        ConfigManager configManager = new ConfigManager();
        messageDelay = configManager.getProperty("BotHandler", "messageDelay", 200);
        commandDelay = configManager.getProperty("BotHandler", "commandDelay", 60);
    }

    private void loadMessages() {
        File file = new File(MESSAGE_FILE_PATH);
        if (!file.exists()) {
            try {
                file.getParentFile().mkdirs();
                file.createNewFile();
                FileWriter writer = new FileWriter(file);
                writer.write("Your default message here");
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
                utils.sendChatMessage("&c[ERROR] Error sent to VexSystems");
                utils.sendChatMessage("&c[ERROR] Please contact Cryzis [cryzis.]");
                utils.sendChatMessage("&c[ERROR] ERROR: LOADING MESSAGES - BOTHANDLER");
            }
        }

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                messages.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @SubscribeEvent
    public void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.phase == TickEvent.Phase.START && event.player == Minecraft.getMinecraft().thePlayer) {
            ticksElapsed++;
            if (masterControl.isBotRunning()) {
                if (ticksElapsed >= messageDelay) {
                    sendMessage.send();
                    ticksElapsed = 0;
                    commandScheduled = true;
                }
                if (commandScheduled) {
                    commandTicks++;
                    if (commandTicks >= commandDelay) {
                        sendCommand.send();
                        commandTicks = 0;
                        commandScheduled = false;
                    }
                }
            }
        }
    }
}
