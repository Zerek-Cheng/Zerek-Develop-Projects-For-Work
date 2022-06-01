package com._0myun.minecraft.peacewarrior.data;

import lombok.var;
import org.bukkit.configuration.serialization.ConfigurationSerializable;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public abstract class DataBase implements ConfigurationSerializable {

    public DataBase(Map<String, Object> args) {
        for (Field field : this.getClass().getDeclaredFields()) {
            try {
                field.set(this, args.get(field.getName().replace("_", "-")));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    protected DataBase() {
    }

    @Override
    public Map<String, Object> serialize() {
        var map = new HashMap<String, Object>();
        for (Field field : this.getClass().getDeclaredFields()) {
            try {
                map.put(field.getName(), field.get(this));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return map;
    }
}
