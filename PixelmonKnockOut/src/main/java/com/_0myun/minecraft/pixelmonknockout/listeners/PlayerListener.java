package com._0myun.minecraft.pixelmonknockout.listeners;

import com._0myun.minecraft.pixelmonknockout.DB;
import com._0myun.minecraft.pixelmonknockout.R;
import com._0myun.minecraft.pixelmonknockout.dao.data.PlayerData;
import com._0myun.minecraft.pixelmonknockout.task.game.GameRunner;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.HashMap;

public class PlayerListener implements Listener {
    @EventHandler
    public void on(PlayerQuitEvent e) {
        try {
            Player p = e.getPlayer();
            PlayerData playerData = DB.playerData.queryForUUID(p.getUniqueId());
            if (playerData.getGame() == -1) return;

            HashMap<Integer, GameRunner> runners = R.INSTANCE.getRunners();
            if (runners.containsKey(playerData.getGame())) {
                runners.get(playerData.getGame()).getRound().quit(p.getUniqueId());
                runners.get(playerData.getGame()).getPlayers().remove(p.getUniqueId());
            }

            playerData.setGame(-1);
            DB.playerData.update(playerData);
            R.INSTANCE.getLogger().info("玩家" + p.getName() + "退出游戏,强制退出锦标赛");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
