package com._0myun.eventmsg.minecraft.vexview.vexredenvelope;

import com._0myun.eventmsg.minecraft.vexview.vexredenvelope.bin.api.Gold;
import com._0myun.eventmsg.minecraft.vexview.vexredenvelope.bin.api.Points;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class NetLoader implements CommandExecutor {

    public NetLoader() {
        DataManager.registerPayer(ConfigManager.RedPacketType.GOLD, new Gold());
        DataManager.registerPayer(ConfigManager.RedPacketType.POINT, new Points());
        Bukkit.getPluginManager().registerEvents(new RedPackageListener(), VexRedEnvelope.plugin);
        Bukkit.getPluginManager().registerEvents(new PlayerListener(), VexRedEnvelope.plugin);

        Bukkit.getPluginCommand("vred").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (args.length == 1 && args[0].equalsIgnoreCase("reload")) {
            VexRedEnvelope.plugin.reloadConfig();
            sender.sendMessage("ok");
            return true;
        }
        if (args.length >= 4 && args[0].equalsIgnoreCase("send")) {//c send word type amount <total>
            //物品取手上的所以不需要输入数量
            try {
                ConfigManager.RedPacketType.valueOf(args[2].toUpperCase());//类型不存在
            } catch (IllegalArgumentException e) {
                sender.sendMessage(LangUtil.get("lang2"));
                return true;
            }
            String word = args[1];
            ConfigManager.RedPacketType type = ConfigManager.RedPacketType.valueOf(args[2].toUpperCase());//红包类型
            int amount = Integer.parseInt(args[3]);//红包总数
            if (amount <= 0) {//为负数
                sender.sendMessage(LangUtil.get("lang4"));
                return true;
            }

            if (type.equals(ConfigManager.RedPacketType.ITEM)) {//发送的是物品
                if (!(sender instanceof Player)) {//控制台不能发
                    sender.sendMessage(LangUtil.get("lang7"));
                    return true;
                }
                Player p = (Player) sender;
                ItemStack itemInHand = p.getItemInHand();
                if (itemInHand == null || itemInHand.getType().equals(Material.AIR) || itemInHand.getAmount() <= 0) {//物品不能为空
                    sender.sendMessage(LangUtil.get("lang6"));
                    return true;
                }
                //到这里为止安全
                int total = itemInHand.getAmount();
                if (amount > total) {//数量比总金额多
                    sender.sendMessage(LangUtil.get("lang5"));
                    return true;
                }
                if (DataManager.sendItem(p, word, itemInHand, amount, total)) {
                    p.sendMessage(LangUtil.get("lang8"));
                    itemInHand.setType(Material.AIR);
                    itemInHand.setAmount(0);
                    p.setItemInHand(itemInHand);
                    p.updateInventory();//以防万一刷新一下
                } else {
                    p.sendMessage(LangUtil.get("lang9"));
                }
                return true;
            } else {
                int total = Integer.parseInt(args[4]);//红包总额
                if (total <= 0) {//数量或者金额为负数
                    sender.sendMessage(LangUtil.get("lang4"));
                    return true;
                }
                if (amount > total) {//数量比总金额多
                    sender.sendMessage(LangUtil.get("lang5"));
                    return true;
                }
                //到这里为止安全
                if (DataManager.send(sender instanceof Player ? ((Player) sender).getUniqueId() : null, word, type, amount, total)) {
                    sender.sendMessage(LangUtil.get("lang8"));
                } else {
                    sender.sendMessage(LangUtil.get("lang9"));
                }
                return true;
            }

        } else if (args.length >= 2 && args[0].equalsIgnoreCase("get") && sender instanceof Player) {//c get word
            Player p = (Player) sender;
            DataManager.receive(p, args[1]);
            return true;
        }
        sender.sendMessage(LangUtil.get("lang1"));
        return true;
    }
}
