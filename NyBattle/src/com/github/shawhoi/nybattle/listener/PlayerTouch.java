// 
// Decompiled by Procyon v0.5.30
// 

package com.github.shawhoi.nybattle.listener;

import org.bukkit.inventory.Inventory;
import com.github.shawhoi.nybattle.playerdata.PlayerData;
import org.bukkit.entity.Player;
import com.github.shawhoi.nybattle.Main;
import com.gmail.filoghost.holographicdisplays.api.Hologram;
import com.gmail.filoghost.holographicdisplays.api.handler.TouchHandler;

public class PlayerTouch implements TouchHandler
{
    private Hologram h;
    private Main main;
    
    public void onTouch(final Player player) {
        if (this.main.hasTouch(player.getName())) {
            return;
        }
        if (PlayerData.PlayerInGame(player) && PlayerData.getPlayerGameArena(player).hasHologramInventory(this.h)) {
            final Inventory inv = PlayerData.getPlayerGameArena(player).getHologramInventory(this.h);
            this.main.addTouch(player.getName());
            if (inv != null) {
                player.openInventory(inv);
            }
        }
    }
    
    public PlayerTouch(final Main main, final Hologram h) {
        this.main = main;
        this.h = h;
    }
}
