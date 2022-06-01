package com.lmyun.event.skill;

import org.bukkit.Effect;
import org.bukkit.EntityEffect;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerMoveEvent;

public class Listener implements org.bukkit.event.Listener {
    Assault assault;

    public Listener(Assault assault) {
        this.assault = assault;
    }

    @EventHandler
    public void playerMove(PlayerMoveEvent e) {
        Player p = e.getPlayer();
        Location from = e.getFrom();
        Location to = e.getTo();
        String effect = this.assault.assaultEffect.get(p.getName());
        if (!this.assault.assaultList.contains(p.getName())) {
            return;
        }
        if (Effect.valueOf(effect) == null) {
            effect = Effect.ENDER_SIGNAL.getName();
            p.sendMessage("粒子效果不存在,已设置为默认粒子效果");
        }
        p.playEffect(p.getLocation(), Effect.valueOf(effect), 0);

    }
}
