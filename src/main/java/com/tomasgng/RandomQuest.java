package com.tomasgng;

import com.tomasgng.commands.RandomQuestCommand;
import com.tomasgng.listeners.BlockBreakListener;
import com.tomasgng.utils.QuestManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class RandomQuest extends JavaPlugin {

    private static RandomQuest instance;
    private QuestManager questManager;

    @Override
    public void onEnable() {
        instance = this;
        questManager = new QuestManager();

        register();
    }

    private void register() {
        PluginManager manager = Bukkit.getPluginManager();

        manager.registerEvents(new BlockBreakListener(), this);

        getCommand("getquest").setExecutor(new RandomQuestCommand());
    }

    public static RandomQuest getInstance() {
        return instance;
    }

    public QuestManager getQuestManager() {
        return questManager;
    }
}
