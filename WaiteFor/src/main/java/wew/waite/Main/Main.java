/*
 * Decompiled with CFR 0_133.
 *
 * Could not load the following classes:
 *  com.herocraftonline.heroes.Heroes
 *  com.herocraftonline.heroes.characters.CharacterManager
 *  com.herocraftonline.heroes.characters.Hero
 *  com.herocraftonline.heroes.characters.classes.HeroClass
 *  net.milkbowl.vault.economy.Economy
 *  net.milkbowl.vault.economy.EconomyResponse
 *  net.milkbowl.vault.permission.Permission
 *  org.bukkit.Bukkit
 *  org.bukkit.Location
 *  org.bukkit.OfflinePlayer
 *  org.bukkit.Server
 *  org.bukkit.command.Command
 *  org.bukkit.command.CommandSender
 *  org.bukkit.entity.Player
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.Listener
 *  org.bukkit.event.player.PlayerMoveEvent
 *  org.bukkit.event.player.PlayerQuitEvent
 *  org.bukkit.plugin.Plugin
 *  org.bukkit.plugin.PluginManager
 *  org.bukkit.plugin.RegisteredServiceProvider
 *  org.bukkit.plugin.ServicesManager
 *  org.bukkit.plugin.java.JavaPlugin
 *  org.bukkit.potion.PotionEffect
 *  org.bukkit.potion.PotionEffectType
 *  org.bukkit.scheduler.BukkitTask
 */
package wew.waite.Main;

import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Main
        extends JavaPlugin
        implements Listener {
    public static Economy econ = null;
    public static Permission perm = null;
    private static List<Player> playerslist = new ArrayList<Player>();
    private static HashMap<Player, Integer> players = new HashMap();

    public void onEnable() {
        this.getServer().getPluginManager().registerEvents((Listener) this, (Plugin) this);
        RegisteredServiceProvider rsp = this.getServer().getServicesManager().getRegistration(Economy.class);
        RegisteredServiceProvider rsp1 = this.getServer().getServicesManager().getRegistration(Permission.class);
        econ = (Economy) rsp.getProvider();
        perm = (Permission) rsp1.getProvider();
        this.start();
        this.getLogger().info("运行成功！");
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("guaji") && sender instanceof Player && !playerslist.contains((Object) ((Player) sender))) {
            sender.sendMessage("§a[系统] §6您已进入挂机模式,再次移动可取消挂机状态");
            playerslist.add((Player) sender);
            players.put((Player) sender, 0);
            return true;
        }
        return false;
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        Player p = e.getPlayer();
        this.award(p);
    }

    @EventHandler
    public void onMove(PlayerMoveEvent e) {
        Player p = e.getPlayer();
        if (e.getTo().distance(e.getFrom()) == 0.0) {
            return;
        }
        this.award(p);
    }

    void award(Player p) {
        if (playerslist.contains((Object) p)) {
            int min = players.get((Object) p) / 60;
            int money = 25 * min;
            int exp = 1 * min;
            if (perm.getPrimaryGroup(p).equalsIgnoreCase("S") || perm.getPrimaryGroup(p).equalsIgnoreCase("SS")) {
                money = 50 * min;
                exp = 3 * min;
            } else if (perm.getPrimaryGroup(p).equalsIgnoreCase("SSS")) {
                money = 100 * min;
                exp = 5 * min;
            }
            econ.depositPlayer((OfflinePlayer) p, (double) money);
            p.sendMessage("§a[系统] §d您本次挂机了§c" + min + "§d分钟,获得了奖励: §3(" + money + "金币) §5(" + exp + "经验)");
            playerslist.remove((Object) p);
            players.remove((Object) p);
            p.removePotionEffect(PotionEffectType.INVISIBILITY);
        }
    }

    void start() {
        Bukkit.getScheduler().runTaskTimer((Plugin) this, new Runnable() {

            @Override
            public void run() {
                for (Player p : playerslist) {
                    p.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 72000, 0));
                    players.put(p, (Integer) players.get((Object) p) + 5);
                    p.sendTitle("§a[您已持续挂机了 §b" + (Integer) players.get((Object) p) / 60 + " §a分钟]", "§d移动可退出挂机模式,并领取挂机奖励");
                }
            }
        }, 0L, 100L);
    }

}

