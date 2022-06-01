package com._0myun.minecraft.eventmsg.itembreaker.bin;

import com._0myun.minecraft.eventmsg.itembreaker.DataManager;
import lombok.Data;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.SerializableAs;

import java.util.*;

@SerializableAs("com._0myun.minecraft.eventmsg.itembreaker.bin.BreakAble")
@Data
public class BreakAble implements ConfigurationSerializable {
    private String lore;
    private List<String> product;
    private int minGold;
    private int maxGold;
    private int minPoint;
    private int maxPoint;

    public List<Product> getProducts() {
        List<Product> products = new ArrayList<>();
        this.getProduct().forEach((id) -> products.add(DataManager.get(id)));
        return products;
    }

    public static BreakAble deserialize(Map<String, Object> map) {
        BreakAble product = new BreakAble();
        product.setLore((String) map.get("lore"));
        product.setProduct((List<String>) map.get("product"));
        product.setMinGold((Integer) map.get("minGold"));
        product.setMaxGold((Integer) map.get("maxGold"));
        product.setMinPoint((Integer) map.get("minPoint"));
        product.setMaxPoint((Integer) map.get("maxPoint"));
        return product;
    }

    public int randGold() {
        return new Random(System.currentTimeMillis()).nextInt(this.getMaxGold() - this.getMinGold()) + this.getMinGold();
    }

    public int randPoint() {
        return new Random(System.currentTimeMillis()).nextInt(this.getMaxPoint() - this.getMinPoint()) + this.getMinPoint();
    }

    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> map = new HashMap<>();
        map.put("lore", this.getLore());
        map.put("product", this.getProduct());
        map.put("minGold", this.getMinGold());
        map.put("maxGold", this.getMaxGold());
        map.put("minPoint", this.getMinPoint());
        map.put("maxPoint", this.getMaxPoint());
        return map;
    }
}
