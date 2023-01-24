package com.tomasgng.listeners;

import com.tomasgng.RandomQuest;
import com.tomasgng.utils.QuestManager;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class BlockBreakListener implements Listener {

    QuestManager questManager = RandomQuest.getInstance().getQuestManager();

    @EventHandler
    public void onBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        Material blockMaterial = event.getBlock().getType();

        if(!questManager.hasStartedQuest(player))
            return;

        Material questMaterial = questManager.getQuestMaterial(player);

        if(blockMaterial.equals(questMaterial) && !player.getInventory().getItemInMainHand().containsEnchantment(Enchantment.SILK_TOUCH)) {
            questManager.decreaseItemRemaining(player);
        }
    }
}
