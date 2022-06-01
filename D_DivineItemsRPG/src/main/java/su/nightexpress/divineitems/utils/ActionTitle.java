/*
 * Decompiled with CFR 0_133.
 * 
 * Could not load the following classes:
 *  org.bukkit.entity.Player
 */
package su.nightexpress.divineitems.utils;

import org.bukkit.entity.Player;
import su.nightexpress.divineitems.DivineItems;
import su.nightexpress.divineitems.nms.NMS;

public class ActionTitle {
    private static DivineItems plugin = DivineItems.instance;

    public static void sendActionBar(Player player, String string) {
        plugin.getNMS().sendActionBar(player, string);
    }

    public static void sendTitles(Player player, String string, String string2, int n, int n2, int n3) {
        plugin.getNMS().sendTitles(player, string, string2, n, n2, n3);
    }
}

