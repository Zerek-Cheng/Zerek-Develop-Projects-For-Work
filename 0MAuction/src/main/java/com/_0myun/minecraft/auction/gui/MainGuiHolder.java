package com._0myun.minecraft.auction.gui;

import com._0myun.minecraft.auction.Auction;
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
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Arrays;
import java.util.List;

@Data
public class MainGuiHolder implements InventoryHolder {

    Inventory inv;
    int page = 0;

    @Override
    public Inventory getInventory() {
        return inv;
    }

    public void init() {
        Inventory inv = this.getInventory();
        List<Orders> orders = OrderManager.list(Arrays.asList(0, 1), 45, 45 * (this.getPage()));
        for (Orders order : orders) {
            new BukkitRunnable() {
                @Override
                public void run() {
                    ItemStack itemShow = order.getItemShow();
                    if (itemShow == null || itemShow.getType().equals(Material.AIR)) return;
                    itemShow = MinecraftReflection.getBukkitItemStack(MinecraftReflection.getMinecraftItemStack(itemShow));
                    setOrderSign(itemShow, order.getId());
                    synchronized (inv) {
                        inv.addItem(itemShow);
                    }
                }
            }.runTaskAsynchronously(Auction.INSTANCE);
        }
        inv.setItem(45, ConfigUtils.getConfigItem("gui.button.page-left"));
        inv.setItem(46, ConfigUtils.getConfigItem("gui.button.page-sellings"));
        inv.setItem(52, ConfigUtils.getConfigItem("gui.button.page-timeouts"));
        //if (page > 0)
        inv.setItem(48, ConfigUtils.getConfigItem("gui.button.page-before"));
        //if (orders.size() >= 45)
        inv.setItem(50, ConfigUtils.getConfigItem("gui.button.page-after"));

        inv.setItem(53, ConfigUtils.getConfigItem("gui.button.page-logs"));
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
