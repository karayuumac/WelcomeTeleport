package com.github.karayuumac.listeners;

import com.github.karayuumac.Sql;
import com.github.karayuumac.WelcomeTeleport;
import com.github.karayuumac.config.Config;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ServerConnectedEvent;
import org.bukkit.Bukkit;
import org.bukkit.Location;
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

    /**
     * 上のメソッド後に指定座標に飛ばすメソッド
     */
    @EventHandler
    public void playerTeleport(PlayerJoinEvent event) {
        Bukkit.getServer().getLogger().info("playerjoinevent");
        Player player = event.getPlayer();
        if (player.getServer().getName().equals(config.getTeleportServer())) {
            if (config.isAfterTeleported() && !sql.isPlayerDataExisted(player)) {
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        player.teleport(new Location(player.getWorld(), config.getTeleportX(),
                                config.getTeleportY(), config.getTeleportZ()));
                        Bukkit.getServer().getLogger().info("x.y.z:" + config.getTeleportX() + "," + config.getTeleportY() + "," + config.getTeleportZ());
                    }
                }.runTaskLater(WelcomeTeleport.plugin, 20);

            }
        }
    }
}
