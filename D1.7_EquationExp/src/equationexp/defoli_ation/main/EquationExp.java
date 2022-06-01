/*
 * Decompiled with CFR 0_133.
 *
 * Could not load the following classes:
 *  org.bukkit.Bukkit
 *  org.bukkit.Server
 *  org.bukkit.command.CommandExecutor
 *  org.bukkit.command.PluginCommand
 *  org.bukkit.configuration.InvalidConfigurationException
 *  org.bukkit.configuration.file.FileConfiguration
 *  org.bukkit.configuration.file.YamlConfiguration
 *  org.bukkit.event.Listener
 *  org.bukkit.plugin.Plugin
 *  org.bukkit.plugin.PluginManager
 *  org.bukkit.plugin.java.JavaPlugin
 */
package equationexp.defoli_ation.main;

import equationexp.defoli_ation.main.Commands;
import equationexp.defoli_ation.main.equation.Equation;
import equationexp.defoli_ation.main.equation.EquationManager;
import equationexp.defoli_ation.main.equation.LevelScope;
import equationexp.defoli_ation.main.exp.Exp;
import equationexp.defoli_ation.main.exp.ExpManager;
import equationexp.defoli_ation.main.file.equationfile.EquationFile;
import equationexp.defoli_ation.main.file.equationfile.EquationYaml;
import equationexp.defoli_ation.main.file.expfile.LocalPlayerExpFile;
import equationexp.defoli_ation.main.file.expfile.MySQLPlayerExpFile;
import equationexp.defoli_ation.main.file.expfile.PlayerExpFile;
import equationexp.defoli_ation.main.file.mysql.MySQL;
import equationexp.defoli_ation.main.hook.PlaceholderHook;
import equationexp.defoli_ation.main.listener.ExpEvent;
import equationexp.defoli_ation.main.listener.OnPlayerJoin;
import equationexp.defoli_ation.main.listener.OnPlayerQuit;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Logger;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.PluginCommand;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class EquationExp
        extends JavaPlugin {
    private final EquationManager equationManager = new EquationManager();
    private FileConfiguration config;
    private EquationFile customEquation;
    private static ExpManager expManager;
    private MySQL mysql;
    private PlayerExpFile expFile;

    public void onEnable() {
        this.loadDefaultEquation();
        File configFile = new File(this.getDataFolder(), "config.yml");
        if (!configFile.exists()) {
            this.saveDefaultConfig();
        }
        this.config = new YamlConfiguration();
        try {
            this.config.load(configFile);
        }
        catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
        this.customEquation = new EquationYaml(this.config);
        Equation[] arrequation = this.customEquation.getEquation();
        int n = arrequation.length;
        int n2 = 0;
        while (n2 < n) {
            Equation equation = arrequation[n2];
            this.addEquation(equation);
            ++n2;
        }
        this.mysql = this.getMySQL();
        this.expFile = this.getPlayerExpFile();
        expManager = new ExpManager(this.expFile, new Exp(this.equationManager));
        if (Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")) {
            new PlaceholderHook((Plugin)this, "EquationExp", this.expFile, expManager.getExp()).hook();
            this.getLogger().info("Placeholder 已找到，变量已启用");
        }
        this.getServer().getPluginManager().registerEvents((Listener)new ExpEvent(expManager, this.expFile), (Plugin)this);
        this.getServer().getPluginManager().registerEvents((Listener)new OnPlayerJoin(this.expFile, expManager), (Plugin)this);
        this.getServer().getPluginManager().registerEvents((Listener)new OnPlayerQuit(expManager, this.expFile), (Plugin)this);
        this.getCommand("vexp").setExecutor((CommandExecutor)new Commands(expManager, this.expFile));
    }

    public static ExpManager getExpManager() {
        return expManager;
    }

    public void onDisable() {
        this.expFile.disable();
    }

    private void loadDefaultEquation() {
        this.addEquation(new Equation("$level$*$level$+6*$level$", new LevelScope(0, 16)));
        this.addEquation(new Equation("2.5*$level$*$level$-40.5*$level$+360", new LevelScope(16, 31)));
        this.addEquation(new Equation("4.5*$level$*$level$-162.5*$level$+2220", new LevelScope(31, 40)));
    }

    private void addEquation(Equation e) {
        if (this.equationManager.addEquation(e)) {
            this.getLogger().info("添加" + e.toString() + "----成功");
        } else {
            this.getLogger().info("添加" + e.toString() + "----失败");
        }
    }

    private PlayerExpFile getPlayerExpFile() {
        if (this.mysql != null) {
            return new MySQLPlayerExpFile(this.mysql, this.getSaveTime());
        }
        return new LocalPlayerExpFile(new File(this.getDataFolder(), "playerexp"), this.getSaveTime());
    }

    private MySQL getMySQL() {
        String userName = this.config.getString("mysql.username");
        String password = this.config.getString("mysql.password");
        String stringPort = this.config.getString("mysql.port");
        String host = this.config.getString("mysql.host");
        if (userName == null) {
            return null;
        }
        if (stringPort != null) {
            int port = Integer.parseInt(stringPort);
            if (host != null) {
                try {
                    return new MySQL(host, port, userName, password);
                }
                catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            try {
                return new MySQL("localhost", port, userName, password);
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
        }
        try {
            return new MySQL("localhost", 3306, userName, password);
        }
        catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    private int getSaveTime() {
        return this.config.getInt("savetime") * 1000 * 60;
    }
}

