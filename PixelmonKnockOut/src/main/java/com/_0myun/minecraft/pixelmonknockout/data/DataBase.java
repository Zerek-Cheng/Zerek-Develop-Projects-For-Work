package com._0myun.minecraft.pixelmonknockout.data;

import com.google.common.base.CaseFormat;
import lombok.NoArgsConstructor;
import lombok.var;
import org.bukkit.configuration.serialization.ConfigurationSerializable;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
@NoArgsConstructor
public abstract class DataBase implements ConfigurationSerializable {

    public DataBase(Map<String, Object> args) {
        for (Field field : this.getClass().getDeclaredFields()) {
            try {
                field.set(this, args.get(CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_HYPHEN, field.getName())));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public Map<String, Object> serialize() {
        var map = new HashMap<String, Object>();
        for (Field field : this.getClass().getDeclaredFields()) {
            try {
                map.put(CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_HYPHEN, field.getName()), field.get(this));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return map;
    }
}
