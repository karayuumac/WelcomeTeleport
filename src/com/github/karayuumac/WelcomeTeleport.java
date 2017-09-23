package com.github.karayuumac;

import com.github.karayuumac.config.Config;
import com.github.karayuumac.listeners.PlayerJoinListener;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * プラグインのメインクラス。
 * @author karayuu
 * 2017/09/17
 */
public class WelcomeTeleport extends JavaPlugin {
    public static WelcomeTeleport plugin;
    public static Config config;
    public static Sql sql;

    public static final String PLAYERDATA_TABLENAME = "playerdata";
    private String pluginChannel = "BungeeCord";

    public void onEnable() {
        plugin = this;

        //チャンネル追加
        Bukkit.getMessenger().registerOutgoingPluginChannel(this, this.pluginChannel);

        //コンフィグ処理
        config = new Config(this);
        config.loadConfig();

        //SQL処理
        sql = new Sql(this, config.getURL(), config.getDB(), config.getID(), config.getPW());
        if (!sql.connect()) {
            getLogger().info("データベース接続処理時にエラーが発生しました。");
        }

        //リスナーの登録
        getServer().getPluginManager().registerEvents(new PlayerJoinListener(), this);

        Bukkit.getLogger().info("[WelcomeTeleport]初参加者テレポートプラグイン起動");
    }

    public void onDisable() {
        //SQL切断処理
        if (!sql.isNormallyDisconnectd()) {
            getLogger().info("データベース切断に失敗しました。");
        }
        Bukkit.getLogger().info("[WelcomeTeleport]初参加者テレポートプラグイン終了");
    }
}
