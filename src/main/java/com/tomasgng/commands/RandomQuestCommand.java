package com.tomasgng.commands;

import com.tomasgng.RandomQuest;
import com.tomasgng.utils.QuestManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class RandomQuestCommand implements CommandExecutor {

    QuestManager questManager = RandomQuest.getInstance().getQuestManager();

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(!(sender instanceof Player)) {
            sender.sendMessage("§cDieser Befehl ist nicht für die Konsole.");
            return true;
        }
        Player player = (Player) sender;

        questManager.startQuest(player);
        return false;
    }
}
