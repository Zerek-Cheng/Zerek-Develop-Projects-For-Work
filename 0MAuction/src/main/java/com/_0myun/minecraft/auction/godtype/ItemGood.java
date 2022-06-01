package com._0myun.minecraft.auction.godtype;

import com.comphenix.protocol.utility.StreamSerializer;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.io.IOException;
import java.util.UUID;

public class ItemGood extends GoodType<ItemStack> {
    @Override
    public String getName() {
        return "物品";
    }

    @Override
    public String toString(ItemStack good) {
        try {
            return StreamSerializer.getDefault().serializeItemStack(good);
        } catch (IOException e) {
            return null;
        }
    }

    @Override
    public ItemStack fromString(String str) {
        try {
            return StreamSerializer.getDefault().deserializeItemStack(str);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean giveGood(UUID uuid, ItemStack good) {
        OfflinePlayer op = Bukkit.getOfflinePlayer(uuid);
        if (!op.isOnline()) return false;
        Player p = op.getPlayer();
        PlayerInventory inv = p.getInventory();
        if (inv.firstEmpty() == -1) return false;
        inv.addItem(good);
        p.updateInventory();
        return true;
    }

    @Override
    public String getData() {
        return "item";
    }
}

