package space.moonstudio.moonairdrop.utils;

import org.bukkit.Bukkit;
import space.moonstudio.moonairdrop.main.SpigotPlugin;

public interface AirdropRunnable {
    void run();

    static void runThread(AirdropRunnable runnable, long ticks) {
        Bukkit.getScheduler().runTaskLater(SpigotPlugin.getPlugin(SpigotPlugin.class), () -> {
            runnable.run();
        }, ticks);
    }
}