package xyz.vexsystems.RBW.Commands;

import net.minecraft.command.ICommandSender;

public interface ICommandHandler {
    void processCommand(ICommandSender sender, String[] args);
}
