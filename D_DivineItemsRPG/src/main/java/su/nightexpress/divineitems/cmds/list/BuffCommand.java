/*
 * Decompiled with CFR 0_133.
 * 
 * Could not load the following classes:
 *  org.bukkit.Bukkit
 *  org.bukkit.command.CommandSender
 *  org.bukkit.entity.Player
 */
package su.nightexpress.divineitems.cmds.list;

import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import su.nightexpress.divineitems.DivineItems;
import su.nightexpress.divineitems.cmds.CommandBase;
import su.nightexpress.divineitems.config.Lang;
import su.nightexpress.divineitems.modules.ModuleManager;
import su.nightexpress.divineitems.modules.buffs.BuffManager;
import su.nightexpress.divineitems.utils.Utils;

public class BuffCommand
extends CommandBase {
    private DivineItems plugin;

    public BuffCommand(DivineItems divineItems) {
        this.plugin = divineItems;
    }

    @Override
    public void perform(CommandSender commandSender, String[] arrstring) {
        BuffManager buffManager = this.plugin.getMM().getBuffManager();
        if (arrstring.length == 7 && arrstring[1].equalsIgnoreCase("add")) {
            Player player = Bukkit.getPlayer((String)arrstring[2]);
            if (player == null) {
                commandSender.sendMessage(String.valueOf(Lang.Prefix.toMsg()) + Lang.Other_InvalidPlayer.toMsg());
                return;
            }
            BuffManager.BuffType buffType = null;
            try {
                buffType = BuffManager.BuffType.valueOf(arrstring[3].toUpperCase());
            }
            catch (IllegalArgumentException illegalArgumentException) {
                commandSender.sendMessage(String.valueOf(Lang.Prefix.toMsg()) + Lang.Other_InvalidType.toMsg().replace("%types%", Utils.getEnums(BuffManager.BuffType.class, "\u00a7a", "\u00a77")));
                return;
            }
            String string = arrstring[4];
            double d = 0.0;
            try {
                d = Double.parseDouble(arrstring[5]);
            }
            catch (NumberFormatException numberFormatException) {
                commandSender.sendMessage(String.valueOf(Lang.Prefix.toMsg()) + Lang.Other_InvalidNumber.toMsg().replace("%s", arrstring[5]));
                return;
            }
            int n = 0;
            try {
                n = Integer.parseInt(arrstring[6]);
            }
            catch (NumberFormatException numberFormatException) {
                commandSender.sendMessage(String.valueOf(Lang.Prefix.toMsg()) + Lang.Other_InvalidNumber.toMsg().replace("%s", arrstring[6]));
                return;
            }
            if (buffManager.addBuff(player, buffType, string, d, n, true)) {
                commandSender.sendMessage(String.valueOf(Lang.Prefix.toMsg()) + Lang.Buffs_Give.toMsg().replace("%value%", arrstring[4]).replace("%mod%", arrstring[5]).replace("%time%", arrstring[6]).replace("%type%", arrstring[3]).replace("%p", arrstring[2]));
            } else {
                commandSender.sendMessage(String.valueOf(Lang.Prefix.toMsg()) + Lang.Buffs_Invalid.toMsg());
            }
        } else if (arrstring.length == 5 && arrstring[1].equalsIgnoreCase("reset")) {
            Player player = Bukkit.getPlayer((String)arrstring[2]);
            if (player == null) {
                commandSender.sendMessage(String.valueOf(Lang.Prefix.toMsg()) + Lang.Other_InvalidPlayer.toMsg());
                return;
            }
            BuffManager.BuffType buffType = null;
            try {
                buffType = BuffManager.BuffType.valueOf(arrstring[3].toUpperCase());
            }
            catch (IllegalArgumentException illegalArgumentException) {
                commandSender.sendMessage(String.valueOf(Lang.Prefix.toMsg()) + Lang.Other_InvalidType.toMsg().replace("%types%", Utils.getEnums(BuffManager.BuffType.class, "\u00a7a", "\u00a77")));
                return;
            }
            String string = arrstring[4];
            buffManager.resetBuff(player, buffType, string);
            commandSender.sendMessage(String.valueOf(Lang.Prefix.toMsg()) + "Done!");
        } else if (arrstring.length == 3 && arrstring[1].equalsIgnoreCase("resetall")) {
            Player player = Bukkit.getPlayer((String)arrstring[2]);
            if (player == null) {
                commandSender.sendMessage(String.valueOf(Lang.Prefix.toMsg()) + Lang.Other_InvalidPlayer.toMsg());
                return;
            }
            for (BuffManager.Buff buff : buffManager.getPlayerBuffs(player)) {
                buffManager.resetBuff(player, buff.getType(), buff.getValue());
            }
            commandSender.sendMessage(String.valueOf(Lang.Prefix.toMsg()) + "Done!");
        } else {
            for (String string : Lang.Help_Buffs.getList()) {
                commandSender.sendMessage(string.replace("%m_state%", this.plugin.getMM().getColorStatus(buffManager.isActive())).replace("%m_ver%", buffManager.version()).replace("%m_name%", buffManager.name()));
            }
            return;
        }
    }

    @Override
    public String getPermission() {
        return "divineitems.admin";
    }

    @Override
    public boolean playersOnly() {
        return false;
    }
}

