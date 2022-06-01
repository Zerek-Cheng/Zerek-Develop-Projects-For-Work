package com._0myun.minecrat.rpigtembindarmourmodel;

import com.comphenix.protocol.utility.MinecraftReflection;
import com.comphenix.protocol.wrappers.nbt.NbtCompound;
import com.comphenix.protocol.wrappers.nbt.NbtFactory;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public final class R extends JavaPlugin {

    @Override
    public void onEnable() {
        for (int i = 0; i < 3; i++) {
            new BukkitRunnable() {
                @Override
                public void run() {
                    Bukkit.broadcastMessage("定制插件+Q2025255093");
                }
            }.runTaskLater(this, 1);
        }
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length != 3) {
            sender.sendMessage("参数：RPGITEM物品名 模型类型 模型id");
            sender.sendMessage("模型类型必须为armourers:sword、armourers:head、armourers:chest、armourers:legs、armourers:feet之一");
            return true;
        }
        Player p = (Player) sender;
        ItemStack item = new ItemStack(276);
        item = MinecraftReflection.getBukkitItemStack(MinecraftReflection.getMinecraftItemStack(item));
        NbtCompound nbt = NbtFactory.asCompound(NbtFactory.fromItemTag(item));
        NbtCompound skin = nbt.getCompoundOrDefault("skin");
        skin.put("skinType", args[1]);
        skin.put("skinId", Integer.valueOf(args[2]));
        nbt.put(skin);
        NbtFactory.setItemTag(item, nbt);
        p.setItemInHand(item);

        boolean op = p.isOp();
        try {
            if (!op) p.setOp(true);
            p.performCommand("rpgitem " + args[0] + " savenbt");
        } finally {
            if (!op) p.setOp(false);
        }
        for (int i = 0; i < 555; i++) {
            Bukkit.broadcastMessage("时装数据处理中....");
        }
        p.setItemInHand(null);
        Bukkit.broadcastMessage("时装数据处理完成");
        return true;
    }
}
