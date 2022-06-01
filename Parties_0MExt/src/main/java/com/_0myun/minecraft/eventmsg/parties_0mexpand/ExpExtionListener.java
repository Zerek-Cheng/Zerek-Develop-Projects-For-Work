package com._0myun.minecraft.eventmsg.parties_0mexpand;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerExpChangeEvent;

public class ExpExtionListener implements Listener {
    @EventHandler
    public void onExpChange(PlayerExpChangeEvent e) {
        Player p = e.getPlayer();
        int amount = e.getAmount();
        if (amount < 0) return;
        if (!Parties_0MExpand.api.haveParty(p.getUniqueId())) return;//没有队伍
        int add = (int) (amount * Parties_0MExpand.plugin.getConfig().getDouble("exp"));
        e.setAmount(amount + add);
        p.sendMessage(LangUtils.get("lang1").replace("<amount>", String.valueOf(add)));
    }
}
