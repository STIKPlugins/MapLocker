package me.stokmenn.maplocker.config;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public class Config {
    private static JavaPlugin plugin;

    public static boolean doLockMaps;
    public static boolean doLockTemplates;

    public static Component usage;
    public static Component noPermissionToReload;
    public static Component recipesReloaded;
    public static Component configReloaded;

    public static Component locked;

    public static Component template;
    public static Component map;

    public static void init(JavaPlugin plugin) {
        Config.plugin = plugin;
        plugin.saveDefaultConfig();
        reload();
    }

    public static void reload() {
        plugin.reloadConfig();
        MiniMessage miniMessage = MiniMessage.miniMessage();
        YamlConfiguration config = YamlConfiguration.loadConfiguration(
                new File(plugin.getDataFolder(), "config.yml"));

        doLockMaps = config.getBoolean("doLockMaps", true);
        doLockTemplates = config.getBoolean("doLockTemplates", true);

        usage = miniMessage.deserialize(config.getString(
                "messages.reload.usage", "<red>✘ <white>Usage: /maplocker reload"));

        noPermissionToReload = miniMessage.deserialize(config.getString(
                "messages.reload.noPermissionToReload", "<red>✘ <white>You don't have permission to reload Config!"));

        recipesReloaded = miniMessage.deserialize(config.getString(
                "messages.reload.recipesReloaded", "<green>✔ <white>Recipes reloaded!"));

        configReloaded = miniMessage.deserialize(config.getString(
                "messages.reload.configReloaded", "<green>✔ <white>Config reloaded!"));

        locked = miniMessage.deserialize(config.getString(
                "messages.items.locked", "<blue><bold>i <reset><white>Item is locked and can't be copied."));

        template = miniMessage.deserialize(config.getString(
                "messages.onCopyTry.template", "<red>✘ <white>This template is locked and can't be copied."));

        map = miniMessage.deserialize(config.getString(
                "messages.onCopyTry.map", "<red>✘ <white>This map is locked and can't be copied."));
    }
}