/*
 * Decompiled with CFR 0_133.
 * 
 * Could not load the following classes:
 *  org.bukkit.Bukkit
 */
package su.nightexpress.divineitems.utils;

import org.bukkit.Bukkit;
import su.nightexpress.divineitems.Module;

public class ErrorLog {
    public static void sendError(Module module, String string, String string2, boolean bl) {
        Bukkit.getConsoleSender().sendMessage("\u00a7c-=-=-=-=-=-=-=-=-=-=-=-=-=-");
        Bukkit.getConsoleSender().sendMessage("[\u00a7cDivineItems\u00a77] \u00a7c\u00a7lWARNING CONFIGURATION ERROR:");
        Bukkit.getConsoleSender().sendMessage("[\u00a7cDivineItems\u00a77] \u00a7fModule: \u00a7c" + module.name());
        Bukkit.getConsoleSender().sendMessage("[\u00a7cDivineItems\u00a77] \u00a7fEntry: \u00a7c" + string.replace(".", " -> "));
        Bukkit.getConsoleSender().sendMessage("[\u00a7cDivineItems\u00a77] \u00a7fInfo: \u00a7c" + string2);
        if (bl) {
            Bukkit.getConsoleSender().sendMessage("[\u00a7aDivineItems\u00a77] \u00a7aThis error has been fixed automatically!");
        } else {
            Bukkit.getConsoleSender().sendMessage("[\u00a7cDivineItems\u00a77] \u00a7cThis error can not be fixed automatically.");
        }
        Bukkit.getConsoleSender().sendMessage("\u00a7c-=-=-=-=-=-=-=-=-=-=-=-=-=-");
        Bukkit.getConsoleSender().sendMessage("");
    }
}

