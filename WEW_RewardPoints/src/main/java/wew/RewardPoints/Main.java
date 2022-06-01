//
// Decompiled by Procyon v0.5.30
//

package wew.RewardPoints;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Main extends JavaPlugin implements Listener {
    private List<ItemStack> items;
    private HashMap<ItemStack, ItemStack> item_item;
    private HashMap<ItemStack, List<String>> item_cmds;
    private HashMap<ItemStack, String> item_rcmds;
    private HashMap<ItemStack, Integer> item_need;
    private HashMap<ItemStack, Integer> item_slot;
    private HashMap<ItemStack, List<String>> item_player;
    private HashMap<ItemStack, String> item_key;

    public Main() {
        this.items = new ArrayList<>();
        this.item_item = new HashMap<>();
        this.item_cmds = new HashMap<>();
        this.item_rcmds = new HashMap<>();
        this.item_need = new HashMap<>();
        this.item_slot = new HashMap<>();
        this.item_player = new HashMap<>();
        this.item_key = new HashMap<>();
    }

    @Override
    public void onEnable() {
        if (!new File("./plugins/RewardPoints/config.yml").exists()) {
            this.saveDefaultConfig();
        }
        this.setUp();
        new Expansion().register();
        this.getServer().getPluginManager().registerEvents(this, this);
        this.getLogger().info("成功启动！");
    }

    @Override
    public void onDisable() {
        final FileConfiguration config = this.getConfig();
        for (final ItemStack arg : this.items) {
            config.set(this.item_key.get(arg) + ".players", this.item_player.get(arg));
        }
        try {
            config.save(new File("./plugins/RewardPoints/config.yml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.getLogger().info("成功保存！");
    }

    @Override
    public boolean onCommand(@NotNull final CommandSender sender, @NotNull final Command cmd, final String label, final String[] args) {
        if (cmd.getName().equalsIgnoreCase("rewardpoints")) {
            final int line = (this.items.size() % 9 != 0) ? (this.items.size() - this.items.size() % 9 + 9) : this.items.size();
            final Inventory in = Bukkit.createInventory((InventoryHolder) sender, (line == 0) ? 9 : line, "§5[累计充值赢取礼包]");
            final Player p = (Player) sender;
            for (final ItemStack arg : this.items) {
                if (this.item_player.get(arg).contains(p.getName())) {
                    in.setItem(this.item_slot.get(arg), this.toShowPoints(this.item_item.get(arg).clone(), p));
                } else {
                    in.setItem(this.item_slot.get(arg), this.toShowPoints(arg.clone(), p));
                }
            }
            ((Player) sender).openInventory(in);
            return true;
        }
        return false;
    }

    @EventHandler
    public void onClick(@NotNull final InventoryClickEvent e) {
        final String title = e.getInventory().getTitle();
        final Player p = (Player) e.getWhoClicked();
        if (title.contains("[累计充值赢取礼包]")) {
            e.setCancelled(true);
            final ItemStack ite = e.getCurrentItem();
            if (ite == null || ite.getType() == Material.AIR || this.item_cmds.get(ite) == null) {
                return;
            }
            final ItemStack item = this.toNotShow(ite.clone(), p);
            final int need = this.item_need.get(item);
            if (e.getClick() == ClickType.LEFT) {
                if (wew.oscar_wen.Main.getPoints(p) >= need) {
                    for (final String arg : this.item_cmds.get(item)) {
                        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), arg.replace("%player%", p.getName()));
                    }
                    this.item_player.get(item).add(p.getName());
                    e.getInventory().setItem(e.getSlot(), this.item_item.get(item));
                }
            } else if (e.getClick() == ClickType.RIGHT) {
                p.closeInventory();
                Bukkit.getScheduler().runTaskLater(this, new Runnable() {
                    @Override
                    public void run() {
                        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), Main.this.item_rcmds.get(item).replace("%player%", p.getName()));
                    }
                }, 10L);
            }
        }
    }

    public void setUp() {
        final FileConfiguration config = this.getConfig();
        for (final String arg : config.getKeys(false)) {
            final ItemStack item = new ItemStack(config.getInt(arg + ".qian.id"), 1, (short) 0, (byte) config.getInt(arg + ".qian.data"));
            final ItemMeta meta = item.getItemMeta();
            meta.setDisplayName(config.getString(arg + ".qian.name"));
            meta.setLore(config.getStringList(arg + ".qian.lore"));
            item.setItemMeta(meta);
            this.items.add(item);
            this.item_cmds.put(item, config.getStringList(arg + ".qian.commands"));
            this.item_rcmds.put(item, config.getString(arg + ".qian.rightcmd"));
            this.item_need.put(item, config.getInt(arg + ".need"));
            this.item_slot.put(item, config.getInt(arg + ".qian.slot"));
            this.item_player.put(item, (config.getStringList(arg + ".players") == null) ? new ArrayList<String>() : config.getStringList(arg + ".players"));
            this.item_key.put(item, arg);
            final ItemStack item2 = new ItemStack(config.getInt(arg + ".hou.id"), 1, (short) 0, (byte) config.getInt(arg + ".hou.data"));
            final ItemMeta meta2 = item2.getItemMeta();
            meta2.setDisplayName(config.getString(arg + ".hou.name"));
            meta2.setLore(config.getStringList(arg + ".hou.lore"));
            item2.setItemMeta(meta2);
            this.item_item.put(item, item2);
        }
    }

    @NotNull ItemStack toShowPoints(@NotNull final ItemStack item, @NotNull final Player p) {
        final ItemMeta meta = item.getItemMeta();
        final List<String> lores = meta.getLore();
        for (int i = 0; i < lores.size(); ++i) {
            lores.set(i, lores.get(i).replace("%points_own%", new StringBuilder().append(wew.oscar_wen.Main.getPoints(p)).toString()));
        }
        meta.setLore(lores);
        item.setItemMeta(meta);
        return item;
    }

    @NotNull ItemStack toNotShow(@NotNull final ItemStack item, @NotNull final Player p) {
        final ItemMeta meta = item.getItemMeta();
        final List<String> lores = meta.getLore();
        for (int i = 0; i < lores.size(); ++i) {
            lores.set(i, lores.get(i).replace("§c" + wew.oscar_wen.Main.getPoints(p) + "§7/", "§c%points_own%§7/"));
        }
        meta.setLore(lores);
        item.setItemMeta(meta);
        return item;
    }
}
