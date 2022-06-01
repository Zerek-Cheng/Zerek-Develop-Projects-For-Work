package com._0myun.minecraft.payfordeathnotdrop;

import com.comphenix.protocol.utility.StreamSerializer;
import com.comphenix.protocol.wrappers.nbt.NbtCompound;
import com.comphenix.protocol.wrappers.nbt.NbtFactory;
import com.comphenix.protocol.wrappers.nbt.NbtWrapper;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

public final class Main extends JavaPlugin implements Listener {
    public static Economy economy = null;

    private boolean setupEconomy() {
        saveDefaultConfig();
        RegisteredServiceProvider<Economy> economyProvider = getServer().getServicesManager().getRegistration(net.milkbowl.vault.economy.Economy.class);
        if (economyProvider != null) {
            economy = economyProvider.getProvider();
        }

        return (economy != null);
    }

    @Override
    public void onEnable() {
        saveDefaultConfig();
        setupEconomy();
        Bukkit.getPluginManager().registerEvents(this, this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player p = (Player) sender;
        ItemStack item = p.getItemInHand();
        if (item == null || item.getType().equals(Material.AIR)) {
            p.sendMessage(getConfig().getString("lang4"));
            return true;
        }
        if (args.length == 1 && args[0].equalsIgnoreCase("bind")) {
            if (this.isBind(item)) {
                p.sendMessage(getConfig().getString("lang5"));
                return true;
            }
            if (!economy.has(p, getConfig().getInt("price"))) {
                p.sendMessage(getConfig().getString("lang3"));
                return true;
            }
            economy.withdrawPlayer(p, getConfig().getInt("price"));
            this.bind(item);
            p.sendMessage(getConfig().getString("lang1"));
            return true;
        }
        if (args.length == 1 && args[0].equalsIgnoreCase("unbind")) {
            NbtWrapper<?> nbtWrapper = NbtFactory.fromItemTag(item);
            NbtCompound nbt = NbtFactory.asCompound(nbtWrapper);
            nbt = nbt.put("com._0myun.minecraft.payfordeathnotdrop.bind", 0);
            NbtFactory.setItemTag(item, nbt);
            p.sendMessage(getConfig().getString("lang6"));
            return true;
        }
        return true;
    }

    public void bind(ItemStack itemStack) {
        NbtWrapper<?> nbtWrapper = NbtFactory.fromItemTag(itemStack);
        NbtCompound nbt = NbtFactory.asCompound(nbtWrapper);
        nbt = nbt.put("com._0myun.minecraft.payfordeathnotdrop.bind", 1);
        NbtFactory.setItemTag(itemStack, nbt);
    }

    public boolean isBind(ItemStack itemStack) {
        if (itemStack == null || itemStack.getType().equals(Material.AIR)) return false;
        NbtWrapper<?> nbtWrapper = NbtFactory.fromItemTag(itemStack);
        NbtCompound nbt = NbtFactory.asCompound(nbtWrapper);
        return nbt.getIntegerOrDefault("com._0myun.minecraft.payfordeathnotdrop.bind") == 1;
    }

    @EventHandler(ignoreCancelled = true)
    public void onDeathDrop(PlayerDeathEvent e) {
        List<ItemStack> drops = e.getDrops();
        Iterator<ItemStack> iter = drops.iterator();
        while (iter.hasNext()) {
            ItemStack item = iter.next();
            if (!this.isBind(item)) return;
            this.addDropItem(e.getEntity().getUniqueId(), item);
            iter.remove();
        }
        saveDefaultConfig();
    }

    @EventHandler
    public void onRespawn(PlayerRespawnEvent e) {
        Player p = e.getPlayer();
        List<String> list = getConfig().getStringList("data." + p.getUniqueId().toString());
        list = list == null ? new ArrayList<>() : list;
        list.forEach(itemStr -> {
            try {
                p.getInventory().addItem(StreamSerializer.getDefault().deserializeItemStack(itemStr));
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        });
        getConfig().set("data." + p.getUniqueId().toString(), null);

    }

    public void addDropItem(UUID p, ItemStack item) {
        try {
            String itemStr = StreamSerializer.getDefault().serializeItemStack(item);
            List<String> list = getConfig().getStringList("data." + p.toString());
            list = list == null ? new ArrayList<>() : list;
            list.add(itemStr);
            getConfig().set("data." + p.toString(), list);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
