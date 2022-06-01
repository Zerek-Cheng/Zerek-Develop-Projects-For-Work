package com._0myun.minecraft.entitydeadthruncommand;

import noppes.npcs.entity.EntityCustomNpc;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_7_R4.entity.CraftEntity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.Field;

public final class EntityDeadthRunCommand extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        saveDefaultConfig();
        Bukkit.getPluginManager().registerEvents(this, this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        reloadConfig();
        sender.sendMessage("ok");
        return true;
    }

    @EventHandler
    public void on(EntityDeathEvent e) {
        try {
            LivingEntity entity = e.getEntity();
            String customName = entity.getCustomName();
            Field entity1 = CraftEntity.class.getDeclaredField("entity");

            entity1.setAccessible(true);
            Object nmsE = entity1.get(entity);
            if (!(nmsE instanceof EntityCustomNpc)) return;
            EntityCustomNpc npc = (EntityCustomNpc) nmsE;
            String nameStr = nmsE.toString();
            String[] split = nameStr.split("'");
            customName = split[1];

/*
            Method method = nmsE.getClass().getDeclaredMethod("getName");
            method.setAccessible(true);
            customName = (String) method.invoke(nmsE);*/
               if (!getConfig().isSet("entity." + customName)) return;
            for (String cmd : getConfig().getStringList("entity." + customName))
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), cmd);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
