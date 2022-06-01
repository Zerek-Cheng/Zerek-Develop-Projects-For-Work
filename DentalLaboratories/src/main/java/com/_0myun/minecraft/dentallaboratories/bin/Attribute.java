package com._0myun.minecraft.dentallaboratories.bin;

import lombok.Data;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.SerializableAs;

import java.util.LinkedHashMap;
import java.util.Map;

@Data
@SerializableAs("com._0myun.minecraft.dentallaboratories.bin.Attribute")
public class Attribute implements ConfigurationSerializable {
    private int min;
    private int max;
    private double luck;

    public static Attribute deserialize(Map<String, Object> map) {
        Attribute obj = new Attribute();
        obj.setMin((Integer) map.get("min"));
        obj.setMax((Integer) map.get("max"));
        obj.setLuck((Double) map.get("luck"));
        return obj;
    }

    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("min", this.getMin());
        map.put("max", this.getMax());
        map.put("luck", this.getLuck());
        return map;
    }
}
