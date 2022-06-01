/*
 * Decompiled with CFR 0_133.
 */
package info.TrenTech.EasyKits.SQL;

import info.TrenTech.EasyKits.Utils.Utils;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public abstract class SQLUtils {
    protected static Connection connection = null;
    private static Map<String, PreparedStatement> statementCache = new HashMap<String, PreparedStatement>();
    private static boolean useStatementCache = true;

    public static boolean connect() throws SQLException, IllegalAccessException, InstantiationException, ClassNotFoundException {
        Properties properties;
        if (connection != null) {
            return true;
        }
        Driver driver = (Driver)Class.forName("org.sqlite.JDBC").newInstance();
        connection = driver.connect("jdbc:sqlite:plugins/EasyKitsRel/data.db", properties = new Properties());
        if (connection == null) {
            throw new NullPointerException("Connecting to database failed!");
        }
        return true;
    }

    public static void dispose() {
        statementCache.clear();
        try {
            if (connection != null) {
                connection.close();
            }
        }
        catch (SQLException e) {
            Utils.getLogger().severe(e.getMessage());
        }
        connection = null;
    }

    public static Connection getConnection() {
        if (connection == null) {
            throw new NullPointerException("No connection!");
        }
        return connection;
    }

    public static PreparedStatement prepare(String sql) throws SQLException {
        return SQLUtils.prepare(sql, false);
    }

    public static PreparedStatement prepare(String sql, boolean returnGeneratedKeys) throws SQLException {
        if (connection == null) {
            throw new SQLException("No connection");
        }
        if (useStatementCache && statementCache.containsKey(sql)) {
            return statementCache.get(sql);
        }
        PreparedStatement preparedStatement = returnGeneratedKeys ? connection.prepareStatement(sql, 1) : connection.prepareStatement(sql);
        statementCache.put(sql, preparedStatement);
        return preparedStatement;
    }

    public static boolean useStatementCache() {
        return useStatementCache;
    }

    public static void setUseStatementCache(boolean useStatementCache) {
        SQLUtils.useStatementCache = useStatementCache;
    }
}

