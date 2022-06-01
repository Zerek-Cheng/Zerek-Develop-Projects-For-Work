/*
 * Decompiled with CFR 0_133.
 * 
 * Could not load the following classes:
 *  org.bukkit.Bukkit
 *  org.bukkit.command.Command
 *  org.bukkit.command.CommandExecutor
 *  org.bukkit.command.CommandSender
 *  org.bukkit.configuration.file.FileConfiguration
 *  org.bukkit.entity.Player
 */
package cc.kunss.commands;

import cc.kunss.CDKMain;
import cc.kunss.utils.Files;
import cc.kunss.utils.Methods;
import java.io.PrintStream;
import java.util.List;
import java.util.Set;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class Cmd
implements CommandExecutor {
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        int amout;
        if (command.getName().equalsIgnoreCase("lcdk") && args.length == 0) {
            if (commandSender instanceof Player) {
                if (commandSender.isOp()) {
                    for (String cmd : CDKMain.getMain().getConfig().getStringList("Message.Command.op")) {
                        commandSender.sendMessage(cmd.replace("&", "\u00a7"));
                    }
                } else {
                    for (String cmd : CDKMain.getMain().getConfig().getStringList("Message.Command.player")) {
                        commandSender.sendMessage(cmd.replace("&", "\u00a7"));
                    }
                }
            } else {
                for (String cmd : CDKMain.getMain().getConfig().getStringList("Message.Command.op")) {
                    commandSender.sendMessage(cmd.replace("&", "\u00a7"));
                }
            }
            return true;
        }
        if (args[0].equalsIgnoreCase("create")) {
            if (commandSender instanceof Player && !commandSender.isOp()) {
                Methods.SendMessage(commandSender, "ERROR.NoPermiss");
                return true;
            }
            try {
                if (args.length != 5) {
                    Methods.SendMessage(commandSender, "ERROR.CommandArgs");
                    return true;
                }
                String cmd = args[1].replace("-", " ");
                if (Files.getCdkdata().getKeys(false).contains(cmd)) {
                    Methods.SendMessage(commandSender, "ERROR.CDKExists");
                    return true;
                }
                amout = Integer.parseInt(args[2]);
                String mess = args[3].replace("-", " ");
                String Rename = args[4].replace("-", " ");
                Files.getCdkdata().set(cmd + ".CDK", Methods.CreateCDK(amout));
                Files.getCdkdata().set(cmd + ".Message", (Object)mess);
                Files.getCdkdata().set(cmd + ".rename", (Object)Rename);
                Files.SaveConfig();
                Methods.SendMessage(commandSender, "CreateCDK");
                Files.Reloadconfig();
            }
            catch (NumberFormatException e) {
                Methods.SendMessage(commandSender, "ERROR.CommandArgs");
                return true;
            }
        }
        if (args[0].equalsIgnoreCase("add")) {
            if (commandSender instanceof Player && !commandSender.isOp()) {
                Methods.SendMessage(commandSender, "ERROR.NoPermiss");
                return true;
            }
            try {
                if (args.length != 3) {
                    Methods.SendMessage(commandSender, "ERROR.CommandArgs");
                    return true;
                }
                String CDK = args[1].replace("-", " ");
                amout = Integer.parseInt(args[2]);
                Files.getCdkdata().set(CDK + ".CDK", Methods.addCDK(CDK, amout));
                Files.SaveConfig();
                System.out.println("\u00a7aCDK\u589e\u52a0\u6210\u529f\uff01");
                return true;
            }
            catch (NumberFormatException e) {
                Methods.SendMessage(commandSender, "ERROR.CommandArgs");
                return true;
            }
        }
        if (args[0].equalsIgnoreCase("info")) {
            if (commandSender instanceof Player && !commandSender.isOp()) {
                Methods.SendMessage(commandSender, "ERROR.NoPermiss");
                return true;
            }
            if (args.length != 1) {
                Methods.SendMessage(commandSender, "ERROR.CommandArgs");
                return true;
            }
            Methods.getCDKList(commandSender);
            return true;
        }
        if (args[0].equalsIgnoreCase("open")) {
            if (args.length != 2) {
                Methods.SendMessage(commandSender, "ERROR.CommandArgs");
                return true;
            }
            if (!Bukkit.getOnlinePlayers().contains((Object)Bukkit.getPlayer((String)args[1]))) {
                Methods.SendMessage(commandSender, "ERROR.PlayerExists");
                return true;
            }
            Player p = Bukkit.getPlayer((String)args[1]);
            Methods.VexGui(p);
            return true;
        }
        if (args[0].equalsIgnoreCase("reload")) {
            if (commandSender instanceof Player && !commandSender.isOp()) {
                Methods.SendMessage(commandSender, "ERROR.NoPermiss");
                return true;
            }
            Files.Reloadconfig();
            return true;
        }
        if (args[0].equalsIgnoreCase("delete")) {
            if (!CDKMain.getCDK().keySet().contains(args[1])) {
                Methods.SendMessage(commandSender, "ERROR.CommandCDKNO");
                return true;
            }
            Files.getCdkdata().set(args[1], null);
            Files.Reloadconfig();
            Methods.SendMessage(commandSender, "DeleteMessage");
            return true;
        }
        return false;
    }
}

