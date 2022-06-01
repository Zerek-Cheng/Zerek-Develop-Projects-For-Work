package com.lmyun.event.banandbuff;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;


public class GroupKillListener implements Listener {

    private Main plugin;

    public GroupKillListener(Main main) {
        this.plugin = main;
    }

    @EventHandler
    public void onKill(EntityDamageByEntityEvent e) {
        Entity damager = e.getDamager();
        Entity entity = e.getEntity();
        if (!(damager instanceof Player) || !(entity instanceof Player)) {
            return;
        }
        if (e.getFinalDamage() <= ((Player) entity).getHealth()) {
            return;
        }
        Player pD = (Player) damager;
        Player pE = (Player) entity;
        double enemyMoney = this.plugin.getEnemyMoney(Main.permission.getPlayerGroups(pE)[0], Main.permission.getPlayerGroups(pD)[0]);
        if(enemyMoney==-1){
            return;
        }
        double pEMoneyTaken = Main.economy.getBalance(pE) * enemyMoney;
        Main.economy.withdrawPlayer(pE, pEMoneyTaken);
        Main.economy.depositPlayer(pD, pEMoneyTaken);
        pD.sendMessage(LangUtil.getLang("lang4").replaceAll("<money>", String.valueOf(pEMoneyTaken)));
        pE.sendMessage(LangUtil.getLang("lang5").replaceAll("<money>", String.valueOf(pEMoneyTaken)));
    }
}
