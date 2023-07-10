package org.lyvesteal.lyvevaults;

import org.bukkit.plugin.java.JavaPlugin;
import org.lyvesteal.lyvevaults.commands.OpenCommand;
import org.lyvesteal.lyvevaults.listeners.Listeners;

public final class Main extends JavaPlugin {
    private static Main plugin;

    public static Main getPlugin() {
        return plugin;
    }

    @Override
    public void onEnable() {
        // Plugin startup login
        plugin = this;
        plugin.getLogger().info("[LYVESTEAL] Started plugin!");
        getCommand("pv").setExecutor(new OpenCommand());
        getServer().getPluginManager().registerEvents(new Listeners(), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        plugin.getLogger().info("[LYVESTEAL] Stopping plugin!");
    }

}
