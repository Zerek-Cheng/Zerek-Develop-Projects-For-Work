package com._0myun.minecraft.dentallaboratories;

import com._0myun.minecraft.dentallaboratories.bin.Item;
import com._0myun.minecraft.dentallaboratories.events.PlayerItemLevelUpEvent;
import com._0myun.minecraft.dentallaboratories.events.PlayerItemLevelUpSuccessEvent;
import com._0myun.minecraft.dentallaboratories.utils.ListUtil;
import com.comphenix.protocol.wrappers.nbt.NbtCompound;
import com.comphenix.protocol.wrappers.nbt.NbtFactory;
import com.comphenix.protocol.wrappers.nbt.NbtWrapper;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;

public class ItemsManager {
    private Map<String, Item> items = new HashMap<>();

    protected void cleanItems() {
        this.items.clear();
    }

    /**
     * 删除加载的物品
     *
     * @param sign
     */
    private void removeItem(String sign) {
        items.remove(sign);
    }

    /**
     * 加载物品
     *
     * @param sign
     * @param item
     * @return
     */
    protected boolean addItem(String sign, Item item) {
        if (this.items.containsKey(sign)) return false;
        this.items.put(sign, item);
        return true;
    }

    /**
     * 获取加载的物品
     *
     * @param sign
     * @return
     */
    public Item getItem(String sign) {
        return this.items.get(sign);
    }

    /**
     * 获取加载的物品
     *
     * @return
     */
    public Item getItem(ItemStack itemStack) {
        String sign = this.getSign(itemStack);
        return sign == null ? null : this.getItem(sign);
    }

    /**
     * 物品标签是否存在
     *
     * @param sign
     * @return
     */
    public boolean existItem(String sign) {
        return this.items.containsKey(sign);
    }

    /**
     * 所有物品标签
     *
     * @return
     */
    public List<String> getSigns() {
        return new ArrayList<>(this.items.keySet());
    }

    /**
     * 获取物品中的标签
     *
     * @param item
     * @return
     */
    public String getSign(ItemStack item) {
        if (item == null || item.getType().equals(Material.AIR) || !item.getItemMeta().hasLore()) return null;
        List<String> lore = item.getItemMeta().getLore();
        String[] tmpSign = {null};
        getSigns().forEach(tmp -> {
            if (ListUtil.contain(lore, tmp)) tmpSign[0] = tmp;
        });
        return tmpSign[0];
    }

    /**
     * 获取强化等级
     *
     * @param item
     * @return
     */
    public int getLevel(ItemStack item) {
        NbtWrapper<?> nbtWrapper = NbtFactory.fromItemTag(item);
        NbtCompound nbt = NbtFactory.asCompound(nbtWrapper);
        return nbt.getIntegerOrDefault("com._0myun.minecraft.dentallaboratories.level");
    }

    /**
     * 设置强化等级
     *
     * @param item
     * @param level
     */
    public void setLevel(ItemStack item, int level) {
        NbtWrapper<?> nbtWrapper = NbtFactory.fromItemTag(item);
        NbtCompound nbt = NbtFactory.asCompound(nbtWrapper);
        nbt = nbt.put("com._0myun.minecraft.dentallaboratories.level", level);
        NbtFactory.setItemTag(item, nbt);
    }

    /**
     * 增加强化等级
     *
     * @param item
     */
    public void addLevel(ItemStack item) {
        setLevel(item, getLevel(item) + 1);
    }


    /**
     * 获取属性
     */
    public int getAttribute(ItemStack itemstack, String attribute) {
        NbtWrapper<?> nbtWrapper = NbtFactory.fromItemTag(itemstack);
        NbtCompound nbt = NbtFactory.asCompound(nbtWrapper);
        return nbt.getIntegerOrDefault("com._0myun.minecraft.dentallaboratories.attribute." + attribute);
    }

    /**
     * 设置属性
     */
    public void setAttribute(ItemStack itemstack, String attribute, int value) {
        NbtWrapper<?> nbtWrapper = NbtFactory.fromItemTag(itemstack);
        NbtCompound nbt = NbtFactory.asCompound(nbtWrapper);
        nbt = nbt.put("com._0myun.minecraft.dentallaboratories.attribute." + attribute, value);
        NbtFactory.setItemTag(itemstack, nbt);
    }

    /**
     * 增加属性
     */
    public void addAllAttributes(ItemStack itemstack, HashMap<String, Integer> attributes) {
        attributes.forEach((name, value) -> {
            ItemsManager.this.setAttribute(itemstack, name, ItemsManager.this.getAttribute(itemstack, name) + value);
        });
    }

    private void addLores(ItemStack itemstack, int index) {
        String sign = Main.plugin.getSignStr();
        ItemMeta itemMeta = itemstack.getItemMeta();
        if (!itemMeta.hasLore()) itemMeta.setLore(new ArrayList<>());
        List<String> lore = itemMeta.getLore();
        Item item = this.getItem(this.getSign(itemstack));
        if (item == null) return;
        Set<String> attributes = item.getAttributes().keySet();
        List<String> lores = (List<String>) new ArrayList<>(item.getLores()).clone();
        for (int i = 0; i < lores.size(); i++) {
            int finalI = i;
            int finalI1 = i;
            attributes.forEach(attributesName -> {
                lores.set(finalI, lores.get(finalI1).replace("%" + attributesName + "%"
                        , String.valueOf(ItemsManager.this.getAttribute(itemstack, String.valueOf(attributesName)))));
                lores.set(finalI, sign + "§r" + lores.get(finalI1));
            });
            lores.set(i, lores.get(i).replace("%level%", String.valueOf(this.getLevel(itemstack))));
        }
        lore.addAll(index, lores);
        itemMeta.setLore(lore);
        itemstack.setItemMeta(itemMeta);
    }

    /**
     * 增加强化lore
     */
    public void addLores(ItemStack itemstack) {
        ItemMeta itemMeta = itemstack.getItemMeta();
        addLores(itemstack, itemMeta.hasLore() ? itemMeta.getLore().size() : 0);
    }

    /**
     * 更新LORE
     */
    public void updateLores(ItemStack itemStack) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        List<String> lores = itemMeta.getLore();
        lores = lores == null ? new ArrayList<>() : lores;
        int line = -1;
        for (int i = 0; i < lores.size(); i++) {
            String lore = lores.get(i);
            if (lore.startsWith(Main.plugin.getSignStr())) {
                line = i;
                break;
            }
        }
        for (int i = lores.size() - 1; i >= 0; i--) {
            String lore = lores.get(i);
            if (lore.startsWith(Main.plugin.getSignStr())) {
                lores.remove(i);
            }
        }
        itemMeta.setLore(lores);
        itemStack.setItemMeta(itemMeta);
        if (line == -1) {
            this.addLores(itemStack);
        } else {
            this.addLores(itemStack, line);
        }
    }

    public boolean levelUp(Player p, ItemStack itemStack) {
        Item item = this.getItem(itemStack);
        if (item == null) return false;
        int level = this.getLevel(itemStack);
        HashMap<String, Integer> attributesChange = item.randAttributesChange();

        PlayerItemLevelUpEvent event = new PlayerItemLevelUpEvent(p, itemStack, level, level + 1, attributesChange);
        Bukkit.getPluginManager().callEvent(event);
        if (event.isCancelled()) {
            return false;
        }
        this.setLevel(itemStack, event.getTo());
        this.addAllAttributes(itemStack, event.getAttributes());

        Bukkit.getPluginManager().callEvent(new PlayerItemLevelUpSuccessEvent(p, itemStack, level, level + 1, attributesChange));
        return true;
    }

    public boolean checkLeavelUpMaterial(Player p, ItemStack itemStack, List<ItemStack> materialItems) {
        Item item = this.getItem(itemStack);
        List<com._0myun.minecraft.dentallaboratories.bin.Material> materials = item.getMaterials().get(this.getLevel(itemStack));

        if (materials == null) materials = item.getMaterials().get(0);
        //先寻找需要的金币
        int gold = getLeaveUpMaterialsGolds(materials);
        if (!Main.economy.has(p, gold)) {
            p.sendMessage(Main.plugin.getLangManager().get("lang5"));
            return false;
        }//金币检测通过

        HashMap<String, Integer> materialsLoreItemNeed = getLeaveUpMaterialsLoreMap(materials);

        for (int i = 0; i < materialItems.size(); i++) {
            ItemStack itemStackTmp = materialItems.get(i);
            ItemMeta itemMeta = itemStackTmp.getItemMeta();
            List<String> lores = itemMeta.hasLore() ? itemMeta.getLore() : new ArrayList<>();
            int amount = itemStackTmp.getAmount();
            for (int j = 0; j < materials.size(); j++) {
                com._0myun.minecraft.dentallaboratories.bin.Material material = materials.get(j);
                if (!material.getType().equalsIgnoreCase("item")) continue;
                String lore = material.getLore();
                if (!ListUtil.contain(lores, lore)) continue;//不是该物品
                Integer need = materialsLoreItemNeed.get(lore);
                if (need == null) continue;
                if (amount >= need) {
                    amount -= need;
                    materialsLoreItemNeed.remove(lore);
                } else {
                    materialsLoreItemNeed.put(lore, need - amount);
                    amount = 0;
                }
            }
        }

        if (materialsLoreItemNeed.size() > 0) {
            p.sendMessage(Main.plugin.getLangManager().get("lang6"));
            return false;
        } else {
            return true;
        }
    }


    public void takeLeavelUpMaterial(Player p, ItemStack itemStack, List<ItemStack> materialItems) {
        Item item = this.getItem(itemStack);
        List<com._0myun.minecraft.dentallaboratories.bin.Material> materials = item.getMaterials().get(this.getLevel(itemStack));

        if (materials == null) materials = item.getMaterials().get(0);
        //先寻找需要的金币
        int gold = getLeaveUpMaterialsGolds(materials);
        Main.economy.withdrawPlayer(p, gold);

        HashMap<String, Integer> materialsLoreItemNeed = getLeaveUpMaterialsLoreMap(materials);

        for (int i = 0; i < materialItems.size(); i++) {
            ItemStack itemStackTmp = materialItems.get(i);
            ItemMeta itemMeta = itemStackTmp.getItemMeta();
            List<String> lores = itemMeta.hasLore() ? itemMeta.getLore() : new ArrayList<>();
            int amount = itemStackTmp.getAmount();
            for (int j = 0; j < materials.size(); j++) {
                com._0myun.minecraft.dentallaboratories.bin.Material material = materials.get(j);
                if (!material.getType().equalsIgnoreCase("item")) continue;
                String lore = material.getLore();
                if (!ListUtil.contain(lores, lore)) continue;//不是该物品
                Integer need = materialsLoreItemNeed.get(lore);
                if (need == null) continue;
                if (amount >= need) {
                    amount -= need;
                    materialsLoreItemNeed.remove(lore);
                    itemStackTmp.setAmount(amount);
                } else {
                    materialsLoreItemNeed.put(lore, need - amount);
                    amount = 0;
                    itemStackTmp.setAmount(0);
                }
                if (itemStackTmp.getAmount() <= 0) {
                    itemStackTmp.setType(Material.AIR);
                    itemMeta.setLore(new ArrayList<>());
                    lores = new ArrayList<>();
                }
            }
        }
    }

    private static int getLeaveUpMaterialsGolds(List<com._0myun.minecraft.dentallaboratories.bin.Material> materials) {
        int gold = 0;
        for (int i = 0; i < materials.size(); i++) {
            com._0myun.minecraft.dentallaboratories.bin.Material material = materials.get(i);
            if (!material.getType().equalsIgnoreCase("gold")) continue;
            gold += material.getAmount();
        }
        return gold;
    }

    private static HashMap<String, Integer> getLeaveUpMaterialsLoreMap(List<com._0myun.minecraft.dentallaboratories.bin.Material> materials) {
        HashMap<String, Integer> materialsLoreItemNeed = new HashMap<>();
        for (int i = 0; i < materials.size(); i++) {
            com._0myun.minecraft.dentallaboratories.bin.Material materialTmp = materials.get(i);
            if (!materialTmp.getType().equalsIgnoreCase("item")) continue;
            Integer needTmp = materialsLoreItemNeed.get(materialTmp.getLore());
            if (needTmp == null) needTmp = 0;
            materialsLoreItemNeed.put(materialTmp.getLore(), needTmp + materialTmp.getAmount());
        }
        return materialsLoreItemNeed;
    }
}
