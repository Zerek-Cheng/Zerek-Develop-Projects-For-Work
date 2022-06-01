/*
 * Decompiled with CFR 0_133.
 * 
 * Could not load the following classes:
 *  org.bukkit.entity.Player
 */
package com.pedrojm96.referralsystem.Storage;

import com.pedrojm96.referralsystem.ConfigManager;
import com.pedrojm96.referralsystem.ReferralSystem;
import com.pedrojm96.referralsystem.Storage.dataCore;
import com.pedrojm96.referralsystem.itemPlayer;
import com.pedrojm96.referralsystem.rLog;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.bukkit.entity.Player;

public class DMYSQL
implements dataCore {
    private Connection connection;
    private String host;
    private String database;
    private String username;
    private String password;
    private int port;
    private String table;

    public DMYSQL(String string) {
        rLog.info("Data set to MySQL");
        this.host = ReferralSystem.config.getString("dataStorage.host");
        if (this.host == null || this.host.equals("")) {
            rLog.info("DMYSQL() - host nulo");
        }
        this.port = ReferralSystem.config.getInt("dataStorage.port");
        this.database = ReferralSystem.config.getString("dataStorage.database");
        if (this.database == null || this.database.equals("")) {
            rLog.info("DMYSQL() - database nulo");
        }
        this.username = ReferralSystem.config.getString("dataStorage.username");
        if (this.username == null || this.username.equals("")) {
            rLog.info("DMYSQL() - username nulo");
        }
        this.password = ReferralSystem.config.getString("dataStorage.password");
        if (this.password == null || this.password.equals("")) {
            rLog.info("DMYSQL() - password nulo");
        }
        this.table = string;
        try {
            this.MYSQLConnection();
        }
        catch (SQLException sQLException) {
            sQLException.printStackTrace();
        }
    }

    public void MYSQLConnection() {
        if (this.connection != null && !this.connection.isClosed()) {
            return;
        }
        if (this.connection != null && !this.connection.isClosed()) {
            return;
        }
        try {
            Class.forName("com.mysql.jdbc.Driver");
            this.connection = DriverManager.getConnection("jdbc:mysql://" + this.host + ":" + this.port + "/" + this.database, this.username, this.password);
        }
        catch (SQLException sQLException) {
            rLog.error("MySQL exception on initialize.", sQLException);
        }
        catch (ClassNotFoundException classNotFoundException) {
            rLog.error("You need the MySQL JBDC library.", classNotFoundException);
        }
    }

    @Override
    public int getPoints(String string) {
        int n;
        block7 : {
            PreparedStatement preparedStatement;
            ResultSet resultSet;
            n = 0;
            if (string == null || string.equals("")) {
                rLog.info("getPoints() - ID malo");
                return n;
            }
            preparedStatement = null;
            resultSet = null;
            try {
                try {
                    preparedStatement = this.connection.prepareStatement("SELECT points FROM " + this.table + " WHERE uuid=?;");
                    preparedStatement.setString(1, string);
                    resultSet = preparedStatement.executeQuery();
                    if (resultSet != null && resultSet.next()) {
                        n = resultSet.getInt("points");
                    }
                }
                catch (SQLException sQLException) {
                    rLog.error("Could not create getter statement.", sQLException);
                    this.cleanup(resultSet, preparedStatement);
                    break block7;
                }
            }
            catch (Throwable throwable) {
                this.cleanup(resultSet, preparedStatement);
                throw throwable;
            }
            this.cleanup(resultSet, preparedStatement);
        }
        return n;
    }

    protected void cleanup(ResultSet resultSet, PreparedStatement preparedStatement) {
        if (resultSet != null) {
            try {
                resultSet.close();
            }
            catch (SQLException sQLException) {
                rLog.error("SQLException on cleanup.", sQLException);
            }
        }
        if (preparedStatement != null) {
            try {
                preparedStatement.close();
            }
            catch (SQLException sQLException) {
                rLog.error("SQLException on cleanup.", sQLException);
            }
        }
    }

    @Override
    public void setPoints(String string, int n) {
        block6 : {
            PreparedStatement preparedStatement;
            ResultSet resultSet;
            if (string == null || string.equals("")) {
                rLog.info("setPoints() - ID malo");
            }
            preparedStatement = null;
            resultSet = null;
            try {
                try {
                    preparedStatement = this.connection.prepareStatement("UPDATE " + this.table + " SET points=? WHERE uuid=?");
                    preparedStatement.setInt(1, n);
                    preparedStatement.setString(2, string);
                    preparedStatement.executeUpdate();
                }
                catch (SQLException sQLException) {
                    rLog.error("Could not create getter statement.", sQLException);
                    this.cleanup(resultSet, preparedStatement);
                    break block6;
                }
            }
            catch (Throwable throwable) {
                this.cleanup(resultSet, preparedStatement);
                throw throwable;
            }
            this.cleanup(resultSet, preparedStatement);
        }
    }

    @Override
    public boolean checkData(String string) {
        boolean bl;
        block5 : {
            ResultSet resultSet;
            PreparedStatement preparedStatement;
            preparedStatement = null;
            resultSet = null;
            try {
                try {
                    preparedStatement = this.connection.prepareStatement("SELECT uuid FROM " + this.table + " WHERE uuid=?;");
                    preparedStatement.setString(1, string);
                    resultSet = preparedStatement.executeQuery();
                    bl = resultSet != null && resultSet.next();
                }
                catch (SQLException sQLException) {
                    bl = false;
                    this.cleanup(resultSet, preparedStatement);
                    break block5;
                }
            }
            catch (Throwable throwable) {
                this.cleanup(resultSet, preparedStatement);
                throw throwable;
            }
            this.cleanup(resultSet, preparedStatement);
        }
        return bl;
    }

    @Override
    public boolean destroy() {
        return false;
    }

    @Override
    public boolean build() {
        rLog.info("Creating table " + this.table);
        try {
            this.connection.createStatement().executeUpdate("CREATE TABLE IF NOT EXISTS " + this.table + " (id int(64) NOT NULL AUTO_INCREMENT PRIMARY KEY, " + "name varchar(40)," + "uuid varchar(40)," + "ip varchar(40)," + "code int(11)," + "referrals int(11)," + "points int(11)," + "referring varchar(40)," + "bonus int(1)," + "playtime bigint(20)," + "time bigint(20))");
        }
        catch (SQLException sQLException) {
            sQLException.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean checkStorage() {
        boolean bl;
        block5 : {
            PreparedStatement preparedStatement;
            ResultSet resultSet;
            preparedStatement = null;
            resultSet = null;
            try {
                try {
                    preparedStatement = this.connection.prepareStatement("SELECT * FROM " + this.table);
                    resultSet = preparedStatement.executeQuery();
                    bl = true;
                    rLog.info("&7Loaded database");
                }
                catch (SQLException sQLException) {
                    bl = false;
                    rLog.info("&cThe table " + this.table + " does not exist");
                    this.cleanup(resultSet, preparedStatement);
                    break block5;
                }
            }
            catch (Throwable throwable) {
                this.cleanup(resultSet, preparedStatement);
                throw throwable;
            }
            this.cleanup(resultSet, preparedStatement);
        }
        return bl;
    }

    @Override
    public void insert(Player player, int n) {
        block5 : {
            PreparedStatement preparedStatement;
            ResultSet resultSet;
            preparedStatement = null;
            resultSet = null;
            try {
                try {
                    preparedStatement = this.connection.prepareStatement("INSERT INTO " + this.table + " (name,uuid,ip,code,referrals,points,referring,bonus,playtime,time) VALUES (?,?,?,?,?,?,?,?,?,?)");
                    preparedStatement.setString(1, player.getName());
                    preparedStatement.setString(2, player.getUniqueId().toString());
                    preparedStatement.setString(3, player.getAddress().getAddress().getHostAddress());
                    preparedStatement.setInt(4, n);
                    preparedStatement.setInt(5, 0);
                    preparedStatement.setInt(6, 0);
                    preparedStatement.setString(7, null);
                    preparedStatement.setBoolean(8, false);
                    preparedStatement.setInt(9, 0);
                    preparedStatement.setLong(10, System.currentTimeMillis());
                    preparedStatement.executeUpdate();
                }
                catch (SQLException sQLException) {
                    rLog.error("Could not create getter statement.", sQLException);
                    this.cleanup(resultSet, preparedStatement);
                    break block5;
                }
            }
            catch (Throwable throwable) {
                this.cleanup(resultSet, preparedStatement);
                throw throwable;
            }
            this.cleanup(resultSet, preparedStatement);
        }
    }

    @Override
    public boolean update() {
        return false;
    }

    @Override
    public int getReferrals(String string) {
        int n;
        block7 : {
            PreparedStatement preparedStatement;
            ResultSet resultSet;
            n = 0;
            if (string == null || string.equals("")) {
                rLog.info("getReferrals() - ID malo");
                return n;
            }
            preparedStatement = null;
            resultSet = null;
            try {
                try {
                    preparedStatement = this.connection.prepareStatement("SELECT referrals FROM " + this.table + " WHERE uuid=?;");
                    preparedStatement.setString(1, string);
                    resultSet = preparedStatement.executeQuery();
                    if (resultSet != null && resultSet.next()) {
                        n = resultSet.getInt("referrals");
                    }
                }
                catch (SQLException sQLException) {
                    rLog.error("Could not create getter statement.", sQLException);
                    this.cleanup(resultSet, preparedStatement);
                    break block7;
                }
            }
            catch (Throwable throwable) {
                this.cleanup(resultSet, preparedStatement);
                throw throwable;
            }
            this.cleanup(resultSet, preparedStatement);
        }
        return n;
    }

    @Override
    public void setReferrals(String string, int n) {
        block6 : {
            PreparedStatement preparedStatement;
            ResultSet resultSet;
            if (string == null || string.equals("")) {
                rLog.info("setReferrals() - ID malo");
            }
            preparedStatement = null;
            resultSet = null;
            try {
                try {
                    preparedStatement = this.connection.prepareStatement("UPDATE " + this.table + " SET referrals=? WHERE uuid=?");
                    preparedStatement.setInt(1, n);
                    preparedStatement.setString(2, string);
                    preparedStatement.executeUpdate();
                }
                catch (SQLException sQLException) {
                    rLog.error("Could not create getter statement.", sQLException);
                    this.cleanup(resultSet, preparedStatement);
                    break block6;
                }
            }
            catch (Throwable throwable) {
                this.cleanup(resultSet, preparedStatement);
                throw throwable;
            }
            this.cleanup(resultSet, preparedStatement);
        }
    }

    @Override
    public void setReferring(String string, String string2) {
        block6 : {
            PreparedStatement preparedStatement;
            ResultSet resultSet;
            if (string == null || string.equals("")) {
                rLog.info("setReferring() - ID malo");
            }
            preparedStatement = null;
            resultSet = null;
            try {
                try {
                    preparedStatement = this.connection.prepareStatement("UPDATE " + this.table + " SET referring=? WHERE uuid=?");
                    preparedStatement.setString(1, string2);
                    preparedStatement.setString(2, string);
                    preparedStatement.executeUpdate();
                }
                catch (SQLException sQLException) {
                    rLog.error("Could not create getter statement.", sQLException);
                    this.cleanup(resultSet, preparedStatement);
                    break block6;
                }
            }
            catch (Throwable throwable) {
                this.cleanup(resultSet, preparedStatement);
                throw throwable;
            }
            this.cleanup(resultSet, preparedStatement);
        }
    }

    @Override
    public boolean checkCode(int n) {
        boolean bl;
        block5 : {
            ResultSet resultSet;
            PreparedStatement preparedStatement;
            preparedStatement = null;
            resultSet = null;
            try {
                try {
                    preparedStatement = this.connection.prepareStatement("SELECT code FROM " + this.table + " WHERE code=?;");
                    preparedStatement.setInt(1, n);
                    resultSet = preparedStatement.executeQuery();
                    bl = resultSet != null && resultSet.next();
                }
                catch (SQLException sQLException) {
                    bl = false;
                    this.cleanup(resultSet, preparedStatement);
                    break block5;
                }
            }
            catch (Throwable throwable) {
                this.cleanup(resultSet, preparedStatement);
                throw throwable;
            }
            this.cleanup(resultSet, preparedStatement);
        }
        return bl;
    }

    @Override
    public String getPlayer(int n) {
        String string;
        block6 : {
            PreparedStatement preparedStatement;
            ResultSet resultSet;
            string = "";
            preparedStatement = null;
            resultSet = null;
            try {
                try {
                    preparedStatement = this.connection.prepareStatement("SELECT uuid FROM " + this.table + " WHERE code=?;");
                    preparedStatement.setInt(1, n);
                    resultSet = preparedStatement.executeQuery();
                    if (resultSet != null && resultSet.next()) {
                        string = resultSet.getString("uuid");
                    }
                }
                catch (SQLException sQLException) {
                    rLog.error("Could not create getter statement.", sQLException);
                    this.cleanup(resultSet, preparedStatement);
                    break block6;
                }
            }
            catch (Throwable throwable) {
                this.cleanup(resultSet, preparedStatement);
                throw throwable;
            }
            this.cleanup(resultSet, preparedStatement);
        }
        return string;
    }

    @Override
    public String getReferring(String string) {
        String string2;
        block6 : {
            PreparedStatement preparedStatement;
            ResultSet resultSet;
            string2 = null;
            preparedStatement = null;
            resultSet = null;
            try {
                try {
                    preparedStatement = this.connection.prepareStatement("SELECT referring FROM " + this.table + " WHERE uuid=?;");
                    preparedStatement.setString(1, string);
                    resultSet = preparedStatement.executeQuery();
                    if (resultSet != null && resultSet.next()) {
                        string2 = resultSet.getString("referring");
                    }
                }
                catch (SQLException sQLException) {
                    rLog.error("Could not create getter statement.", sQLException);
                    this.cleanup(resultSet, preparedStatement);
                    break block6;
                }
            }
            catch (Throwable throwable) {
                this.cleanup(resultSet, preparedStatement);
                throw throwable;
            }
            this.cleanup(resultSet, preparedStatement);
        }
        return string2;
    }

    @Override
    public int getNumbPlayerIp(String string) {
        int n;
        block7 : {
            PreparedStatement preparedStatement;
            ResultSet resultSet;
            n = 0;
            preparedStatement = null;
            resultSet = null;
            try {
                try {
                    preparedStatement = this.connection.prepareStatement("SELECT ip FROM " + this.table + " WHERE ip=?;");
                    preparedStatement.setString(1, string);
                    resultSet = preparedStatement.executeQuery();
                    if (resultSet != null) {
                        while (resultSet.next()) {
                            ++n;
                        }
                    }
                }
                catch (SQLException sQLException) {
                    rLog.error("Could not create getter statement.", sQLException);
                    this.cleanup(resultSet, preparedStatement);
                    break block7;
                }
            }
            catch (Throwable throwable) {
                this.cleanup(resultSet, preparedStatement);
                throw throwable;
            }
            this.cleanup(resultSet, preparedStatement);
        }
        return n;
    }

    @Override
    public int getCode(String string) {
        int n;
        block6 : {
            PreparedStatement preparedStatement;
            ResultSet resultSet;
            n = 0;
            preparedStatement = null;
            resultSet = null;
            try {
                try {
                    preparedStatement = this.connection.prepareStatement("SELECT code FROM " + this.table + " WHERE uuid=?;");
                    preparedStatement.setString(1, string);
                    resultSet = preparedStatement.executeQuery();
                    if (resultSet != null && resultSet.next()) {
                        n = resultSet.getInt("code");
                    }
                }
                catch (SQLException sQLException) {
                    rLog.error("Could not create getter statement.", sQLException);
                    this.cleanup(resultSet, preparedStatement);
                    break block6;
                }
            }
            catch (Throwable throwable) {
                this.cleanup(resultSet, preparedStatement);
                throw throwable;
            }
            this.cleanup(resultSet, preparedStatement);
        }
        return n;
    }

    @Override
    public List<String> getTop() {
        ArrayList<String> arrayList;
        block7 : {
            PreparedStatement preparedStatement;
            ResultSet resultSet;
            arrayList = new ArrayList<String>();
            preparedStatement = null;
            resultSet = null;
            try {
                try {
                    preparedStatement = this.connection.prepareStatement("Select * from " + this.table + " order by referrals DESC limit 10");
                    resultSet = preparedStatement.executeQuery();
                    if (resultSet != null) {
                        int n = 0;
                        while (resultSet.next()) {
                            String string = resultSet.getString("name");
                            int n2 = resultSet.getInt("referrals");
                            arrayList.add("&a" + String.valueOf(++n) + " &7- &b" + string + " &7: &7" + n2);
                        }
                    }
                }
                catch (SQLException sQLException) {
                    rLog.error("Could not create getter statement.", sQLException);
                    this.cleanup(resultSet, preparedStatement);
                    break block7;
                }
            }
            catch (Throwable throwable) {
                this.cleanup(resultSet, preparedStatement);
                throw throwable;
            }
            this.cleanup(resultSet, preparedStatement);
        }
        return arrayList;
    }

    @Override
    public long getPlayTime(String string) {
        long l;
        block6 : {
            ResultSet resultSet;
            PreparedStatement preparedStatement;
            l = 0L;
            preparedStatement = null;
            resultSet = null;
            try {
                try {
                    preparedStatement = this.connection.prepareStatement("SELECT playtime FROM " + this.table + " WHERE uuid=?;");
                    preparedStatement.setString(1, string);
                    resultSet = preparedStatement.executeQuery();
                    if (resultSet != null && resultSet.next()) {
                        l = resultSet.getInt("playtime");
                    }
                }
                catch (SQLException sQLException) {
                    rLog.error("Could not create getter statement.", sQLException);
                    this.cleanup(resultSet, preparedStatement);
                    break block6;
                }
            }
            catch (Throwable throwable) {
                this.cleanup(resultSet, preparedStatement);
                throw throwable;
            }
            this.cleanup(resultSet, preparedStatement);
        }
        return l;
    }

    @Override
    public void setPlayTime(String string, long l) {
        block6 : {
            ResultSet resultSet;
            PreparedStatement preparedStatement;
            if (string == null || string.equals("")) {
                rLog.info("setPlayTime() - ID malo");
            }
            preparedStatement = null;
            resultSet = null;
            try {
                try {
                    preparedStatement = this.connection.prepareStatement("UPDATE " + this.table + " SET playtime=? WHERE uuid=?");
                    preparedStatement.setLong(1, l);
                    preparedStatement.setString(2, string);
                    preparedStatement.executeUpdate();
                }
                catch (SQLException sQLException) {
                    rLog.error("Could not create getter statement.", sQLException);
                    this.cleanup(resultSet, preparedStatement);
                    break block6;
                }
            }
            catch (Throwable throwable) {
                this.cleanup(resultSet, preparedStatement);
                throw throwable;
            }
            this.cleanup(resultSet, preparedStatement);
        }
    }

    @Override
    public List<itemPlayer> getList(String string, int n) {
        ArrayList<itemPlayer> arrayList;
        block7 : {
            ResultSet resultSet;
            PreparedStatement preparedStatement;
            arrayList = new ArrayList<itemPlayer>();
            int n2 = 45;
            int n3 = n * 45;
            preparedStatement = null;
            resultSet = null;
            try {
                try {
                    preparedStatement = this.connection.prepareStatement("Select * from " + this.table + " WHERE referring=? order by referrals DESC limit ?,?");
                    preparedStatement.setString(1, string);
                    preparedStatement.setInt(2, n3);
                    preparedStatement.setInt(3, n2);
                    resultSet = preparedStatement.executeQuery();
                    if (resultSet != null) {
                        int n4 = 0;
                        while (resultSet.next()) {
                            itemPlayer itemPlayer2 = new itemPlayer(resultSet.getString("name"), resultSet.getInt("points"), resultSet.getInt("referrals"), false, ++n4);
                            arrayList.add(itemPlayer2);
                        }
                    }
                }
                catch (SQLException sQLException) {
                    rLog.error("Could not create getter statement.", sQLException);
                    this.cleanup(resultSet, preparedStatement);
                    break block7;
                }
            }
            catch (Throwable throwable) {
                this.cleanup(resultSet, preparedStatement);
                throw throwable;
            }
            this.cleanup(resultSet, preparedStatement);
        }
        return arrayList;
    }
}

