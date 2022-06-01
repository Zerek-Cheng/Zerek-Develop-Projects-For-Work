package com._0myun.minecraft.peacewarrior.task.hall;

import com._0myun.minecraft.peacewarrior.DBManager;
import com._0myun.minecraft.peacewarrior.dao.data.Sign;
import com._0myun.minecraft.peacewarrior.services.Hall;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class InfoSignRefreshTask extends BukkitRunnable {
    @Override
    public void run() {
        for (Sign sign : DBManager.signDao) {
            try {
                World world = Bukkit.getWorld(sign.getWorld());
                if (world == null) {
                    DBManager.signDao.delete(sign);
                    return;
                }
                for (Player p : world.getPlayers()) {
                    Hall.sendHallSignInfo(p, sign);
                }
            } catch (Exception ex) {
                System.err.println("在发送告示牌数据时出现异常..可能是版本不支持!");
            }
        }
    }
}
