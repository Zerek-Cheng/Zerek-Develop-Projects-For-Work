package com._0myun.minecraft.auction;

import com.comphenix.protocol.utility.MinecraftReflection;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.RegisteredServiceProvider;

import java.util.Arrays;
import java.util.List;

public class ConfigUtils {

    private static Permission permission = null;

    public static boolean init() {
        RegisteredServiceProvider<Permission> permissionProvider = Bukkit.getServer().getServicesManager().getRegistration(net.milkbowl.vault.permission.Permission.class);
        if (permissionProvider != null) {
            permission = permissionProvider.getProvider();
        }
        return (permission != null);
    }

    public static String getGroup(Player p) {
        return permission.getPrimaryGroup(p);
    }

    public static int getSellAmount(String group) {
        int max = Auction.INSTANCE.getConfig().getInt("permissions.sell." + group, 54);
        return max > 54 ? 54 : max;
    }

    public static int getTimeMax(String group) {
        return Auction.INSTANCE.getConfig().getInt("permissions.time." + group, Integer.MAX_VALUE);
    }

    public static boolean canSell(Player player) {
        String group = permission.getPrimaryGroup(player);
        return OrderManager.list(player.getName(), Arrays.asList(0, 1)).size() < getSellAmount(group);
    }

    public static int getStartSellFee() {
        return Auction.INSTANCE.getConfig().getInt("fee.startSell");
    }

    public static long getAuctionAddTime() {
        return Auction.INSTANCE.getConfig().getLong("auction.add-time-when-try-to-auction");
    }

    public static int getMaxPrice() {
        return Auction.INSTANCE.getConfig().getInt("auction.max-price");
    }

    public static int getMaxStartPrice() {
        return Auction.INSTANCE.getConfig().getInt("auction.max-startPrice");
    }

    public static int getPokemonPhotoId() {
        return Auction.INSTANCE.getConfig().getInt("gui.photo");
    }

    public static ItemStack getConfigItem(String path) {
        ConfigurationSection config = Auction.INSTANCE.getConfig().getConfigurationSection(path);
        String[] id = config.getString("id").split(":");
        String display = config.getString("display");
        List<String> lores = config.getStringList("lores");
        ItemStack itemStack = new ItemStack(Integer.valueOf(id[0]), 1);
        if (id.length > 1) itemStack.setDurability(Short.valueOf(id[1]));
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(display);
        if (!(lores == null) && !lores.isEmpty()) itemMeta.setLore(lores);
        itemStack.setItemMeta(itemMeta);
        itemStack = MinecraftReflection.getBukkitItemStack(MinecraftReflection.getMinecraftItemStack(itemStack));
        return itemStack;
    }

    public static int getTradeSuccessPriceMode() {
        return Auction.INSTANCE.getConfig().getInt("fee.trade-success-mode", 1);
    }

    public static double getTradeSuccessPrice() {
        return Auction.INSTANCE.getConfig().getDouble("fee.trade-success", 0.1);
    }
}
