package com._0myun.minecraft.denymodremover;

import com.bekvon.bukkit.residence.Residence;
import com.bekvon.bukkit.residence.api.ResidenceApi;
import com.bekvon.bukkit.residence.protection.ClaimedResidence;
import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.java.JavaPlugin;

import static org.bukkit.event.block.Action.RIGHT_CLICK_BLOCK;

public final class Main extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        Bukkit.getPluginManager().registerEvents(this,this);
    }

    @EventHandler
    public void onUse(PlayerInteractEvent e){
        if (!e.getAction().equals(RIGHT_CLICK_BLOCK)) return;
        Player p = e.getPlayer();
        Block block = e.getClickedBlock();
        ClaimedResidence res = ResidenceApi.getResidenceManager().getByLoc(block.getLocation());
        if (!res.isOwner(p)){
            p.sendMessage("你不是领地拥有人,无法破坏点击...");
            e.setCancelled(true);
        }
        System.out.println(block);
       // e.setCancelled(true);
    }
}
