package com_0myun.minecraft.nukkit.eventmsg.disdurable;

import cn.nukkit.Nukkit;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.player.PlayerItemConsumeEvent;
import cn.nukkit.plugin.PluginBase;

public class Main extends PluginBase implements Listener {
    @Override
    public void onEnable() {
        this.getServer().getPluginManager().registerEvents(this, this);
    }

    @EventHandler
    public void onUse(PlayerItem e) {
        e.setCancelled(true);
    }
}
