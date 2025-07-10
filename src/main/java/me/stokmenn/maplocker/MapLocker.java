package me.stokmenn.maplocker;

import me.stokmenn.maplocker.commands.ReloadCommand;
import me.stokmenn.maplocker.config.Config;
import me.stokmenn.maplocker.listeners.CraftMapListener;
import me.stokmenn.maplocker.listeners.CrafterLockerListener;
import me.stokmenn.maplocker.listeners.LockerListener;
import org.bukkit.Bukkit;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.java.JavaPlugin;

public final class MapLocker extends JavaPlugin {
    private RecipeManager recipeManager;
    private CraftMapListener craftMapListener;

    @Override
    public void onEnable() {
        Config.init(this);

        recipeManager = new RecipeManager(this);
        if (Config.doLockTemplates) {
            recipeManager.registerRecipes();
        } else {
            recipeManager.unregisterRecipes();
        }

        craftMapListener = new CraftMapListener(this);
        if (Config.doLockMaps) {
            Bukkit.getPluginManager().registerEvents(craftMapListener, this);
        } else {
            HandlerList.unregisterAll(craftMapListener);
        }

        Bukkit.getPluginManager().registerEvents(new LockerListener(this), this);
        Bukkit.getPluginManager().registerEvents(new CrafterLockerListener(this), this);
        getCommand("maplocker").setExecutor(new ReloadCommand(this));
    }

    @Override
    public void onDisable() {
        recipeManager.unregisterRecipes();
    }

    public RecipeManager getRecipeManager() {
        return recipeManager;
    }

    public CraftMapListener getCraftMapListener() {
        return craftMapListener;
    }
}