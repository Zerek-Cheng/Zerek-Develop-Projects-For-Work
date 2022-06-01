/*
 * Decompiled with CFR 0_133.
 * 
 * Could not load the following classes:
 *  org.bukkit.command.CommandSender
 *  org.bukkit.entity.Player
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.inventory.PlayerInventory
 */
package su.nightexpress.divineitems.cmds.list;

import java.util.List;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import su.nightexpress.divineitems.DivineItems;
import su.nightexpress.divineitems.api.ItemAPI;
import su.nightexpress.divineitems.cmds.CommandBase;
import su.nightexpress.divineitems.config.Lang;
import su.nightexpress.divineitems.modules.ModuleManager;
import su.nightexpress.divineitems.modules.soulbound.SoulboundManager;

public class SoulboundCommand
extends CommandBase {
    private DivineItems plugin;

    public SoulboundCommand(DivineItems divineItems) {
        this.plugin = divineItems;
    }

    @Override
    public void perform(CommandSender commandSender, String[] arrstring) {
        SoulboundManager soulboundManager = this.plugin.getMM().getSoulboundManager();
        Player player = (Player)commandSender;
        if (arrstring.length == 3 || arrstring.length == 4 && arrstring[1].equalsIgnoreCase("set")) {
            ItemStack itemStack;
            int n = -1;
            if (arrstring.length == 4) {
                try {
                    n = Integer.parseInt(arrstring[3]);
                }
                catch (NumberFormatException numberFormatException) {
                    commandSender.sendMessage(String.valueOf(Lang.Prefix.toMsg()) + Lang.Other_InvalidNumber.toMsg().replace("%s", arrstring[3]));
                }
            }
            if ((itemStack = player.getInventory().getItemInMainHand()) == null) {
                player.sendMessage(String.valueOf(Lang.Prefix.toMsg()) + Lang.Other_InvalidItem.toMsg());
                return;
            }
            itemStack = ItemAPI.setSoulboundRequired(itemStack, arrstring[2], n);
            player.getInventory().setItemInMainHand(itemStack);
        } else if (arrstring.length == 3 || arrstring.length == 4 && arrstring[1].equalsIgnoreCase("untrade")) {
            ItemStack itemStack;
            int n = -1;
            if (arrstring.length == 4) {
                try {
                    n = Integer.parseInt(arrstring[3]);
                }
                catch (NumberFormatException numberFormatException) {
                    commandSender.sendMessage(String.valueOf(Lang.Prefix.toMsg()) + Lang.Other_InvalidNumber.toMsg().replace("%s", arrstring[3]));
                }
            }
            if ((itemStack = player.getInventory().getItemInMainHand()) == null) {
                player.sendMessage(String.valueOf(Lang.Prefix.toMsg()) + Lang.Other_InvalidItem.toMsg());
                return;
            }
            itemStack = ItemAPI.setUntradeable(itemStack, arrstring[2], n);
            player.getInventory().setItemInMainHand(itemStack);
        } else {
            for (String string : Lang.Help_Soulbound.getList()) {
                commandSender.sendMessage(string.replace("%m_state%", this.plugin.getMM().getColorStatus(soulboundManager.isActive())).replace("%m_ver%", soulboundManager.version()).replace("%m_name%", soulboundManager.name()));
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
        return true;
    }
}

