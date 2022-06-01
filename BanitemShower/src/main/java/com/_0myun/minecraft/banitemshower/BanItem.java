package com._0myun.minecraft.banitemshower;

import lombok.Data;
import org.bukkit.configuration.ConfigurationSection;

import java.util.HashMap;

@Data
public class BanItem {
    public boolean confiscate;
    public String reason;
    public int currentId;
    public HashMap<Integer, SubBanItem> subItems;

    public BanItem(ConfigurationSection config) {
        this.confiscate = config.getBoolean("Confiscate");
        this.reason = config.getString("Reason");
        this.currentId = config.getInt("CurrentId");
        this.subItems = new HashMap<>();
        config.getKeys(false).forEach(key -> {
            if (!key.startsWith("damage@")) return;
            ConfigurationSection subConfig = config.getConfigurationSection(key);
            int subId = Integer.valueOf(key.replace("damage@", ""));
            this.subItems.put(subId, new SubBanItem(this, subId, subConfig));
        });
    }
}
