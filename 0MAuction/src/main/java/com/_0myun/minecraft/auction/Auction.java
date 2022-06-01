package com._0myun.minecraft.auction;

import com._0myun.minecraft.auction.checker.OrderGiveChecker;
import com._0myun.minecraft.auction.checker.TimeOutChecker;
import com._0myun.minecraft.auction.commands.*;
import com._0myun.minecraft.auction.godtype.GoodTypeManager;
import com._0myun.minecraft.auction.godtype.ItemGood;
import com._0myun.minecraft.auction.godtype.PokemonGood;
import com._0myun.minecraft.auction.payway.Gold;
import com._0myun.minecraft.auction.payway.PaywayManager;
import com._0myun.minecraft.auction.payway.Points;
import com._0myun.minecraft.auction.table.Orders;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;

public final class Auction extends JavaPlugin {
    public static Auction INSTANCE;
    @Getter
    ConnectionSource db;
    @Getter
    Dao<Orders, Integer> dao;

    static {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void connDb() {
        try {
            String databaseUrl = "jdbc:sqlite:" + getDataFolder() + "/data.db";
            db = new JdbcConnectionSource(databaseUrl);
            dao = DaoManager.createDao(db, Orders.class);
            dao.setObjectCache(true);
            TableUtils.createTableIfNotExists(db, Orders.class);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void initPayway() {
        PaywayManager.getPayway().put("gold", new Gold());
        PaywayManager.getPayway().put("points", new Points());

        GoodTypeManager.getGodType().put("item", new ItemGood());
        GoodTypeManager.getGodType().put("pokemon", new PokemonGood());
    }

    @Override
    public void onEnable() {
        INSTANCE = this;
        saveDefaultConfig();
        this.connDb();
        this.initPayway();
        ConfigUtils.init();
        Bukkit.getPluginManager().registerEvents(new AuctionListener(), this);
        Bukkit.getPluginManager().registerEvents(new GuiListener(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerListener(), this);
        Bukkit.getScheduler().runTaskTimerAsynchronously(this, new TimeOutChecker(), 20, 20);
        Bukkit.getScheduler().runTaskTimerAsynchronously(this, new OrderGiveChecker(), 20, 20);
    }

    @Override
    public void onDisable() {
        try {
            this.dao.closeLastIterator();
            this.db.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(args.length >= 1)) return true;
        if (args[0].equalsIgnoreCase("reload"))
            (new ReloadCommand()).onCommand(sender, command, label, args);
        if (args[0].equalsIgnoreCase("sell"))
            (new SellCommand()).onCommand(sender, command, label, args);
        if (args[0].equalsIgnoreCase("sellp"))
            (new SellpCommand()).onCommand(sender, command, label, args);
        if (args[0].equalsIgnoreCase("pokesell"))
            (new PokesellCommand()).onCommand(sender, command, label, args);
        if (args[0].equalsIgnoreCase("pokesellp"))
            (new PokesellpCommand()).onCommand(sender, command, label, args);
        if (args[0].equalsIgnoreCase("open"))
            (new OpenCommand()).onCommand(sender, command, label, args);
        return true;
    }
}
