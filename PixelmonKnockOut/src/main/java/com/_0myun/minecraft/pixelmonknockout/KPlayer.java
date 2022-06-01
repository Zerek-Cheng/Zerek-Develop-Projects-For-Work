package com._0myun.minecraft.pixelmonknockout;


import com._0myun.minecraft.pixelmonknockout.dao.data.PlayerData;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.sql.SQLException;
import java.util.UUID;

public class KPlayer {
    @Getter
    private Player player;

    public KPlayer(String name) {
        this(Bukkit.getPlayer(name));
    }

    public KPlayer(UUID uuid) {
        this(Bukkit.getPlayer(uuid));
    }

    public KPlayer(Player p) {
        this.player = p;
    }

    public PlayerData getPlayerData() throws SQLException {
        return DB.playerData.queryForUUID(getPlayer().getUniqueId());
    }

    public void broadcastSelf(String msg) {
        getPlayer().sendMessage(msg);
    }

    public void broadcastRound(String msg) {
//TODO
    }
}
