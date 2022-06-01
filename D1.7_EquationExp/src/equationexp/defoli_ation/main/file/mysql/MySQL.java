/*
 * Decompiled with CFR 0_133.
 */
package equationexp.defoli_ation.main.file.mysql;

import java.io.PrintStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MySQL {
    private final Connection connection;

    public MySQL(String host, int port, String userName, String password) throws SQLException {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        }
        catch (ClassNotFoundException e) {
            System.out.println("MYSQL\u52a0\u8f7d\u5931\u8d25");
        }
        this.connection = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/?useUnicode=true&characterEncoding=utf-8&useSSL=false", userName, password);
        this.connection.setAutoCommit(false);
    }

    public boolean execute(String statement) throws SQLException {
        if (this.isConnected().booleanValue()) {
            PreparedStatement sql = this.connection.prepareStatement(statement);
            return sql.execute();
        }
        return false;
    }

    public synchronized ResultSet executeQuery(String statement) throws SQLException {
        if (this.isConnected().booleanValue()) {
            PreparedStatement sql = this.connection.prepareStatement(statement);
            return sql.executeQuery();
        }
        return null;
    }

    public Boolean isConnected() throws SQLException {
        return !this.connection.isClosed();
    }

    public void commit() throws SQLException {
        this.connection.commit();
    }

    public synchronized void disconnect() throws SQLException {
        if (!this.connection.isClosed()) {
            this.connection.close();
        }
    }
}

