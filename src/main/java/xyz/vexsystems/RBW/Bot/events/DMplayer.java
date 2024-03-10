package xyz.vexsystems.RBW.Bot.events;

import net.minecraft.client.Minecraft;
import xyz.vexsystems.RBW.Bot.BotMasterControl;

import java.util.Timer;
import java.util.TimerTask;

public class DMplayer {
    public static void processFriendRequest(String username) {
        int cumulativeDelay = 0;
        BotMasterControl.stopBot();
        cumulativeDelay = sendDelayedMessage("/f accept " + username, cumulativeDelay);
        cumulativeDelay = sendDelayedMessage("/msg " + username + " Hey " + username + "!", cumulativeDelay + 2400);
        cumulativeDelay = sendDelayedMessage("/msg " + username + " you gotta join the discord", cumulativeDelay + 2300);
        cumulativeDelay = sendDelayedMessage("/msg " + username + " gimme a sec, let me generate a discord invite", cumulativeDelay + 3000);
        cumulativeDelay = sendDelayedMessage("/msg " + username + " discord.gg/ashjdvb2", cumulativeDelay + 7000);
        cumulativeDelay = sendDelayedMessage("/msg " + username + " bye", cumulativeDelay + 2000);
        cumulativeDelay = sendDelayedMessage("/msg " + username + " need to remove you as a friend lol", cumulativeDelay + 2500);
        cumulativeDelay = sendDelayedMessage("/ignore add " + username, cumulativeDelay + 5000);
        cumulativeDelay = sendDelayedMessage("/f remove " + username, cumulativeDelay + 3800);
        cumulativeDelay = startBotWithDelay(cumulativeDelay + 5000);
    }

    private static int startBotWithDelay(int delay) {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                SendCommand sendCommand = new SendCommand();
                sendCommand.send();
                BotMasterControl.startBot();
            }
        }, delay);
        return delay;
    }

    private static int sendDelayedMessage(String message, int delay) {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Minecraft.getMinecraft().thePlayer.sendChatMessage(message);
            }
        }, delay);
        return delay;
    }
}
