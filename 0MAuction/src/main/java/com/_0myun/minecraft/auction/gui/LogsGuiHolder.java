package com._0myun.minecraft.auction.gui;

import com._0myun.minecraft.auction.ConfigUtils;
import com._0myun.minecraft.auction.OrderManager;
import com._0myun.minecraft.auction.table.Orders;
import com.comphenix.protocol.utility.MinecraftReflection;
import com.comphenix.protocol.wrappers.nbt.NbtCompound;
import com.comphenix.protocol.wrappers.nbt.NbtFactory;
import com.comphenix.protocol.wrappers.nbt.NbtWrapper;
import lombok.Data;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

import java.util.List;

@Data
public class LogsGuiHolder implements InventoryHolder {

    Inventory inv;
    int page = 0;
    String player;

    @Override
    public Inventory getInventory() {
        return inv;
    }

    public void init() {
        Inventory inv = this.getInventory();
        List<Orders> orders = OrderManager.listAssociated(player, 45, 45 * (this.getPage()));
        for (Orders order : orders) {
            ItemStack itemShow = order.getItemLogShow();
            if (itemShow == null || itemShow.getType().equals(Material.AIR)) continue;
            itemShow = MinecraftReflection.getBukkitItemStack(MinecraftReflection.getMinecraftItemStack(itemShow));
            setOrderSign(itemShow, order.getId());
            inv.addItem(itemShow);
        }

        inv.setItem(45, ConfigUtils.getConfigItem("gui.button.back"));
        if (page > 0) inv.setItem(48, ConfigUtils.getConfigItem("gui.button.page-before"));
        if (orders.size() >= 45) inv.setItem(50, ConfigUtils.getConfigItem("gui.button.page-after"));
    }

    public static void setOrderSign(ItemStack itemStack, int id) {
        NbtWrapper<?> nbtWrapper = NbtFactory.fromItemTag(itemStack);
        NbtCompound nbt = NbtFactory.asCompound(nbtWrapper);
        nbt.put("com._0myun.minecraft.auction.Auction.order", id);
        NbtFactory.setItemTag(itemStack, nbt);
    }

    public static int getOrderSign(ItemStack itemStack) {
        NbtWrapper<?> nbtWrapper = NbtFactory.fromItemTag(itemStack);
        NbtCompound nbt = NbtFactory.asCompound(nbtWrapper);
        return nbt.getIntegerOrDefault("com._0myun.minecraft.auction.Auction.order");
    }
}
