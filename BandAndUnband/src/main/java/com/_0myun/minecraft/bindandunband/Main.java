package com._0myun.minecraft.bindandunband;

import com.comphenix.protocol.wrappers.nbt.NbtCompound;
import com.comphenix.protocol.wrappers.nbt.NbtFactory;
import com.comphenix.protocol.wrappers.nbt.NbtWrapper;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public final class Main extends JavaPlugin {
    public static Main plugin;

    @Override
    public void onEnable() {
        plugin = this;
        saveDefaultConfig();

        Bukkit.getPluginManager().registerEvents(new PlayerListener(), this);
        new PlayerInvChecker().runTaskTimer(this, 5, 5);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 1 && args[0].equalsIgnoreCase("reload")) {
            this.reloadConfig();
            sender.sendMessage("ok");
            return true;
        }
        Player p = (Player) sender;
        if (args.length == 1 && args[0].equalsIgnoreCase("bind")) {
            if (!p.isOp()) {
                p.sendMessage("你不是op");
                return true;
            }
            this.bind(p.getItemInHand(), p);
            p.updateInventory();
            p.sendMessage(getConfig().getString("lang4"));
            return true;
        }
        if (args.length == 1 && args[0].equalsIgnoreCase("unbind")) {
            if (!isBind(p.getItemInHand())) {
                p.sendMessage(getConfig().getString("lang2"));
                return true;
            }
            if (!checkUnbindItemAndTake(p)) {
                p.sendMessage(getConfig().getString("lang1"));
                return true;
            }
            this.unbind(p.getItemInHand());
            p.updateInventory();
            p.sendMessage(getConfig().getString("lang3"));
            return true;
        }
        return true;
    }

    public void setOwner(ItemStack itemStack, Player p) {
        if (!isBind(itemStack)) return;
        NbtWrapper<?> nbtWrapper = NbtFactory.fromItemTag(itemStack);
        NbtCompound nbt = NbtFactory.asCompound(nbtWrapper);
        nbt.put("com._0myun.minecraft.bindandunband.Main.owner", p.getUniqueId().toString());
        NbtFactory.setItemTag(itemStack, nbt);
    }

    public void bind(ItemStack itemStack, Player p) {
        if (isBind(itemStack)) return;
        NbtWrapper<?> nbtWrapper = NbtFactory.fromItemTag(itemStack);
        NbtCompound nbt = NbtFactory.asCompound(nbtWrapper);
        nbt.put("com._0myun.minecraft.bindandunband.Main.bind", "true");
        nbt.put("com._0myun.minecraft.bindandunband.Main.binder", p.getUniqueId().toString());
        nbt.remove("com._0myun.minecraft.bindandunband.Main.owner");
        NbtFactory.setItemTag(itemStack, nbt);

        ItemMeta itemMeta = itemStack.getItemMeta();
        if (!itemMeta.hasLore()) itemMeta.setLore(new ArrayList<>());
        List<String> lores = itemMeta.getLore();
        lores.add(getConfig().getString("bindLore"));
        itemMeta.setLore(lores);
        itemStack.setItemMeta(itemMeta);
    }

    public void unbind(ItemStack itemStack) {
        if (!isBind(itemStack)) return;
        NbtWrapper<?> nbtWrapper = NbtFactory.fromItemTag(itemStack);
        NbtCompound nbt = NbtFactory.asCompound(nbtWrapper);
        nbt.remove("com._0myun.minecraft.bindandunband.Main.bind");
        nbt.remove("com._0myun.minecraft.bindandunband.Main.owner");
        nbt.remove("com._0myun.minecraft.bindandunband.Main.binder");
        NbtFactory.setItemTag(itemStack, nbt);

        ItemMeta itemMeta = itemStack.getItemMeta();
        if (!itemMeta.hasLore()) itemMeta.setLore(new ArrayList<>());
        List<String> lores = itemMeta.getLore();
        for (int i = 0; i < lores.size(); i++) {
            if (lores.get(i).contains(getConfig().getString("bindLore"))) lores.remove(i);
        }
        itemMeta.setLore(lores);
        itemStack.setItemMeta(itemMeta);
    }

    public boolean isBind(ItemStack itemStack) {
        if (itemStack == null || itemStack.getType().equals(Material.AIR))
            return false;
        boolean bind = getOwner(itemStack) != null && !getOwner(itemStack).isEmpty();
        if (!bind) {
            NbtWrapper<?> nbtWrapper = NbtFactory.fromItemTag(itemStack);
            NbtCompound nbt = NbtFactory.asCompound(nbtWrapper);
            bind = !nbt.getStringOrDefault("com._0myun.minecraft.bindandunband.Main.bind").isEmpty();
        }
        return bind;
    }

    public String getOwner(ItemStack itemStack) {
        if (itemStack == null || itemStack.getType().equals(Material.AIR))
            return "";
        NbtWrapper<?> nbtWrapper = NbtFactory.fromItemTag(itemStack);
        NbtCompound nbt = NbtFactory.asCompound(nbtWrapper);
        return nbt.getStringOrDefault("com._0myun.minecraft.bindandunband.Main.owner");
    }

    public String getBinder(ItemStack itemStack) {
        if (itemStack == null || itemStack.getType().equals(Material.AIR))
            return "";
        NbtWrapper<?> nbtWrapper = NbtFactory.fromItemTag(itemStack);
        NbtCompound nbt = NbtFactory.asCompound(nbtWrapper);
        return nbt.getStringOrDefault("com._0myun.minecraft.bindandunband.Main.binder");
    }

    public boolean checkUnbindItemAndTake(Player p) {
        PlayerInventory inv = p.getInventory();
        boolean success[] = {false};
        inv.forEach(itemStack -> {
            if (success[0]) return;
            if (itemStack == null || itemStack.getType().equals(Material.AIR)) return;
            ItemMeta itemMeta = itemStack.getItemMeta();
            if (!itemMeta.hasLore()) return;
            List<String> lores = itemMeta.getLore();
            lores.forEach(lore -> {
                if (lore.contains(getConfig().getString("unbindLore"))) success[0] = true;
            });
            if (!success[0]) return;
            itemStack.setAmount(itemStack.getAmount() - 1);
            if (itemStack.getAmount() <= 0) itemStack.setType(Material.AIR);
        });
        return success[0];
    }
}
