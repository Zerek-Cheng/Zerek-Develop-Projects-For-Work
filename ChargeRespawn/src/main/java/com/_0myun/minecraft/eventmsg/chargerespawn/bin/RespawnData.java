package com._0myun.minecraft.eventmsg.chargerespawn.bin;

import lombok.Data;
import lombok.var;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.SerializableAs;

import java.util.HashMap;
import java.util.Map;

@Data
@SerializableAs("com._0myun.minecraft.eventmsg.chargerespawn.bin.RespawnData")
public class RespawnData implements ConfigurationSerializable {
    long lastDeath;
    int free;
    int coin;

    public static RespawnData deserialize(Map<String, Object> map) {
        RespawnData info = new RespawnData();
        info.setLastDeath((Long) map.get("lastDeath"));
        info.setFree((Integer) map.get("free"));
        info.setCoin((Integer) map.get("coin"));
        return info;
    }

    @Override
    public Map<String, Object> serialize() {
        var config = new HashMap<String, Object>();
        config.put("lastDeath", this.getLastDeath());
        config.put("free", this.getFree());
        config.put("coin", this.getCoin());
        return config;
    }
}
