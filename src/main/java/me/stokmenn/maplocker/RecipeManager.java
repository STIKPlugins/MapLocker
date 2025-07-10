package me.stokmenn.maplocker;

import me.stokmenn.maplocker.config.Config;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class RecipeManager {
    private final JavaPlugin plugin;

    private final Set<Material> ARMOR_TEMPLATES = Set.of(
            Material.NETHERITE_UPGRADE_SMITHING_TEMPLATE, Material.SENTRY_ARMOR_TRIM_SMITHING_TEMPLATE,
            Material.VEX_ARMOR_TRIM_SMITHING_TEMPLATE, Material.WILD_ARMOR_TRIM_SMITHING_TEMPLATE,
            Material.COAST_ARMOR_TRIM_SMITHING_TEMPLATE, Material.DUNE_ARMOR_TRIM_SMITHING_TEMPLATE,
            Material.WAYFINDER_ARMOR_TRIM_SMITHING_TEMPLATE, Material.RAISER_ARMOR_TRIM_SMITHING_TEMPLATE,
            Material.SHAPER_ARMOR_TRIM_SMITHING_TEMPLATE, Material.HOST_ARMOR_TRIM_SMITHING_TEMPLATE,
            Material.WARD_ARMOR_TRIM_SMITHING_TEMPLATE, Material.SILENCE_ARMOR_TRIM_SMITHING_TEMPLATE,
            Material.TIDE_ARMOR_TRIM_SMITHING_TEMPLATE, Material.SNOUT_ARMOR_TRIM_SMITHING_TEMPLATE,
            Material.RIB_ARMOR_TRIM_SMITHING_TEMPLATE, Material.EYE_ARMOR_TRIM_SMITHING_TEMPLATE,
            Material.SPIRE_ARMOR_TRIM_SMITHING_TEMPLATE, Material.BOLT_ARMOR_TRIM_SMITHING_TEMPLATE,
            Material.FLOW_ARMOR_TRIM_SMITHING_TEMPLATE
    );

    public RecipeManager(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    public void registerRecipes() {
        for (Material material : ARMOR_TEMPLATES) {
            NamespacedKey key = new NamespacedKey(plugin, material.name().toLowerCase() + "_honeycomb");
            if (Bukkit.getRecipe(key) != null) continue;

            ShapedRecipe recipe = new ShapedRecipe(key, lockItem(material));

            recipe.shape("HHH", "HXH", "HHH");
            recipe.setIngredient('H', Material.HONEYCOMB);
            recipe.setIngredient('X', material);

            Bukkit.addRecipe(recipe);
        }
    }

    public void unregisterRecipes() {
        for (Material material : ARMOR_TEMPLATES) {
            NamespacedKey key = new NamespacedKey(plugin, material.name().toLowerCase() + "_honeycomb");
            if (Bukkit.getRecipe(key) == null) continue;

            Bukkit.removeRecipe(key);
        }
    }

    private ItemStack lockItem(Material material) {
        ItemStack result = new ItemStack(material);
        ItemMeta meta = result.getItemMeta();
        if (meta == null) return result;

        meta.getPersistentDataContainer().set(new NamespacedKey(plugin, "locked"), PersistentDataType.BYTE, (byte) 1);

        List<Component> lore = new ArrayList<>();
        lore.add(Config.locked.decoration(TextDecoration.ITALIC, false));

        if (meta.hasLore()) {
            lore.addAll(0, meta.lore());
        }

        meta.lore(lore);
        result.setItemMeta(meta);
        return result;
    }

    public Set<Material> getARMOR_TEMPLATES() {
        return ARMOR_TEMPLATES;
    }
}