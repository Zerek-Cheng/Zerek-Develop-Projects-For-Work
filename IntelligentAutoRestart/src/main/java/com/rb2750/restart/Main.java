//
// Decompiled by Procyon v0.5.30
//

package com.rb2750.restart;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {
    int timeRemaining;
    String restartMsg;

    public Main() {
        this.timeRemaining = 0;
        this.restartMsg = null;
    }

    public void onEnable() {
        saveDefaultConfig();
        if (!this.getConfig().contains("Restart.Time")) {
            this.getConfig().set("Restart.Time", 5000);
            this.saveConfig();
        }
        if (!this.getConfig().contains("Restart.Message")) {
            this.getConfig().set("Restart.Message", "§4重启中!");
            this.saveConfig();
        }
        this.restartMsg = this.getConfig().getString("Restart.Message");
        this.timeRemaining = this.getConfig().getInt("Restart.Time");
        this.getServer().getScheduler().scheduleSyncRepeatingTask(this, () -> {
            --Main.this.timeRemaining;
            if (Main.this.timeRemaining == 300) {
                Bukkit.broadcastMessage(ChatColor.GREEN + "5 分钟后重启");
                //Main.this.HiBeep();
            }
            if (Main.this.timeRemaining == 120) {
                Bukkit.broadcastMessage(ChatColor.GREEN + "2 分钟后重启");
                //     Main.this.MidBeep();
            }
            if (Main.this.timeRemaining == 60) {
                Bukkit.broadcastMessage(ChatColor.GREEN + "1 分钟后重启");
                //    Main.this.MidBeep();
            }
/*            if (Main.this.timeRemaining == 5) {
                Bukkit.broadcastMessage(ChatColor.RED + "5 秒后重启");
                //   Main.this.HiBeep();
            }
            if (Main.this.timeRemaining == 4) {
                Bukkit.broadcastMessage(ChatColor.RED + "4 秒后重启");
                //  Main.this.MidBeep();
            }
            if (Main.this.timeRemaining == 3) {
                Bukkit.broadcastMessage(ChatColor.RED + "3 秒后重启");
                //  Main.this.MidBeep1();
            }
            if (Main.this.timeRemaining == 2) {
                Bukkit.broadcastMessage(ChatColor.RED + "2 秒后重启");
                //   Main.this.LowBeep();
            }
            if (Main.this.timeRemaining == 1) {
                Bukkit.broadcastMessage(ChatColor.RED + "1 秒后重启");
                //    Main.this.HiBeep();
            }*/
            if (Main.this.timeRemaining < 1) {
              //  Bukkit.broadcastMessage(ChatColor.RED + "尝试重启!");
                if (Bukkit.getOnlinePlayers().size() > 0) {
                    //Bukkit.broadcastMessage(ChatColor.RED + "检测到服内有玩家,延迟重启!");
                    Main.this.timeRemaining = 2;
                    return;
                }
                Main.this.Restart(Main.this.restartMsg);
            }
        }, 0L, 20L);
        this.getServer().getPluginManager().registerEvents(new EventHandle(this), this);
    }

    public boolean onCommand(final CommandSender sender, final Command command, final String commandLabel, final String[] args) {
       /* if (command.getName().equalsIgnoreCase("Restart")) {
            if (args.length > 0) {
                if (sender.isOp()) {
                    if (args[0].equalsIgnoreCase("set")) {
                        if (args.length == 3) {
                            if (args[2].equalsIgnoreCase("H")) {
                                this.timeRemaining = Integer.parseInt(args[1]) * 60 * 60;
                                sender.sendMessage(ChatColor.GOLD + "Restart set to " + args[1] + " hours!");
                            }
                            if (args[2].equalsIgnoreCase("M")) {
                                this.timeRemaining = Integer.parseInt(args[1]) * 60;
                                sender.sendMessage(ChatColor.GOLD + "Restart set to " + args[1] + " Minutes!");
                            }
                            if (args[2].equalsIgnoreCase("S")) {
                                this.timeRemaining = Integer.parseInt(args[1]);
                                sender.sendMessage(ChatColor.GOLD + "Restart set to " + args[1] + " Seconds!");
                            }
                            this.getConfig().set("Restart.Time", (Object) this.timeRemaining);
                            this.saveConfig();
                        } else {
                            sender.sendMessage(ChatColor.RED + "Usage: /restart set [Time] H/M/S");
                            sender.sendMessage(ChatColor.GOLD + "EG: /restart set 50 M");
                        }
                    } else if (args[0].equalsIgnoreCase("now")) {
                        this.Restart(this.restartMsg);
                    } else if (args[0].equalsIgnoreCase("setMessage")) {
                        if (args.length > 1) {
                            String sb = "";
                            for (int i = 1; i < args.length; ++i) {
                                sb = String.valueOf(sb) + args[i] + " ";
                            }
                            this.getConfig().set("Restart.Message", (Object) sb);
                            this.saveConfig();
                            sender.sendMessage(ChatColor.BLUE + "Set the restart message to: " + ChatColor.WHITE + sb.trim().replace("&", "§").replace("\\n", "\n"));
                        } else {
                            sender.sendMessage(ChatColor.RED + "Usage: /restart setMessage [Message]");
                        }
                    } else {
                        sender.sendMessage(ChatColor.DARK_AQUA + "Help:");
                        sender.sendMessage(ChatColor.GOLD + "/restart set [Time] [H/M/S]" + "\n" + ChatColor.DARK_AQUA + "Set how long until next restart.");
                        sender.sendMessage(ChatColor.GOLD + "/restart now" + "\n" + ChatColor.DARK_AQUA + "Restart now");
                        sender.sendMessage(ChatColor.GOLD + "/restart setMessage [Message]" + "\n" + ChatColor.DARK_AQUA + "Set the restart message. Supports chat colours.");
                    }
                } else {
                    sender.sendMessage(ChatColor.RED + "You don't have permission!");
                }
            } else {
                sender.sendMessage(ChatColor.GOLD + "Restarting in: " + ChatColor.GREEN + this.timeRemaining + " Seconds" + ChatColor.GOLD + " Or " + ChatColor.DARK_AQUA + this.timeRemaining / 60 + " Minutes" + ChatColor.GOLD + " Or " + ChatColor.AQUA + this.timeRemaining / 60 / 60 + " Hours" + ChatColor.GOLD + "!");
            }
            return true;
        }
        return false;*/
        return true;
    }
/*
    public void HiBeep() {
    }

    public void MidBeep() {
    }

    public void MidBeep1() {
    }

    public void LowBeep() {
    }*/

    public void Restart(final String msg) {
        this.getServer().getScheduler().scheduleSyncDelayedTask(this, new Runnable() {
            @Override
            public void run() {
                Bukkit.getServer().shutdown();
            }
        }, 20L);
    }
}
