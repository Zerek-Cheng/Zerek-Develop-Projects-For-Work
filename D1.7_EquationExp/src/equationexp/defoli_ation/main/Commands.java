/*
 * Decompiled with CFR 0_133.
 *
 * Could not load the following classes:
 *  org.bukkit.Bukkit
 *  org.bukkit.command.Command
 *  org.bukkit.command.CommandExecutor
 *  org.bukkit.command.CommandSender
 *  org.bukkit.entity.Player
 */
package equationexp.defoli_ation.main;

import equationexp.defoli_ation.main.exp.Exp;
import equationexp.defoli_ation.main.exp.ExpManager;
import equationexp.defoli_ation.main.file.expfile.PlayerExpFile;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Commands
        implements CommandExecutor {
    private ExpManager expManager;
    private Exp exp;
    private PlayerExpFile expFile;

    public Commands(ExpManager expManager, PlayerExpFile expFile) {
        this.expManager = expManager;
        this.exp = expManager.getExp();
        this.expFile = expFile;
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!cmd.getName().equalsIgnoreCase("vexp")) {
            return true;
        }
        if (args.length == 0) {
            this.viewPlayerExpAndUplevelNeedExp(sender, (Player)sender);
            return true;
        }
        if (args[0].equalsIgnoreCase("give")) {
            if (args.length >= 2) {
                if (this.isPlayerAndOp(sender)) {
                    if (this.isNum(args[1])) {
                        this.givePlayerExp((Player)sender, Integer.parseInt(args[1]));
                    } else if (args.length >= 3 && this.isNum(args[2])) {
                        this.giveOtherPlayerExp(sender, args[1], args[2]);
                    }
                } else if (this.isCMD(sender) && args.length >= 3) {
                    this.giveOtherPlayerExp(sender, args[1], args[2]);
                }
            }
        } else if (args[0].equalsIgnoreCase("set")) {
            Player p;
            if (args.length >= 3 && this.isNum(args[2]) && (this.isPlayerAndOp(sender) || this.isCMD(sender)) && (p = this.foundPlayerInServer(args[1])) != null) {
                this.expManager.setPlayerExp(p, Integer.parseInt(args[2]));
                this.expManager.loadPlayerExpLevelAndExpBar(p);
            }
        } else if (this.isNum(args[0])) {
            this.viewExp(sender, Integer.parseInt(args[0]));
        } else if (args[0].equalsIgnoreCase("list")) {
            if (args.length >= 2 && this.isNum(args[1])) {
                this.viewExpList(sender, Integer.parseInt(args[1]));
            } else {
                this.viewExpList(sender, this.exp.getLoadMax());
            }
        } else if (args[0].equalsIgnoreCase("save")) {
            if (this.isCMD(sender) || sender.isOp()) {
                this.expFile.save();
                sender.sendMessage("保存成功");
            }
        } else {
            Player p = this.foundPlayerInServer(args[0]);
            if (p != null) {
                this.viewPlayerExpAndUplevelNeedExp(sender, p);
            }
        }
        return true;
    }

    private boolean isPlayerAndOp(CommandSender sender) {
        Player p;
        if (sender instanceof Player && (p = (Player)sender).isOp()) {
            return true;
        }
        return false;
    }

    private boolean isCMD(CommandSender sender) {
        return !(sender instanceof Player);
    }

    private void givePlayerExp(Player who, int exp) {
        this.expManager.addPlayerExp(who, exp);
        this.expManager.loadPlayerExpLevelAndExpBar(who);
    }

    private boolean isNum(String str) {
        if (str.matches("[\\d]+")) {
            return true;
        }
        if (str.matches("[-][\\d]+")) {
            return true;
        }
        return false;
    }

    private void sendMessage(CommandSender sender, String s) {
        sender.sendMessage("§6EquationExp: §e" + s);
    }

    private void giveOtherPlayerExp(CommandSender sender, String who, String exp) {
        Player p = this.foundPlayerInServer(who);
        if (p != null) {
            this.givePlayerExp(p, Integer.parseInt(exp));
            this.sendMessage(sender, "已给予玩家" + who + "," + exp + "点经验");
        } else {
            this.sendMessage(sender, "玩家不存在");
        }
    }

    private void viewPlayerExpAndUplevelNeedExp(CommandSender sender, Player player) {
        int nextLevelExp = this.exp.getExp(player.getLevel() + 1);
        int playerExp = this.expFile.getPlayerExp(player);
        int uplevelNeedExp = nextLevelExp - playerExp;
        this.sendMessage(sender, "§6§l玩家:" + player.getName() + ",LV.§c" + player.getLevel() + "§6§l拥有:§c" + playerExp + "§6§l点经验,升级还需:§c" + uplevelNeedExp + "§6§l点经验");
    }

    private Player foundPlayerInServer(String name) {
        return Bukkit.getServer().getPlayer(name);
    }

    private void viewExpList(CommandSender sender, int level) {
        int i = 0;
        while (i < level + 1) {
            this.viewExp(sender, i);
            ++i;
        }
    }

    private void viewExp(CommandSender sender, int level) {
        this.sendMessage(sender, "§6到达等级" + level + "所需总经验:" + this.exp.getExp(level));
    }
}

