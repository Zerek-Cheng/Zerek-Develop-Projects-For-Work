package com._0myun.minecraft.vexshopbutton;

import lk.vexview.api.VexViewAPI;
import lk.vexview.hud.VexImageShow;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Random;

public class HudSender extends BukkitRunnable {
    Player p = null;

    public HudSender(Player p) {
        this.p = p;
    }

    @Override
    public void run() {

        if (!p.isOnline()) {
            this.cancel();
            return;
        }
        getConfig().getKeys(false).forEach(key -> {
            if (!getConfig().isConfigurationSection(key)) return;
            VexImageShow img = new VexImageShow(20190407 + new Random().nextInt(20190410),
                    getConfig().getString(key + ".button-img"),
                    getConfig().getInt(key + ".x"),
                    getConfig().getInt(key + ".y"),
                    getConfig().getInt(key + ".w"),
                    getConfig().getInt(key + ".h"),
                    getConfig().getInt(key + ".w"),
                    getConfig().getInt(key + ".h"),
                    3600 * 24
            );
            VexViewAPI.sendHUD(p, img);
        });
    }

    public ConfigurationSection getConfig() {
        return Main.INSTANCE.getConfig();
    }

}
