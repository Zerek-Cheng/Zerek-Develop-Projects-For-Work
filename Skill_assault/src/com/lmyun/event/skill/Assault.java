package com.lmyun.event.skill;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class Assault extends JavaPlugin {
    public List<String> assaultList = new ArrayList<>();
    public HashMap<String, String> assaultEffect = new HashMap<>();
    public FileConfiguration config;

    @Override
    public void onEnable() {
        saveResource("config.yml", false);
        config = this.getConfig();
        Bukkit.getServer().getPluginManager().registerEvents(
                new Listener(this), this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        ///cf 冲锋距离 冲锋伤害 粒子类型
        if (args.length != 3) {
            sender.sendMessage("/cf 冲锋距离 冲锋伤害 粒子类型\n");
            return true;
        }
        double distance = Double.valueOf(args[0]);
        double damage = Double.valueOf(args[1]);
        String effect = args[2];
        Player p = (Player) sender;
        if(!p.hasPermission("skill.assault.use")&& !p.isOp()){
            sender.sendMessage("小傻逼，你没权限用冲刺\n");
            return true;
        }
        Location loc = p.getLocation().clone();
        Vector vector = loc.getDirection().clone();
        vector=vector.clone();
            vector.multiply(distance);
        if (!this.config.getBoolean("enableY")) {
            vector.setY(0);
        }
        this.assaultList.add(p.getName());
        this.assaultEffect.put(p.getName(), effect);
        AssaultTask task = new AssaultTask(this, p, damage, 4);
        p.setVelocity(vector);
        task.runTaskLater(this, 0);

        return true;

    }

}
