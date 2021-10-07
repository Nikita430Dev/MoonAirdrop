package space.moonstudio.moonairdrop.events;

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import space.moonstudio.moonairdrop.main.SpigotPlugin;
import space.moonstudio.moonairdrop.utils.AirdropThread;
import space.moonstudio.moonairdrop.utils.ChatUtils;

public class AirdropCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) return false;
        Player player = (Player) sender;
        if (args.length == 1) {
            if (args[0].equals("accept")) {
                if (AirdropThread.getThread(player) == null) {
                    player.sendMessage(ChatUtils.parseColor(SpigotPlugin.getPrefix()+"&fВам никто не присылал &9AirDrop&f."));
                    return true;
                }
                AirdropThread thread = AirdropThread.getThread(player);
                thread.getFrom().getInventory().remove(thread.getItem());
                player.getInventory().addItem(thread.getItem());
                thread.getFrom().sendMessage("");
                thread.getFrom().sendMessage(ChatUtils.parseColor(SpigotPlugin.getPrefix()+"&fИгрок &9"+player.getName()+" &fпринял ваш &9AirDrop&f."));
                thread.getFrom().sendMessage("");
                player.sendMessage("");
                player.sendMessage(ChatUtils.parseColor(SpigotPlugin.getPrefix()+"&fВы успешно приняли &9AirDrop &fот игрока &9"+thread.getFrom().getName()));
                player.sendMessage("");
                thread.remove();
                return true;
            }
        }
        if (args.length == 2) {
            if (args[0].equals("send")) {
                Player p1 = Bukkit.getPlayer(args[1]);
                if (p1 == null) {
                    player.sendMessage(ChatUtils.parseColor(SpigotPlugin.getPrefix()+"&fИгрок &9"+args[1]+" &fне в сети."));
                    return true;
                }
                if (AirdropThread.getThread(p1) != null) {
                    player.sendMessage(ChatUtils.parseColor(SpigotPlugin.getPrefix()+"&fИгроку &9"+args[1]+" &fуже отправили запрос на &9AirDrop&f."));
                    return true;
                }
                if (player.getInventory().getItem(player.getInventory().getHeldItemSlot()) == null || player.getInventory().getItem(player.getInventory().getHeldItemSlot()).getType().equals(Material.AIR)) {
                    player.sendMessage(ChatUtils.parseColor(SpigotPlugin.getPrefix()+"&fВы не взяли предмет в руки."));
                    return true;
                }
                AirdropThread thread = new AirdropThread(player, p1, player.getInventory().getItem(player.getInventory().getHeldItemSlot()));
                p1.sendMessage("");
                p1.sendMessage(ChatUtils.parseColor(SpigotPlugin.getPrefix()+"&fИгрок &9"+player.getName()+" &fхочет отправить вам &9AirDrop&f."));
                p1.spigot().sendMessage(new ComponentBuilder(ChatUtils.parseColor(SpigotPlugin.getPrefix())).append(ChatUtils.parseColor("&7[/airdrop accept]")).event(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/airdrop accept")).create());
                p1.sendMessage("");
                p1.sendTitle(ChatUtils.parseColor("&fВам прислали &9AirDrop"), ChatUtils.parseColor("&7Нажмите на сообщение в чате."));
                player.sendMessage(ChatUtils.parseColor(SpigotPlugin.getPrefix()+"&fВы отправили игроку &9"+p1.getName()+" &fсвой &9AirDrop&f."));
                return true;
            }
        }
        return help(player);
    }

    private boolean help(Player player) {
        player.sendMessage(ChatUtils.parseColor(SpigotPlugin.getPrefix()+"&f/airdrop accept - принять &9AirDrop"));
        player.sendMessage(ChatUtils.parseColor(SpigotPlugin.getPrefix()+"&f/airdrop send <игрок> - прислать &9AirDrop &fигроку"));
        return true;
    }
}