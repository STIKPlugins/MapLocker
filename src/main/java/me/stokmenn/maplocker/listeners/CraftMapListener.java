package me.stokmenn.maplocker.listeners;

import me.stokmenn.maplocker.config.Config;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public class CraftMapListener implements Listener {
    private final JavaPlugin plugin;

    public CraftMapListener(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPrepareCraft(PrepareItemCraftEvent event) {
        ItemStack[] matrix = event.getInventory().getMatrix();
        if (matrix.length != 9) return;

        ItemStack center = matrix[4];
        if (center == null || center.getType() != Material.FILLED_MAP) return;

        ItemMeta meta = center.getItemMeta();
        if (meta == null) return;

        for (int i = 0; i < 9; i++) {
            if (matrix[i] == null) return;
            if (matrix[i].getAmount() != 1) return;
            if (matrix[i].getType() != Material.HONEYCOMB && i != 4) return;
        }

        if (meta.getPersistentDataContainer().has(
                new NamespacedKey(plugin, "locked"), PersistentDataType.BYTE)) return;

        event.getInventory().setResult(lockItem(center));
    }

    private ItemStack lockItem(ItemStack item) {
        ItemStack result = item.clone();
        ItemMeta meta = result.getItemMeta();
        if (meta == null) return result;

        meta.getPersistentDataContainer().set(
                new NamespacedKey(plugin, "locked"), PersistentDataType.BYTE, (byte) 1);

        List<Component> lore = new ArrayList<>();
        lore.add(Config.locked.decoration(TextDecoration.ITALIC, false));

        if (meta.hasLore()) {
            lore.addAll(0, meta.lore());
        }

        meta.lore(lore);
        result.setItemMeta(meta);
        return result;
    }
}
