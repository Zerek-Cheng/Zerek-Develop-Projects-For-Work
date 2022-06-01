package com.lmyun.minecraft.commandplaysound;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

public class CommandPlayerSound extends JavaPlugin {

    @Override
    public void onEnable() {
        saveResource("/p.yml", false);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.isOp() && !sender.hasPermission("commandplaysound.use")) {
            sender.sendMessage("权限不足");
            return true;
        }
        if (args.length < 2) {
            sender.sendMessage("命令: /CommandPlayerSound 音效路径 范围");
            return true;
        }
        if (!(sender instanceof Player)) {
            return true;
        }
        Player p = (Player) sender;
        List<Entity> nearbyEntities = p.getNearbyEntities(Double.valueOf(args[1]), Double.valueOf(args[1]), Double.valueOf(args[1]));
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "playsound " + args[0] + " " + p.getName());
        for (Entity entity : nearbyEntities) {
            if (!(entity instanceof Player)) {
                continue;
            }
            Player listener = (Player) entity;
            System.out.println(listener.getName());
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "playsound " + args[0] + " " + listener.getName());
        }
        //sender.sendMessage("播放命令发送成功");
        return true;
    }
}
