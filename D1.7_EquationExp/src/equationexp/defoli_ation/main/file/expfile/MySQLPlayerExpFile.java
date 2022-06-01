/*
 * Decompiled with CFR 0_133.
 * 
 * Could not load the following classes:
 *  org.bukkit.entity.Player
 */
package equationexp.defoli_ation.main.file.expfile;

import equationexp.defoli_ation.main.file.expfile.PlayerExpFile;
import equationexp.defoli_ation.main.file.mysql.MySQL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Timer;
import java.util.TimerTask;
import org.bukkit.entity.Player;

public class MySQLPlayerExpFile
implements PlayerExpFile {
    private MySQL sql;
    private final String schema = "equationExp";
    private final String table = "playerExp";
    private final Timer timer;

    public MySQLPlayerExpFile(MySQL sql, int time) {
        this.sql = sql;
        this.check();
        this.timer = new Timer();
        this.timer.schedule(new TimerTask(){

            @Override
            public void run() {
                MySQLPlayerExpFile.this.save();
            }
        }, 0L, (long)time);
    }

    @Override
    public void savePlayerExp(Player p, int exp) {
        try {
            this.sql.execute("insert into equationExp.playerExp set playerName='" + p.getName() + "',exp=" + exp + " on duplicate key update exp=" + exp + "; ");
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getPlayerExp(Player p) {
        int exp = 0;
        try {
            ResultSet result = this.sql.executeQuery("select * from equationExp.playerExp where playerName = '" + p.getName() + "'");
            if (result.next()) {
                exp = result.getInt(2);
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return exp;
    }

    @Override
    public void disable() {
        this.timer.cancel();
        this.save();
        try {
            this.sql.disconnect();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean exist(Player p) {
        try {
            ResultSet result = this.sql.executeQuery("select * from equationExp.playerExp where playerName = '" + p.getName() + "'");
            return result.next();
        }
        catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private void check() {
        try {
            this.sql.execute("create database if not exists equationExp");
            this.sql.execute("create table if not exists equationExp.playerExp(playerName char(32) not null primary key,exp int unsigned not null default 0)");
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void save() {
        try {
            this.sql.commit();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

}

