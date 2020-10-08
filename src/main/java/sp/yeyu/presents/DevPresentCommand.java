package sp.yeyu.presents;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class DevPresentCommand implements CommandExecutor {
    /**
     * Executes the given command, returning its success.
     * <br>
     * If false is returned, then the "usage" plugin.yml entry for this command
     * (if defined) will be sent to the player.
     *
     * @param sender  Source of the command
     * @param command Command which was executed
     * @param label   Alias of the command which was used
     * @param args    Passed command arguments
     * @return true if a valid command, otherwise false
     */
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) return false;
        if(!sender.isOp()) {
            sender.sendMessage(ChatColor.RED + "Only OPs can run this command. Check your permission.");
            return false;
        }
        if (args.length > 1) sender.sendMessage(ChatColor.YELLOW + "Only one parameter is allowed." + ChatColor.RESET);
        ((Player) sender).getInventory().addItem(HeadUtil.random().get());
        return true;
    }
}
