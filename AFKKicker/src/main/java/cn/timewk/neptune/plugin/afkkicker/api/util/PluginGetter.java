/*
 * Decompiled with CFR 0_133.
 *
 * Could not load the following classes:
 *  org.bukkit.Server
 */
package cn.timewk.neptune.plugin.afkkicker.api.util;

import cn.timewk.neptune.plugin.afkkicker.AFKKicker;
import cn.timewk.neptune.plugin.afkkicker.api.API;
import org.bukkit.Server;

public interface PluginGetter {
    default public AFKKicker getPlugin() {
        return AFKKicker.getPlugin();
    }

    default public Server getServer() {
        return this.getPlugin().getServer();
    }

    default public API getAPI() {
        return this.getPlugin().getAPI();
    }

    default public String getInfoHead() {
        return "§7[§bAFK踢出§7]§e > §a信息 §e>> §b";
    }

    default public String getErrorHead() {
        return "§7[§bAFK踢出§7]§e > §c错误 §e>> §c";
    }
}

