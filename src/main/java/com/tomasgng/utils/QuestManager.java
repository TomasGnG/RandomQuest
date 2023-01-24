package com.tomasgng.utils;

import com.tomasgng.RandomQuest;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.HashMap;
import java.util.Random;

public class QuestManager {

    private String prefix = "§l§aQuest §8| §7";

    // Die erste Zahl("60") entspricht der Sekundenanzahl. Derzeit ist die Zeit auf 60 Sekunden eingestellt.
    final private int time = 30;

    // Der maximale Wert wie viel ein Spieler jemals farmen sollte.
    final private int maxMaterialCount = 16;

    // Ressourcen Liste
    final private Material[] materials = {Material.COAL_ORE, Material.IRON_ORE};

    // Player data
    private HashMap<Player, PlayersQuest> activeQuest = new HashMap<>();

    public void startQuest(Player player) {
        if(hasStartedQuest(player)) {
            player.sendMessage(Component.text(prefix + "§cDerzeit läuft bereits eine Quest!"));
            player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 1, 1);
            return;
        }
        int materialCount = getRandomMaterialCount();
        String materialName = getRandomQuestMaterial().name();

        player.sendMessage(Component.text(prefix + "Du hast erfolgreich eine Quest gestartet!"));
        player.sendMessage(Component.text(prefix + "§7Du musst §e" + materialCount + " §7Stück §e" + materialName + " §7in §e" + time + " Sekunden §7farmen."));

        activeQuest.put(player, new PlayersQuest(time, materialCount, Material.getMaterial(materialName)));

        Bukkit.getScheduler().runTaskTimer(RandomQuest.getInstance(), bukkitTask -> {
            player.sendActionBar(Component.text("§7Fehlend: §e" + getItemRemaining(player) + " §7Stück von §e" + materialName + " §7| Zeit übrig: §e" + getTimeRemaining(player)));

            if(getTimeRemaining(player) <= 0) {
                player.sendMessage(Component.text(prefix + "§cDie Quest wurde abgebrochen! Zeit abgelaufen."));
                player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 1, 1);
                activeQuest.remove(player);
                bukkitTask.cancel();
                return;
            }
            if(getItemRemaining(player) <= 0) {
                rewardPlayer(player);
                bukkitTask.cancel();
                return;
            }

            decreaseTimeRemaining(player);
        }, 0, 20);
    }

    public void stopQuest(Player player) {
        activeQuest.get(player).setTimeRemaining(0);
    }

    private void rewardPlayer(Player player) {
        ItemStack diamond = new ItemStack(Material.DIAMOND, 2);

        player.sendMessage(Component.text(prefix + "§aDu hast die Quest erfolgreich abgeschlossen! Du hast eine Belohnung bekommen!"));
        player.playSound(player.getLocation(), Sound.ENTITY_FIREWORK_ROCKET_BLAST_FAR, 1, 1);
        player.getInventory().addItem(diamond);

        activeQuest.remove(player);
    }

    private Material getRandomQuestMaterial() {
        return materials[new Random().nextInt(materials.length)];
    }

    private int getRandomMaterialCount() {
        return new Random().nextInt(1, maxMaterialCount);
    }

    public boolean hasStartedQuest(Player player) {
        return activeQuest.containsKey(player);
    }

    public Material getQuestMaterial(Player player) {
        return activeQuest.get(player).getMaterial();
    }

    public void decreaseItemRemaining(Player player) {
        activeQuest.get(player).decreaseItemRemaining();
        player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, 1);
    }

    private void decreaseTimeRemaining(Player player) {
        activeQuest.get(player).decreaseTimeRemaining();
    }

    private int getTimeRemaining(Player player) {
        return activeQuest.get(player).getTimeRemaining();
    }

    private int getItemRemaining(Player player) {
        return activeQuest.get(player).getMaterialCount();
    }

}
