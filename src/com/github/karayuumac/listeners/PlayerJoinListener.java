package com.github.karayuumac.listeners;

import com.github.karayuumac.Sql;
import com.github.karayuumac.WelcomeTeleport;
import com.github.karayuumac.config.Config;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class PlayerJoinListener implements Listener {
    private static Sql sql = WelcomeTeleport.sql;
    private static Config config = WelcomeTeleport.config;

    /**
     * お初さんのListner
     * @param event
     */
    @EventHandler
    public void onPlayerFirstJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        if (!sql.isPlayerDataExisted(player)) {
            new BukkitRunnable() {
                @Override
                public void run() {
                    ByteArrayDataOutput byteArrayDataOutput = ByteStreams.newDataOutput();
                    byteArrayDataOutput.writeUTF("Connect");
                    byteArrayDataOutput.writeUTF(config.getTeleportServer());
                    player.sendPluginMessage(WelcomeTeleport.plugin, "BungeeCord", byteArrayDataOutput.toByteArray());
                }
            }.runTaskLater(WelcomeTeleport.plugin, 20);
        }
    }
}
