package com._0myun.minecraft.netease.shopgodsender;

import com._0myun.minecraft.netease.shopgodsender.api.ShopApi;
import com._0myun.minecraft.netease.shopgodsender.bin.Order;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Main extends JavaPlugin {
    public HashMap<String, FileConfiguration> pluginConfig = new HashMap<>();
    private ConcurrentLinkedQueue<Order> deliverQueue = new ConcurrentLinkedQueue<>();
    private List<Long> delivered = new ArrayList<>();
    private static Main plugin;

    public static Main getPlugin() {
        return plugin;
    }

    @Override
    public void onEnable() {
        plugin = this;
        this.loadConfig();
        Bukkit.getMessenger().registerOutgoingPluginChannel(this, "storemod");
        Bukkit.getMessenger().registerIncomingPluginChannel(this, "storemod",
                new NeteaseMessageListener());
        FileConfiguration config = this.pluginConfig.get("Config");
        ShopApi.setGameId(config.getString("GameId"));
        ShopApi.setKey(config.getString("Key"));

        new BukkitRunnable() {
            @Override
            public void run() {
                if (Main.this.deliverQueue.isEmpty()) {
                    return;
                }
                Order order = Main.this.deliverQueue.poll();
                OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(order.getUuid());
                if (!offlinePlayer.isOnline() || Main.this.delivered.contains(order.getOrderid())) {
                    return;
                }
                Player p = offlinePlayer.getPlayer();
                int needGrid = Main.this.getNeedGrid(order.getItem_id());
                int hasGrid = Main.this.getEmpty(p.getInventory());
                if (needGrid > hasGrid) {
                    p.sendMessage(LangUtil.getLang("lang10").replace("<needSpace>", String.valueOf(needGrid)));
                    return;
                }
                p.sendMessage(LangUtil.getLang("lang11").replace("<orderid>", String.valueOf(order.getOrderid())));
                List<String> cmds = Arrays.asList(order.getCmd().split("\n"));
                boolean isOp = p.isOp();
                try {
                    if (!isOp) {
                        p.setOp(true);
                    }
                    cmds.forEach((cmd) -> {
                        p.performCommand(cmd.replace("<player>", p.getName()));
                    });
                } finally {
                    if (!isOp) {
                        p.setOp(false);
                    }
                }
                Main.this.delivered.add(order.getOrderid());
                ShopApi.shipOrder(p.getUniqueId(), Arrays.asList(new Long[]{Long.valueOf(order.getOrderid())}));
                System.out.println("订单" + order.getOrderid() + "发货成功");

            }
        }.runTaskTimer(this, 24, 24);
    }

    public void loadConfig() {
        saveResource("Config.yml", false);
        this.pluginConfig.put("Config", YamlConfiguration.loadConfiguration(new File(getDataFolder().getPath() + "/Config.yml")));
        saveResource("Lang.yml", false);
        this.pluginConfig.put("Lang", YamlConfiguration.loadConfiguration(new File(getDataFolder().getPath() + "/Lang.yml")));
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if ((args.length == 0 || (args.length > 0 && args[0].equalsIgnoreCase("help"))) && sender.isOp()) {
            sender.sendMessage(LangUtil.getLang("lang1"));
            return true;
        }
        if (args.length > 0 && args[0].equalsIgnoreCase("reload") && sender.isOp()) {
            this.loadConfig();
            sender.sendMessage(LangUtil.getLang("lang2"));
            return true;
        }
        if (args.length > 1 && args[0].equalsIgnoreCase("list") && sender.isOp()) {
            int index = 1;
            String pName = args[1];
            OfflinePlayer p = Bukkit.getOfflinePlayer(pName);
            if (p == null || p.getUniqueId() == null) {
                sender.sendMessage(LangUtil.getLang("lang4"));
                return true;
            }
            if (args.length > 2) {
                index = Integer.valueOf(args[2]);
            }
            this.printOrderList(sender, p.getUniqueId(), index);
            return true;
        }
        if (!(sender instanceof Player)) {
            sender.sendMessage(LangUtil.getLang("lang3"));
            return true;
        }
        Player p = (Player) sender;
        if (args.length > 0 && args[0].equalsIgnoreCase("delivery")) {
            this.delivery(p);
            return true;
        }
        sender.sendMessage(LangUtil.getLang("lang3"));
        return true;
    }

    public void printOrderList(CommandSender sender, UUID uuid, int iN) {
        List<Order> orders = ShopApi.requestOrders(uuid);
        if (orders == null) {
            sender.sendMessage(LangUtil.getLang("lang7"));
            sender.sendMessage("可能是玩家不存在(没有进入过本服)");
            return;
        }
        if (orders.size() == 0) {
            sender.sendMessage(LangUtil.getLang("lang8"));
        }
        if (((iN - 1) * 10) > orders.size()) {
            sender.sendMessage(LangUtil.getLang("lang6"));
            iN = orders.size() / 10;
            iN++;
        }
        orders = orders.subList((iN - 1) * 10, iN * 10 > orders.size() ? orders.size() : iN * 10);
        String printStr = "";
        for (Order order : orders) {
            String template = LangUtil.getLang("lang5");
            template = template.replace("<item_id>", String.valueOf(order.getItem_id()));
            template = template.replace("<uuid>", String.valueOf(order.getUuid()));
            template = template.replace("<item_num>", String.valueOf(order.getItem_num()));
            template = template.replace("<orderid>", String.valueOf(order.getOrderid()));
            template = template.replace("<cmd>", String.valueOf(order.getCmd()));
            template = template.replace("<buy_time>", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(Long.valueOf(order.getBuy_time()) * 1000l)));
            template = template.replace("<group>", String.valueOf(order.getGroup()));
            printStr += template + "\n";
        }
        sender.sendMessage(printStr);
    }

    public void delivery(Player p) {
        List<Order> orders = ShopApi.requestOrders(p.getUniqueId());
        if (orders.size() == 0) {
            p.sendMessage(LangUtil.getLang("lang8"));
            return;
        }
        this.deliverQueue.addAll(orders);
        p.sendMessage(LangUtil.getLang("lang9"));
    }

    public int getNeedGrid(long item_id) {
        return this.pluginConfig.get("Config").getInt("needGrid." + item_id);
    }

    public int getEmpty(Inventory inv) {
        if (inv == null) {
            return 0;
        }
        int empty = 0;
        for (int i = 35; i >= 9; i--) {
            ItemStack item = inv.getItem(i);
            if (item == null || item.getType() == Material.AIR) {
                empty++;
            }
        }
        return empty;
    }
}
