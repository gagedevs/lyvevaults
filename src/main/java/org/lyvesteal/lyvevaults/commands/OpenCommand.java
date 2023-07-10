package org.lyvesteal.lyvevaults.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.lyvesteal.lyvevaults.Main;
import org.lyvesteal.lyvevaults.utils.InvUtils;
import org.lyvesteal.lyvevaults.utils.VaultUtils;

import java.text.NumberFormat;
import java.util.HashMap;

public class OpenCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, Command command, @NotNull String label, String[] args) {
        if(command.getName().equalsIgnoreCase("pv")) {
            if(!(sender instanceof Player)) {
                sender.sendMessage("Command only executable by players.");
                return false;
            } else {
                Player p = (Player) sender;
                if(!p.hasPermission("lyvevaults.vault")) {
                    p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8[&clyvesteal&8] | &cYou do not have permission to execute this!"));
                    return false;
                }
                if(args.length == 0) {
                    HashMap<Integer, ItemStack> items = VaultUtils.getItems(p, 1);
                    InvUtils vault = new InvUtils(1);
                    items.forEach((i, item) -> {
                        vault.getInventory().setItem(i, item);
                    });
                    p.openInventory(vault.getInventory());
                } else {
                    try {
                        int vaultNum = Integer.parseInt(args[0]);
                        boolean hasPerms = false;
                        for(int i = vaultNum; i>0; i--) {
                            if(p.hasPermission("lyvevaults.vault."+vaultNum)) {
                                hasPerms = true;
                                break;
                            }
                        }
                        if(hasPerms) {
                            HashMap<Integer, ItemStack> items = VaultUtils.getItems(p, vaultNum);
                            InvUtils vault = new InvUtils(vaultNum);
                            items.forEach((i, item) -> {
                                vault.getInventory().setItem(i, item);
                            });
                            p.openInventory(vault.getInventory());
                        } else {
                            Main.getPlugin().getLogger().info("11");
                            p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8[&clyvesteal&8] | &cYou do not have permission to execute this!"));
                            return false;
                        }

                    }catch(NumberFormatException e) {
                        p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8[&clyvesteal&8] | &cPlease specify a number vault to open"));
                    }
                }

            }
        }
        return true;
    }

}
