package com._0myun.minecraft.eventmsg.pvptitlesdeathtaker;

import com.gmail.mikeundead.DatabaseHandler;
import com.gmail.mikeundead.PvPTitles;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.Field;

public class Main extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        Bukkit.getPluginManager().registerEvents(this, this);
    }

    @EventHandler(ignoreCancelled = true)
    public void onDeath(PlayerDeathEvent e) {
        Player p = e.getEntity();
        Player kP = p.getKiller();
        Field databaseHandlerField = null;
        try {
            databaseHandlerField = PvPTitles.class.getDeclaredField("databaseHandler");
            databaseHandlerField.setAccessible(true);
            DatabaseHandler database = ((DatabaseHandler) databaseHandlerField.get(Bukkit.getPluginManager().getPlugin("PvPTitles")));
            database.LoadPlayerFame(p.getName());
            database.SavePlayerFame(p.getName(), database.PlayerFame() - 1);
            p.sendMessage("你因死亡导致声望-1");
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }
}
