package com._0myun.minecraft.treasuremap;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.SerializableAs;

import java.util.HashMap;
import java.util.Map;

@SerializableAs("com._0myun.minecraft.treasuremap.RandCommand")
@Data
@AllArgsConstructor
public class RandCommand implements ConfigurationSerializable {

    String cmd;
    Double rand;
    String msg;

    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("cmd", getCmd());
        result.put("rand", getRand());
        result.put("msg", getMsg());
        return result;
    }

    public static RandCommand deserialize(Map<String, Object> args) {
        return new RandCommand((String) args.get("cmd"), (Double) args.get("rand"), (String) args.get("msg"));
    }
}