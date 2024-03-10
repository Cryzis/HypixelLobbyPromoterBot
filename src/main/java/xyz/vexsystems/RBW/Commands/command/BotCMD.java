package xyz.vexsystems.RBW.Commands.command;

import net.minecraft.command.ICommandSender;
import xyz.vexsystems.RBW.Bot.BotMasterControl;
import xyz.vexsystems.RBW.Commands.ICommandHandler;
import xyz.vexsystems.RBW.Utils.utils;

public class BotCMD implements ICommandHandler {

    @Override
    public void processCommand(ICommandSender sender, String[] args) {
        if (args.length == 1 && args[0].equalsIgnoreCase("bot")) {
            toggleBot();
        } else {
            utils.sendChatMessage("&cInvalid command. Usage: /rbw bot");
        }
    }

    private void toggleBot() {
        BotMasterControl botMasterControl = BotMasterControl.getInstance();
        if (botMasterControl.isBotRunning()) {
            botMasterControl.stopBot();
            utils.sendChatMessage("&cBot stopped.");
        } else {
            botMasterControl.startBot();
            utils.sendChatMessage("&aBot started.");
        }
    }
}
