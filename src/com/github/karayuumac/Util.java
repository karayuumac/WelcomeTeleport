package com.github.karayuumac;

import org.bukkit.entity.Player;

/**
 * Util
 * @author karayuu
 * 2017/09/23
 */
public class Util {
    /**
     * プレイヤーネームを格納。toLowerCaseですべて小文字化
     * @param player プレイヤー
     * @return 小文字化したプレイヤー名
     */
    public static String getName(Player player) {
        return player.getName().toLowerCase();
    }

    /**
     * String->int
     */
    public static int toInt(String s) {
        return Integer.parseInt(s);
    }
}
