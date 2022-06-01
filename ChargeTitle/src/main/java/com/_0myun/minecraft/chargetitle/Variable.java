package com._0myun.minecraft.chargetitle;

import me.clip.placeholderapi.external.EZPlaceholderHook;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class Variable extends EZPlaceholderHook {
    public Variable() {
        super(Main.plugin, "chargetitle");
    }

    @Override
    public String onPlaceholderRequest(Player p, String params) {
        return Main.plugin.getprefix(p);
    }
}
