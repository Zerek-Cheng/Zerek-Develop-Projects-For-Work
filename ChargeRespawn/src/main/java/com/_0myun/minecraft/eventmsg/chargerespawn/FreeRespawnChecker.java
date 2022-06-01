package com._0myun.minecraft.eventmsg.chargerespawn;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

public class FreeRespawnChecker extends BukkitRunnable {
    @Override
    public void run() {
        try {
            Bukkit.getOnlinePlayers().forEach((p) -> {
                if (!RespawnManager.isDeath(p)) return;//没死就跳过
                if (!RespawnManager.canFreeRespawn(p)) {
                    p.sendTitle(LangUtils.get("lang12", p), LangUtils.get("lang13", p), 10, 20, 10);
                    return;
                }//没到免费复活时间就跳过
                RespawnManager.freeRespawn(p);
                p.sendMessage(LangUtils.get("lang3", p));
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
