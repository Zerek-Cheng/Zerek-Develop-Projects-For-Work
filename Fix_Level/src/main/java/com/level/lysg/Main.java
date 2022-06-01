package com.level.lysg;

import me.clip.placeholderapi.external.EZPlaceholderHook;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class Main extends JavaPlugin implements Listener {
    public static Main INSTANCE;
    public static HashMap<String, Integer> level_dj;
    public static HashMap<String, Integer> level_xp;
    public static HashMap<Integer, Integer> level_yq;

    static {
        Main.level_dj = new HashMap<>();
        Main.level_xp = new HashMap<>();
        Main.level_yq = new HashMap<>();
    }

    public void onEnable() {
        INSTANCE = this;
        this.getLogger().info("\u300c\u63a5\u63d2\u4ef6\u5b9a\u5236\u300d \u9ad8\u54c1\u8d28\uff0c\u9ad8\u6548\u7387\uff0c\u4f4e\u4ef7\u683c\uff0c\u9ad8\u8bda\u4fe1\uff01");
        this.getLogger().info("\u300c\u63a5\u63d2\u4ef6\u5b9a\u5236\u300d QQ: 2956319760");
        Bukkit.getPluginManager().registerEvents((Listener) this, (Plugin) this);
        final File file = new File("./plugins/Level", "Cmg.yml");
        if (!file.exists()) {
            this.saveResource("Cmg.yml", true);
        }
        for (int n = 1; n <= getCmg().getInt("level.max"); ++n) {
            Main.level_yq.put(n, getCmg().getInt("level.need." + n));
        }

        new Checker().runTaskTimer(this, 20, 20);

        new EZPlaceholderHook(this, "lysglevel") {
            @Override
            public String onPlaceholderRequest(Player p, String params) {
                return String.valueOf(Main.this.getLevel(p));
            }
        }.hook();
    }

    public static YamlConfiguration getCmg() {
        final File file = new File("./plugins/Level", "Cmg.yml");
        final YamlConfiguration yml = YamlConfiguration.loadConfiguration(file);
        return yml;
    }

    public static void takeExp(final Player p, final int amount) {
        final File file = new File("./plugins/Level/data", String.valueOf(p.getName()) + ".yml");
        final YamlConfiguration yml = YamlConfiguration.loadConfiguration(file);
        Main.level_xp.put(p.getName(), (int) (yml.getDouble("exp") - amount));
        yml.set("exp", (Object) (yml.getDouble("exp") - amount));
        try {
            yml.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void setExp(final Player p, final double s) {
        final File file = new File("./plugins/Level/data", String.valueOf(p.getName()) + ".yml");
        final YamlConfiguration yml = YamlConfiguration.loadConfiguration(file);
        Main.level_xp.put(p.getName(), (int) (yml.getInt("exp") - s));
        yml.set("exp", (Object) s);
        try {
            yml.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void setLevel(final Player p, final int amount) {
        final File file = new File("./plugins/Level/data", String.valueOf(p.getName()) + ".yml");
        final YamlConfiguration yml = YamlConfiguration.loadConfiguration(file);
        Main.level_dj.put(p.getName(), amount);
        yml.set("level", (Object) amount);
        try {
            yml.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void addLevel(final Player p, final int amount) {
        final File file = new File("./plugins/Level/data", String.valueOf(p.getName()) + ".yml");
        final YamlConfiguration yml = YamlConfiguration.loadConfiguration(file);
        if (yml.getInt("level") + amount == getCmg().getInt("level.max")) {
            yml.set("level", (Object) getCmg().getInt("level.max"));
            try {
                yml.save(file);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return;
        }
        Main.level_dj.put(p.getName(), yml.getInt("level") + amount);
        yml.set("level", (Object) (yml.getInt("level") + amount));
        try {
            yml.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void addExp(final Player p, final double amount) {
        final File file = new File("./plugins/Level/data", String.valueOf(p.getName()) + ".yml");
        final YamlConfiguration yml = YamlConfiguration.loadConfiguration(file);
        Main.level_xp.put(p.getName(), (int) (yml.getInt("exp") + amount));
        if (yml.getInt("level") == getCmg().getInt("level.max")) {
            p.sendMessage(getCmg().getString("level.maxmessage").replace("&", "§"));
            return;
        }
        yml.set("exp", (Object) (yml.getInt("exp") + amount));
        try {
            yml.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        p.sendMessage(getCmg().getString("giveexp").replace("&", "§").replace("%a%", new StringBuilder().append(amount).toString()));
        if (getExp(p) == getNext(p)) {
            setExp(p, 0.0);
            addLevel(p, 1);
            p.sendTitle(getCmg().getString("givelevels"), getCmg().getString("givelevelx"));
        } else if (getExp(p) > getNext(p)) {
            final double xp = getExp(p);
            final double next = getNext(p);
            final double s = xp - next;
            setExp(p, s);
            addLevel(p, 1);
            p.sendTitle(getCmg().getString("givelevels").replace("&", "§").replace("%a%", new StringBuilder().append(getLevel(p)).toString()), getCmg().getString("givelevelx").replace("&", "§").replace("%a%", new StringBuilder().append(getLevel(p)).toString()));
        }
    }

    public static double getExp(final Player p) {
        final File file = new File("./plugins/Level/data", String.valueOf(p.getName()) + ".yml");
        final YamlConfiguration yml = YamlConfiguration.loadConfiguration(file);
        return yml.getDouble("exp");
    }

    public static int getLevel(final Player p) {
        final File file = new File("./plugins/Level/data", String.valueOf(p.getName()) + ".yml");
        final YamlConfiguration yml = YamlConfiguration.loadConfiguration(file);
        return yml.getInt("level");
    }

    public static double getJc(final Player p) {
        final File file = new File("./plugins/Level/data", String.valueOf(p.getName()) + ".yml");
        final YamlConfiguration yml = YamlConfiguration.loadConfiguration(file);
        return yml.getDouble("jc");
    }

    public static double getNext(final Player p) {
        final double n = Main.level_yq.get(getLevel(p));
        return n;
    }

    @EventHandler
    public void onj(final PlayerJoinEvent e) {
        final Player p = e.getPlayer();
        final File file = new File("./plugins/Level/data", String.valueOf(p.getName()) + ".yml");
        final YamlConfiguration yml = YamlConfiguration.loadConfiguration(file);
        if (!file.exists()) {
            yml.set("exp", (Object) 0);
            yml.set("level", (Object) 1);
            yml.set("jc", (Object) 0);
            try {
                yml.save(file);
            } catch (IOException e2) {
                e2.printStackTrace();
            }
        }
        Main.level_dj.put(p.getName(), yml.getInt("level"));
        Main.level_xp.put(p.getName(), (int) yml.getDouble("exp"));
    }

    public boolean onCommand(final CommandSender sender, final Command cmd, final String label, final String[] args) {
        if (args.length==1&&args[0].equalsIgnoreCase("reload")){
            reloadConfig();
            sender.sendMessage("reload ok");
            return true;
        }
        if (cmd.getName().equalsIgnoreCase("level")) {
            if (args.length == 0) {
                if (sender.isOp()) {
                    sender.sendMessage("level exp [player] [amount]");
                    sender.sendMessage("level level [player] [amount]");
                    sender.sendMessage("level setlevel [player] [amount]");
                    sender.sendMessage("level see");
                }
            } else {
                if (args[0].equals("exp")) {
                    final String name = args[1];
                    final int type = Integer.parseInt(args[2]) - 1;
                    final Player p = (Player) sender;
                    addExp(p, type);
                }
                if (args[0].equals("level")) {
                    final String name = args[1];
                    final int type = Integer.parseInt(args[2]) - 1;
                    final Player p = (Player) sender;
                    Main.level_dj.put(p.getName(), getLevel(p) + type);
                    addLevel(p, type);
                    p.sendTitle(getCmg().getString("givelevels").replace("&", "§").replace("%a%", new StringBuilder().append(getLevel(p)).toString()), getCmg().getString("givelevelx").replace("&", "§").replace("%a%", new StringBuilder().append(getLevel(p)).toString()));
                }
                if (args[0].equals("setlevel")) {
                    final String name = args[1];
                    final int type = Integer.parseInt(args[2]) - 1;
                    final Player p = (Player) sender;
                    setLevel(p, type);
                }
                if (args[0].equals("see")) {
                    final Player p2 = (Player) sender;
                    final double yq = Integer.valueOf(Main.level_yq.get(getLevel(p2)));
                    p2.sendMessage("§7§m\u4e00\u4e00\u4e00\u4e00\u4e00\u4e00\u4e00\u4e00\u4e00\u4e00\u4e00\u4e00\u4e00\u4e00\u4e00");
                    p2.sendMessage("§c\u7b49\u7ea7: §f" + getLevel(p2));
                    p2.sendMessage("§c\u7ecf\u9a8c: §f" + getExp(p2) + "/" + yq);
                    p2.sendMessage("§e\u7ecf\u9a8c\u52a0\u6210: §f" + getJc(p2));
                    p2.sendMessage("§7§m\u4e00\u4e00\u4e00\u4e00\u4e00\u4e00\u4e00\u4e00\u4e00\u4e00\u4e00\u4e00\u4e00\u4e00\u4e00");
                }
            }
        }
        return true;
    }

    @EventHandler
    public void oned(final EntityDeathEvent e) {
        final Entity en = (Entity) e.getEntity();
        if (en.getCustomName() != null) {
            if (e.getEntity().getKiller() instanceof Player) {
                final Player p = e.getEntity().getKiller();
                final ConfigurationSection name = getCmg().getConfigurationSection("KillMob");
                final Set<String> x = (Set<String>) name.getKeys(false);
                for (final String n : x) {
                    if (n.replace("&", "§").equals(en.getCustomName())) {
                        final int exp = getCmg().getInt("KillMob." + n + ".exp");
                        addExp(p, exp);
                    }
                }
            } else if (e.getEntity().getKiller() instanceof Arrow) {
                final Arrow arrow = (Arrow) e.getEntity().getKiller();
                if (arrow.getShooter() instanceof Player) {
                    final ConfigurationSection name = getCmg().getConfigurationSection("KillMob");
                    final Set<String> x = (Set<String>) name.getKeys(false);
                    for (final String n : x) {
                        if (n.replace("&", "§").equals(en.getCustomName())) {
                            final Player p2 = (Player) arrow.getShooter();
                            final int exp2 = getCmg().getInt("KillMob." + n + ".exp");
                            addExp(p2, exp2);
                        }
                    }
                }
            }
        }
    }

    @EventHandler
    public void oniievw(final PlayerInteractEvent e) {
        final Player p = e.getPlayer();
        if (e.getItem() != null && e.getItem().getType() != Material.AIR) {
            final ItemStack i = e.getItem();
            if (i.getItemMeta().hasLore()) {
                final List<String> l = (List<String>) i.getItemMeta().getLore();
                for (final String d : l) {
                    if (d.contains(getCmg().getString("contains").replace("&", "§"))) {
                        final String con = getCmg().getString("contains").replace("&", "§");
                        final int len = con.length();
                        final int dj = Integer.parseInt(d.substring(len, d.length()));
                        if (dj <= getLevel(p)) {
                            continue;
                        }
                        p.sendMessage(getCmg().getString("NoLevel").replace("&", "§").replace("%a%", new StringBuilder().append(dj).toString()));
                        e.setCancelled(true);
                    }
                }
            }
        }
    }

    @EventHandler
    public void onD(final EntityDamageByEntityEvent e) {
        if (e.getDamager() instanceof Player) {
            final Player p = (Player) e.getDamager();
            if (p.getInventory().getItemInMainHand() == null || p.getInventory().getItemInMainHand().getTypeId() == 0) {
                return;
            }
            final ItemStack i = p.getInventory().getItemInMainHand();
            if (i.getItemMeta().hasLore()) {
                final List<String> l = (List<String>) i.getItemMeta().getLore();
                for (final String d : l) {
                    if (d.contains(getCmg().getString("contains").replace("&", "§"))) {
                        final String con = getCmg().getString("contains").replace("&", "§");
                        final int len = con.length();
                        final int dj = Integer.parseInt(d.substring(len, d.length()));
                        if (dj <= getLevel(p)) {
                            continue;
                        }
                        p.sendMessage(getCmg().getString("NoLevel").replace("&", "§").replace("%a%", new StringBuilder().append(dj).toString()));
                        e.setCancelled(true);
                    }
                }
            }
        } else if (e.getDamager() instanceof Arrow) {
            final Arrow arrow = (Arrow) e.getDamager();
            if (arrow.getShooter() instanceof Player) {
                final Player p2 = (Player) arrow.getShooter();
                if (p2.getInventory().getItemInMainHand() != null && p2.getInventory().getItemInMainHand().getTypeId() != 0) {
                    final ItemStack j = p2.getInventory().getItemInMainHand();
                    if (j.getItemMeta().hasLore()) {
                        final List<String> k = (List<String>) j.getItemMeta().getLore();
                        for (final String d2 : k) {
                            if (d2.contains(getCmg().getString("contains").replace("&", "§"))) {
                                final String con2 = getCmg().getString("contains").replace("&", "§");
                                final int len2 = con2.length();
                                final int dj2 = Integer.parseInt(d2.substring(len2, d2.length()));
                                if (dj2 <= getLevel(p2)) {
                                    continue;
                                }
                                p2.sendMessage(getCmg().getString("NoLevel").replace("&", "§").replace("%a%", new StringBuilder().append(dj2).toString()));
                                e.setCancelled(true);
                            }
                        }
                    }
                }
            }
        }
    }

    @EventHandler
    public void onIc(final InventoryClickEvent e) {
        //checkPlayer((Player) e.getWhoClicked());
        /*

        final Player p = (Player) e.getWhoClicked();
        ItemStack item = e.getCurrentItem();
        if (e.getClick().equals(ClickType.NUMBER_KEY)) {
            e.setCancelled(true);
            return;
        }
        if (item != null && item.getType() != Material.AIR) {
            final ItemStack i = item;
            if (i.getItemMeta().hasLore()) {
                final List<String> l = (List<String>) i.getItemMeta().getLore();
                for (final String d : l) {
                    if (d.contains(getCmg().getString("contains").replace("&", "§"))) {
                        final String con = getCmg().getString("contains").replace("&", "§");
                        final int len = con.length();
                        final int dj = Integer.parseInt(d.substring(len, d.length()));
                        if (dj <= getLevel(p)) {
                            continue;
                        }
                        p.sendMessage(getCmg().getString("NoLevel").replace("&", "§").replace("%a%", new StringBuilder().append(dj).toString()));
                        *//*p.getInventory().setItemInMainHand(new ItemStack(0));
                        p.getInventory().addItem(new ItemStack[]{i});*//*
                        e.setCancelled(true);
                    }
                }
            }
        }


        if (1 == 1) return;
        if (p.getInventory().getItemInMainHand() != null && p.getInventory().getItemInMainHand().getTypeId() != 0) {
            final ItemStack i = p.getInventory().getItemInMainHand();
            if (i.getItemMeta().hasLore()) {
                final List<String> l = (List<String>) i.getItemMeta().getLore();
                for (final String d : l) {
                    if (d.contains(getCmg().getString("contains").replace("&", "§"))) {
                        final String con = getCmg().getString("contains").replace("&", "§");
                        final int len = con.length();
                        final int dj = Integer.parseInt(d.substring(len, d.length()));
                        if (dj <= getLevel(p)) {
                            continue;
                        }
                        p.sendMessage(getCmg().getString("NoLevel").replace("&", "§").replace("%a%", new StringBuilder().append(dj).toString()));
                        *//*p.getInventory().setItemInMainHand(new ItemStack(0));
                        p.getInventory().addItem(new ItemStack[]{i});*//*
                        e.setCancelled(true);
                    }
                }
            }
        }
        if (p.getInventory().getItemInOffHand() != null && p.getInventory().getItemInOffHand().getTypeId() != 0) {
            final ItemStack i = p.getInventory().getItemInOffHand();
            if (i.getItemMeta().hasLore()) {
                final List<String> l = (List<String>) i.getItemMeta().getLore();
                for (final String d : l) {
                    if (d.contains(getCmg().getString("contains").replace("&", "§"))) {
                        final String con = getCmg().getString("contains").replace("&", "§");
                        final int len = con.length();
                        final int dj = Integer.parseInt(d.substring(len, d.length()));
                        if (dj <= getLevel(p)) {
                            continue;
                        }
                        p.sendMessage(getCmg().getString("NoLevel").replace("&", "§").replace("%a%", new StringBuilder().append(dj).toString()));
                        *//*p.getInventory().setItemInMainHand(new ItemStack(0));
                        p.getInventory().addItem(new ItemStack[]{i});*//*
                        e.setCancelled(true);
                    }
                }
            }
        }
        if (p.getInventory().getHelmet() != null && p.getInventory().getHelmet().getTypeId() != 0) {
            final ItemStack i = p.getInventory().getHelmet();
            if (i.getItemMeta().hasLore()) {
                final List<String> l = (List<String>) i.getItemMeta().getLore();
                for (final String d : l) {
                    if (d.contains(getCmg().getString("contains").replace("&", "§"))) {
                        final String con = getCmg().getString("contains").replace("&", "§");
                        final int len = con.length();
                        final int dj = Integer.parseInt(d.substring(len, d.length()));
                        if (dj <= getLevel(p)) {
                            continue;
                        }
                        p.sendMessage(getCmg().getString("NoLevel").replace("&", "§").replace("%a%", new StringBuilder().append(dj).toString()));
                        *//*p.getInventory().setItemInMainHand(new ItemStack(0));
                        p.getInventory().addItem(new ItemStack[]{i});*//*
                        e.setCancelled(true);
                    }
                }
            }
        }
        if (p.getInventory().getChestplate() != null && p.getInventory().getChestplate().getTypeId() != 0) {
            final ItemStack i = p.getInventory().getChestplate();
            if (i.getItemMeta().hasLore()) {
                final List<String> l = (List<String>) i.getItemMeta().getLore();
                for (final String d : l) {
                    if (d.contains(getCmg().getString("contains").replace("&", "§"))) {
                        final String con = getCmg().getString("contains").replace("&", "§");
                        final int len = con.length();
                        final int dj = Integer.parseInt(d.substring(len, d.length()));
                        if (dj <= getLevel(p)) {
                            continue;
                        }
                        p.sendMessage(getCmg().getString("NoLevel").replace("&", "§").replace("%a%", new StringBuilder().append(dj).toString()));
                        *//*p.getInventory().setItemInMainHand(new ItemStack(0));
                        p.getInventory().addItem(new ItemStack[]{i});*//*
                        e.setCancelled(true);
                    }
                }
            }
        }
        if (p.getInventory().getLeggings() != null && p.getInventory().getLeggings().getTypeId() != 0) {
            final ItemStack i = p.getInventory().getLeggings();
            if (i.getItemMeta().hasLore()) {
                final List<String> l = (List<String>) i.getItemMeta().getLore();
                for (final String d : l) {
                    if (d.contains(getCmg().getString("contains").replace("&", "§"))) {
                        final String con = getCmg().getString("contains").replace("&", "§");
                        final int len = con.length();
                        final int dj = Integer.parseInt(d.substring(len, d.length()));
                        if (dj <= getLevel(p)) {
                            continue;
                        }
                        p.sendMessage(getCmg().getString("NoLevel").replace("&", "§").replace("%a%", new StringBuilder().append(dj).toString()));
                        *//*p.getInventory().setItemInMainHand(new ItemStack(0));
                        p.getInventory().addItem(new ItemStack[]{i});*//*
                        e.setCancelled(true);
                    }
                }
            }
        }
        if (p.getInventory().getBoots() != null && p.getInventory().getBoots().getTypeId() != 0) {
            final ItemStack i = p.getInventory().getBoots();
            if (i.getItemMeta().hasLore()) {
                final List<String> l = (List<String>) i.getItemMeta().getLore();
                for (final String d : l) {
                    if (d.contains(getCmg().getString("contains").replace("&", "§"))) {
                        final String con = getCmg().getString("contains").replace("&", "§");
                        final int len = con.length();
                        final int dj = Integer.parseInt(d.substring(len, d.length()));
                        if (dj <= getLevel(p)) {
                            continue;
                        }
                        p.sendMessage(getCmg().getString("NoLevel").replace("&", "§").replace("%a%", new StringBuilder().append(dj).toString()));
                        *//*p.getInventory().setItemInMainHand(new ItemStack(0));
                        p.getInventory().addItem(new ItemStack[]{i});*//*
                        e.setCancelled(true);
                    }
                }
            }
        }*/
    }

    public int getItemLevel(ItemStack itemStack) {
        if (itemStack == null || itemStack.getType().equals(Material.AIR) || !itemStack.hasItemMeta() || !itemStack.getItemMeta().hasLore())
            return 0;
        final ItemStack i = itemStack;
        if (i.getItemMeta().hasLore()) {
            final List<String> l = i.getItemMeta().getLore();
            for (final String d : l) {
                if (d.contains(getCmg().getString("contains").replace("&", "§"))) {
                    final String con = getCmg().getString("contains").replace("&", "§");
                    final int len = con.length();
                    final int dj = Integer.parseInt(d.substring(len));
                    return dj;
                }
            }
        }
        return 0;
    }

    public synchronized void checkPlayer(Player p) {
        int level = getLevel(p);
        EntityEquipment eq = p.getEquipment();
        List<ItemStack> drops = new ArrayList<>();
        if (getItemLevel(eq.getHelmet()) > level) {
            drops.add(eq.getHelmet());
            eq.setHelmet(null);
        }

        if (getItemLevel(eq.getLeggings()) > level) {
            drops.add(eq.getLeggings());
            eq.setLeggings(null);
        }

        if (getItemLevel(eq.getBoots()) > level) {
            drops.add(eq.getBoots());
            eq.setBoots(null);
        }

        if (getItemLevel(eq.getChestplate()) > level) {
            drops.add(eq.getChestplate());
            eq.setChestplate(null);
        }
        drops.forEach(itemStack -> {
            if (itemStack == null) return;
            p.getWorld().dropItem(p.getLocation(), itemStack);
        });
        if (drops.size()>0){
            p.sendMessage("&8&l[&c&l!&8&l] &f使用的装备等级不足,已移除".replace("&","§"));
            p.updateInventory();
        }
    }


}
