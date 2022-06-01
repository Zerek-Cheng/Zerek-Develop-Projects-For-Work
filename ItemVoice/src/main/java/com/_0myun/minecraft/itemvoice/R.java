package com._0myun.minecraft.itemvoice;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public final class R extends JavaPlugin implements Listener {
    List<UUID> killing_player = new ArrayList<>();

    List<UUID> killing_entity = new ArrayList<>();

    @Override
    public void onEnable() {
        saveDefaultConfig();
        Bukkit.getPluginManager().registerEvents(this, this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        reloadConfig();
        sender.sendMessage("oks");
        Player p = (Player) sender;
        p.playSound(p.getLocation(), "shoot.ak", 1F, 0F);
        return true;
    }

    public String getType(ItemStack itemStack) {
        if (itemStack == null || itemStack.getType().equals(Material.AIR) || !itemStack.hasItemMeta() || !itemStack.getItemMeta().hasLore())
            return null;
        List<String> lores = itemStack.getItemMeta().getLore();
        for (String lore : getConfig().getConfigurationSection("lores").getKeys(false)) {
            if (!lores.toString().contains(lore)) continue;
            return getConfig().getString("lores." + lore);
        }
        return null;
    }

    public List<String> getTypes(ItemStack[] items) {
        List<String> result = new ArrayList<>();
        for (ItemStack item : items) {
            String type = getType(item);
            if (type == null || type.isEmpty()) continue;
            if (result.contains(type)) continue;
            result.add(type);
        }
        return result;
    }

    public List<String> getTypes(Player p) {
        ArrayList<ItemStack> itemStacks = new ArrayList<>(Arrays.asList(p.getEquipment().getArmorContents()));
        itemStacks.add(p.getItemInHand());
        return getTypes(itemStacks.toArray(new ItemStack[0]));
    }

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent e) {
        if (!(e.getDamager() instanceof Player)) return;
        playSound((Player) e.getDamager(), "attack");
    }

    @EventHandler
    public void onDamaged(EntityDamageByEntityEvent e) {
        if (!(e.getEntity() instanceof Player)) return;
        playSound((Player) e.getEntity(), "beAttacked");
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        if (!e.getAction().equals(Action.RIGHT_CLICK_AIR) && !e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) return;
        Player p = e.getPlayer();
        playSound(p, "right");
    }

    @EventHandler
    public void onKillPlayer(PlayerDeathEvent e) {
        Player p = e.getEntity();
        Player killer = p.getKiller();
        if (killer != null && killer.isOnline()) {
            if (killing_player.contains(killer.getUniqueId())) {//连续击杀
                playSound(killer, "kills");
            } else {//不是连续击杀
                playSound(killer, "kill");
            }
        }

        if (killing_player.contains(p.getUniqueId())) {//终结
            playSound(p, "beKilledWhenKilling");//被别人终结
            if (killer != null && killer.isOnline())
                playSound(killer, "killsWhenKilling");//终结别人
        } else {//不是被终结
            playSound(p, "beKilled");
        }
        if (killer != null && killer.isOnline())
            killing_player.add(killer.getUniqueId());

        killing_player.remove(p.getUniqueId());
    }

    @EventHandler
    public void onKill(EntityDeathEvent e) {
        LivingEntity entity = e.getEntity();
        Player killer = entity.getKiller();
        if (entity instanceof Player) return;
        if (!(killer instanceof Player)) return;
        if (killer != null && killer.isOnline()) {
            if (killing_entity.contains(killer.getUniqueId())) {//连续击杀
                playSound(killer, "kills_entity");
            } else {//不是连续击杀
                playSound(killer, "kill_entity");
            }
        }
        killing_entity.add(killer.getUniqueId());
    }

    @EventHandler
    public void onKill2(EntityDeathEvent e) {
        LivingEntity entity = e.getEntity();
        Player killer = entity.getKiller();
        if (killer instanceof Player) return;
        if (!(entity instanceof Player)) return;
        killing_entity.remove(entity.getUniqueId());
    }

    public void playSound(Player p, String doType) {
        List<String> types = getTypes(p);
        types.forEach(type -> {
            ConfigurationSection config = getConfig().getConfigurationSection("voice." + type + "." + doType);
            if (config == null) return;
            if (Math.random() > config.getDouble("rand")) return;
            String file = config.getString("file");
            double voice = config.getDouble("voice");
            if (config.getInt("mode") == 1) {
                p.playSound(p.getLocation(), file, (float) voice, 0F);
            } else if (config.getInt("mode") == 2) {
                for (Player player : Bukkit.getOnlinePlayers()) {
                    if (!player.getLocation().getWorld().equals(p.getLocation().getWorld())) continue;
                    player.playSound(p.getLocation(), file, (float) voice, 0F);
                }
            }
        });

    }
}