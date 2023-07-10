package org.lyvesteal.lyvevaults.utils;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import org.lyvesteal.lyvevaults.Main;

public class InvUtils implements InventoryHolder {
    private static Inventory inventory;

    public InvUtils(Integer num) {
        Plugin plugin = Main.getPlugin();
        this.inventory = plugin.getServer().createInventory(this, 54, "Vault #"+num);
    }
    @Override
    public @NotNull Inventory getInventory() {
        return this.inventory;
    }
    public void addItem(ItemStack item) {
        this.inventory.addItem(item);
    }
}
