/*
 * Decompiled with CFR 0_133.
 * 
 * Could not load the following classes:
 *  org.bukkit.entity.Player
 */
package yo;

import org.bukkit.entity.Player;

public class az_1 {
    public static void a(Player player, int exp) {
        if (exp < 0) {
            throw new IllegalArgumentException("Experience is negative!");
        }
        player.setExp(0.0f);
        player.setLevel(0);
        player.setTotalExperience(0);
        int amount = exp;
        while (amount > 0) {
            int expToLevel = az_1.a(player);
            if ((amount -= expToLevel) >= 0) {
                player.giveExp(expToLevel);
                continue;
            }
            player.giveExp(amount += expToLevel);
            amount = 0;
        }
    }

    public static int a(Player player) {
        return az_1.a(player.getLevel());
    }

    public static int a(int level) {
        if (level <= 15) {
            return 2 * level + 7;
        }
        if (level >= 16 && level <= 30) {
            return 5 * level - 38;
        }
        return 9 * level - 158;
    }

    public static int b(int level) {
        int exp = 0;
        for (int currentLevel = 0; currentLevel < level; ++currentLevel) {
            exp += az_1.a(currentLevel);
        }
        if (exp < 0) {
            exp = Integer.MAX_VALUE;
        }
        return exp;
    }

    public static int b(Player player) {
        int exp = Math.round((float)az_1.a(player) * player.getExp());
        int currentLevel = player.getLevel();
        while (currentLevel > 0) {
            exp += az_1.a(--currentLevel);
        }
        if (exp < 0) {
            exp = Integer.MAX_VALUE;
        }
        return exp;
    }

    public static int c(Player player) {
        int exp = Math.round((float)az_1.a(player) * player.getExp());
        int nextLevel = player.getLevel();
        return az_1.a(nextLevel) - exp;
    }
}

