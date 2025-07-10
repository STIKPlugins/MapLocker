package me.stokmenn.maplocker.commands;

import me.stokmenn.maplocker.MapLocker;
import me.stokmenn.maplocker.config.Config;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class ReloadCommand implements CommandExecutor {
    private final MapLocker plugin;

    public ReloadCommand(MapLocker plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!sender.hasPermission("mapLocker.reload")) {
            sender.sendMessage(Config.noPermissionToReload);
            return false;
        } else if (args.length == 0) {
            sender.sendMessage(Config.usage);
            return false;
        }
        if (args[0].equals("reloadrecipes")) {
            plugin.getRecipeManager().unregisterRecipes();
            plugin.getRecipeManager().registerRecipes();

            sender.sendMessage(Config.recipesReloaded);
            return true;
        } else if (!args[0].equals("reload")) {
            sender.sendMessage(Config.usage);
            return false;
        }

        boolean oldDoLockTemplates = Config.doLockTemplates;
        boolean oldDoLockMaps = Config.doLockMaps;

        Bukkit.getAsyncScheduler().runNow(plugin, task -> {
            Config.reload();

            if (Config.doLockTemplates != oldDoLockTemplates) {
                if (Config.doLockTemplates) {
                    plugin.getRecipeManager().registerRecipes();
                } else {
                    plugin.getRecipeManager().unregisterRecipes();
                }
            }
            if (Config.doLockMaps != oldDoLockMaps) {
                if (Config.doLockMaps) {
                    Bukkit.getPluginManager().registerEvents(plugin.getCraftMapListener(), plugin);
                } else {
                    HandlerList.unregisterAll(plugin.getCraftMapListener());
                }
            }
        });

        sender.sendMessage(Config.configReloaded);
        return true;
    }
}