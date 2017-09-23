package com.github.karayuumac.config;

import com.github.karayuumac.WelcomeTeleport;
import org.bukkit.configuration.file.FileConfiguration;

/**
 * コンフィグ関連クラス
 * @author karayuu
 * 2017/09/23
 */
public class Config {
    private static FileConfiguration config;
    private WelcomeTeleport plugin;

    /**
     * コンストラクタ
     * @param plugin プラグインのインスタンス
     */
    public Config(WelcomeTeleport plugin) {
        this.plugin = plugin;
        createDefaultConfig();
    }

    /**
     * config.ymlがない際Defaultのファイルを生成する
     */
    public void createDefaultConfig() {
        plugin.saveDefaultConfig();
    }

    /**
     * コンフィグのロード
     */
    public void loadConfig() {
        config = getConfig();
    }

    /**
     * コンフィグのリロード
     */
    public void reloadConfig() {
        plugin.reloadConfig();
        config = getConfig();
    }

    /**
     * config.ymlからの読み込み
     */
    public FileConfiguration getConfig() {
        return plugin.getConfig();
    }

    public String getURL() {
        String url = "jdbc:mysql://";
        url += config.getString("host");
        if (!config.getString("port").isEmpty()) {
            url += ":" + config.getString("port");
        }
        return url;
    }

    public String getDB() {
        return config.getString("db");
    }

    public String getID() {
        return config.getString("id");
    }

    public String getPW() {
        return config.getString("pw");
    }

}
