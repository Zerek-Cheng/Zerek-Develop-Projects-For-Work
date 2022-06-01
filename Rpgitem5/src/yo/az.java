// 
// Decompiled by Procyon v0.5.30
// 

package yo;

import org.bukkit.entity.Player;

public class aZ
{
    public static void a(final Player player, final int exp) {
        if (exp < 0) {
            throw new IllegalArgumentException("Experience is negative!");
        }
        player.setExp(0.0f);
        player.setLevel(0);
        player.setTotalExperience(0);
        int amount = exp;
        while (amount > 0) {
            final int expToLevel = a(player);
            amount -= expToLevel;
            if (amount >= 0) {
                player.giveExp(expToLevel);
            }
            else {
                amount += expToLevel;
                player.giveExp(amount);
                amount = 0;
            }
        }
    }
    
    public static int a(final Player player) {
        return a(player.getLevel());
    }
    
    public static int a(final int level) {
        if (level <= 15) {
            return 2 * level + 7;
        }
        if (level >= 16 && level <= 30) {
            return 5 * level - 38;
        }
        return 9 * level - 158;
    }
    
    public static int b(final int level) {
        int currentLevel = 0;
        int exp = 0;
        while (currentLevel < level) {
            exp += a(currentLevel);
            ++currentLevel;
        }
        if (exp < 0) {
            exp = Integer.MAX_VALUE;
        }
        return exp;
    }
    
    public static int b(final Player player) {
        int exp = Math.round(a(player) * player.getExp());
        for (int currentLevel = player.getLevel(); currentLevel > 0; --currentLevel, exp += a(currentLevel)) {}
        if (exp < 0) {
            exp = Integer.MAX_VALUE;
        }
        return exp;
    }
    
    public static int c(final Player player) {
        final int exp = Math.round(a(player) * player.getExp());
        final int nextLevel = player.getLevel();
        return a(nextLevel) - exp;
    }
}
