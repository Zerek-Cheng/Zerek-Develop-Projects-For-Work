package com._0myun.minecraft.repairsxadurable;

import github.saukiya.sxattribute.data.condition.sub.DurabilityCondition;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

public final class RepairSXADurable extends JavaPlugin {

    public static Economy economy;

    @Override
    public void onEnable() {
        setupEconomy();
        saveDefaultConfig();
    }

    private boolean setupEconomy() {
        RegisteredServiceProvider<Economy> economyProvider = getServer().getServicesManager().getRegistration(net.milkbowl.vault.economy.Economy.class);
        if (economyProvider != null) {
            economy = economyProvider.getProvider();
        }

        return (economy != null);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("必须是玩家");
            return true;
        }
        Player p = (Player) sender;
        ItemStack itemStack = p.getItemInHand();
        if (itemStack == null || itemStack.getType().equals(Material.AIR) || !itemStack.hasItemMeta() || !itemStack.getItemMeta().hasLore()) {
            sender.sendMessage(getConfig().getString("lang1"));
            return true;
        }
        int moneyBase = getConfig().getInt("money-base");
        ItemMeta itemMeta = itemStack.getItemMeta();
        List<String> lores = itemMeta.getLore();
        int line = listContain(lores, getConfig().getString("keyword"));
        if (line == -1) {
            sender.sendMessage(getConfig().getString("lang1"));
            return true;
        }
        String lore = lores.get(line);
        int durability = DurabilityCondition.getDurability(lore);
        int maxDurability = DurabilityCondition.getMaxDurability(lore);
        //System.out.println("lore = " + lore);
      //  System.out.println("durability = " + durability);
       // System.out.println("maxDurability = " + maxDurability);
        int needRepair = maxDurability - durability;
      //  System.out.println("needRepair = " + needRepair);
        if (durability==maxDurability){
            p.sendMessage(getConfig().getString("lang5"));
            return true;
        }
        if (!economy.has(p, moneyBase + needRepair)) {
            p.sendMessage(getConfig().getString("lang2"));
            return true;
        }
        economy.withdrawPlayer(p, moneyBase + needRepair);
        boolean success = Math.random() <= getConfig().getDouble("rand");
        if (!success)
            lore = lore.replace(String.valueOf(maxDurability), String.valueOf(Double.valueOf(maxDurability - (needRepair * 0.1d)).intValue()));
        lore = lore.replace(String.valueOf(durability), String.valueOf(DurabilityCondition.getMaxDurability(lore)));
        lores.set(line, lore);
        itemMeta.setLore(lores);
        itemStack.setItemMeta(itemMeta);
        p.sendMessage(success ? getConfig().getString("lang3") : getConfig().getString("lang4"));
        return true;
    }

    public static int listContain(List<String> lores, String word) {
        for (String lore : lores) {
            if (lore.contains(word)) return lores.indexOf(lore);
        }
        return -1;
    }
}
