package com._0myun.minecraft.mosaic;

import com.comphenix.protocol.utility.StreamSerializer;
import com.comphenix.protocol.wrappers.nbt.NbtCompound;
import com.comphenix.protocol.wrappers.nbt.NbtFactory;
import com.comphenix.protocol.wrappers.nbt.NbtWrapper;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.util.List;

public final class Mosaic extends JavaPlugin {
    public static Mosaic INSTANCE;

    @Override
    public void onEnable() {
        INSTANCE = this;
        saveDefaultConfig();
        Bukkit.getPluginManager().registerEvents(new GuiListener(), this);
        LangManager.setLang(getConfig().getConfigurationSection("lang"));

    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 1 && args[0].equalsIgnoreCase("reload")) {
            this.reloadConfig();
            sender.sendMessage("ok");
            return true;
        }
        Player p = (Player) sender;
        ItemStack itemInHand = p.getItemInHand();
        if (!canUseGem(itemInHand)) {
            p.sendMessage(LangManager.get("lang8"));
            return true;
        }
        p.openInventory(GuiManager.getGemGui(p, itemInHand));
        return true;
    }

    public String getSignStr() {
        return ColorSign.parse(getConfig().getString("sign"));
    }

    public int getMax() {
        return 3;
    }

    public boolean canUseGem(ItemStack itemStack) {
        String type = getItemType(itemStack);
        return type != null && !type.isEmpty();
    }

    public boolean canUseGem(ItemStack itemStack, String gem) {
        if (!canUseGem(itemStack)) return false;
        String type = getItemType(itemStack);
        return getConfig().getStringList("limit." + type).contains(gem);
    }

    public String getItemType(ItemStack itemStack) {
        if (itemStack == null || itemStack.getType().equals(Material.AIR) || !itemStack.hasItemMeta() || !itemStack.getItemMeta().hasLore())
            return null;
        List<String> lores = itemStack.getItemMeta().getLore();
        ConfigurationSection config = getConfig().getConfigurationSection("lore");
        for (String key : config.getKeys(false)) {
            if (Utils.contains(lores, config.getString(key))) return key;
        }
        return null;
    }

    public String getGemType(ItemStack itemStack) {
        if (itemStack == null || itemStack.getType().equals(Material.AIR) || !itemStack.hasItemMeta() || !itemStack.getItemMeta().hasLore())
            return null;
        List<String> lores = itemStack.getItemMeta().getLore();
        ConfigurationSection config = getConfig().getConfigurationSection("gem");
        for (String key : config.getKeys(false)) {
            if (Utils.contains(lores, key)) return key;
        }
        return null;
    }

    public String getGemLore(String type){
        return getConfig().getString("gem."+type);
    }

    public double getOpenSuccessRand() {
        return 0.3d;
    }

    public boolean isSlotOpen(ItemStack itemStack, int slot) {
        return getSlotStatus(itemStack, slot) == 1;
    }

    public boolean isSlotUse(ItemStack itemStack, int slot) {
        return getSlotStatus(itemStack, slot) == 2;
    }

    public boolean isSlotCanOpen(ItemStack itemStack, int slot) {
        return getSlotStatus(itemStack, slot) == 0;
    }

    public boolean isSlotBreak(ItemStack itemStack, int slot) {
        return getSlotStatus(itemStack, slot) == -1;
    }

    public boolean canSlotOpen(ItemStack itemStack, int slot) {
        return getSlotStatus(itemStack, slot) == 0;
    }

    public void openSlot(ItemStack itemStack, int slot) {
        setSlotStatus(itemStack, slot, 1);
    }

    public void breakSlot(ItemStack itemStack, int slot) {
        setSlotStatus(itemStack, slot, -1);
    }

    public void useSlot(ItemStack itemStack, int slot) {
        setSlotStatus(itemStack, slot, 2);
    }

    public void setSlotStatus(ItemStack itemStack, int slot, int status) {
        NbtWrapper<?> nbtWrapper = NbtFactory.fromItemTag(itemStack);
        NbtCompound nbt = NbtFactory.asCompound(nbtWrapper);
        nbt.put("com._0myun.minecraft.mosaic.Mosaic.slot." + slot, status);
        NbtFactory.setItemTag(itemStack, nbt);
    }

    public Integer getSlotStatus(ItemStack itemStack, int slot) {
        try {
            NbtWrapper<?> nbtWrapper = NbtFactory.fromItemTag(itemStack);
            NbtCompound nbt = NbtFactory.asCompound(nbtWrapper);
            return nbt.getInteger("com._0myun.minecraft.mosaic.Mosaic.slot." + slot);
        } catch (Exception ex) {
            return 0;
        }
    }

    public void setSlotGemType(ItemStack itemStack, int slot, String type) {
        NbtWrapper<?> nbtWrapper = NbtFactory.fromItemTag(itemStack);
        NbtCompound nbt = NbtFactory.asCompound(nbtWrapper);
        nbt.put("com._0myun.minecraft.mosaic.Mosaic.slot." + slot + ".type", type);
        NbtFactory.setItemTag(itemStack, nbt);
    }

    public void setSlotGemData(ItemStack itemStack, int slot, String data) {
        NbtWrapper<?> nbtWrapper = NbtFactory.fromItemTag(itemStack);
        NbtCompound nbt = NbtFactory.asCompound(nbtWrapper);
        nbt.put("com._0myun.minecraft.mosaic.Mosaic.slot." + slot + ".data", data);
        NbtFactory.setItemTag(itemStack, nbt);
    }

    public String getSlotGemType(ItemStack itemStack, int slot) {
        NbtWrapper<?> nbtWrapper = NbtFactory.fromItemTag(itemStack);
        NbtCompound nbt = NbtFactory.asCompound(nbtWrapper);
        return nbt.getString("com._0myun.minecraft.mosaic.Mosaic.slot." + slot + ".type");
    }

    public String getSlotGemData(ItemStack itemStack, int slot) {
        NbtWrapper<?> nbtWrapper = NbtFactory.fromItemTag(itemStack);
        NbtCompound nbt = NbtFactory.asCompound(nbtWrapper);
        return nbt.getString("com._0myun.minecraft.mosaic.Mosaic.slot." + slot + ".data");
    }

    public void setSlotGemData(ItemStack itemStack, int slot, ItemStack gem) {
        setSlotGemType(itemStack, slot, getGemType(gem));
        String data = null;
        try {
            data = StreamSerializer.getDefault().serializeItemStack(gem);
        } catch (IOException e) {
            e.printStackTrace();
        }
        NbtWrapper<?> nbtWrapper = NbtFactory.fromItemTag(itemStack);
        NbtCompound nbt = NbtFactory.asCompound(nbtWrapper);
        nbt.put("com._0myun.minecraft.mosaic.Mosaic.slot." + slot + ".data", data);
        NbtFactory.setItemTag(itemStack, nbt);
    }

    public ItemStack getSlotGemDataItem(ItemStack itemStack, int slot) {
        NbtWrapper<?> nbtWrapper = NbtFactory.fromItemTag(itemStack);
        NbtCompound nbt = NbtFactory.asCompound(nbtWrapper);
        String data = nbt.getString("com._0myun.minecraft.mosaic.Mosaic.slot." + slot + ".data");
        try {
            ItemStack gem = StreamSerializer.getDefault().deserializeItemStack(data);
            return gem;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean isSlotOpenItem(ItemStack itemStack) {
        if (itemStack == null || itemStack.getType().equals(Material.AIR) || !itemStack.hasItemMeta() || !itemStack.getItemMeta().hasLore())
            return false;
        return Utils.contains(itemStack.getItemMeta().getLore(), getConfig().getString("open-slot-lore"))
                || Utils.contains(itemStack.getItemMeta().getLore(), getConfig().getString("open-slot-safe-lore"));
    }

    public boolean isSlotSafeOpenItem(ItemStack itemStack) {
        if (itemStack == null || itemStack.getType().equals(Material.AIR) || !itemStack.hasItemMeta() || !itemStack.getItemMeta().hasLore())
            return false;
        return Utils.contains(itemStack.getItemMeta().getLore(), getConfig().getString("open-slot-safe-lore"));
    }
}
