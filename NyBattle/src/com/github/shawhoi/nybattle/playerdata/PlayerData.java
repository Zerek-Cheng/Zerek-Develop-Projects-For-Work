// 
// Decompiled by Procyon v0.5.30
// 

package com.github.shawhoi.nybattle.playerdata;

import com.github.shawhoi.nybattle.game.ArenaData;
import com.github.shawhoi.nybattle.game.BattleArena;
import org.bukkit.entity.Player;
import java.util.HashMap;

public class PlayerData
{
    public static HashMap<String, String> playerdata;
    public static HashMap<String, PlayerGameData> playergamedata;
    
    public static boolean PlayerInGame(final Player player) {
        return PlayerData.playerdata.containsKey(player.getName());
    }
    
    public static BattleArena getPlayerGameArena(final Player player) {
        return ArenaData.badata.get(PlayerData.playerdata.get(player.getName()));
    }
    
    public static void setPlayerGameData(final Player p) {
        if (PlayerData.playergamedata.containsKey(p.getName())) {
            PlayerData.playergamedata.get(p.getName()).reset();
            PlayerData.playergamedata.remove(p.getName());
        }
    }
    
    static {
        PlayerData.playerdata = new HashMap<String, String>();
        PlayerData.playergamedata = new HashMap<String, PlayerGameData>();
    }
}
