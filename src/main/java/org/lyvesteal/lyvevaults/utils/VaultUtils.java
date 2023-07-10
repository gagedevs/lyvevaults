package org.lyvesteal.lyvevaults.utils;

import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;
import org.lyvesteal.lyvevaults.Main;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.*;

public class VaultUtils {
    public static void storeItems(HashMap<Integer, ItemStack> items, Player p, Integer num) {
        PersistentDataContainer data = p.getPersistentDataContainer();
        if(items.size() == 0) {
            data.set(new NamespacedKey(Main.getPlugin(), "vault"+num), PersistentDataType.STRING, "");
        } else {
            try {
                ByteArrayOutputStream io = new ByteArrayOutputStream();
                BukkitObjectOutputStream os = new BukkitObjectOutputStream(io);
                os.writeInt(items.size());
                items.forEach((i, item) -> {
                    try{
                        os.writeInt(i);
                        os.writeObject(items.get(i));
                    }catch(IOException e) {
                        System.out.println(e);
                    }
                });
                os.flush();
                byte[] rawData = io.toByteArray();
                String encodedData = Base64.getEncoder().encodeToString(rawData);
                data.set(new NamespacedKey(Main.getPlugin(), "vault"+num), PersistentDataType.STRING, encodedData);
                os.close();
            } catch(IOException e) {
                System.out.println(e);
            }

        }
    }
    public static HashMap<Integer, ItemStack> getItems(Player p, Integer num) {
        PersistentDataContainer data = p.getPersistentDataContainer();
        HashMap<Integer, ItemStack> items = new HashMap<Integer, ItemStack>();
        if(data.get(new NamespacedKey(Main.getPlugin(), "vault"+num), PersistentDataType.STRING) == null) {
            data.set(new NamespacedKey(Main.getPlugin(), "vault"+num), PersistentDataType.STRING, "");
        }
        String encodedData = data.get(new NamespacedKey(Main.getPlugin(), "vault"+num), PersistentDataType.STRING);
        if(!encodedData.isEmpty()) {
            byte[] rawData = Base64.getDecoder().decode(encodedData);
            try {
                ByteArrayInputStream io = new ByteArrayInputStream(rawData);
                BukkitObjectInputStream in = new BukkitObjectInputStream(io);
                int itemCount = in.readInt();
                for (int i = 0; i<itemCount; i++) {
                    items.put(in.readInt(), (ItemStack) in.readObject());
                }
                in.close();
            } catch(IOException | ClassNotFoundException e) {
                System.out.println(e);
            }
        }
        return items;
    }
}
