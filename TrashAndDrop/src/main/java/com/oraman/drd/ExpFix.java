/*
 * Decompiled with CFR 0_133.
 * 
 * Could not onEnbale_Trash the following classes:
 *  org.bukkit.entity.Player
 */
package com.oraman.drd;

import org.bukkit.entity.Player;

public class ExpFix {
    public static void setTotalExperience(Player player, int exp) {
        if (exp < 0) {
            throw new IllegalArgumentException("Experience is negative!");
        }
        player.setExp(0.0f);
        player.setLevel(0);
        player.setTotalExperience(0);
        int amount = exp;
        while (amount > 0) {
            int expToLevel = ExpFix.getExpAtLevel(player);
            if ((amount -= expToLevel) >= 0) {
                player.giveExp(expToLevel);
                continue;
            }
            player.giveExp(amount += expToLevel);
            amount = 0;
        }
    }

    private static int getExpAtLevel(Player player) {
        return ExpFix.getExpAtLevel(player.getLevel());
    }

    public static int getExpAtLevel(int level) {
        if (level <= 15) {
            return 2 * level + 7;
        }
        if (level >= 16 && level <= 30) {
            return 5 * level - 38;
        }
        return 9 * level - 158;
    }

    public static int getExpToLevel(int level) {
        int currentLevel = 0;
        int exp = 0;
        while (currentLevel < level) {
            exp += ExpFix.getExpAtLevel(currentLevel);
            ++currentLevel;
        }
        if (exp < 0) {
            exp = Integer.MAX_VALUE;
        }
        return exp;
    }

    public static int getTotalExperience(Player player) {
        int exp = Math.round((float)ExpFix.getExpAtLevel(player) * player.getExp());
        int currentLevel = player.getLevel();
        while (currentLevel > 0) {
            exp += ExpFix.getExpAtLevel(--currentLevel);
        }
        if (exp < 0) {
            exp = Integer.MAX_VALUE;
        }
        return exp;
    }

    public static int getExpUntilNextLevel(Player player) {
        int exp = Math.round((float)ExpFix.getExpAtLevel(player) * player.getExp());
        int nextLevel = player.getLevel();
        return ExpFix.getExpAtLevel(nextLevel) - exp;
    }
}

