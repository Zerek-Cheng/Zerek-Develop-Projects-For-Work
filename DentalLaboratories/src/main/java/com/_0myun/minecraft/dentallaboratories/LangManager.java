package com._0myun.minecraft.dentallaboratories;

import lombok.Setter;
import org.bukkit.configuration.file.YamlConfiguration;

public class LangManager {
    @Setter
    private YamlConfiguration lang;

    public String get(String name) {
        return this.lang.getString(name);
    }
}
