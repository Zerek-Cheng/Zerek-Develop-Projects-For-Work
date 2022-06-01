/*
 * Decompiled with CFR 0_133.
 * 
 * Could not load the following classes:
 *  org.bukkit.ChatColor
 */
package yo;

import org.bukkit.ChatColor;

public enum bd_0 {
    TRASH(ChatColor.GRAY.toString(), "7"),
    COMMON(ChatColor.WHITE.toString(), "f"),
    UNCOMMON(ChatColor.GREEN.toString(), "a"),
    RARE(ChatColor.BLUE.toString(), "9"),
    EPIC(ChatColor.DARK_PURPLE.toString(), "5"),
    LEGENDARY(ChatColor.GOLD.toString(), "6");
    
    public final String colour;
    public final String cCode;

    private bd_0(String colour, String code) {
        this.colour = colour;
        this.cCode = code;
    }
}

