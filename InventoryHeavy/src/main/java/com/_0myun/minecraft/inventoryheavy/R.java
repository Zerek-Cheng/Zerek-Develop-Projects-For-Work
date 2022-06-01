package com._0myun.minecraft.inventoryheavy;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.plugin.java.JavaPlugin;

public final class R extends JavaPlugin {
    YamlConfiguration data = new YamlConfiguration();

    @Override
    public void onEnable() {
        try {
            saveDefaultConfig();
            saveResource("data.yml", false);
            data.load(getDataFolder().toString() + "//data.yml");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        new Checker(this).runTaskTimer(this, 20L, 20L);
    }

    @Override
    public void onDisable() {
        try {
            data.save(getDataFolder().toString() + "//data.yml");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 1 && args[0].equalsIgnoreCase("reload") && sender.isOp()) {
            reloadConfig();
            sender.sendMessage("ok");
            return true;
        }
        Player p = (Player) sender;
        if (args.length == 1 && args[0].equalsIgnoreCase("info")) {
            p.sendMessage(String.format(getConfig().getString("lang1"), getHeavy(p), getMaxHeavy(p)));
        }
        if (args.length == 2 && args[0].equalsIgnoreCase("set")) {
            data.set(p.getName(), Integer.valueOf(args[1]));
            sender.sendMessage("背包重量已变更");
            return true;
        }
        if (args.length == 3 && args[0].equalsIgnoreCase("set")) {
            data.set(args[1], Integer.valueOf(args[2]));
            sender.sendMessage("背包重量已变更");
            return true;
        }
        return true;
    }

    public int getHeavy(Player p) {
        int heavy = 0;
        EntityEquipment eq = p.getEquipment();
        PlayerInventory inv = p.getInventory();
        for (ItemStack armorContent : eq.getArmorContents())
            heavy += getHeavy(armorContent);
        for (ItemStack itemStack : inv)
            heavy += getHeavy(itemStack);
        return heavy;
    }

    public int getHeavy(ItemStack itemStack) {
        if (itemStack == null || itemStack.getType().equals(Material.AIR) || !itemStack.hasItemMeta() || !itemStack.getItemMeta().hasLore())
            return 0;
        for (String heavy : getConfig().getConfigurationSection("heavy").getKeys(false)) {
            if (!itemStack.getItemMeta().getLore().toString().contains(heavy)) continue;
            return getConfig().getInt("heavy." + heavy) * itemStack.getAmount();
        }
        return 0;
    }

    public int getMaxHeavy(Player p) {
        int base = getConfig().getInt("base");
        if (data.isSet(p.getName())) base += data.getInt(p.getName());
        return base;
    }

    public float getPrecent(Player p) {
        return Float.valueOf(getHeavy(p) / Float.valueOf(getMaxHeavy(p)));
    }
}
