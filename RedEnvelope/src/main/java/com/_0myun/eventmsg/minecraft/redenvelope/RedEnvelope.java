package com._0myun.eventmsg.minecraft.redenvelope;

import com._0myun.eventmsg.minecraft.redenvelope.bin.api.Gold;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.logging.Level;

public final class RedEnvelope extends JavaPlugin {
    public static RedEnvelope plugin;
    public static Economy economy;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        RedEnvelope.plugin = this;
        RedEnvelope.economy = getServer().getServicesManager().getRegistration(net.milkbowl.vault.economy.Economy.class).getProvider();

        DataManager.registerPayer(ConfigManager.RedPacketType.GOLD, new Gold());

        Bukkit.getPluginManager().registerEvents(new RedPackageListener(), this);

        RedEnvelope.this.getLogger().log(Level.WARNING, "本插件为免费版,附带有神秘惊喜哟~");
        RedEnvelope.this.getLogger().log(Level.WARNING, "本插件版权解释权归属于灵梦云科技0MYUN.COM(QQ2025255093)");

    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length >= 4 && args[0].equalsIgnoreCase("send")) {//c send word type amount <total>
            //物品取手上的所以不需要输入数量
            try {
                ConfigManager.RedPacketType.valueOf(args[2].toUpperCase());//类型不存在
            } catch (IllegalArgumentException e) {
                sender.sendMessage(LangUtil.get("lang2"));
                return true;
            }
            String word = args[1];
            ConfigManager.RedPacketType type = ConfigManager.RedPacketType.GOLD;//红包类型
            int amount = Integer.parseInt(args[3]);//红包总数
            int total = Integer.parseInt(args[4]);//红包总额
            if (amount <= 0 || total <= 0) {//为负数
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
        } else if (args.length >= 2 && args[0].equalsIgnoreCase("get") && sender instanceof Player) {//c get word
            Player p = (Player) sender;
            DataManager.receive(p, args[1]);
            return true;
        }
        sender.sendMessage(LangUtil.get("lang1"));
        return true;
    }
}
