package xyz.vexsystems.RBW.Bot;

public class BotMasterControl {
    private static BotMasterControl instance;
    private static boolean isRunning;

    private BotMasterControl() {
    }

    public static BotMasterControl getInstance() {
        if (instance == null) {
            instance = new BotMasterControl();
        }
        return instance;
    }

    public static void startBot() {
        isRunning = true;
    }

    public static void stopBot() {
        isRunning = false;
    }

    public boolean isBotRunning() {
        return isRunning;
    }
}
