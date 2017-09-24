package com.github.karayuumac.listeners;

import com.github.karayuumac.Sql;
import com.github.karayuumac.WelcomeTeleport;
import com.github.karayuumac.config.Config;
import com.github.karayuumac.events.PlayerFirstJoinEvent;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
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
            Bukkit.getServer().getPluginManager().callEvent(new PlayerFirstJoinEvent(player));
        }
    }

    /**
     * 上のメソッド後に指定座標に飛ばすメソッド
     */
    @EventHandler(priority = EventPriority.HIGHEST)
    public void playerTeleport(PlayerFirstJoinEvent event) {
        Player player = event.getPlayer();
        if (config.isAfterTeleported()) {
            player.teleport(new Location(player.getWorld(), config.getTeleportX(),
                    config.getTeleportY(), config.getTeleportZ()));
        }
    }
}
