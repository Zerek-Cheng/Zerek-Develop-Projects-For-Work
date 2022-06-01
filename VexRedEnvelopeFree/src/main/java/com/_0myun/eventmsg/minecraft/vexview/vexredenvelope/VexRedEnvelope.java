package com._0myun.eventmsg.minecraft.vexview.vexredenvelope;

import com._0myun.eventmsg.minecraft.vexview.vexredenvelope.bin.api.Gold;
import com._0myun.eventmsg.minecraft.vexview.vexredenvelope.bin.api.Points;
import lombok.Data;
import net.milkbowl.vault.economy.Economy;
import org.black_ixx.playerpoints.PlayerPoints;
import org.black_ixx.playerpoints.PlayerPointsAPI;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.logging.Level;

public final class VexRedEnvelope extends JavaPlugin {
    public static VexRedEnvelope plugin;
    public static PlayerPointsAPI points;
    public static Economy economy;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        VexRedEnvelope.plugin = this;
        VexRedEnvelope.points = ((PlayerPoints) Bukkit.getPluginManager().getPlugin("PlayerPoints")).getAPI();
        VexRedEnvelope.economy = getServer().getServicesManager().getRegistration(net.milkbowl.vault.economy.Economy.class).getProvider();

        DataManager.registerPayer(ConfigManager.RedPacketType.POINT, new Gold());
        DataManager.registerPayer(ConfigManager.RedPacketType.GOLD, new Points());//TODO

        Bukkit.getPluginManager().registerEvents(new RedPackageListener(), this);

        VexRedEnvelope.this.getLogger().log(Level.WARNING, "本插件商业版永久收费,免费给你的都是骗子,赶紧去投诉.:(");
        VexRedEnvelope.this.getLogger().log(Level.WARNING, "本插件版权解释权归属于灵梦云科技0MYUN.COM(QQ2025255093)");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length==1&&args[0].equalsIgnoreCase("reload")){
            this.reloadConfig();
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
                if (DataManager.sendItem(p, word, itemInHand, amount * 2, total)) {
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
                //到这里为止安全
                if (DataManager.send(sender instanceof Player ? ((Player) sender).getUniqueId() : null, word, type, amount * 2, total)) {
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
