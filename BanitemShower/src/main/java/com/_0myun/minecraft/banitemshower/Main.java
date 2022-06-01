package com._0myun.minecraft.banitemshower;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public final class Main extends JavaPlugin {
    public static Main INSTANCE;
    List<BanItem> banitems = new ArrayList<>();
    List<SubBanItem> subItems = new ArrayList<>();

    YamlConfiguration lang = new YamlConfiguration();

    @Override
    public void onEnable() {
        INSTANCE = this;
        saveDefaultConfig();
        saveResource("lang.yml", false);

        Bukkit.getPluginManager().registerEvents(new UiListener(), this);

        getItems().forEach(key -> Main.this.banitems.add(getItem(key)));
        banitems.forEach(banitem -> banitem.getSubItems().forEach((subId, subItem) -> subItems.add(subItem)));
        try {
            lang.load(this.getDataFolder() + "/lang.yml");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 1 && args[0].equalsIgnoreCase("rleoad")) {
            this.reloadConfig();
            try {
                lang.load(this.getDataFolder() + "/lang.yml");
            } catch (Exception e) {
                e.printStackTrace();
            }
            sender.sendMessage("ok");
            return true;
        }
        int page = args.length >= 1 ? Integer.valueOf(args[0]) : 1;
        Player p = (Player) sender;
        p.openInventory(this.getInv(page));
        return true;
    }

    public List<String> getItems() {
        return new ArrayList<>(getConfig().getKeys(false));
    }

    public BanItem getItem(String key) {
        return new BanItem(getConfig().getConfigurationSection(key));
    }

    public Inventory getInv(int page) {
        PageUtils<SubBanItem> pm = new PageUtils();
        pm.setRecords(this.subItems);
        pm.setTotalRecord(this.subItems.size());
        pm.setPageNo(page);
        pm.setPageSize(52);

        List<SubBanItem> list = pm.getSplitList();

        ShowerHolder holder = new ShowerHolder();
        Inventory inv = Bukkit.createInventory(holder, 6 * 9,"BanitemShower");
        holder.setInv(inv);
        holder.setPage(page);
        for (int i = 0; i < list.size(); i++) {
            inv.setItem(1 + i, list.get(i).toItemStack());
        }
        ItemStack before = new ItemStack(39, 1);
        ItemStack after = new ItemStack(40, 1);
        ItemMeta itemMetaBefore = before.getItemMeta();
        itemMetaBefore.setDisplayName(lang.getString("lang7"));
        before.setItemMeta(itemMetaBefore);

        ItemMeta itemMetaAfter = after.getItemMeta();
        itemMetaAfter.setDisplayName(lang.getString("lang8"));
        after.setItemMeta(itemMetaAfter);

        if (page != 1) inv.setItem(0, before);
        if ((pm.getTotalRecord()/pm.getPageSize())+1>page)
            inv.setItem(53, after);
        return inv;
    }
}
