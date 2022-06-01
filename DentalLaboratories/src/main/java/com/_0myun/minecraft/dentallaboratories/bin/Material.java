package com._0myun.minecraft.dentallaboratories.bin;

import lombok.Data;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.SerializableAs;

import java.util.LinkedHashMap;
import java.util.Map;

@Data
@SerializableAs("com._0myun.minecraft.dentallaboratories.bin.Material")
public class Material implements ConfigurationSerializable {
    private String type;
    private String lore;
    private int amount;
    private String show;
    private String display;

    public static Material deserialize(Map<String, Object> map) {
        Material obj = new Material();
        if (map.containsKey("type")) obj.setType((String) map.get("type"));
        if (map.containsKey("lore")) obj.setLore((String) map.get("lore"));
        if (map.containsKey("amount")) obj.setAmount((Integer) map.get("amount"));
        if (map.containsKey("show")) obj.setShow((String) map.get("show"));
        if (map.containsKey("display")) obj.setDisplay((String) map.get("display"));
        return obj;
    }

    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("type", this.getType());
        map.put("lore", this.getLore());
        map.put("amount", this.getAmount());
        map.put("show", this.getShow());
        map.put("display", this.getDisplay());
        return map;
    }
}
