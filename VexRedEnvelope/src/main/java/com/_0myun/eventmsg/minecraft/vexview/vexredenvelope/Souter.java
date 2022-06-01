package com._0myun.eventmsg.minecraft.vexview.vexredenvelope;

import org.bukkit.scheduler.BukkitRunnable;

import java.util.logging.Level;

public class Souter extends BukkitRunnable {
    @Override
    public void run() {
        VexRedEnvelope.plugin.getLogger().log(Level.WARNING, "本插件永久收费,免费给你的都是骗子,赶紧去投诉.:(");
        VexRedEnvelope.plugin.getLogger().log(Level.WARNING, "本插件版权解释权归属于灵梦云科技0MYUN.COM(QQ2025255093)");
    }
}
