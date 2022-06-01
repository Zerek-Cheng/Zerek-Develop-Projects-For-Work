package com._0myun.minecraft.eventmsg.mythicmobsdamagestatistical;

import com._0myun.minecraft.eventmsg.mythicmobsdamagestatistical.bin.Rule;
import com._0myun.minecraft.eventmsg.mythicmobsdamagestatistical.bin.Statistical;
import io.lumine.xikage.mythicmobs.MythicMobs;
import io.lumine.xikage.mythicmobs.api.bukkit.BukkitAPIHelper;
import lombok.Data;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;


public class Main extends JavaPlugin {
    @Getter
    public static Main plugin;
    private MythicMobs mm;
    @Getter
    private BukkitAPIHelper mmApi;

    @Override
    public void onEnable() {
        ConfigurationSerialization.registerClass(Rule.class, "com._0myun.minecraft.eventmsg.mythicmobsdamagestatistical.bin.Rule");
        ConfigurationSerialization.registerClass(Statistical.class, "com._0myun.minecraft.eventmsg.mythicmobsdamagestatistical.bin.Statistical");
        Main.plugin = this;

        this.loadConfig();

        this.mm = (MythicMobs) Bukkit.getPluginManager().getPlugin("MythicMobs");
        this.mmApi = mm.getAPIHelper();

        Bukkit.getPluginManager().registerEvents(new PlayerListener(), this);


        Bukkit.getScheduler().runTaskTimer(this, new Timer(), 20, 20);

        new PlaceholderExpansion().register();
    }

    private void loadConfig() {
        saveDefaultConfig();
    }

    @Override
    public void onDisable() {
        this.saveConfig();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 1 && args[0].equalsIgnoreCase("reload") && sender.isOp()) {
            this.reloadConfig();
            sender.sendMessage("重载ok");
            return true;
        }
        Player p = (Player) sender;
        List<String> reward = DataManager.getPlayerReward(p.getUniqueId());
        if (reward == null) {
            p.sendMessage("你没有待领取的排行榜奖励");
            return true;
        }
        boolean op = p.isOp();
        try {
            if (!op) p.setOp(true);
            reward.forEach(cmd -> {
                cmd = cmd.replace("<player>", p.getName());
                p.performCommand(cmd);
            });
        } finally {
            if (!op) p.setOp(false);
        }
        return true;
    }
}
