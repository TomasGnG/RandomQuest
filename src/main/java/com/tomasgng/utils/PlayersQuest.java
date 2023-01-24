package com.tomasgng.utils;

import org.bukkit.Material;

public class PlayersQuest {

    private int _timeRemaining;
    private int _materialCount;
    private Material _material;

    public PlayersQuest(int timeRemaining, int materialCount, Material material) {
        _material = material;
        _materialCount = materialCount;
        _timeRemaining = timeRemaining;
    }

    public int getMaterialCount() {
        return _materialCount;
    }

    public Material getMaterial() {
        return _material;
    }

    public int getTimeRemaining() {
        return _timeRemaining;
    }

    public void decreaseItemRemaining() {
        _materialCount--;
    }

    public void decreaseTimeRemaining() {
        _timeRemaining--;
    }

    public void setTimeRemaining(int newTime) {
        _timeRemaining = newTime;
    }
}
