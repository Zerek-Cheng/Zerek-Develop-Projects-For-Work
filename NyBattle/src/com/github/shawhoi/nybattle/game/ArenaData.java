// 
// Decompiled by Procyon v0.5.30
// 

package com.github.shawhoi.nybattle.game;

import java.util.Iterator;
import java.io.File;
import org.bukkit.plugin.Plugin;
import org.bukkit.entity.Player;
import com.github.shawhoi.nybattle.Main;
import org.bukkit.Bukkit;
import com.github.shawhoi.nybattle.playerdata.PlayerData;
import java.util.HashMap;

public class ArenaData
{
    public static HashMap<String, BattleArena> badata;
    
    public static void addArena(final String an, final BattleArena ba) {
        ArenaData.badata.put(an, ba);
    }
    
    public static void removeArena(final String an) {
        if (ArenaData.badata.containsKey(an)) {
            ArenaData.badata.remove(an);
        }
    }
    
    public static void ResetAllArena() {
        for (final String name : PlayerData.playerdata.keySet()) {
            final Player p = Bukkit.getPlayerExact(name);
            if (p != null && p.isOnline()) {
                Bukkit.getScheduler().runTask((Plugin)Main.getInstance(), (Runnable)new Runnable() {
                    @Override
                    public void run() {
                        PlayerData.getPlayerGameArena(p).PlayerLeave(p, false);
                    }
                });
            }
        }
        for (final BattleArena arena : ArenaData.badata.values()) {
            arena.FailStop();
        }
        ArenaData.badata.clear();
        final File arenas = new File(Main.getInstance().getDataFolder(), "Arena");
        for (final File i : arenas.listFiles()) {
            try {
                final BattleArena ba = new BattleArena(i);
                addArena(i.getName().split(".yml")[0], ba);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    
    static {
        ArenaData.badata = new HashMap<String, BattleArena>();
    }
}
