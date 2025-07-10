package me.stokmenn.maplocker.listeners;

import me.stokmenn.maplocker.MapLocker;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.CrafterCraftEvent;

public class CrafterLockerListener implements Listener {
    private final MapLocker plugin;

    public CrafterLockerListener(MapLocker plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onCrafterCraft(CrafterCraftEvent event) {
        Material type = event.getResult().getType();
        if (!plugin.getRecipeManager().getARMOR_TEMPLATES().contains(type) && type != Material.FILLED_MAP) return;

        event.setCancelled(true);
    }
}
