package space.moonstudio.moonairdrop.utils;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

public class AirdropThread {
    private static HashMap<Player, AirdropThread> threads = new HashMap<>();
    private Player from, to;
    private ItemStack item;

    public AirdropThread(Player from, Player to, ItemStack item) {
        this.from = from;
        this.to = to;
        this.item = item;
        threads.put(to, this);
        AirdropRunnable runnable = () -> { this.remove(); };
        AirdropRunnable.runThread(runnable, 20L*15);
    }

    public void remove() {
        threads.remove(to);
    }

    public ItemStack getItem() {
        return item;
    }

    public Player getFrom() {
        return from;
    }

    public Player getTo() {
        return to;
    }

    public static AirdropThread getThread(Player to) {
        return threads.get(to);
    }
}