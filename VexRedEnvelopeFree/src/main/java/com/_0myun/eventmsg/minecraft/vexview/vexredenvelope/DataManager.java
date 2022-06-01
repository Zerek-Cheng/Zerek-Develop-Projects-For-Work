package com._0myun.eventmsg.minecraft.vexview.vexredenvelope;

import com._0myun.eventmsg.minecraft.vexview.vexredenvelope.bin.RedPackage;
import com._0myun.eventmsg.minecraft.vexview.vexredenvelope.bin.api.Payer;
import com._0myun.eventmsg.minecraft.vexview.vexredenvelope.event.PlayerRedPackageSendEvent;
import com.comphenix.protocol.utility.StreamSerializer;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.UUID;

public class DataManager {
    private static HashMap<String, RedPackage> data = new HashMap<>();
    private static HashMap<ConfigManager.RedPacketType, Payer> payer = new HashMap<>();
    @Getter
    @Setter
    private static String lastWord = null;

    public static boolean add(String word, RedPackage red) {
        if (exist(word)) return false;
        PlayerRedPackageSendEvent event = new PlayerRedPackageSendEvent();
        event.setWord(word);
        event.setRedPackage(red);
        event.setOwner(red.getOwner());
        Bukkit.getPluginManager().callEvent(event);
        if (event.isCancel()) {
            if (red.getOwner() != null)
                payer.get(red.getType()).give(Bukkit.getOfflinePlayer(red.getOwner()), red.getTotal());
            return false;
        } else data.put(word, red);
        return true;
    }

    public static RedPackage get(String word) {
        return data.get(word);
    }

    public static boolean exist(String word) {
        return data.containsKey(word);
    }

    public static void registerPayer(ConfigManager.RedPacketType redPacketType, Payer payer) {
        DataManager.payer.put(redPacketType, payer);
    }

    public static Payer getPayer(ConfigManager.RedPacketType redPacketType) {
        return DataManager.payer.get(redPacketType);
    }

    public static boolean send(UUID uuid, String word, ConfigManager.RedPacketType type, int amount, int total) {
        CommandSender sender = uuid == null ? Bukkit.getConsoleSender() : Bukkit.getPlayer(uuid);
        if (exist(word)) {//口令存在
            sender.sendMessage(LangUtil.get("lang11"));
            return false;
        }
        if (uuid != null) {//没有uuid就是控制台发送的
            Player p = (Player) sender;
            Payer payer = getPayer(type);
            if (payer == null) {//支付未定义
                sender.sendMessage(LangUtil.get("lang10"));
                return false;
            }
            if (!payer.take(p, total)) {//此处直接扣除
                sender.sendMessage(LangUtil.get("lang9"));
                return false;
            }
        }
        Payer payer = getPayer(type);
        RedPackage red = new RedPackage();
        red.setOwner(uuid);
        red.setType(type);
        red.setAmount(amount);
        red.setTotal(total);
        red.setTitle(red.getTotal() + payer.getName());
        red.setWord(word);
        return add(word, red);
    }

    public static boolean sendItem(Player p, String word, ItemStack item, int amount, int total) {
        if (exist(word)) {
            p.sendMessage(LangUtil.get("lang11"));
            return false;
        }
        if (item == null || item.getType().equals(Material.AIR) || item.getAmount() <= 0) {//物品不能为空
            p.sendMessage(LangUtil.get("lang6"));
            return true;
        }
        RedPackage red = new RedPackage();
        red.setOwner(p.getUniqueId());
        red.setType(ConfigManager.RedPacketType.ITEM);
        red.setAmount(amount);
        red.setTotal(total);
        red.setWord(word);
        try {
            item = item.clone();
            item.setAmount(1);
            String itemStr = StreamSerializer.getDefault().serializeItemStack(item);
            red.setItem(itemStr);
        } catch (Exception e) {
            return false;
        }
        ItemMeta itemMeta = item.getItemMeta();
        red.setTitle(red.getTotal() + "个" + (itemMeta.getDisplayName() == null ? String.valueOf(item.getType().toString()) :
                itemMeta.getDisplayName()));
        return add(word, red);
    }

    public static boolean receive(Player p, String word) {
        if (!exist(word)) {
            p.sendMessage(LangUtil.get("lang13"));
            return false;
        }
        RedPackage red = get(word);
        if (red.getBalanceAmount() <= 0 && red.getBalanceAmount() != -1) {//-1是还没初始化领取
            p.sendMessage(LangUtil.get("lang14"));
            return false;
        }
        if (!red.canReceive(p)) return false;
        return red.receive(p) != -1;
    }
}
