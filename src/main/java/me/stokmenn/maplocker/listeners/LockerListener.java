package me.stokmenn.maplocker.listeners;

import io.papermc.paper.event.player.CartographyItemEvent;
import me.stokmenn.maplocker.MapLocker;
import me.stokmenn.maplocker.config.Config;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

public class LockerListener implements Listener {
    private final MapLocker plugin;

    public LockerListener(MapLocker plugin) {
        this.plugin = plugin;
    }

//    @EventHandler
//    public void onCartographyTableUse(InventoryClickEvent event) {
//        if (event.getView().getType() != InventoryType.CARTOGRAPHY) return;
//        if (event.getRawSlot() != 0) return;
//
//        ItemStack cursor = event.getCursor();
//        if (cursor.getType() != Material.FILLED_MAP) return;
//
//        ItemMeta meta = cursor.getItemMeta();
//        if (meta == null) return;
//
//        if (!meta.getPersistentDataContainer().has(
//                new NamespacedKey(plugin, "locked"), PersistentDataType.BYTE)) return;
//
//        event.setCancelled(true);
//        Player player = (Player) event.getWhoClicked();
//        player.sendMessage(Config.map);
//
//        player.getScheduler().runDelayed(plugin, task -> event.getInventory().close(), null, 1L);
//    }

    @EventHandler
    public void onCartographyTableUse(CartographyItemEvent event) {
        ItemStack item = event.getCurrentItem();
        if (item == null) return;
        if (item.getType() != Material.FILLED_MAP) return;

        ItemMeta meta = item.getItemMeta();
        if (meta == null) return;

        if (!meta.getPersistentDataContainer().has(
                new NamespacedKey(plugin, "locked"), PersistentDataType.BYTE)) return;

        event.setCancelled(true);
        Player player = (Player) event.getWhoClicked();
        player.sendMessage(Config.map);

        player.getScheduler().runDelayed(plugin, task -> event.getInventory().close(), null, 1L);
    }

    @EventHandler
    public void onCraft(CraftItemEvent event) {
        ItemStack result = event.getInventory().getResult();
        if (result == null) return;

        Material type = result.getType();
        if (!plugin.getRecipeManager().getARMOR_TEMPLATES().contains(type) && type != Material.FILLED_MAP) return;

        HumanEntity player = event.getView().getPlayer();
        for (ItemStack item : event.getInventory().getMatrix()) {
            if (item == null) continue;
            if (!plugin.getRecipeManager().getARMOR_TEMPLATES().contains(item.getType())
                    && item.getType() != Material.FILLED_MAP) continue;

            ItemMeta meta = item.getItemMeta();
            if (meta == null) continue;

            if (!meta.getPersistentDataContainer().has(
                    new NamespacedKey(plugin, "locked"), PersistentDataType.BYTE)) continue;

            event.setCancelled(true);
            if (type == Material.FILLED_MAP) {
                player.sendMessage(Config.map);
            } else {
                player.sendMessage(Config.template);
            }

            player.getScheduler().runDelayed(plugin, task -> event.getInventory().close(), null, 1L);
        }
    }
}