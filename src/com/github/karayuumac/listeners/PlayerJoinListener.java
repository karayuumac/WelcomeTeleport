package com.github.karayuumac.listeners;

import com.github.karayuumac.Sql;
import com.github.karayuumac.WelcomeTeleport;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {
    private static Sql sql = WelcomeTeleport.sql;

    /**
     * お初さんのListner
     * @param event
     */
    @EventHandler
    public void onPlayerFirstJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        if (!sql.isPlayerDataExisted(player)) {
            player.sendMessage("お初さん！こんにちわ");
        }
    }
}
