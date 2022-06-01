package com._0myun.minecraft.dentallaboratories;

import com._0myun.minecraft.dentallaboratories.bin.Item;
import com._0myun.minecraft.dentallaboratories.bin.Material;
import com._0myun.minecraft.dentallaboratories.gui.GuiHolder;
import com.comphenix.protocol.utility.MinecraftReflection;
import com.comphenix.protocol.utility.StreamSerializer;
import com.comphenix.protocol.wrappers.nbt.NbtCompound;
import com.comphenix.protocol.wrappers.nbt.NbtFactory;
import com.comphenix.protocol.wrappers.nbt.NbtWrapper;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.regex.Pattern;

public class GuiManager {
    public void openGui(Player p, ItemStack itemStack) {
        p.openInventory(this.getGui(itemStack));
    }

    public Inventory getGui(ItemStack itemStack) {
        GuiHolder guiHolder = new GuiHolder();
        guiHolder.setItemStack(itemStack);
        Inventory inv = Bukkit.createInventory(guiHolder, 54, Main.plugin.getLangManager().get("lang1"));
        guiHolder.setInventory(inv);


        getShowItems(guiHolder.getItemStack()).forEach((id, itemTmp) -> {
            inv.setItem(id, itemTmp);
        });
        List<ItemStack> materialItems = getMaterialItems(itemStack);
        for (int i = 0; i < materialItems.size(); i++) {
            ItemStack itemTmp = materialItems.get(i);
            inv.setItem(Main.plugin.getConfig().getInt("gui.materialShowStart") + i, itemTmp);
        }
        return inv;
    }

    public HashMap<Integer, ItemStack> getShowItems(ItemStack itemStack) {
        HashMap<Integer, ItemStack> showItems = new HashMap<>();
        ConfigurationSection showsStr = Main.plugin.getConfig().getConfigurationSection("gui.show");
        ArrayList<String> rows = new ArrayList<>(showsStr.getKeys(false));
        rows.forEach(rowIdStr -> {
            if (!isNumeric(rowIdStr)) return;
            int rowId = Integer.valueOf(rowIdStr);
            String itemStr = showsStr.getString(rowIdStr);
            ItemStack itemStackTmp = null;
            if (!itemStr.startsWith("[system]")) {
                try {
                    itemStackTmp = StreamSerializer.getDefault().deserializeItemStack(itemStr);
                } catch (Exception e) {
                    Main.plugin.getLogger().log(Level.WARNING, "反序列化装饰品物品错误");
                    e.printStackTrace();
                }
            } else {//预定义物品
                itemStr = itemStr.substring("[system]".length());
                if (itemStr.equalsIgnoreCase("itemstack")) itemStackTmp = itemStack.clone();
                if (itemStr.equalsIgnoreCase("query")) itemStackTmp = getButtonQuery();
            }
            if (itemStackTmp != null) showItems.put(rowId, itemStackTmp);
        });
        return showItems;
    }

    public List<ItemStack> getMaterialItems(ItemStack itemStack) {
        List<ItemStack> items = new ArrayList<>();
        Item item = Main.plugin.getItemsManager().getItem(itemStack);
        List<Material> materials = item.getMaterials().get(Main.plugin.getItemsManager().getLevel(itemStack));
        if (materials == null) materials = item.getMaterials().get(0);
        materials.forEach(material -> {
            String display = material.getDisplay();
            String type = material.getType();
            String lore = material.getLore();
            int amount = material.getAmount();
            String show = material.getShow();

            ItemStack itemTmp = new ItemStack(7, amount);
            if (show != null) {
                try {
                    itemTmp = StreamSerializer.getDefault().deserializeItemStack(show);
                } catch (IOException e) {
                    Main.plugin.getLogger().log(Level.WARNING, "材料显示图反序列化错误...");
                    e.printStackTrace();
                }
            }
            itemTmp.setAmount(amount);
            ItemMeta itemMeta = itemTmp.getItemMeta();
            if (!itemMeta.hasLore()) itemMeta.setLore(new ArrayList<>());
            List<String> itemTmplore = itemMeta.getLore();
            itemTmplore.add(String.format(Main.plugin.getLangManager().get("lang4"),
                    (type.equalsIgnoreCase("gold") ? "金币" : lore) + "*" + amount));
            itemMeta.setLore(itemTmplore);
            if (!itemMeta.hasDisplayName()) itemMeta.setDisplayName(lore);
            if (display != null) itemMeta.setDisplayName(display);
            if (type.equalsIgnoreCase("gold")) {
                if (show==null){
                    itemTmp.setType(org.bukkit.Material.EMERALD_BLOCK);
                }
                itemTmp.setAmount(1);
            }
            itemTmp.setItemMeta(itemMeta);
            items.add(itemTmp);
        });

        return items;
    }

    private ItemStack getButtonQuery() {
        ConfigurationSection queryConfig = Main.plugin.getConfig().getConfigurationSection("gui.button.query");
        String[] ids = queryConfig.getString("id").split(":");
        ItemStack itemStack = new ItemStack(Integer.valueOf(ids[0]), 1, ids.length > 1 ? Short.valueOf(ids[1]) : 0);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(queryConfig.getString("display"));
        itemStack.setItemMeta(itemMeta);

        itemStack = MinecraftReflection.getBukkitItemStack(itemStack);

        NbtWrapper<?> nbtWrapper = NbtFactory.fromItemTag(itemStack);
        NbtCompound nbt = NbtFactory.asCompound(nbtWrapper);
        nbt.put("com._0myun.minecraft.dentallaboratories.button.type", "query");
        NbtFactory.setItemTag(itemStack, nbt);
        return itemStack;
    }

    private static boolean isNumeric(String str) {
        Pattern pattern = Pattern.compile("[0-9]*");
        return pattern.matcher(str).matches();
    }

    public boolean isButtonQuery(ItemStack itemStack) {
        try {
            itemStack = MinecraftReflection.getBukkitItemStack(itemStack);
            NbtWrapper<?> nbtWrapper = NbtFactory.fromItemTag(itemStack);
            NbtCompound nbt = NbtFactory.asCompound(nbtWrapper);
            return nbt.getStringOrDefault("com._0myun.minecraft.dentallaboratories.button.type").equalsIgnoreCase("query");
        } catch (Exception ex) {
            return false;
        }
    }

    public boolean isAllow(int rawSlot) {
        return Main.plugin.getConfig().getIntegerList("gui.allow").contains(rawSlot);
    }
}
