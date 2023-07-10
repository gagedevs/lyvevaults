package org.lyvesteal.lyvevaults.listeners;

import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.lyvesteal.lyvevaults.Main;
import org.lyvesteal.lyvevaults.utils.InvUtils;
import org.lyvesteal.lyvevaults.utils.VaultUtils;

import java.util.HashMap;

public class Listeners implements Listener {
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        PersistentDataContainer data = p.getPersistentDataContainer();
        if(!data.has(new NamespacedKey(Main.getPlugin(), "vault"), PersistentDataType.STRING)){
            data.set(new NamespacedKey(Main.getPlugin(), "vault"), PersistentDataType.STRING, "");
        }
    }
    @EventHandler
    public void onInventoryClose(InventoryCloseEvent e) {
        Player p = (Player) e.getPlayer();
        if(e.getInventory().getHolder() instanceof InvUtils) {
            HashMap<Integer, ItemStack> items = new HashMap<Integer, ItemStack>();
            for(int i = 0; i<e.getInventory().getSize(); i++) {
                ItemStack item = e.getInventory().getItem(i);
                if(item != null) {
                    items.put(i, item);
                }
            }
            String inventoryName = e.getView().getTitle();
            String vaultNum = inventoryName.split("#")[1];
            VaultUtils.storeItems(items, p, Integer.parseInt(vaultNum));
        }
    }
}
