package com._0myun.minecraft.eventmsg.bindqq;

import com._0myun.minecraft.eventmsg.bindqq.bin.QQInfo;
import com.earth2me.essentials.Essentials;
import lombok.var;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Map;

public final class Main extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        ConfigurationSerialization.registerClass(QQInfo.class, "QQInfo");

        this.saveDefaultConfig();
        Bukkit.getPluginManager().registerEvents(this, this);

    }

    @Override
    public void onDisable() {
        this.saveConfig();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length != 1) {
            sender.sendMessage("缺少QQ号！命令：/qq <qq号>");
            return true;
        }
        Player p = (Player) sender;
        Object o = getConfig().get(p.getName());
        var info = new QQInfo();
        if (o != null) info = (QQInfo) o;
        info.setQq(args[0]);
        getConfig().set(p.getName(), info);
        sender.sendMessage("设置成功!");
        this.saveConfig();
        return true;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        Object o = getConfig().get(p.getName());
        var info = new QQInfo();
        if (o != null) {
            info = (QQInfo) o;
        } else {
            info.setReg(System.currentTimeMillis());
        }
        info.setLog(System.currentTimeMillis());
        getConfig().set(p.getName(), info);
        this.saveConfig();
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onCommand(PlayerCommandPreprocessEvent e) {
        Player p = e.getPlayer();
        var info = (QQInfo) getConfig().get(p.getName());
        if (info.getQq() != null && !info.getQq().equalsIgnoreCase("null")) return;
        if (e.getMessage().startsWith("/qq")) return;
        e.setCancelled(true);
        p.sendMessage("你没有绑定QQ,请输入/qq <QQ号>以绑定QQ");
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onMove(PlayerMoveEvent e) {
        Player p = e.getPlayer();
        var info = (QQInfo) getConfig().get(p.getName());
        if (info.getQq() != null && !info.getQq().equalsIgnoreCase("null")) return;
        e.setCancelled(true);
        p.sendMessage("你没有绑定QQ,请输入/qq <QQ号>以绑定QQ");
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onBreak(BlockBreakEvent e) {
        Player p = e.getPlayer();
        var info = (QQInfo) getConfig().get(p.getName());
        if (info.getQq() != null && !info.getQq().equalsIgnoreCase("null")) return;
        e.setCancelled(true);
        p.sendMessage("你没有绑定QQ,请输入/qq <QQ号>以绑定QQ");
    }
}
