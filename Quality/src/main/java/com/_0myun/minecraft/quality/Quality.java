package com._0myun.minecraft.quality;

import com._0myun.minecraft.ColorSign;
import com._0myun.minecraft.quality.rand.LotteryUtil;
import com._0myun.minecraft.quality.rand.QuailityType;
import com.comphenix.protocol.wrappers.nbt.NbtCompound;
import com.comphenix.protocol.wrappers.nbt.NbtFactory;
import com.comphenix.protocol.wrappers.nbt.NbtWrapper;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public final class Quality extends JavaPlugin {

    public static Quality INSTANCE;

    List<QuailityType> quailitys = new ArrayList<>();

    @Override
    public void onEnable() {
        INSTANCE = this;
        saveDefaultConfig();
        LangManager.setLang(getConfig().getConfigurationSection("lang"));
        initQuality();
    }

    public void initQuality() {
        quailitys = new ArrayList<>();
        for (String key : getConfig().getConfigurationSection("level-rand").getKeys(false)) {
            quailitys.add(new QuailityType(key, getConfig().getDouble("level-rand." + key)));
        }
    }

    public int randQuality() {
        List<Double> orignalRates = new ArrayList<Double>(quailitys.size());
        for (QuailityType quailityType : quailitys) {
            double probability = quailityType.getProbability();
            if (probability < 0) {
                probability = 0;
            }
            orignalRates.add(probability);
        }
        int orignalIndex = LotteryUtil.lottery(orignalRates);
        return Integer.valueOf(quailitys.get(orignalIndex).getGitfId());
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 1 && args[0].equalsIgnoreCase("reload")) {
            this.reloadConfig();
            initQuality();
            return true;
        }
        boolean luck = false;
        if (label.equalsIgnoreCase("ldz")) luck = true;
        Player p = (Player) sender;
        ItemStack itemInHand = p.getItemInHand();
        if (itemInHand == null || itemInHand.getType().equals(Material.AIR)) {
            p.sendMessage(LangManager.get("lang2"));
            return true;
        }
        String type = getType(itemInHand);
        if (type == null) {
            p.sendMessage(LangManager.get("lang3"));
            return true;
        }
        if (luck && !ItemsManager.takeLuckItem(p, 1)) {
            p.sendMessage(LangManager.get("lang7"));
            return true;
        }

        int amount = getConfig().getInt("amount." + type);
        int hasAmount = ItemsManager.searchMakeItemAmount(p);
        if (hasAmount < amount) {
            p.sendMessage(LangManager.get("lang6"));
            return true;
        }
        ItemsManager.takeMakeItem(p, amount);

        setQuaility(itemInHand, randQuality());
        ItemsManager.updateLores(itemInHand);
        p.updateInventory();
        p.sendMessage(LangManager.get(!luck ? "lang4" : "lang5"));//不使用 使用幸运石
        return true;
    }

    public String getType(ItemStack itemStack) {
        if (itemStack == null || itemStack.getType().equals(Material.AIR) || !itemStack.hasItemMeta() || !itemStack.getItemMeta().hasLore())
            return null;
        for (String key : getConfig().getConfigurationSection("lore").getKeys(false)) {
            if (itemStack.getItemMeta().getLore().toString().contains(String.valueOf(getConfig().getString("lore." + key))))
                return key;
        }
        return null;
    }

    public String getTypeLoreAttribute(String type) {
        return getConfig().getString("lore-attribute." + type);
    }

    public String getQualityValue(String type, int level) {
        return getConfig().getString("level-value-" + type + "." + level);
    }

    public String getSignStr() {
        return ColorSign.parse(getConfig().getString("sign"));
    }

    public String getQualityName(int level) {
        return getConfig().getString("level." + level);
    }

    public int getQuality(ItemStack itemStack) {
        NbtWrapper<?> nbtWrapper = NbtFactory.fromItemTag(itemStack);
        NbtCompound nbt = NbtFactory.asCompound(nbtWrapper);
        return nbt.getIntegerOrDefault("com._0myun.minecraft.quality.Quality.level");
    }

    public void setQuaility(ItemStack itemStack, int level) {
        NbtWrapper<?> nbtWrapper = NbtFactory.fromItemTag(itemStack);
        NbtCompound nbt = NbtFactory.asCompound(nbtWrapper);
        nbt.put("com._0myun.minecraft.quality.Quality.level", level);
    }
}
