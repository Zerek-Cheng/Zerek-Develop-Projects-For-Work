/*
 * Decompiled with CFR 0_133.
 *
 * Could not load the following classes:
 *  org.bukkit.Bukkit
 *  org.bukkit.entity.Player
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.Listener
 *  org.bukkit.event.player.AsyncPlayerChatEvent
 *  org.bukkit.event.player.PlayerJoinEvent
 *  org.bukkit.plugin.Plugin
 *  org.bukkit.scheduler.BukkitRunnable
 *  org.bukkit.scheduler.BukkitTask
 */
package cn.timewk.neptune.plugin.afkkicker.listener;

import cn.timewk.neptune.plugin.afkkicker.AFKKicker;
import cn.timewk.neptune.plugin.afkkicker.api.util.PluginGetter;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

public class PlayerListener
        implements Listener,
        PluginGetter {
    private int period;
    private int kickAfter;
    private int tenSec;
    private String kickMessage;
    private String noticeTen;
    private String checkMes;
    private String notice;
    private Map<String, Integer> timeleft = new HashMap<String, Integer>();
    private Map<String, String> code = new HashMap<String, String>();

    public PlayerListener(int period, int delay2, String mes3, String mes4, String mes5, String mes6) {
        this.period = period;
        this.kickAfter = - delay2;
        this.tenSec = this.kickAfter + 200;
        this.kickMessage = mes3;
        this.noticeTen = mes4;
        this.checkMes = mes5;
        this.notice = mes6;
        for (Player p : Bukkit.getOnlinePlayers()) {
            this.timeleft.put(p.getName(), period);
        }
        new BukkitRunnable(){

            public void run() {
                try {

                    for (String name : PlayerListener.this.timeleft.keySet()) {
                        Player p = Bukkit.getPlayer((String)name);
                        if (p == null || !p.isOnline() || p.hasPermission("afkkicker.ignoreKick")) {
                            PlayerListener.this.timeleft.remove(name);
                            continue;
                        }
                        int temp = (Integer)PlayerListener.this.timeleft.get(name) - 200;
                        if (temp > PlayerListener.this.kickAfter) {
                            if (temp <= 0 && temp + 200 > 0) {
                                PlayerListener.this.code.put(name, PlayerListener.this.randomCode());
                                String temp_mes = PlayerListener.this.notice.replaceAll("&", "§").replace("%player%", name).replace("%code%", (CharSequence)PlayerListener.this.code.get(name));
                                p.sendMessage(temp_mes);
                                p.sendTitle("", temp_mes, 20, 60, 20);
                            } else if (temp < PlayerListener.this.tenSec) {
                                p.sendMessage(PlayerListener.this.noticeTen.replaceAll("&", "§").replace("%player%", name).replace("%code%", (CharSequence)PlayerListener.this.code.get(name)));
                            }
                            PlayerListener.this.timeleft.put(name, temp);
                            continue;
                        }
                        p.kickPlayer(PlayerListener.this.kickMessage.replaceAll("&", "§").replace("%player%", name));
                        PlayerListener.this.timeleft.remove(name);
                        PlayerListener.this.code.remove(name);
                    }
                }catch (Exception ex){

                }
            }
        }.runTaskTimer((Plugin)this.getPlugin(), 200L, 200L);
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        this.timeleft.put(e.getPlayer().getName(), this.period);
    }

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent e) {
        String name = e.getPlayer().getName();
        if (this.code.containsKey(name) && this.code.get(name).equals(e.getMessage())) {
            this.code.remove(name);
            this.timeleft.put(name, this.period);
            e.setCancelled(true);
            e.getPlayer().sendMessage(this.checkMes.replaceAll("&", "§").replace("%player%", name));
        }
    }

    private String randomCode() {
        return String.valueOf((int)((Math.random() * 9.0 + 1.0) * 1000.0));
    }

}

