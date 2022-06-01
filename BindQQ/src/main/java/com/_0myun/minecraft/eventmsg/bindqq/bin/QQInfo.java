package com._0myun.minecraft.eventmsg.bindqq.bin;

import lombok.Data;
import lombok.var;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.SerializableAs;

import java.util.HashMap;
import java.util.Map;

@Data
@SerializableAs("QQInfo")
public class QQInfo implements ConfigurationSerializable {

    private String qq;
    private long reg;
    private long log;

    public static QQInfo deserialize(Map<String, Object> map) {
        QQInfo info = new QQInfo();
        info.setQq(String.valueOf(map.get("qq")));
        info.setReg(Long.valueOf(String.valueOf(map.get("reg"))));
        info.setLog(Long.valueOf(String.valueOf(map.get("log"))));
        return info;
    }

    @Override
    public Map<String, Object> serialize() {
        var map = new HashMap<String, Object>();
        map.put("qq", this.getQq());
        map.put("reg", this.getReg());
        map.put("log", this.getLog());
        return map;
    }
}
