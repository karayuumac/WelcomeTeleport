package com.github.karayuumac.events;

import org.bukkit.entity.Player;

public class PlayerFirstJoinEvent extends CustomEvent {
    private Player player;

    public PlayerFirstJoinEvent(Player player) {
        this.player = player;
    }

    public Player getPlayer() {
        return this.player;
    }
}
