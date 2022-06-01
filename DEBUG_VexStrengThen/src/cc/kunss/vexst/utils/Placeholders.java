/*
 * Decompiled with CFR 0_133.
 * 
 * Could not load the following classes:
 *  me.clip.placeholderapi.external.EZPlaceholderHook
 *  org.bukkit.entity.Player
 *  org.bukkit.plugin.Plugin
 */
package cc.kunss.vexst.utils;

import cc.kunss.vexst.api.VexStrengThenAPI;
import cc.kunss.vexst.listeners.Click;
import me.clip.placeholderapi.external.EZPlaceholderHook;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class Placeholders
extends EZPlaceholderHook {
    public Placeholders(Plugin plugin) {
        super(plugin, "vexstreng");
    }

    public String onPlaceholderRequest(Player player, String s) {
        if (s.equalsIgnoreCase("level")) {
            return String.valueOf(VexStrengThenAPI.getPlayerStrengLevel(player));
        }
        if (s.equalsIgnoreCase("exp")) {
            return Click.getDecimalFormat(VexStrengThenAPI.getPlayerStrengExp(player));
        }
        if (s.equalsIgnoreCase("maxexp")) {
            return Click.getDecimalFormat(VexStrengThenAPI.getPlayerStringMaxExp(player));
        }
        if (s.equalsIgnoreCase("prefix")) {
            return VexStrengThenAPI.getPlayerStrengPrefix(player);
        }
        return "N/O";
    }
}

