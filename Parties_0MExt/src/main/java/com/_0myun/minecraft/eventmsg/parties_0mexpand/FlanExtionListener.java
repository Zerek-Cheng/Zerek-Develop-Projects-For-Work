package com._0myun.minecraft.eventmsg.parties_0mexpand;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import java.util.ArrayList;

public class FlanExtionListener implements Listener {
    @EventHandler
    public void onDamaged(EntityDamageByEntityEvent e) {
        Entity entity = e.getEntity();
        if (!(entity instanceof Player)) return;//被击中不是玩家
        Player p = (Player) entity;
        if (!Parties_0MExpand.api.haveParty(p.getUniqueId())) return;//没有队伍
        ArrayList<Player> partyPlayers = Parties_0MExpand.api.getPartyOnlinePlayers(Parties_0MExpand.api.getPartyName(p.getUniqueId()));
        if (!("FLANSMOD_BULLET".equals(e.getDamager().getType().name()) || "FLANSMODX_BULLET".equals(e.getDamager().getType().name()))) {//不是子弹
            return;
        }
        Player damager = null;
        try {
            Object bullet = e.getDamager().getClass().getMethod("getHandle").invoke(e.getDamager());
            Object owner = bullet.getClass().getField("owner").get(bullet);
            Object bukkitEntity = owner.getClass().getMethod("getBukkitEntity").invoke(owner);
            if (bukkitEntity instanceof Player) {
                damager = (Player) bukkitEntity;
            }
        } catch (Exception ex) {

        }
        if (damager == null) return;//获取失败
        boolean[] isTeamMember = {false};
        Player finalDamager = damager;
        partyPlayers.forEach((tp) -> {
            if (!finalDamager.getUniqueId().equals(tp.getUniqueId()))return;
            isTeamMember[0]=true;
        });
        if (!isTeamMember[0]){//不是队友
            return;
        }
        e.setCancelled(true);
    }
}
