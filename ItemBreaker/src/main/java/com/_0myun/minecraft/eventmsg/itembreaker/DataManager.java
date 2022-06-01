package com._0myun.minecraft.eventmsg.itembreaker;

import com._0myun.minecraft.eventmsg.itembreaker.bin.Product;
import com._0myun.minecraft.eventmsg.itembreaker.util.SHA256Util;
import com.comphenix.protocol.utility.StreamSerializer;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DataManager {
    public static YamlConfiguration getData() {
        return ItemBreaker.getPlugin().getData();
    }

    public static List<Product> all() {
        YamlConfiguration data = getData();
        List<Product> all = new ArrayList<>();
        data.getKeys(false).forEach((k) -> {
            all.add((Product) data.get(k));
        });
        return all;
    }

    public static Product get(String id) {
        return getData().isSet(id) ? (Product) getData().get(id) : null;
    }

    public static void remove(String id) {
        getData().set(id, null);
    }

    public static String add(String id, Product data) {
        getData().set(id, data);
        return id;
    }

    public static String add(String id, ItemStack item) {
        try {
            Product product = getItemProduct(item);
            return add(id, product);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static Product getItemProduct(ItemStack item) throws IOException {
        String str = StreamSerializer.getDefault().serializeItemStack(item);
        Product product = new Product();
        product.setData(str);
        product.setShow("Material:" + item.getType().toString() + "|Lore:"
                + Arrays.toString(item.getItemMeta().hasLore() ? item.getItemMeta().getLore().toArray(new String[0]) : new ArrayList<>().toArray(new String[0]))
        );
        return product;
    }

    public static String add(ItemStack item) {
        try {
            Product product = getItemProduct(item);
            return add(String.valueOf(System.currentTimeMillis()) + SHA256Util.getSHA256StrJava(product.getShow()), product);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
