// 
// Decompiled by Procyon v0.5.30
// 

package com.qiyue.RPGBind;

import java.util.Iterator;
import java.util.List;
import org.bukkit.inventory.ItemStack;
import org.bukkit.OfflinePlayer;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import java.util.regex.Pattern;
import org.bukkit.command.CommandExecutor;

public class Commands implements CommandExecutor
{
    public static Main instance;
    
    public Commands(final Main m) {
        Commands.instance = m;
    }
    
    public static boolean isNumeric(final String str) {
        final Pattern pattern = Pattern.compile("[0-9]*");
        return pattern.matcher(str).matches();
    }
    
    public boolean onCommand(final CommandSender sender, final Command cmd, final String lable, final String[] args) {
        if (!lable.equalsIgnoreCase("rgb") || !(sender instanceof Player)) {
            return false;
        }
        final Player p = (Player)sender;
        if (args.length == 0) {
            sender.sendMessage("¡ì6/rgb bd ¡ìf\u7ed1\u5b9a\u624b\u4e2d\u7269\u54c1");
            sender.sendMessage("¡ì6/rgb get [id] ¡ìf\u627e\u56de\u7ed1\u5b9a\u7684\u7269\u54c1");
            sender.sendMessage("¡ì6/rgb list ¡ìf\u5217\u51fa\u7ed1\u5b9a\u5217\u8868");
            return true;
        }
        if (args[0].equalsIgnoreCase("bd") && args.length == 1) {
            if (p.getItemInHand() == null || p.getItemInHand().getType() == null || p.getItemInHand().getType() == Material.AIR) {
                Commands.instance.sendMessage(p, Commands.instance.getConfig().getString("NoHand"));
                return true;
            }
            if (Commands.instance.getUUID(p.getItemInHand()) != null) {
                Commands.instance.sendMessage(p, Commands.instance.getConfig().getString("Binding"));
                return true;
            }
            if (Commands.instance.isEnable(String.valueOf(p.getItemInHand().getData().getItemTypeId()) + ":" + p.getItemInHand().getData().getData())) {
                Commands.instance.sendMessage(p, Commands.instance.getConfig().getString("NotBdItemsAdd"));
                return true;
            }
            if (!Commands.instance.isRPG(p.getItemInHand())) {
                Commands.instance.sendMessage(p, Commands.instance.getConfig().getString("BindNot"));
                return true;
            }
            if (!Commands.instance.getVault().has((OfflinePlayer)p, Commands.instance.getConfig().getDouble("money"))) {
                Commands.instance.sendMessage(p, Commands.instance.getConfig().getString("NoMoney"));
                return true;
            }
            Commands.instance.addUUID(Commands.instance.madeUUID(), p.getName(), p.getItemInHand());
            Commands.instance.getVault().withdrawPlayer((OfflinePlayer)p, Commands.instance.getConfig().getDouble("money"));
            Commands.instance.sendMessage(p, Commands.instance.getConfig().getString("BindOK"));
            return true;
        }
        else if (args[0].equalsIgnoreCase("get") && args.length == 2) {
            if (!Commands.instance.hasUUID(Commands.instance.getIDUUID(p.getName(), args[1])) || !Commands.instance.getUUIDEnable(Commands.instance.getIDUUID(p.getName(), args[1]))) {
                Commands.instance.sendMessage(p, Commands.instance.getConfig().getString("HasID"));
                return true;
            }
            if (!Commands.instance.getUUIDPlayer(Commands.instance.getIDUUID(p.getName(), args[1])).equals(p.getName())) {
                Commands.instance.sendMessage(p, Commands.instance.getConfig().getString("NoID"));
                return true;
            }
            p.getInventory().addItem(new ItemStack[] { Commands.instance.getItemStack(Commands.instance.getUUIDItemStack(Commands.instance.getIDUUID(p.getName(), args[1]))) });
            Commands.instance.sendMessage(p, "&a\u627e\u56de\u6210\u529f");
            Commands.instance.remove(Commands.instance.getIDUUID(p.getName(), args[1]));
            return true;
        }
        else {
            if (args[0].equalsIgnoreCase("list") && args.length == 1) {
                final List<String> list = Commands.instance.getUUIDList(p.getName());
                p.sendMessage("¡ìa\u60a8\u7ed1\u5b9a\u7684\u6240\u6709\u795e\u5668: ");
                if (list.size() == 0) {
                    p.sendMessage("- ¡ì6\u65e0");
                }
                else {
                    String nodes = "";
                    for (final String node : list) {
                        nodes = String.valueOf(nodes) + "- ¡ìf[\u7f16\u53f7:" + node.split(",")[0] + " " + node.split(",")[2] + "¡ìf]" + ",";
                    }
                    nodes = nodes.substring(0, nodes.length() - 1);
                    p.sendMessage(nodes);
                }
                p.sendMessage("¡ì6\u8bf7\u8f93\u5165/rgb get <\u7f16\u53f7> \u6765\u627e\u56de\u795e\u5668 ");
                p.sendMessage("¡ìcPS: \u627e\u56de\u540e\u4e4b\u524d\u7684\u795e\u5668\u5c31\u4f1a\u5931\u6548\u88ab\u5220\u9664");
                return true;
            }
            if (args[0].equalsIgnoreCase("reload") && args.length == 1 && p.isOp()) {
                Commands.instance.reloadPlugin();
                p.sendMessage("\u91cd\u8f7d\u5b8c\u6bd5!");
                return true;
            }
            return true;
        }
    }
}
