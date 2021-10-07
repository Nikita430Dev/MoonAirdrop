package space.moonstudio.moonairdrop.main;

import org.bukkit.plugin.java.JavaPlugin;
import space.moonstudio.moonairdrop.events.AirdropCommand;

public class SpigotPlugin extends JavaPlugin {
    private static final String prefix = "&9&lMoon&f&lStudio &8>> ";

    public static String getPrefix() {
        return prefix;
    }

    public void onEnable() {
        getCommand("airdrop").setExecutor(new AirdropCommand());
    }
}