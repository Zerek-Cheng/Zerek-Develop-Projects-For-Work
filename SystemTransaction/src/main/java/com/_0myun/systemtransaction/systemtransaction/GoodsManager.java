package com._0myun.systemtransaction.systemtransaction;

import com._0myun.systemtransaction.systemtransaction.util.ListUtil;
import com._0myun.systemtransaction.systemtransaction.util.Minecraft;
import com.comphenix.protocol.utility.MinecraftReflection;
import com.comphenix.protocol.wrappers.nbt.NbtCompound;
import com.comphenix.protocol.wrappers.nbt.NbtFactory;
import com.comphenix.protocol.wrappers.nbt.NbtWrapper;
import lombok.Data;
import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class GoodsManager {
    @Getter
    private List<Good> goods = new ArrayList<Good>();

    public GoodsManager() {
        this(Main.getPlugin().getConfig().getMapList("goods"));
    }

    public GoodsManager(List<Map<?, ?>> configs) {
        for (Map config : configs) {
            Good good = new Good();
            Minecraft.IdParser idParser = Minecraft.parseItemId((String) config.get("id"));
            good.setId(idParser.getId());
            good.setSubId(idParser.getSubId());
            good.setPrice((Integer) config.get("price"));
            good.setSign(String.valueOf(config.get("sign")));
            if (config.containsKey("lore"))
                good.setLore(String.valueOf(config.get("lore")));
            if (config.containsKey("name"))
                good.setName(String.valueOf(config.get("name")));
            if (config.containsKey("loreShow"))
                good.setLoreShow((List<String>) config.get("loreShow"));
            this.goods.add(good);
        }

    }

    public List<Good> getPage(int page) {
        int start = page * 5 * 9;
        if (start > this.goods.size() - 1) {
            return new ArrayList<Good>();
        }
        int end = ((page + 1) * 5 * 9) - 1;
        if (end > this.goods.size()) {
            end = this.goods.size();
        }
        return this.goods.subList(start, end);
    }

    public Good searchGoodBySign(String sign) {
        for (int i = 0; i < this.goods.size(); i++) {
            Good good = this.goods.get(i);
            if (good.getSign().equalsIgnoreCase(sign)) return good;
        }
        return null;
    }

    @Data
    public class Good {
        int id = 0;
        short subId = -1;
        int price = 0;
        String lore = null;
        String name = null;
        List<String> loreShow = null;
        String sign;

        public boolean verify(ItemStack item) {
            if (item == null) {
                return false;
            }
            if ((item.getTypeId() != this.id))
                return false;
            if (subId != -1 && item.getDurability() != this.subId)
                return false;
            if (this.name != null) {
                if (item.getItemMeta() == null || item.getItemMeta().getDisplayName() == null || !item.getItemMeta().getDisplayName().equalsIgnoreCase(this.name))
                    return false;
            }
            if (this.lore != null) {
                if (item.getItemMeta() == null || !item.getItemMeta().hasLore() || item.getItemMeta().getLore() == null ||
                        !ListUtil.isInclude(item.getItemMeta().getLore(), this.lore)) {
                    return false;
                }
            }
            return true;
        }

        public ItemStack toItem() {
            ItemStack item = new ItemStack(0);
            item.setTypeId(this.id);
            item.setDurability(this.subId >= 0 ? this.subId : 0);
            ItemMeta itemMeta = item.getItemMeta();
            itemMeta.setLore(Arrays.asList(new String[]{""}));
            if (this.lore != null) {
                List<String> loreList = itemMeta.getLore();
                loreList.add(this.lore);
                itemMeta.setLore(loreList);
            }
            if (this.name != null) {
                itemMeta.setDisplayName(this.name);
            }
            if (this.loreShow != null) {
                itemMeta.setLore(this.loreShow);
            }
            item.setItemMeta(itemMeta);

            item = MinecraftReflection.getBukkitItemStack(item);
            NbtWrapper<?> nbtWrapper = NbtFactory.fromItemTag(item);
            NbtCompound nbt = NbtFactory.asCompound(nbtWrapper);
            nbt.put("com._0myun.systemtransaction.systemtransaction.Main.good.sign", this.sign);
            NbtFactory.setItemTag(item, nbt);
            return item;
        }

        public boolean sell(Player p) {
            Inventory inv = p.getInventory();
            for (int i = 0; i < inv.getSize(); i++) {
                ItemStack item = inv.getItem(i);
                if (item == null || item.getType().equals(Material.AIR)) continue;
                if (!verify(item)) continue;
                if (item.getAmount() <= 1) {
                    item.setAmount(0);
                    item.setType(Material.AIR);
                    inv.setItem(i, new ItemStack(Material.AIR, 0));
                } else {
                    item.setAmount(item.getAmount() - 1);
                }
                Main.economy.depositPlayer(p, this.getPrice() / 100d);
                return true;
            }
            return false;
        }
    }

    public synchronized static String getGoodSign(ItemStack itemStack) {
        NbtWrapper<?> nbtWrapper = NbtFactory.fromItemTag(itemStack);
        NbtCompound nbt = NbtFactory.asCompound(nbtWrapper);
        return nbt.getStringOrDefault("com._0myun.systemtransaction.systemtransaction.Main.good.sign");
    }
}


