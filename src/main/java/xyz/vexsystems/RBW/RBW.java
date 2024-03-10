package xyz.vexsystems.RBW;

import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import xyz.vexsystems.RBW.Bot.BotHandler;
import xyz.vexsystems.RBW.Bot.BotMasterControl;
import xyz.vexsystems.RBW.Bot.listener.ChatListener;
import xyz.vexsystems.RBW.Commands.CommandManager;
import xyz.vexsystems.RBW.Config.ConfigManager;
import xyz.vexsystems.RBW.Connection.Auth;
import xyz.vexsystems.RBW.Connection.HardwareID;


@Mod(modid = RBW.MODID, version = RBW.VERSION)
public class RBW {
    public static final String MODID = "RBW";
    public static final String VERSION = "1.0";
    public static ConfigManager configManager;
// AUTH REMOVED
    private static boolean authenticate() {
        String hardwareId = HardwareID.getHardwareID();
        String version = VERSION;
        return hardwareId != null && Auth.authenticate(hardwareId, version);

    }
// LOAD THE THINGS
    @EventHandler
    public void init(FMLInitializationEvent event) {
        BotMasterControl masterControl = BotMasterControl.getInstance();
        MinecraftForge.EVENT_BUS.register(new BotHandler(masterControl));
        MinecraftForge.EVENT_BUS.register(new ChatListener());
        ClientCommandHandler.instance.registerCommand(new CommandManager());

    }


// CONFIG
    @EventHandler
    public static void preInit() {
        configManager = new ConfigManager();
    }
}
