package com._0myun.minecraft.eventmsg.itembreaker.bin;

import com.comphenix.protocol.utility.StreamSerializer;
import lombok.Data;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.SerializableAs;
import org.bukkit.inventory.ItemStack;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@SerializableAs("com._0myun.minecraft.eventmsg.itembreaker.bin.Product")
@Data
public class Product implements ConfigurationSerializable {
    String show;
    String data;

    public static Product deserialize(Map<String, Object> map) {
        Product product = new Product();
        product.setShow((String) map.get("show"));
        product.setData((String) map.get("data"));
        return product;
    }

    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> map = new HashMap<>();
        map.put("show", this.getShow());
        map.put("data", this.getData());
        return map;
    }

    public ItemStack getItem() {
        try {
            return StreamSerializer.getDefault().deserializeItemStack(this.getData());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
