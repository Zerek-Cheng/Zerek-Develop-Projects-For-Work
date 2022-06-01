package com._0myun.minecraft.projectilefuckblock;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.List;

public final class R extends JavaPlugin implements Listener {
    HashMap<Location, Long> last = new HashMap<>();
    HashMap<Location, Integer> durable = new HashMap<>();

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

    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void onInteract(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        int damage = getDamage(p.getItemInHand());
        Block block = e.getClickedBlock();

        if (getConfig().getBoolean("debug")) {
            System.out.println("damage = " + damage);
            System.out.println("block = " + block.getType().toString());
        }
        if (!e.getAction().equals(Action.LEFT_CLICK_BLOCK)) return;//是否左键
        if (block == null || block.getType().equals(Material.AIR)) return;//方块是否为存在的
        if (!getConfig().isSet("block." + block.getType().toString())) return;//方块是否配置
        if (damage <= 0) {
            p.sendMessage(getConfig().getString("lang.lang4"));
            return;
        }

        if (last.containsKey(block.getLocation()))
            if (System.currentTimeMillis() - last.get(block.getLocation()) < getConfig().getInt("min-time")) {
                //p.sendMessage(getConfig().getString("lang.lang3"));
                return;
            }


        if (!durable.containsKey(block.getLocation())) durable.put(block.getLocation(), 0);
        durable.put(block.getLocation(), durable.get(block.getLocation()) + damage);

        p.sendMessage(String.format(getConfig().getString("lang.lang1")
                , String.valueOf(getConfig().getInt("block." + block.getType().toString(), 0) - durable.get(block.getLocation()))
                , String.valueOf(getConfig().getInt("block." + block.getType().toString(), 0))));

        last.put(block.getLocation(), System.currentTimeMillis());

        if (durable.get(block.getLocation()) >= getConfig().getInt("block." + block.getType().toString(), 0)) {
            block.setType(Material.AIR);
            p.sendMessage(getConfig().getString("lang.lang2"));
        }
    }

    public int getDamage(ItemStack itemStack) {
        if (itemStack == null || itemStack.getType().equals(Material.AIR) || !itemStack.hasItemMeta() || !itemStack.getItemMeta().hasLore())
            return 0;
        List<String> lores = itemStack.getItemMeta().getLore();
        for (String lore : getConfig().getConfigurationSection("lores").getKeys(false)) {
            if (!lores.toString().contains(lore)) continue;
            return getConfig().getInt("lores." + lore);
        }
        return 0;
    }
}
