package com._0myun.minecraft.betonquestpapiv2;

import me.clip.placeholderapi.external.EZPlaceholderHook;
import org.bukkit.entity.Player;

public class Variable extends EZPlaceholderHook {
    public Variable() {
        super(Main.plugin, "betonquesteventwritev2");
    }

    @Override
    public String onPlaceholderRequest(Player p, String params) {
        return Main.plugin.get(p.getUniqueId());
    }
}
