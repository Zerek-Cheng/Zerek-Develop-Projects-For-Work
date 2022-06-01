package com._0myun.minecraft.pixelmonknockout.listeners.init;

import com._0myun.minecraft.pixelmonknockout.DB;
import com._0myun.minecraft.pixelmonknockout.R;
import com._0myun.minecraft.pixelmonknockout.dao.data.PlayerData;
import com._0myun.minecraft.pixelmonknockout.dao.data.PlayerGameData;
import com._0myun.minecraft.pixelmonknockout.data.Game;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;

import java.util.List;
import java.util.UUID;

public class PlayerInitListener implements Listener {
    @EventHandler(priority = EventPriority.LOWEST)
    public void on(AsyncPlayerPreLoginEvent e) {
        try {
            UUID uuid = e.getUniqueId();
            if (DB.playerData.queryForUUID(uuid) == null) {
                PlayerData playerData = new PlayerData();
                playerData.setUuid(uuid);
                playerData.setGame(-1);
                DB.playerData.createIfNotExists(playerData);
                R.INSTANCE.getLogger().info("玩家" + e.getName() + "数据库记录初始化");
            }

            List<PlayerGameData> playerGameData = DB.playerGameData.queryForUUID(uuid);
            f2:
            for (Game game : R.INSTANCE.getGames()) {
                for (PlayerGameData gameData : playerGameData)
                    if (gameData.getGame().equalsIgnoreCase(game.getName())) continue f2;

                PlayerGameData gameData = new PlayerGameData();
                gameData.setUuid(uuid);
                gameData.setGame(game.getName());
                DB.playerGameData.createIfNotExists(gameData);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
