package xyz.vexsystems.RBW.Commands;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import xyz.vexsystems.RBW.Commands.command.BotCMD;
import xyz.vexsystems.RBW.Commands.command.Help;
import xyz.vexsystems.RBW.Utils.utils;

import java.util.HashMap;
import java.util.Map;

public class CommandManager extends CommandBase {

    private Map<String, ICommandHandler> commands = new HashMap<>();

    public CommandManager() {
        registerCommand("help", new Help());
        registerCommand("bot", new BotCMD());

    }

    private void registerCommand(String commandName, ICommandHandler commandHandler) {
        commands.put(commandName.toLowerCase(), commandHandler);
    }

    @Override
    public String getCommandName() {
        return "rbw";
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "/rbw help";
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) {
        if (args.length > 0) {
            String subCommandName = args[0].toLowerCase();
            if (commands.containsKey(subCommandName)) {
                ICommandHandler commandHandler = commands.get(subCommandName);
                commandHandler.processCommand(sender, args);
            } else {
                utils.sendChatMessage("&cUnknown command. Type /rbw help for commands. [" + subCommandName + "]");
            }
        } else {
            utils.sendChatMessage("&fRBW&7Logger &7&8- &3Developed by Cryzis");
            utils.sendChatMessage("&7/rbw help &cfor commands.");
        }
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 0;
    }
}
