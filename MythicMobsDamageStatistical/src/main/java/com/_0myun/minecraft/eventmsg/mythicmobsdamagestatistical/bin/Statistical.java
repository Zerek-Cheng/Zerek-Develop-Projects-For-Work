package com._0myun.minecraft.eventmsg.mythicmobsdamagestatistical.bin;

import lombok.Data;
import lombok.var;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.SerializableAs;

import java.util.*;

@Data
@SerializableAs("com._0myun.minecraft.eventmsg.mythicmobsdamagestatistical.bin.Statistical")
public class Statistical implements ConfigurationSerializable {
    Map<String, Map<UUID, Long>> data = new HashMap<>();

    public static Statistical deserialize(Map<String, Object> map) {
        var tmp = new Statistical();
        Map<String, Map<UUID, Long>> data = tmp.getData();

        Iterator<String> iter = map.keySet().iterator();//统计数据的key

        iter.forEachRemaining(key -> {
            if (key.equalsIgnoreCase("==")) return;
            Map<String, Object> value = (Map<String, Object>) map.get(key);

            var tapDataValue = new HashMap<UUID, Long>();
            value.forEach((uuid, damage) -> tapDataValue.put(UUID.fromString(uuid), Long.valueOf(String.valueOf(damage))));
            data.put(key, tapDataValue);
        });
        tmp.setData(data);
        return tmp;
    }

    @Override
    public Map<String, Object> serialize() {
        var tmp = new HashMap<String, Object>();
        Map<String, Map<UUID, Long>> data = this.getData();
        Iterator<String> iter = data.keySet().iterator();
        iter.forEachRemaining(key -> {
            Map<UUID, Long> value = data.get(key);

            var tmpDataValue = new HashMap<String, Object>();
            value.forEach((uuid, damage) -> tmpDataValue.put(uuid.toString(), Long.valueOf(String.valueOf(damage))));
            tmp.put(key, tmpDataValue);
        });
        return tmp;
    }
}
