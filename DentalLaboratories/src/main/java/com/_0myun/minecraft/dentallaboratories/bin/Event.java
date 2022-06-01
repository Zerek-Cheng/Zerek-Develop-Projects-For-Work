package com._0myun.minecraft.dentallaboratories.bin;

import lombok.Data;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.SerializableAs;

import java.util.LinkedHashMap;
import java.util.Map;

@Data
@SerializableAs("com._0myun.minecraft.dentallaboratories.bin.Event")
public class Event implements ConfigurationSerializable {
    private String type;
    private String data;

    public static Event deserialize(Map<String, Object> map) {
        Event obj = new Event();
        obj.setType((String) map.get("type"));
        obj.setData((String) map.get("data"));
        return obj;
    }

    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("type", this.getType());
        map.put("data", this.getData());
        return map;
    }
}
