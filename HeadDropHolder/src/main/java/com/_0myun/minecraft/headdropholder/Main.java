package com._0myun.minecraft.headdropholder;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

public final class Main extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        saveDefaultConfig();
        Bukkit.getPluginManager().registerEvents(this, this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        this.reloadConfig();
        sender.sendMessage("ok");
        return true;
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onDrop(EntityDeathEvent e) {
        if (getConfig().getBoolean("debug"))e.getEntity().getKiller().sendMessage(e.getEntity().getType().toString());
        LivingEntity entity = e.getEntity();
        List<ItemStack> drops = e.getDrops();
        if (!getConfig().getStringList("mobs").contains(entity.getType().toString())) return;
        ConfigurationSection config = getConfig().getConfigurationSection("head." + entity.getType().toString());
        if (config == null) return;
        drops.removeIf((itemStack -> itemStack.getTypeId() == 397));
        if (Math.random() > config.getDouble("rand")) return;
        drops.add(new ItemStack(397, 1, (short) config.getInt("type")));
    }
}
