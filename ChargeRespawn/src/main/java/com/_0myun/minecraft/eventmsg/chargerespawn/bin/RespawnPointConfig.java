package com._0myun.minecraft.eventmsg.chargerespawn.bin;

import lombok.Data;
import lombok.var;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.SerializableAs;

import java.util.HashMap;
import java.util.Map;

@Data
@SerializableAs("com._0myun.minecraft.eventmsg.chargerespawn.bin.RespawnPointConfig")
public class RespawnPointConfig implements ConfigurationSerializable {
    private boolean goWhenToDeath;
    private int invincible;
    private int cost;

    public static RespawnPointConfig deserialize(Map<String, Object> map) {
        RespawnPointConfig info = new RespawnPointConfig();
        info.setGoWhenToDeath((boolean) map.get("goWhenToDeath"));
        info.setInvincible((Integer) map.get("invincible"));
        info.setCost((Integer) map.get("cost"));
        return info;
    }

    @Override
    public Map<String, Object> serialize() {
        var config = new HashMap<String, Object>();
        config.put("goWhenToDeath", this.isGoWhenToDeath());
        config.put("invincible", this.getInvincible());
        config.put("cost", this.getCost());
        return config;
    }
}
