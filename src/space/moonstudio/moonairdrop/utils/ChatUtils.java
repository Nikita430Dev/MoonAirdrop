package space.moonstudio.moonairdrop.utils;

import org.bukkit.ChatColor;

import java.util.List;

public class ChatUtils {
    public static String parseColor(String text) {
        return ChatColor.translateAlternateColorCodes('&', text);
    }
}