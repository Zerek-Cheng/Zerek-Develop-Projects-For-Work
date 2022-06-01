/*
 * Decompiled with CFR 0_133.
 * 
 * Could not load the following classes:
 *  net.milkbowl.vault.economy.Economy
 *  net.milkbowl.vault.economy.EconomyResponse
 *  org.bukkit.OfflinePlayer
 *  org.bukkit.command.Command
 *  org.bukkit.command.CommandExecutor
 *  org.bukkit.command.CommandSender
 *  org.bukkit.entity.Player
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.inventory.PlayerInventory
 *  org.bukkit.inventory.meta.ItemMeta
 */
package com.mchim.ItemLoreOrigin.b;

import com.mchim.ItemLoreOrigin.Config.Settings;
import com.mchim.ItemLoreOrigin.ItemLorePlugin;
import com.mchim.ItemLoreOrigin.e.a;
import com.mchim.ItemLoreOrigin.e.h;
import java.util.HashMap;
import java.util.List;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.economy.EconomyResponse;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;

public class b
implements CommandExecutor {
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        List lore;
        ItemMeta meta;
        int value;
        if (!(sender instanceof Player)) {
            return true;
        }
        Player p2 = (Player)sender;
        if (a.a(p2) == null) {
            p2.sendMessage("\u00a7c[\u7cfb\u7edf]\u00a7a\u8bf7\u5c06\u9700\u8981\u51fa\u552e\u7684\u88c5\u5907\u653e\u5728\u624b\u4e0a...");
            return true;
        }
        ItemStack item = a.a(p2);
        if (item.hasItemMeta() && (meta = item.getItemMeta()).hasLore() && (value = h.a(lore = meta.getLore())) > 0) {
            int amount = item.getAmount();
            int money = value * amount;
            ItemLorePlugin.b.depositPlayer((OfflinePlayer)p2, (double)money);
            p2.getInventory().removeItem(new ItemStack[]{item});
            p2.sendMessage("\u00a7c[\u7cfb\u7edf]\u00a7a\u4f60\u5df2\u6210\u529f\u7684\u51fa\u552e\u4e86\u8be5\u7269\u54c1,\u5e76\u83b7\u5f97\u4e86\u00a7e\u00a7l" + money + "\u00a7a\u91d1\u5e01");
            return true;
        }
        p2.sendMessage("\u00a7c[\u7cfb\u7edf]\u00a7a\u8be5\u7269\u54c1\u5c1a\u672a\u6709\u7cfb\u7edf\u5b9a\u4ef7,\u8bf7\u624b\u6301\u7269\u54c1\u4ecb\u7ecd\u5185\u542b\u6709\u00a7e\u00a7l" + Settings.I.ValueFormat.split("<price>")[0] + "\u00a7a\u8bf4\u660e\u7684\u7269\u54c1.");
        return true;
    }
}

