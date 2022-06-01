/*
 * Decompiled with CFR 0_133.
 *
 * Could not load the following classes:
 *  org.bukkit.command.Command
 *  org.bukkit.command.CommandExecutor
 *  org.bukkit.command.CommandSender
 */
package info.TrenTech.EasyKits.Commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class CommandHandler
        implements CommandExecutor {
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (label.equalsIgnoreCase("kit") || label.equalsIgnoreCase("k") || label.equalsIgnoreCase("easykits")) {
            if (args.length >= 1) {
                if (args[0].equalsIgnoreCase("reload")) {
                    CMDReload.execute(sender);
                } else if (args[0].equalsIgnoreCase("create")) {
                    CMDCreate.execute(sender, args);
                } else if (args[0].equalsIgnoreCase("cooldown")) {
                    CMDCooldown.execute(sender, args);
                } else if (args[0].equalsIgnoreCase("limit")) {
                    CMDLimit.execute(sender, args);
                } else if (args[0].equalsIgnoreCase("price")) {
                    CMDPrice.execute(sender, args);
                } else if (args[0].equalsIgnoreCase("remove")) {
                    CMDRemove.execute(sender, args);
                } else if (args[0].equalsIgnoreCase("give")) {
                    CMDGive.execute(sender, args);
                } else if (args[0].equalsIgnoreCase("book")) {
                    CMDBook.execute(sender);
                } else if (args[0].equalsIgnoreCase("list")) {
                    CMDList.execute(sender);
                } else if (args[0].equalsIgnoreCase("view")) {
                    CMDView.execute(sender, args);
                } else if (args[0].equalsIgnoreCase("reset")) {
                    CMDReset.execute(sender, args);
                } else if (args[0].equalsIgnoreCase("help")) {
                    CMDHelp.execute(sender, args);
                } else if (args[0].equalsIgnoreCase("giveall") && sender.isOp()) {
                    if (args.length != 2) {
                        sender.sendMessage("参数错误");
                        return true;
                    }
                    String kitName = args[1];
                    Bukkit.getOnlinePlayers().forEach((p) -> {
                        /*Kit kit = new Kit(kitName);
                        KitUser kitUser = new KitUser(p, kit);
                        ItemStack[] give = kit.getInventory();
                        for(ItemStack item:give){
                            if (item==null||item.getType()== Material.AIR) continue;
                            p.getInventory().addItem(item);
                        }
                        p.sendMessage("成功领取到礼包....");
                    */
                        boolean op = p.isOp();
                        p.performCommand("kit " + kitName);

                    });
                    sender.sendMessage("请求已发送...");
                } else {
                    CMDKit.execute(sender, args);
                }
            } else {
                CMDKit.execute(sender, args);
            }
        }
        return true;
    }
}

