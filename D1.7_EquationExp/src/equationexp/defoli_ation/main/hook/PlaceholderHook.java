/*
 * Decompiled with CFR 0_133.
 * 
 * Could not load the following classes:
 *  me.clip.placeholderapi.external.EZPlaceholderHook
 *  org.bukkit.entity.Player
 *  org.bukkit.plugin.Plugin
 */
package equationexp.defoli_ation.main.hook;

import equationexp.defoli_ation.main.exp.Exp;
import equationexp.defoli_ation.main.file.expfile.PlayerExpFile;
import me.clip.placeholderapi.external.EZPlaceholderHook;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class PlaceholderHook
extends EZPlaceholderHook {
    private Exp exp;
    private PlayerExpFile file;

    public PlaceholderHook(Plugin plugin, String identifier, PlayerExpFile file, Exp exp) {
        super(plugin, identifier);
        this.file = file;
        this.exp = exp;
    }

    public String onPlaceholderRequest(Player arg0, String arg1) {
        if (arg0 == null) {
            return new String();
        }
        if (arg1.equals("exp")) {
            return new Integer(this.file.getPlayerExp(arg0)).toString();
        }
        if (arg1.equals("need")) {
            return new Integer(this.exp.getExp(arg0.getLevel() + 1) - this.file.getPlayerExp(arg0)).toString();
        }
        if (arg1.equals("level")) {
            return new Integer(arg0.getLevel()).toString();
        }
        if (arg1.equals("levelexp")) {
            return new Integer(this.file.getPlayerExp(arg0) - this.exp.getExp(arg0.getLevel())).toString();
        }
        return new String();
    }
}

