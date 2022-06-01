package com._0myun.minecraft.skillsprint;

import com.comphenix.protocol.wrappers.nbt.NbtCompound;
import com.comphenix.protocol.wrappers.nbt.NbtFactory;
import com.comphenix.protocol.wrappers.nbt.NbtWrapper;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public final class SkillSprint extends JavaPlugin implements Listener {
    HashMap<UUID, Long> useTime = new HashMap<>();

    @Override
    public void onEnable() {
        saveDefaultConfig();
        Bukkit.getPluginManager().registerEvents(this, this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        reloadConfig();
        sender.sendMessage("reload ok!");
        return true;
    }

    @EventHandler
    public void onRight(PlayerInteractEvent e) {
        if (!e.getAction().equals(Action.RIGHT_CLICK_BLOCK) && !e.getAction().equals(Action.RIGHT_CLICK_AIR)) return;
        Player p = e.getPlayer();
        ItemStack itemInHand = p.getItemInHand();
        if (!hasSprint(itemInHand)) return;
        if (!canUse(itemInHand)) {
            p.sendMessage(getConfig().getString("lang4"));
            return;
        }
        useTime.put(p.getUniqueId(), System.currentTimeMillis());
        Vector vector = p.getLocation().getDirection();
        vector.multiply(getConfig().getInt("mult"));
        vector.setY(0);
        p.setVelocity(vector);
        p.sendMessage(getConfig().getString("lang1"));
        setLast(itemInHand);
    }

    @EventHandler
    public void onMove(PlayerMoveEvent e) {
        Player p = e.getPlayer();
        if (!useTime.containsKey(p.getUniqueId())) useTime.put(p.getUniqueId(), 0l);
        long validate = Long.valueOf(useTime.get(p.getUniqueId()).longValue() + getConfig().getLong("time"));
        if (validate < System.currentTimeMillis()) return;
        List<Entity> entities = p.getNearbyEntities(getConfig().getDouble("near"), getConfig().getDouble("near"), getConfig().getDouble("near"));
        for (Entity entity : entities) {
            if (!(entity instanceof LivingEntity)) continue;
            LivingEntity livingEntity = (LivingEntity) entity;
            livingEntity.damage(livingEntity.getMaxHealth() * 0.2d);
        }
    }


    public boolean hasSprint(ItemStack itemStack) {
        if (itemStack == null || !itemStack.hasItemMeta() || !itemStack.getItemMeta().hasLore()) return false;
        List<String> lores = itemStack.getItemMeta().getLore();
        for (String lore : lores)
            if (lore.contains(getConfig().getString("lore"))) return true;
        return false;
    }

    public long getLast(ItemStack itemStack) {
        NbtWrapper<?> nbtWrapper = NbtFactory.fromItemTag(itemStack);
        NbtCompound nbt = NbtFactory.asCompound(nbtWrapper);
        return nbt.getLongOrDefault("com._0myun.minecraft.skillsprint.SkillSprint");
    }


    public void setLast(ItemStack itemStack) {
        NbtWrapper<?> nbtWrapper = NbtFactory.fromItemTag(itemStack);
        NbtCompound nbt = NbtFactory.asCompound(nbtWrapper);
        nbt.put("com._0myun.minecraft.skillsprint.SkillSprint", System.currentTimeMillis());
        NbtFactory.setItemTag(itemStack, nbt);
    }

    public boolean canUse(ItemStack itemStack) {
        if (!hasSprint(itemStack)) return false;
        return getLast(itemStack) + getConfig().getLong("cd") <= System.currentTimeMillis();
    }
}
