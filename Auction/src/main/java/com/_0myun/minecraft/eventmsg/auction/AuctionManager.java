package com._0myun.minecraft.eventmsg.auction;

import com._0myun.minecraft.eventmsg.auction.pay.PayType;
import com.comphenix.protocol.utility.StreamSerializer;
import org.black_ixx.playerpoints.PlayerPoints;
import org.black_ixx.playerpoints.PlayerPointsAPI;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.IOException;
import java.lang.invoke.SerializedLambda;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

public class AuctionManager {
    public static ItemStack getNowItem() {
        FileConfiguration config = Main.getPlugin().getConfig();
        int now = getNowIndex();
        List<String> items = config.getStringList("item");
        if (now == -1 || now > items.size() - 1) {
            return null;
        }
        try {
            ItemStack item = StreamSerializer.getDefault().deserializeItemStack(items.get(now));
            ItemMeta itemMeta = item.getItemMeta();
            String displayName = itemMeta.getDisplayName();
            List<String> lore = itemMeta.getLore();
            if (lore == null) lore = new ArrayList<>();

            itemMeta.setDisplayName(LangUtil.getLang("lang12") + (displayName == null ? String.valueOf(item.getTypeId()) : displayName));

            int nowPrice = getNowPrice();
            lore.add(LangUtil.getLang("lang25").replace("<startPrice>", String.valueOf(getNowStartPrice())));
            lore.add(LangUtil.getLang("lang13") + (nowPrice == -1 ? LangUtil.getLang("lang14") : String.valueOf(nowPrice)) + (nowPrice == -1 ? "" : getNowPayType().getName()));
            itemMeta.setLore(lore);
            item.setItemMeta(itemMeta);
            return item;
        } catch (IOException e) {
            Bukkit.getLogger().log(Level.WARNING, "物品反序列化错误...");
        }
        return null;
    }

    public static ItemStack getNextItem() {
        FileConfiguration config = Main.getPlugin().getConfig();
        int now = getNowIndex();
        List<String> items = config.getStringList("item");
        if (now == -1 || now + 1 > items.size() - 1) {
            return null;
        }
        try {
            ItemStack item = StreamSerializer.getDefault().deserializeItemStack(items.get(now + 1));
            ItemMeta itemMeta = item.getItemMeta();
            String displayName = itemMeta.getDisplayName();
            itemMeta.setDisplayName(LangUtil.getLang("lang15") + (displayName == null ? String.valueOf(item.getTypeId()) : displayName));
            item.setItemMeta(itemMeta);
            return item;
        } catch (IOException e) {
            Bukkit.getLogger().log(Level.WARNING, "物品反序列化错误...");
        }
        return null;
    }

    public static int getNowIndex() {
        return Main.getPlugin().getConfig().getInt("now");
    }

    public static int getNowPrice() {
        return Main.getPlugin().getConfig().getInt("nowPrice");
    }

    public static int getNowType() {
        return Main.getPlugin().getConfig().getIntegerList("type").get(getNowIndex());
    }

    public static PayType getNowPayType() {
        return PayType.get(getNowType());
    }

    public static String getNowOwner() {
        return Main.getPlugin().getConfig().getString("nowOwner");
    }

    public static int getNowStartPrice() {
        return Main.getPlugin().getConfig().getIntegerList("startPrice").get(getNowIndex());
    }

    public static void setNowOwner(String owner) {
        Main.getPlugin().getConfig().set("nowOwner", owner);
    }

    public static long getFinfishTime() {
        return Main.getPlugin().getConfig().getLong("over");
    }

    public static boolean hasNext() {
        List<String> items = Main.getPlugin().getConfig().getStringList("item");
        return items.size() > Main.getPlugin().getConfig().getInt("now") + 1;
    }

    public static boolean isStart() {
        return Main.getPlugin().getConfig().getInt("start") == 1;
    }

    public static void setNowIndex(int id) {
        Main.getPlugin().getConfig().set("now", id);
    }

    public static void setNowPrice(int price) {
        Main.getPlugin().getConfig().set("nowPrice", price);
    }

    public static void setFinishTime() {
        setFinishTime(Main.getPlugin().getConfig().getInt("wait"));
    }

    public static void setFinishTime(int sec) {
        Main.getPlugin().getConfig().set("over", Long.valueOf(System.currentTimeMillis() + (sec * 1000l)));
    }

    public static void start() {
        Main.getPlugin().getConfig().set("start", 1);
        if (getNowIndex() == -1) setNowIndex(0);
    }

    public static void stop() {
        Main.getPlugin().getConfig().set("start", 0);
    }

    public static void addItem(ItemStack item, int type, int startPrice) {
        if (item == null || item.getType() == Material.AIR) {
            return;
        }
        FileConfiguration config = Main.getPlugin().getConfig();
        List<String> itemList = config.getStringList("item");
        List<Integer> typeList = config.getIntegerList("type");
        List<Integer> startPriceLsit = config.getIntegerList("startPrice");
        try {
            itemList.add(StreamSerializer.getDefault().serializeItemStack(item));
            typeList.add(type);
            startPriceLsit.add(startPrice);
            config.set("item", itemList);
            config.set("type", typeList);
            config.set("startPrice", startPriceLsit);
        } catch (Exception e) {
            Bukkit.getLogger().log(Level.WARNING, "物品序列化错误...");
        }
    }

    public static void offerAdd(Player p, int amount) {
        PayType payType = getNowPayType();
        int oldPrice = getNowPrice();
        String oldOwnerName = getNowOwner();

        int newPrice = (oldPrice == -1 ? getNowStartPrice() + amount : oldPrice + amount);
        if (!payType.has(p, newPrice)) {
            p.sendMessage(LangUtil.getLang("lang17"));
            return;
        }

        if (!oldOwnerName.equalsIgnoreCase("NONE")) {
            OfflinePlayer oldOwner = Bukkit.getOfflinePlayer(oldOwnerName);
            payType.refund(oldOwner, oldPrice);
            if (oldOwner.isOnline())
                oldOwner.getPlayer().sendMessage(LangUtil.getLang("lang18").replace("<oldPrice>", String.valueOf(oldPrice)).replace("<newOwner>", p.getName()));
        }
        setFinishTime();
        payType.take(p, newPrice);
        setNowOwner(p.getName());
        setNowPrice(newPrice);
        p.sendMessage(LangUtil.getLang("lang19"));
    }

    public static void clinch() {
        String nowOwner = getNowOwner();
        int nowIndex = getNowIndex();
        String item = Main.getPlugin().getConfig().getStringList("item").get(nowIndex);
        List<String> receive = Main.getPlugin().getConfig().getStringList("receive." + nowOwner);
        receive = receive == null ? new ArrayList<>() : receive;
        receive.add(item);
        Main.getPlugin().getConfig().set("receive." + nowOwner, receive);

        setNowOwner("NONE");
        setNowPrice(-1);
        Main.getPlugin().getConfig().set("over", -1);
        if (!hasNext()) stop();
        setNowIndex(getNowIndex() + 1);
        OfflinePlayer p = Bukkit.getOfflinePlayer(nowOwner);
        if (p.isOnline()) p.getPlayer().sendMessage(LangUtil.getLang("lang20"));
        Main.getPlugin().saveConfig();
    }

    public static void receive(Player p) {
        FileConfiguration config = Main.getPlugin().getConfig();
        List<String> itemList = config.getStringList("receive." + p.getName());
        itemList = itemList == null ? new ArrayList<>() : itemList;
        if (itemList.size() == 0) {
            p.sendMessage(LangUtil.getLang("lang22"));
            return;
        }
        PlayerInventory inv = p.getInventory();
        int i = inv.firstEmpty();
        if (i == -1) {
            p.sendMessage(LangUtil.getLang("lang24"));
            return;
        }
        try {
            String itemString = itemList.get(0);
            ItemStack itemStack = StreamSerializer.getDefault().deserializeItemStack(itemString);
            inv.addItem(itemStack);
            itemList.remove(0);
            p.sendMessage(LangUtil.getLang("lang23"));
        } catch (Exception e) {
            Bukkit.getLogger().log(Level.WARNING, "物品反序列化错误");
        }
        config.set("receive." + p.getName(), itemList);
    }

    public static ItemStack getWallItem() {
        String[] premiumItemIds = Main.getPlugin().getConfig().getString("wallItemId").split(":");
        ItemStack addButton = new ItemStack(Integer.valueOf(premiumItemIds[0]));
        addButton.setDurability(premiumItemIds.length == 2 ? Short.valueOf(premiumItemIds[1]) : 0);
        return addButton;
    }

    public static ItemStack getInfoItem(OfflinePlayer p) {
        String[] premiumItemIds = Main.getPlugin().getConfig().getString("infoId").split(":");
        ItemStack addButton = new ItemStack(Integer.valueOf(premiumItemIds[0]));
        addButton.setDurability(premiumItemIds.length == 2 ? Short.valueOf(premiumItemIds[1]) : 0);

        ItemMeta itemMeta = addButton.getItemMeta();
        List<String> lore = itemMeta.getLore();
        if (lore == null) lore = new ArrayList<>();
        lore.add(LangUtil.getLang("lang26").replace("<money>", String.valueOf(Main.economy.getBalance(p))));
        lore.add(LangUtil.getLang("lang27").replace("<money>", String.valueOf(((PlayerPoints) Bukkit.getPluginManager().getPlugin("PlayerPoints")).getAPI().look(p.getUniqueId()))));
        itemMeta.setLore(lore);
        addButton.setItemMeta(itemMeta);
        return addButton;
    }
}
