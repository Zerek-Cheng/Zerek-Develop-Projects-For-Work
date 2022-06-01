package com._0myun.minecraft.pixelmonknockout.task.game;

import com._0myun.minecraft.pixelmonknockout.DB;
import com._0myun.minecraft.pixelmonknockout.R;
import com._0myun.minecraft.pixelmonknockout.dao.data.Baned;
import org.bukkit.Bukkit;

import java.text.SimpleDateFormat;
import java.util.Date;

public class BanedTimeoutTask implements Runnable {
    @Override
    public void run() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日 HH:mm");//2019年6月7日 20:00分
        try {
            for (Baned baned : DB.baned) {
                if (format.parse(baned.getEnd()).after(new Date())) continue;
                DB.baned.delete(baned);
                R.INSTANCE.getLogger().info("玩家" + Bukkit.getOfflinePlayer(baned.getUuid()).getName() + "锦标赛" + baned.getGame() + "封禁已到期");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
