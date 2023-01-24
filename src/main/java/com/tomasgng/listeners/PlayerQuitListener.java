package com.tomasgng.listeners;

import com.tomasgng.RandomQuest;
import com.tomasgng.utils.QuestManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerQuitListener implements Listener {

    QuestManager questManager = RandomQuest.getInstance().getQuestManager();

    @EventHandler
    public void onLeave(PlayerQuitEvent event) {
        if(questManager.hasStartedQuest(event.getPlayer()))
            questManager.stopQuest(event.getPlayer());
    }
}
