//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.Drunk_autumn;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class KDPrefix extends JavaPlugin implements Listener {
    String m = "Message";
    FileConfiguration config;
    List kill = new ArrayList();
    List death = new ArrayList();

    public KDPrefix() {
    }

    public void onEnable() {
        this.getServer().getConsoleSender().sendMessage(ChatColor.AQUA + "KDPrefix 1.0  插件已加载到服务器 ");
        this.getServer().getPluginManager().registerEvents(this, this);
        this.getConfig().options().copyDefaults(true);
        this.saveConfig();
        this.saveDefaultConfig();
        KDPConfig.loadConfig();
        KDPConfig.saveConfig();
    }

    public void onDisable() {
        this.getServer().getConsoleSender().sendMessage(ChatColor.AQUA + "KDPrefix 1.0  插件已关闭");
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        this.config = this.getConfig();
        Player p = (Player) sender;
        String name = p.getName();
        if (label.equalsIgnoreCase("kdp")) {
            if (args.length == 0) {
                sender.sendMessage("§6§lKDPrefix Help：");
                sender.sendMessage("§a/kdp me §7§l- §9§o查看自己的击杀/被杀比值");
                sender.sendMessage("§a/kdp reload §7§l- §9§o重载config.yml");
                return true;
            }

            if (args[0].equalsIgnoreCase("me")) {
                if (!sender.hasPermission("kdp.default")) {
                    sender.sendMessage(this.config.getString(this.m + ".No-Permission").replace("&", "§"));
                    return true;
                }

                int kill = KDPConfig.playerdata.getInt(name + "Kill");
                int death = KDPConfig.playerdata.getInt(name + "Death");
                p.sendMessage("§a=========================");
                p.sendMessage("§c击杀：  §f§l" + kill);
                p.sendMessage("§c死亡：  §f§l" + death);
                BigDecimal bg;
                double kd;
                if (death == 0) {
                    bg = new BigDecimal((double) kill);
                    kd = bg.setScale(1, 4).doubleValue();
                    p.sendMessage("§cKD比值：  §f§l" + kd);
                }

                if (death != 0) {
                    bg = new BigDecimal((double) kill / (double) death);
                    kd = bg.setScale(1, 4).doubleValue();
                    p.sendMessage("§cKD比值：  §f§l" + kd);
                }

                p.sendMessage("§a=========================");
                return true;
            }

            if (args[0].equalsIgnoreCase("reload")) {
                if (!sender.hasPermission("kdp.reload")) {
                    sender.sendMessage(this.config.getString(this.m + ".No-Permission").replace("&", "§"));
                    return true;
                }

                sender.sendMessage("§a[KDPrefix]正在重载配置文件..");
                this.reloadConfig();
                sender.sendMessage("§a[KDPrefix]重载成功！");
                return true;
            }
        }

        return false;
    }

    @EventHandler
    public void onKill(PlayerDeathEvent e) {
        if (e.getEntity().getKiller() instanceof Player && e.getEntity() instanceof Player) {
            Player k = e.getEntity().getKiller();
            Player d = e.getEntity();
            KDPConfig.playerdata.set(k.getName() + "Kill", KDPConfig.playerdata.getInt(k.getName() + "Kill") + 1);
            KDPConfig.playerdata.set(d.getName() + "Death", KDPConfig.playerdata.getInt(d.getName() + "Death") + 1);
            k.sendMessage(this.getConfig().getString(this.m + ".On-Kill").replace("&", "§").replace("{deathplayer}", d.getName()));
            KDPConfig.saveConfig();
        }

    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent e) {
        try {
            Player p = e.getPlayer();
            int kill = KDPConfig.playerdata.getInt(p.getName() + "Kill");
            int death = KDPConfig.playerdata.getInt(p.getName() + "Death");
            if (death == 0) death = 1;
            BigDecimal bg;
            double kd;
            if (death == 0) {
                kd = Double.valueOf(kill).doubleValue();
                kd = Double.valueOf(new DecimalFormat("#.0000").format(kd));
                e.setFormat(this.getConfig().getString("Prefix").replace("&", "§").replace("{kill}", "" + kill).replace("{death}", "" + death).replace("{KD}", "" + kd) + e.getFormat());
            }

            kd = Double.valueOf(kill / death).doubleValue();
            kd = Double.valueOf(new DecimalFormat("#.0000").format(kd));
            e.setFormat(this.getConfig().getString("Prefix").replace("&", "§").replace("{kill}", "" + kill).replace("{death}", "" + death).replace("{KD}", "" + kd) + e.getFormat());

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
