package com.WeiBoss.bossshoptr.Util;

import com.WeiBoss.bossshoptr.Database.MySQL;
import com.WeiBoss.bossshoptr.File.Config;
import com.WeiBoss.bossshoptr.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import java.sql.*;

public class SQLUtil extends MySQL {
    private static Main plugin = Main.instance;
    private final String hostname;
    private final String port;
    private final String database;
    private final String username;
    private final String password;
    private Connection connection;

    public SQLUtil(String hostname, String port, String database, String username, String password) {
        this.hostname = hostname;
        this.port = port;
        this.database = database;
        this.username = username;
        this.password = password;
        this.connection = null;
    }

    public void openConnection() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            this.connection = DriverManager.getConnection("jdbc:mysql://" + this.hostname + ":" + this.port + "/" + this.database, this.username, this.password);
        } catch (SQLException e) {
            Bukkit.getConsoleSender().sendMessage(Config.Prefix + ChatColor.RED + "连接数据库失败!");
            plugin.sqlBool = false;
            this.connection = null;
        } catch (ClassNotFoundException e) {
            Bukkit.getConsoleSender().sendMessage(Config.Prefix + ChatColor.RED + "未找到JDBC驱动程序,连接数据库失败!");
            plugin.sqlBool = false;
            this.connection = null;
        }
    }

    public boolean checkConnection() {
        return this.connection != null;
    }

    public Connection getConnection() {
        return this.connection;
    }

    public void closeConnection() {
        if (this.connection != null) {
            try {
                this.connection.close();
                this.connection = null;
            } catch (SQLException e) {
                Bukkit.getConsoleSender().sendMessage(Config.Prefix + "关闭数据库连接失败");
                e.printStackTrace();
            }
        }
    }

    public ResultSet querySQL(String query) {
        Connection conn;
        if (checkConnection())
            conn = getConnection();
        else
            return null;
        Statement stat = null;
        try {
            stat = conn.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        ResultSet result = null;
        try {
            if (stat != null)
                result = stat.executeQuery(query);
            else
                return null;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return result;
    }

    public boolean updateSQL(String data) {
        Connection conn;
        if (checkConnection())
            conn = getConnection();
        else
            return false;
        Statement stat = null;
        try {
            stat = conn.createStatement();
            stat.executeUpdate(data);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
