package xyz.vexsystems.RBW.Commands.command;

import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import xyz.vexsystems.RBW.Commands.ICommandHandler;
import xyz.vexsystems.RBW.RBW;
import xyz.vexsystems.RBW.Utils.utils;
import xyz.vexsystems.RBW.Connection.Auth;

public class Help implements ICommandHandler {

    @Override
    public void processCommand(ICommandSender sender, String[] args) {
        if (sender instanceof EntityPlayer) {
            sendHelpMessage((EntityPlayer) sender);
        }
    }

    private void sendHelpMessage(EntityPlayer player) {
        utils.sendChatMessage("&f&m------------=&8 [&fRBW&7Logger &8Help&8] &f&m=------------");
        utils.sendChatMessage("&f[-Help-]");
        utils.sendChatMessage(" &7/rbw &8- &fView credits and version of RBW.");
        utils.sendChatMessage("&f[-Botting-]");
        utils.sendChatMessage(" &7/rbw bot &8- &fToggle the bot.");

        String latestVersion = Auth.getLatestVersion();
        String currentVersion = RBW.VERSION;
        if (latestVersion != null && !latestVersion.isEmpty()) {
            utils.sendChatMessage("&f[-Version-]");
            if (currentVersion.equals(latestVersion)) {
                utils.sendChatMessage(" &aYou are on the latest version: " + currentVersion);
            } else {
                utils.sendChatMessage(" &cYou are on an older build. Please download the latest version from the discord: &f" + currentVersion + " &c-> &f" + latestVersion);
            }
        }
    }
}
