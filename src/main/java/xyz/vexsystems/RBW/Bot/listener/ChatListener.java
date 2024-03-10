package xyz.vexsystems.RBW.Bot.listener;

import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import xyz.vexsystems.RBW.Bot.BotMasterControl;
import xyz.vexsystems.RBW.Bot.events.DMplayer;
import xyz.vexsystems.RBW.Bot.events.SendCommand;
import xyz.vexsystems.RBW.Utils.utils;

import java.io.*;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class ChatListener {
    private BotMasterControl masterControl;
    private List<String> failSafeMessages = new ArrayList<>();
    private List<String> fallBackMessages = new ArrayList<>();

    public ChatListener() {
        this.masterControl = BotMasterControl.getInstance();
        loadFailSafeMessages();
        loadFallBackMessages();

    }

    private void loadFailSafeMessages() {
        String filePath = Paths.get("config", "RBW","FailSafe", "FailSafe.txt").toString();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                failSafeMessages.add(line);
            }
        } catch (IOException e) {
            createFailSafeFile(filePath);
            e.printStackTrace();
        }
    }

    private void loadFallBackMessages() {
        String filePath = Paths.get("config", "RBW","FailSafe", "FallBack.txt").toString();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                fallBackMessages.add(line);
            }
        } catch (IOException e) {
            createFallBackFile(filePath);
            e.printStackTrace();
        }
    }

    private void createFailSafeFile(String filePath) {
        try {
            File file = new File(filePath);
            if (!file.exists()) {
                file.getParentFile().mkdirs();
                file.createNewFile();
                try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                    writer.write("You can only chat once every 3 seconds! Ranked users bypass this restriction!\n" +
                            "You cannot say the same message twice!\n" +
                            "Woah there, slow down!");
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
            utils.sendChatMessage("&c[ERROR] Error sent to vexsystems support.");
            utils.sendChatMessage("&c[ERROR] Please contact Cryzis [cryzis.]");
            utils.sendChatMessage("&c[ERROR] ERROR: Unable to create FailSafe.txt.");
        }
    }


    private void createFallBackFile(String filePath) {
        try {
            File file = new File(filePath);
            if (!file.exists()) {
                file.getParentFile().mkdirs();
                file.createNewFile();
                try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                    writer.write("You were spawned in Limbo.");
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
            utils.sendChatMessage("&c[ERROR] Error sent to vexsystems support.");
            utils.sendChatMessage("&c[ERROR] Please contact Cryzis [cryzis.]");
            utils.sendChatMessage("&c[ERROR] ERROR: Unable to create FailSafe.txt.");
        }
    }


    @SubscribeEvent
    public void onChatMessageReceived(ClientChatReceivedEvent event) {
        String message = event.message.getUnformattedText();

        if (message.contains("Friend request from ")) {
            int startIndex = message.indexOf("Friend request from ") + "Friend request from ".length();
            int endIndex = message.indexOf("\n", startIndex);
            if (endIndex == -1) {
                endIndex = message.length();
            }
            String username = message.substring(startIndex, endIndex).trim();

            if (username.startsWith("[")) {
                int rankEndIndex = username.indexOf("]") + 1;
                username = username.substring(rankEndIndex).trim();
            }

            utils.sendChatMessage("&6 [BOT] Friend request from " + username +". Accepting request.");
            DMplayer.processFriendRequest(username);
        }
















        if (masterControl.isBotRunning()) {

            for (String failSafeMessage : failSafeMessages) {
                if (message.contains(failSafeMessage)) {
                    masterControl.stopBot();
                    utils.sendChatMessage("&6 [BOT] Timed Out, changing servers..");
                    Timer timer = new Timer();
                    timer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            SendCommand sendCommand = new SendCommand();
                            sendCommand.send();
                            Timer botStartTimer = new Timer();
                            botStartTimer.schedule(new TimerTask() {
                                @Override
                                public void run() {
                                    masterControl.startBot();
                                }
                            }, 9000);
                        }
                    }, 9000);
                    return;
                }
            }
            for (String fallbackMessage : fallBackMessages) {
                if (message.contains(fallbackMessage)) {
                    utils.sendChatMessage("&6 [BOT] Sent to Limbo. Requesting Bedwars Lobby..");
                    Timer timer = new Timer();
                    timer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            Minecraft.getMinecraft().thePlayer.sendChatMessage("/lobby bedwars");
                            Timer botStartTimer = new Timer();
                            botStartTimer.schedule(new TimerTask() {
                                @Override
                                public void run() {
                                    masterControl.startBot();
                                }
                            }, 3000);
                        }
                    }, 3000);
                    return;
                }
            }
        }
    }
}