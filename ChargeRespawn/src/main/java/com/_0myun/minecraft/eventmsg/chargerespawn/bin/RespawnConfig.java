package com._0myun.minecraft.eventmsg.chargerespawn.bin;

import lombok.Data;
import lombok.var;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.SerializableAs;

import java.util.HashMap;
import java.util.Map;

@Data
@SerializableAs("com._0myun.minecraft.eventmsg.chargerespawn.bin.RespawnConfig")
public class RespawnConfig implements ConfigurationSerializable {
    private int interval;
    private int dayFree;
    private int coinCount;

    private RespawnPointConfig pointConfig;

    public static RespawnConfig deserialize(Map<String, Object> map) {
        RespawnConfig info = new RespawnConfig();
        info.setInterval((Integer) map.get("interval"));
        info.setDayFree((Integer) map.get("dayFree"));
        info.setCoinCount((Integer) map.get("coinCount"));
        info.setPointConfig((RespawnPointConfig) map.get("point"));
        return info;
    }

    @Override
    public Map<String, Object> serialize() {
        var config = new HashMap<String, Object>();
        config.put("interval", this.getInterval());
        config.put("dayFree", this.getDayFree());
        config.put("coinCount", this.getCoinCount());
        config.put("point", this.getPointConfig());
        return config;
    }
}
