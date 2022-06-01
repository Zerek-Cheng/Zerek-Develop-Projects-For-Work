// 
// Decompiled by Procyon v0.5.30
// 

package wew.oscar_wen;

import java.util.ArrayList;
import java.util.List;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.DriverManager;
import java.sql.Connection;

public class Data
{
    public static String SqlIp;
    public static String SqlPort;
    public static String Sql;
    public static String SqlTable;
    public static String SqlUser;
    public static String SqlPasd;
    public static Connection connect;
    
    static {
        Data.SqlIp = null;
        Data.SqlPort = null;
        Data.Sql = null;
        Data.SqlTable = null;
        Data.SqlUser = null;
        Data.SqlPasd = null;
        Data.connect = null;
    }
    
    public static void connect() {
        final String url = "jdbc:mysql://" + Data.SqlIp + ":" + Data.SqlPort + "/" + Data.Sql;
        final String user = Data.SqlUser;
        final String password = Data.SqlPasd;
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url, user, password);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        Data.connect = conn;
    }
    
    public static void close() {
        try {
            Data.connect.close();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public static void createTable() {
        try {
            PreparedStatement state = Data.connect.prepareStatement("create table if not exists " + Data.SqlTable + "(Name varchar(20),Points varchar(20),Date varchar(10),Calendar TEXT)");
            state.executeUpdate();
            state = Data.connect.prepareStatement("alter table " + Data.SqlTable + " convert to character set utf8");
            state.executeUpdate();
            state = Data.connect.prepareStatement("create table if not exists HeroesData(Name varchar(20),Job varchar(20),Level varchar(20))");
            state.executeUpdate();
            state = Data.connect.prepareStatement("alter table HeroesData convert to character set utf8");
            state.executeUpdate();
            state.close();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public static void setData(final String name, final String type, final String str) {
        try {
            PreparedStatement state = Data.connect.prepareStatement("select * from " + Data.SqlTable + " where Name=?");
            state.setString(1, name);
            final ResultSet res = state.executeQuery();
            if (res.next()) {
                state = Data.connect.prepareStatement("update " + Data.SqlTable + " set " + type + "=? where Name=?");
                state.setString(1, new StringBuilder(String.valueOf(str)).toString());
                state.setString(2, name);
                state.executeUpdate();
                state.close();
            }
            else {
                state = Data.connect.prepareStatement("insert into " + Data.SqlTable + " values('" + name + "','0','null','null')");
                state.executeUpdate();
                state = Data.connect.prepareStatement("update " + Data.SqlTable + " set " + type + "=? where Name=?");
                state.setString(1, new StringBuilder(String.valueOf(str)).toString());
                state.setString(2, name);
                state.executeUpdate();
                state.close();
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public static String getData(final String name, final String type) {
        try {
            final PreparedStatement state = Data.connect.prepareStatement("select * from " + Data.SqlTable + " where Name=?");
            state.setString(1, name);
            final ResultSet res = state.executeQuery();
            if (!res.next()) {
                return null;
            }
            switch (type) {
                case "Points": {
                    final String arg = res.getString(2);
                    res.close();
                    return arg;
                }
                case "Calendar": {
                    final String arg = res.getString(4);
                    res.close();
                    return arg;
                }
                case "Date": {
                    final String arg = res.getString(3);
                    res.close();
                    return arg;
                }
                default:
                    break;
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public static List<String> getPlayersList() {
        try {
            final List<String> list = new ArrayList<String>();
            final PreparedStatement state = Data.connect.prepareStatement("select * from " + Data.SqlTable + " where Points<>'0'");
            final ResultSet res = state.executeQuery();
            while (res.next()) {
                list.add(res.getString(1));
            }
            state.close();
            return list;
        }
        catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public static List<Hero> getHeroes() {
        final List<Hero> args = new ArrayList<Hero>();
        try {
            final PreparedStatement state = Data.connect.prepareStatement("select * from HeroesData");
            final ResultSet res = state.executeQuery();
            while (res.next()) {
                args.add(new Hero(res.getString(1), res.getString(2), Integer.parseInt(res.getString(3))));
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return args;
    }
    
    public static void setheroees(final com.herocraftonline.heroes.characters.Hero hero) {
        try {
            PreparedStatement state = Data.connect.prepareStatement("select * from HeroesData where Name=?");
            state.setString(1, hero.getPlayer().getName());
            final ResultSet res = state.executeQuery();
            if (res.next()) {
                state = Data.connect.prepareStatement("update HeroesData set Job=?, Level=? where Name=?");
                state.setString(1, hero.getHeroClass().getName());
                state.setString(2, new StringBuilder(String.valueOf(hero.getLevel())).toString());
                state.setString(3, hero.getPlayer().getName());
                state.executeUpdate();
            }
            else {
                state = Data.connect.prepareStatement("insert into HeroesData values(?,?,?)");
                state.setString(2, hero.getHeroClass().getName());
                state.setString(3, new StringBuilder(String.valueOf(hero.getLevel())).toString());
                state.setString(1, hero.getPlayer().getName());
                state.executeUpdate();
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
