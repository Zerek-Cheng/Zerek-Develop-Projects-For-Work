package com._0myun.minecraft.pixelmonspecialpokemonrefreshmessage;

import org.bukkit.scheduler.BukkitRunnable;

public class Broadcaster extends BukkitRunnable {
    @Override
    public void run() {
        Main.INSTANCE.broadcast();
        //System.out.println("Main.INSTANCE.getConfig().getInt(\"every\") = " + Main.INSTANCE.getConfig().getString("every"));
    }
}
