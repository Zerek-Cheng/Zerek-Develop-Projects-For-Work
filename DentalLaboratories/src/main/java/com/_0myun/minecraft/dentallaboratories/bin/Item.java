package com._0myun.minecraft.dentallaboratories.bin;

import lombok.Data;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.SerializableAs;

import java.util.*;

@Data
@SerializableAs("com._0myun.minecraft.dentallaboratories.bin.Item")
public class Item implements ConfigurationSerializable {
    private String sign;
    private HashMap<String, Attribute> attributes;
    private List<String> lores;
    private HashMap<Integer, Event> events;
    private HashMap<Integer, List<Material>> materials;
    private HashMap<Integer, Double> chances;

    public static Item deserialize(Map<String, Object> map) {
        Item obj = new Item();
        obj.setSign((String) map.get("sign"));
        obj.setAttributes((HashMap<String, Attribute>) map.get("attribute"));
        obj.setLores((List<String>) map.get("lore"));
        obj.setEvents((HashMap<Integer, Event>) map.get("event"));
        obj.setMaterials((HashMap<Integer, List<Material>>) map.get("material"));
        obj.setChances((HashMap<Integer, Double>) map.get("chance"));
        return obj;
    }

    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("sign", this.getSign());
        map.put("attribute", this.getAttributes());
        map.put("lore", this.getLores());
        map.put("event", this.getEvents());
        map.put("material", this.getMaterials());
        map.put("chance", this.getChances());
        return map;
    }

    public HashMap<String, Integer> randAttributesChange() {
        HashMap<String, Integer> change = new HashMap<>();
        HashMap<String, Attribute> attributes = this.getAttributes();
        attributes.forEach((name, attribute) -> {
            boolean luck = Math.random() <= attribute.getLuck();
            int value = new Random(System.currentTimeMillis()).nextInt(attribute.getMax() - attribute.getMin()) + attribute.getMin();
            if (luck) value += value;
            change.put(name, value);
        });
        return change;
    }
}
