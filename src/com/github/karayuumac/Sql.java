package com.github.karayuumac;

import com.github.karayuumac.WelcomeTeleport;
import org.bukkit.entity.Player;

import java.sql.*;
import java.util.UUID;

/**
 * SQL関連クラス
 * @author karayuu
 * 2017/09/23
 */
public class Sql {
    private final String url;
    private final String db;
    private final String id;
    private final String pw;
    public static Connection connection = null;
    private Statement statement = null;
    private ResultSet resultSet = null;

    public static String exc;
    private WelcomeTeleport plugin = WelcomeTeleport.plugin;

    /**
     * コンストラクタ
     */
    Sql(WelcomeTeleport plugin, String url, String db, String id, String pw) {
        this.plugin = plugin;
        this.url = url;
        this.db = db;
        this.id =id;
        this.pw = pw;
    }

    /**
     * MySQLへの接続メソッド
     * @return 成否
     * @throws InstantiationException
     * @throws IllegalAccessException
     * @throws ClassNotFoundException
     */
    public boolean connect() {
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
        } catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
            e.printStackTrace();
            plugin.getLogger().warning("MySQLドライバーのインスタンス生成に失敗しました。");
            return false;
        }
        //SQL鯖への接続確認
        if (!isConnectedMySQL()) {
            plugin.getLogger().warning("SQL接続に失敗しました。");
            return false;
        }

        return true;
    }

    /**
     * SQL鯖への接続
     * @return 接続できたか否か
     * @throws SQLException
     */
    public boolean isConnectedMySQL() {
        try {
            if (statement != null && !statement.isClosed()) {
                statement.close();
                connection.close();
            }
            connection = (Connection) DriverManager.getConnection(url, id, pw);
            statement = connection.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 接続正常かどうか
     * @return 正常時:true 再施行後正常時:true 不良:false
     * @throws SQLException
     */
    public boolean hasConnection() {
        try {
            if (connection.isClosed()) {
                plugin.getLogger().warning("SQLConnectionクローズを検出。再接続試行");
                connection = (Connection) DriverManager.getConnection(url, id, pw);
            }
            if (statement.isClosed()) {
                plugin.getLogger().warning("SQLStatementクローズを検出。再接続試行");
                statement = connection.createStatement();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            plugin.getLogger().warning("SQLExceptoinを検出。再接続試行");
            if (isConnectedMySQL()) {
                plugin.getLogger().info("SQLコネクション正常。");
                return true;
            } else {
                plugin.getLogger().warning("SQlコネクション不良を検出。");
                return false;
            }
        }
        return true;
    }

    /**
     * 正常にコネクションが切断されたかどうか
     * @return 正常か否か
     * @throws SQLException
     */
    public boolean isNormallyDisconnectd() {
        if (connection != null) {
            try {
                statement.close();
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }
        }
        return true;
    }

    /**
     * コマンド出力メソッド
     * @param command コマンド内容
     * @return 成否
     */
    private boolean putCommand(String command) {
        hasConnection();
        try {
            statement.executeUpdate(command);
            return true;
        } catch (SQLException e) {
            System.out.println("sqlクエリの実行に失敗しました。以下にエラーを表示します。");
            exc = e.getMessage();
            e.printStackTrace();
            return false;
        }
    }

    /**
     * プレイヤーデータが存在しているか
     * @param player 対象プレイヤー
     * @return 存在: true お初:false
     */
    public boolean isPlayerDataExisted(final Player player) {
        String playerName = Util.getName(player);
        final UUID uuid = player.getUniqueId();
        final String struuid = uuid.toString().toLowerCase();
        String command;
        final String tableName = WelcomeTeleport.PLAYERDATA_TABLENAME;
        int count = -1;

        command = "select count(*) as count from " + db + "." + tableName
                + " where uuid = '" + struuid + "'";

        hasConnection();
        try {
            resultSet = statement.executeQuery(command);
            while (resultSet.next()) {
                count += resultSet.getInt("count");
            }
            resultSet.close();
        } catch (SQLException e) {
            System.out.println("sqlクエリの実行に失敗しました。以下にエラーを表示します。");
            exc = e.getMessage();
            e.printStackTrace();
            return true;
        }
        return count != 0;
    }

}
