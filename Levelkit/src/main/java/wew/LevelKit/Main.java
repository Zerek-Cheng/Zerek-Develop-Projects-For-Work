/*
 * Decompiled with CFR 0_133.
 *
 * Could not load the following classes:
 *  org.bukkit.Bukkit
 *  org.bukkit.Material
 *  org.bukkit.Server
 *  org.bukkit.command.Command
 *  org.bukkit.command.CommandSender
 *  org.bukkit.configuration.file.FileConfiguration
 *  org.bukkit.entity.Player
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.Listener
 *  org.bukkit.event.player.PlayerInteractEvent
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.inventory.PlayerInventory
 *  org.bukkit.inventory.meta.ItemMeta
 *  org.bukkit.plugin.Plugin
 *  org.bukkit.plugin.PluginManager
 *  org.bukkit.plugin.java.JavaPlugin
 */
package wew.LevelKit;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class Main
        extends JavaPlugin
        implements Listener {
    private static HashMap<ItemStack, List<String>> kitcmd = new HashMap();
    private static HashMap<ItemStack, Integer> kitlevel = new HashMap();
    private static List<ItemStack> kit = new ArrayList<ItemStack>();

    public void onEnable() {
        if (!new File("./plugins/LevelKit/config.yml").exists()) {
            this.saveDefaultConfig();
        }
        this.setUp();
        this.getServer().getPluginManager().registerEvents((Listener)this, (Plugin)this);
        this.getLogger().info("等级礼包成功运行！");
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("wewlevelkit") && args.length == 2 && sender.hasPermission("wewlevelkit.give")) {
            Player receive = Bukkit.getPlayer((String)args[0]);
            int kits = Integer.parseInt(args[1]);
            ItemStack item = kit.get(kits - 1).clone();
            ItemMeta meta = item.getItemMeta();
            List lore = item.getItemMeta().getLore();
            int i = 0;
            while (i < lore.size()) {
                lore.set(i, ((String)lore.get(i)).replace("%player%", receive.getName()));
                ++i;
            }
            meta.setLore(lore);
            item.setItemMeta(meta);
            receive.getInventory().addItem(new ItemStack[]{item});
        }
        return false;
    }

    @EventHandler
    public void onClick(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        ItemStack item = p.getItemInHand();
        if (item != null && item.getType() == Material.ENDER_CHEST && item.hasItemMeta() && item.getItemMeta().hasDisplayName()) {
            for (ItemStack arg : kit) {
                if (!item.getItemMeta().getDisplayName().equals(arg.getItemMeta().getDisplayName())) continue;
                List<String> lores = item.getItemMeta().getLore();
                String user = "";
                int level = kitlevel.get((Object)arg);
                int nowlevel = p.getLevel();
                List<String> cmds = kitcmd.get((Object)arg);
                for (String lore : lores) {
                    if (!lore.contains("拥有者:")) continue;
                    user = lore.replaceAll("§.", "").split(": ")[1];
                    break;
                }
                if (user.equals(p.getName())) {
                    if (nowlevel >= level) {
                        for (String cmd : cmds) {
                            Bukkit.dispatchCommand((CommandSender)Bukkit.getConsoleSender(), (String)cmd.replace("%player%", p.getName()));
                        }
                        if (kit.indexOf((Object)arg) + 1 < kit.size()) {
                            p.setItemInHand(new ItemStack(Material.AIR));
                            Bukkit.dispatchCommand((CommandSender)Bukkit.getConsoleSender(), (String)("wewlevelkit " + p.getName() + " " + (kit.indexOf((Object)arg) + 2)));
                            return;
                        }
                        p.setItemInHand(new ItemStack(Material.AIR));
                        return;
                    }
                    p.sendMessage("§a[系统] §b你的等级不满足开启该礼盒的要求！");
                    return;
                }
                p.sendMessage("§a[系统] §b这个等级成长礼盒不是你的,无法使用");
                return;
            }
        }
    }

    void setUp() {
        FileConfiguration config = this.getConfig();
        for (String arg : config.getKeys(false)) {
            ItemStack item = new ItemStack(Material.ENDER_CHEST);
            ItemMeta meta = item.getItemMeta();
            meta.setDisplayName(String.valueOf(config.getString(new StringBuilder(String.valueOf(arg)).append(".name").toString())) + " §1§5§3");
            meta.setLore(config.getStringList(String.valueOf(arg) + ".lore"));
            item.setItemMeta(meta);
            kit.add(item);
            kitcmd.put(item, config.getStringList(String.valueOf(arg) + ".commands"));
            kitlevel.put(item, config.getInt(String.valueOf(arg) + ".level"));
        }
    }
}

