package com._0myun.minecraft.ishipentityteleport;

import com.lulan.shincolle.entity.BasicEntityShip;
import com.lulan.shincolle.entity.IShipOwner;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_7_R4.entity.CraftEntity;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public final class IShipEntityTeleport extends JavaPlugin implements Listener {
    List<UUID> wait = new ArrayList<>();
    public HashMap<UUID, Entity> tp = new HashMap<>();

    @Override
    public void onEnable() {
        saveDefaultConfig();
        Bukkit.getPluginManager().registerEvents(this, this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length==1&&args[0].equalsIgnoreCase("reload")){
            reloadConfig();
            return true;
        }
        Player p = (Player) sender;
        if (args.length == 1 && args[0].equalsIgnoreCase("from")) {
            wait.add(p.getUniqueId());
            p.sendMessage(getConfig().getString("lang.lang3"));
            return true;
        }
        if (args.length == 1 && args[0].equalsIgnoreCase("to")) {
            if (!tp.containsKey(p.getUniqueId())){
                p.sendMessage(getConfig().getString("lang.lang4"));
                return true;
            }
            wait.remove(p.getUniqueId());
            Entity entity = tp.get(p.getUniqueId());
            tp.remove(p.getUniqueId());
            entity.teleport(p);
            p.sendMessage(getConfig().getString("lang.lang5"));
            return true;
        }
        return true;
    }

    @EventHandler
    public void on(PlayerInteractEntityEvent e) {
        try {
            Player p = e.getPlayer();
            if (!wait.contains(p.getUniqueId())) return;

            Entity entity = e.getRightClicked();
            CraftEntity ce = (CraftEntity) entity;
            Field entityField = CraftEntity.class.getDeclaredField("entity");
            entityField.setAccessible(true);
            Object nmsEntity = entityField.get(ce);
            if (!(nmsEntity instanceof BasicEntityShip)) return;
            BasicEntityShip ship = (BasicEntityShip) nmsEntity;
            String owner = getOwner(ship);
            if (owner == null || !p.getName().equalsIgnoreCase(owner)) {
                p.sendMessage(getConfig().getString("lang.lang1"));
                return;
            }
            tp.put(p.getUniqueId(), entity);
            p.sendMessage(getConfig().getString("lang.lang2"));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public String getOwner(BasicEntityShip ship) {
        IShipOwner owner = ship;
        return owner.getHostEntity() == null ? null : owner.getHostEntity().func_70005_c_();
    }
}
