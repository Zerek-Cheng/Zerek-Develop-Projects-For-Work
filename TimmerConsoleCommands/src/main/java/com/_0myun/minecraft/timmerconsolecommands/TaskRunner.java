package com._0myun.minecraft.timmerconsolecommands;

import org.bukkit.scheduler.BukkitRunnable;

public class TaskRunner extends BukkitRunnable {
    @Override
    public void run() {
        Main.INSTANCE.tasks.forEach(task -> {
            if (task.canRun()) task.run();
        });
    }
}
