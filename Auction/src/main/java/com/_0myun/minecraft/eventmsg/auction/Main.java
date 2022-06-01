package com._0myun.minecraft.eventmsg.auction;

import com._0myun.minecraft.eventmsg.auction.inv.UiHolder;
import com._0myun.minecraft.eventmsg.auction.inv.UiListener;
import com._0myun.minecraft.eventmsg.auction.pay.PayType;
import lombok.Getter;
import lombok.Setter;
import lombok.var;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Level;

public final class Main extends JavaPlugin {
    @Setter
    @Getter
    private static Main plugin;
    public static Economy economy;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        Main.setPlugin(this);
        Main.economy = Main.getPlugin().getServer().getServicesManager().getRegistration(net.milkbowl.vault.economy.Economy.class).getProvider();
        Bukkit.getPluginManager().registerEvents(new UiListener(), this);
        new BukkitRunnable() {
            @Override
            public void run() {
                try {
                    if (!AuctionManager.isStart()) return;
                    long finfishTime = AuctionManager.getFinfishTime();
                    if (finfishTime == -1) return;
                    if (System.currentTimeMillis() >= AuctionManager.getFinfishTime()) AuctionManager.clinch();
                } catch (Exception e) {
                    e.printStackTrace();
                    getLogger().log(Level.WARNING, "拍卖行发货异常...");
                }
            }
        }.runTaskTimer(this, 24, 24);
    }

    @Override
    public void onDisable() {
        AuctionManager.stop();
        saveConfig();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(LangUtil.getLang("lang1"));
            return true;
        }
        Player p = (Player) sender;
        if (args.length == 0) {
            if (!AuctionManager.isStart()) {
                p.sendMessage(LangUtil.getLang("lang2"));
                return true;
            }
            p.openInventory(getAuctionUi(p));
            new BukkitRunnable() {
                @Override
                public void run() {
                    if (!p.isOnline() || !AuctionManager.isStart()) {
                        p.sendMessage(LangUtil.getLang("lang21"));
                        p.closeInventory();
                        this.cancel();
                        Bukkit.getScheduler().cancelTask(getTaskId());
                        return;
                    }
                    InventoryView invView = p.getOpenInventory();
                    if (invView != null) {
                        Inventory topInv = invView.getTopInventory();
                        if (!(topInv.getHolder() instanceof UiHolder)) {
                            this.cancel();
                            return;
                        }
                        if (topInv != null) topInv.setContents(getAuctionUi(p).getContents());
                    }
                }
            }.runTaskTimer(this, 24, 24);
            return true;
        } else if (args.length == 3 && args[0].equalsIgnoreCase("add") && sender.isOp()) {
            int type = Integer.valueOf(args[1]);
            int startPrice = Integer.valueOf(args[2]);
            if (!PayType.exist(type)) {
                p.sendMessage(LangUtil.getLang("lang3"));
                return true;
            }
            ItemStack itemInHand = p.getItemInHand();
            if (itemInHand == null || itemInHand.getType() == Material.AIR) {
                p.sendMessage(LangUtil.getLang("lang4"));
                return true;
            }
            AuctionManager.addItem(itemInHand, type, startPrice);
            p.sendMessage(LangUtil.getLang("lang5") + PayType.get(type).getName());
            return true;
        } else if (args.length == 1 && args[0].equalsIgnoreCase("start")) {
            if (AuctionManager.isStart()) {
                p.sendMessage(LangUtil.getLang("lang6"));
                return true;
            }
            if (AuctionManager.getNowItem() == null) {
                p.sendMessage(LangUtil.getLang("lang7"));
                return true;
            }
            if (AuctionManager.getNowPrice() != -1) AuctionManager.setFinishTime();
            AuctionManager.start();
            p.sendMessage(LangUtil.getLang("lang8"));
            return true;
        } else if (args.length == 1 && args[0].equalsIgnoreCase("stop")) {
            AuctionManager.stop();
            this.getConfig().set("over", -1);
            p.sendMessage(LangUtil.getLang("lang9"));
            return true;
        } else if (args.length == 1 && args[0].equalsIgnoreCase("save")) {
            this.saveConfig();
            p.sendMessage(LangUtil.getLang("lang10"));
            return true;
        } else if (args.length == 1 && args[0].equalsIgnoreCase("receive")) {
            AuctionManager.receive(p);
            return true;
        }

        return true;
    }

    public Inventory getAuctionUi(OfflinePlayer p) {
        ItemStack nowItem = AuctionManager.getNowItem();
        if (nowItem == null) {
            return null;
        }
        UiHolder holder = new UiHolder();
        Inventory inv = Bukkit.createInventory(holder, 45, this.getConfig().getString("ui.title"));
        holder.setInv(inv);

        inv.setItem(22, nowItem);//当前物品
        if (AuctionManager.hasNext()) inv.setItem(24, AuctionManager.getNextItem());//下一个物品
        inv.setItem(17, AuctionManager.getInfoItem(p));

        PayType payType = AuctionManager.getNowPayType();
        Iterator<Map.Entry<Integer, Integer>> iter = payType.getPremiums().entrySet().iterator();

        String[] premiumItemIds = getConfig().getString("premiumItemId").split(":");
        ItemStack addButton = new ItemStack(Integer.valueOf(premiumItemIds[0]));
        addButton.setDurability(premiumItemIds.length == 2 ? Short.valueOf(premiumItemIds[1]) : 0);

        while (iter.hasNext()) {
            Map.Entry<Integer, Integer> next = iter.next();
            Integer key = next.getKey();
            Integer value = next.getValue();
            ItemStack clone = addButton.clone();
            ItemMeta itemMeta = clone.getItemMeta();
            itemMeta.setDisplayName(LangUtil.getLang("lang11") + value + payType.getName());
            clone.setItemMeta(itemMeta);
            inv.setItem(37 + key, clone);
        }


        var wallList = Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 18, 26, 27, 35, 36, 44);
        wallList.forEach((i) -> {
            inv.setItem(i, AuctionManager.getWallItem());
        });
        return inv;
    }
}
