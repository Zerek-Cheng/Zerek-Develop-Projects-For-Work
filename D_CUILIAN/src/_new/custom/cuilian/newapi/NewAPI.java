package _new.custom.cuilian.newapi;

import _new.custom.cuilian.NewCustomCuiLian;
import _new.custom.cuilian.language.Language;
import _new.custom.cuilian.level.Level;
import _new.custom.cuilian.stone.Stone;
import _new.custom.cuilian.variable.Variable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.attribute.Attribute;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;

public class NewAPI {
    Plugin plugin;

    public NewAPI(Plugin p) {
        this.plugin = p;
    }

    public static Level getMinLevel(List list) {
        if (list.size() >= 5 || !NewCustomCuiLian.ServerVersion.equalsIgnoreCase("1.8") && !NewCustomCuiLian.ServerVersion.equalsIgnoreCase("1.7")) {
            if (list.size() < 6 && NewCustomCuiLian.ServerVersion.equalsIgnoreCase("1.9+")) {
                return NewCustomCuiLian.customCuilianManager.NULLLevel;
            } else {
                boolean flag = true;
                Level ret = NewCustomCuiLian.customCuilianManager.NULLLevel;
                Iterator var3 = list.iterator();

                while (var3.hasNext()) {
                    ItemStack item = (ItemStack) var3.next();
                    Level l = getLevelByItem(item);
                    if (l.levelValue.intValue() == -1) {
                        return NewCustomCuiLian.customCuilianManager.NULLLevel;
                    }

                    if (flag) {
                        ret = l;
                        flag = false;
                    } else if (l.levelValue.intValue() < ret.levelValue.intValue()) {
                        ret = l;
                    }
                }

                return ret;
            }
        } else {
            return NewCustomCuiLian.customCuilianManager.NULLLevel;
        }
    }

    public static LivingEntity getLivingEntityByEntityId(UUID id) {
        Iterator var1 = Bukkit.getWorlds().iterator();

        while (var1.hasNext()) {
            World w = (World) var1.next();
            Iterator var3 = w.getLivingEntities().iterator();

            while (var3.hasNext()) {
                LivingEntity e = (LivingEntity) var3.next();
                if (e.getUniqueId().equals(id)) {
                    return e;
                }
            }
        }

        return null;
    }

    public static String getFileVersion() {
        return NewCustomCuiLian.ServerVersion.equalsIgnoreCase("1.9+") ? "-1_9" : "-1_8";
    }

    public static ItemStack getItemInHand(LivingEntity p) {
        if (!(p instanceof Player)) return new ItemStack(0);
        Player player = (Player) p;
        return player.getItemInHand();/*
        try {
            return NewCustomCuiLian.ServerVersion.equalsIgnoreCase("1.9+") ? p.getEquipment().getItemInMainHand() : (p.getType() == EntityType.PLAYER ? ((Player) p).getItemInHand() : p.getEquipment().getItemInHand());
        } catch (Exception e) {
            return new ItemStack(0);
        }*/
    }

    public static ItemStack getItemInOffHand(LivingEntity p) {
        return NewCustomCuiLian.ServerVersion.equalsIgnoreCase("1.9+") ? p.getEquipment().getItemInOffHand() : new ItemStack(Material.AIR);
    }

    public static void setItemInHand(Player p, ItemStack i) {
        if (NewCustomCuiLian.ServerVersion.equalsIgnoreCase("1.9+")) {
            p.getInventory().setItemInMainHand(i);
        } else {
            p.getInventory().setItemInHand(i);
        }

    }

    public static double getMaxHealth(LivingEntity p) {
        return NewCustomCuiLian.ServerVersion.equalsIgnoreCase("1.9+") ? p.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue() : p.getMaxHealth();
    }

    public static void setMaxHealth(LivingEntity p, double m) {
        if (NewCustomCuiLian.ServerVersion.equalsIgnoreCase("1.9+")) {
            p.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(m);
        } else {
            p.setMaxHealth(m);
        }

    }

    public static Level getLevelByQuenchingProtectRune(ItemStack i) {
        if (i != null && i.hasItemMeta() && i.getItemMeta().hasLore()) {
            Iterator var1 = NewCustomCuiLian.customCuilianManager.customCuilianLevelList.iterator();

            while (var1.hasNext()) {
                Level Key = (Level) var1.next();
                if (Key.quenchingProtectRune != null && Key.quenchingProtectRune.getItemMeta().equals(i.getItemMeta())) {
                    return Key;
                }
            }
        }

        return NewCustomCuiLian.customCuilianManager.NULLLevel;
    }

    public static ItemStack cuilian(ItemStack cls, ItemStack item, Player p) {
        if (cls != null && item != null && p != null && NewCustomCuiLian.customCuilianManager.ItemList.contains(item.getType())) {
            Level level = getLevelByItem(item);
            Stone stone = getStoneByItem(cls);
            int dx = getRandom(((Integer) stone.dropLevel.get(0)).intValue(), ((Integer) stone.dropLevel.get(1)).intValue());
            int sx = stone.riseLevel;
            double probability = (double) getRandom(0, 100);
            boolean flag = probability <= ((Double) stone.levelProbability.get(getLevelByInteger(level.levelValue.intValue() + sx))).doubleValue();
            String sendMessage = null;
            if (flag) {
                item = setItemCuiLian(item, getLevelByInteger(level.levelValue.intValue() + sx), p);
                sendMessage = Language.CAN_CUILIAN_PROMPT.replace("%s", (CharSequence) ((Level) NewCustomCuiLian.customCuilianManager.customCuilianLevelList.get(level.levelValue.intValue() + sx)).levelString.get(0));
                if (level.levelValue.intValue() + sx >= 5) {
                    Bukkit.broadcastMessage(Language.ALL_SERVER_PROMPT.replace("%p", p.getDisplayName()).replace("%d", cls.getItemMeta().getDisplayName()).replace("%s", (CharSequence) ((Level) NewCustomCuiLian.customCuilianManager.customCuilianLevelList.get(level.levelValue.intValue() + sx)).levelString.get(0)));
                }
            } else if (getLevelByQuenchingProtectRuneString(item).levelValue.intValue() <= level.levelValue.intValue() && getLevelByQuenchingProtectRuneString(item) != NewCustomCuiLian.customCuilianManager.NULLLevel && level.levelValue.intValue() - getLevelByQuenchingProtectRuneString(item).levelValue.intValue() < ((Integer) stone.dropLevel.get(1)).intValue()) {
                dx = getRandom(0, level.levelValue.intValue() - getLevelByQuenchingProtectRuneString(item).levelValue.intValue());
                item = setItemCuiLian(item, getLevelByInteger(level.levelValue.intValue() - dx), p);
                sendMessage = Language.HAS_BAOHUFU_CUILIAN_OVER.replace("%s", (CharSequence) getLevelByInteger(level.levelValue.intValue() - dx).levelString.get(0)).replace("%d", String.valueOf(dx));
            } else if (level.levelValue.intValue() - dx >= 0) {
                item = setItemCuiLian(item, getLevelByInteger(level.levelValue.intValue() - dx), p);
                sendMessage = Language.CUILIAN_OVER.replace("%s", (CharSequence) getLevelByInteger(level.levelValue.intValue() - dx).levelString.get(0)).replace("%d", String.valueOf(dx));
            } else {
                item = setItemCuiLian(item, NewCustomCuiLian.customCuilianManager.NULLLevel, p);
                sendMessage = Language.CUILIAN_OVER_ZERO.replace("%d", String.valueOf(dx));
            }

            p.sendMessage(sendMessage);
        }

        return item;
    }

    public static ItemStack setItemCuiLian(ItemStack item, Level level, Player p) {
        Object lore;
        ItemMeta meta;
        List lore1;
        if (NewCustomCuiLian.customCuilianManager.ItemList.contains(item.getType()) && level != NewCustomCuiLian.customCuilianManager.NULLLevel) {
            lore = new ArrayList();
            if (item.getItemMeta().hasLore()) {
                lore = item.getItemMeta().getLore();
            }

            lore1 = cleanCuiLian((List) lore);
            lore1.addAll(Language.UNDER_LINE);
            lore1.addAll(level.levelString);
            lore1.addAll(getLore(getListStringByType(item.getType()), level, item.getType()));
            if (lore1.isEmpty()) {
                lore1.add("");
            }

            lore1 = cleanQuenchingProtectRune(lore1);
            meta = item.getItemMeta();
            meta.setLore(lore1);
            item.setItemMeta(meta);
        } else if (level == NewCustomCuiLian.customCuilianManager.NULLLevel) {
            lore = new ArrayList();
            if (item.getItemMeta().hasLore()) {
                lore = item.getItemMeta().getLore();
            }

            lore1 = cleanCuiLian((List) lore);
            lore1 = cleanQuenchingProtectRune(lore1);
            meta = item.getItemMeta();
            meta.setLore(lore1);
            item.setItemMeta(meta);
        }

        return item;
    }

    public static List cleanCuiLian(List lore) {
        if (lore != null) {
            if (lore.containsAll(Language.UNDER_LINE)) {
                lore.removeAll(Language.UNDER_LINE);
            }

            Iterator j = NewCustomCuiLian.customCuilianManager.customCuilianLevelList.iterator();

            while (j.hasNext()) {
                Level Key = (Level) j.next();
                if (lore.containsAll(Key.levelString)) {
                    lore.removeAll(Key.levelString);
                }
            }

            for (int var3 = 0; var3 < lore.size() && var3 >= 0; ++var3) {
                if (var3 >= 0 && ((String) lore.get(var3)).contains(Language.FIRST)) {
                    lore.remove(var3);
                    --var3;
                }
            }
        }

        return lore;
    }

    public static List cleanQuenchingProtectRune(List lore) {
        if (lore != null && !lore.isEmpty()) {
            for (int j = 0; j < lore.size() && j >= 0; ++j) {
                Iterator var2 = NewCustomCuiLian.customCuilianManager.customCuilianLevelList.iterator();

                while (var2.hasNext()) {
                    Level l = (Level) var2.next();
                    if (l.hasQuenchingProtectRune.booleanValue() && j >= 0 && l.quenchingProtectRuneString.equalsIgnoreCase((String) lore.get(j)) && l.levelValue.intValue() > getLevelByLoreList(lore).levelValue.intValue()) {
                        lore.remove(j);
                        --j;
                    }
                }
            }
        }

        return lore;
    }

    public static int getRandom(int min, int max) {
        return min == max ? 0 : (int) (Math.random() * (double) (max - min + 1)) + min;
    }

    public static Stone getStoneByItem(ItemStack i) {
        if (i != null && i.hasItemMeta() && i.getItemMeta().hasLore()) {
            Iterator var1 = NewCustomCuiLian.customCuilianManager.customCuilianStoneList.iterator();

            while (var1.hasNext()) {
                Stone Key = (Stone) var1.next();
                if (Key.cuiLianStone.getItemMeta().equals(i.getItemMeta())) {
                    return Key;
                }
            }
        }

        return NewCustomCuiLian.customCuilianManager.NULLStone;
    }

    public static Level getLevelByQuenchingProtectRuneString(ItemStack i) {
        Level l = NewCustomCuiLian.customCuilianManager.NULLLevel;
        if (i != null && i.hasItemMeta() && i.getItemMeta().hasLore() && NewCustomCuiLian.customCuilianManager.ItemList.contains(i.getType())) {
            Object lore;
            if (i.getItemMeta().hasLore()) {
                lore = i.getItemMeta().getLore();
            } else {
                lore = new ArrayList();
            }

            Iterator var3 = ((List) lore).iterator();

            while (var3.hasNext()) {
                String line = (String) var3.next();
                Iterator var5 = NewCustomCuiLian.customCuilianManager.customCuilianLevelList.iterator();

                while (var5.hasNext()) {
                    Level Key = (Level) var5.next();
                    if (Key.quenchingProtectRuneString.equals(line) && Key.levelValue.intValue() >= l.levelValue.intValue()) {
                        l = Key;
                    }
                }
            }
        }

        return l;
    }

    public static List getListStringByType(Material itemid) {
        return NewCustomCuiLian.arms.contains(itemid) ? NewCustomCuiLian.powerArms : (NewCustomCuiLian.boots.contains(itemid) ? NewCustomCuiLian.powerBoots : (NewCustomCuiLian.chestplate.contains(itemid) ? NewCustomCuiLian.powerChestplate : (NewCustomCuiLian.leggings.contains(itemid) ? NewCustomCuiLian.powerLeggings : (NewCustomCuiLian.helmet.contains(itemid) ? NewCustomCuiLian.powerHelmet : null))));
    }

    public static String getType(Material itemid) {
        return NewCustomCuiLian.arms.contains(itemid) ? "arms" : (NewCustomCuiLian.boots.contains(itemid) ? "boots" : (NewCustomCuiLian.chestplate.contains(itemid) ? "chestplate" : (NewCustomCuiLian.leggings.contains(itemid) ? "leggings" : (NewCustomCuiLian.helmet.contains(itemid) ? "helmet" : ""))));
    }

    public static List getCuiLianTypeForLocal(Material itemid) {
        return NewCustomCuiLian.arms.contains(itemid) ? NewCustomCuiLian.localArms : (NewCustomCuiLian.boots.contains(itemid) ? NewCustomCuiLian.localBoots : (NewCustomCuiLian.chestplate.contains(itemid) ? NewCustomCuiLian.localChestplate : (NewCustomCuiLian.leggings.contains(itemid) ? NewCustomCuiLian.localLeggings : (NewCustomCuiLian.helmet.contains(itemid) ? NewCustomCuiLian.localHelmet : null))));
    }

    public static List getLore(List powerlist, Level level, Material itemid) {
        ArrayList lore = new ArrayList();
        Iterator var4 = powerlist.iterator();

        label124:
        while (var4.hasNext()) {
            String str = (String) var4.next();
            Iterator var6;
            String v;
            if (str.equalsIgnoreCase("damage")) {
                var6 = Language.DAMAGE_LORE.iterator();

                while (var6.hasNext()) {
                    v = (String) var6.next();
                    if (((Double) level.attribute.damage.get(getType(itemid))).doubleValue() != 0.0D) {
                        lore.add(Language.FIRST + v.replace("%d", String.valueOf(level.attribute.damage.get(getType(itemid)))));
                    }
                }
            }

            if (str.equalsIgnoreCase("bloodSuck")) {
                var6 = Language.BLOOD_SUCKING_LORE.iterator();

                while (var6.hasNext()) {
                    v = (String) var6.next();
                    if (((Double) level.attribute.bloodSuck.get(getType(itemid))).doubleValue() != 0.0D) {
                        lore.add(Language.FIRST + v.replace("%d", String.valueOf(level.attribute.bloodSuck.get(getType(itemid)))));
                    }
                }
            }

            if (str.equalsIgnoreCase("experience")) {
                var6 = Language.EXPRIENCE.iterator();

                while (var6.hasNext()) {
                    v = (String) var6.next();
                    if (((Double) level.attribute.experience.get(getType(itemid))).doubleValue() != 0.0D) {
                        lore.add(Language.FIRST + v.replace("%d", String.valueOf(level.attribute.experience.get(getType(itemid)))));
                    }
                }
            }

            if (str.equalsIgnoreCase("defense")) {
                var6 = Language.DEFENSE_LORE.iterator();

                while (var6.hasNext()) {
                    v = (String) var6.next();
                    if (((Double) level.attribute.defense.get(getType(itemid))).doubleValue() != 0.0D) {
                        lore.add(Language.FIRST + v.replace("%d", String.valueOf(level.attribute.defense.get(getType(itemid)))));
                    }
                }
            }

            if (str.equalsIgnoreCase("reboundDamage")) {
                var6 = Language.ANTI_INJURY_LORE.iterator();

                while (var6.hasNext()) {
                    v = (String) var6.next();
                    if (((Double) level.attribute.reboundDamage.get(getType(itemid))).doubleValue() != 0.0D) {
                        lore.add(Language.FIRST + v.replace("%d", String.valueOf(level.attribute.reboundDamage.get(getType(itemid)))));
                    }
                }
            }

            if (str.equalsIgnoreCase("jump")) {
                var6 = Language.JUMP_LORE.iterator();

                while (var6.hasNext()) {
                    v = (String) var6.next();
                    if (((Double) level.attribute.jump.get(getType(itemid))).doubleValue() != 0.0D) {
                        lore.add(Language.FIRST + v.replace("%d", String.valueOf(level.attribute.jump.get(getType(itemid)))));
                    }
                }
            }

            var6 = NewCustomCuiLian.variables.iterator();

            while (true) {
                Variable v1;
                do {
                    if (!var6.hasNext()) {
                        continue label124;
                    }

                    v1 = (Variable) var6.next();
                } while (!str.equalsIgnoreCase(v1.name));

                Iterator var8 = v1.lore.iterator();

                while (var8.hasNext()) {
                    String s = (String) var8.next();
                    if (((Double) ((HashMap) level.attribute.list.get(NewCustomCuiLian.variables.indexOf(v1))).get(getType(itemid))).doubleValue() != 0.0D) {
                        lore.add(Language.FIRST + s.replace("%d", String.valueOf(((HashMap) level.attribute.list.get(NewCustomCuiLian.variables.indexOf(v1))).get(getType(itemid)))));
                    }
                }
            }
        }

        return lore;
    }

    public static ItemStack addCuilianQuenchingProtectRune(ItemStack i, ItemStack quenchingProtectRune) {
        if (i != null && quenchingProtectRune != null && i.hasItemMeta() && quenchingProtectRune.hasItemMeta() && i.getItemMeta().hasLore() && quenchingProtectRune.getItemMeta().hasLore() && getLevelByQuenchingProtectRune(quenchingProtectRune) != NewCustomCuiLian.customCuilianManager.NULLLevel) {
            ItemMeta meta = i.getItemMeta();
            ArrayList lore = new ArrayList();
            if (meta.hasLore()) {
                lore.addAll(meta.getLore());
            }

            List lore1 = cleanQuenchingProtectRune(i.getItemMeta().getLore());
            Level l = getLevelByQuenchingProtectRune(quenchingProtectRune);
            lore1.add(l.quenchingProtectRuneString);
            meta.setLore(lore1);
            i.setItemMeta(meta);
        }

        return i;
    }

    public static Double getExperience(List itemlist) {
        Double value = Double.valueOf(0.0D);
        Iterator var2 = itemlist.iterator();

        while (var2.hasNext()) {
            ItemStack item = (ItemStack) var2.next();
            if (item != null && item.hasItemMeta() && item.getItemMeta().hasLore() && NewCustomCuiLian.customCuilianManager.ItemList.contains(item.getType()) && getLevelByItem(item) != NewCustomCuiLian.customCuilianManager.NULLLevel && getListStringByType(item.getType()).contains("experience")) {
                value = Double.valueOf(value.doubleValue() + ((Double) getLevelByItem(item).attribute.experience.get(getType(item.getType()))).doubleValue());
            }
        }

        return value;
    }

    public static int getJump(List itemlist) {
        int value = 0;
        Iterator var2 = itemlist.iterator();

        while (var2.hasNext()) {
            ItemStack item = (ItemStack) var2.next();
            if (item != null && item.hasItemMeta() && item.getItemMeta().hasLore() && NewCustomCuiLian.customCuilianManager.ItemList.contains(item.getType()) && getLevelByItem(item) != NewCustomCuiLian.customCuilianManager.NULLLevel && getListStringByType(item.getType()).contains("jump")) {
                value = (int) ((double) value + ((Double) getLevelByItem(item).attribute.jump.get(getType(item.getType()))).doubleValue());
            }
        }

        return value;
    }

    public static Double getDamage(List itemlist) {
        Double value = Double.valueOf(0.0D);
        Iterator var2 = itemlist.iterator();

        while (var2.hasNext()) {
            ItemStack item = (ItemStack) var2.next();
            if (item != null && item.hasItemMeta() && item.getItemMeta().hasLore() && NewCustomCuiLian.customCuilianManager.ItemList.contains(item.getType()) && getLevelByItem(item) != NewCustomCuiLian.customCuilianManager.NULLLevel && getListStringByType(item.getType()).contains("damage")) {
                value = Double.valueOf(value.doubleValue() + ((Double) getLevelByItem(item).attribute.damage.get(getType(item.getType()))).doubleValue());
            }
        }

        return value;
    }

    public static Double getBloodSuck(List itemlist) {
        Double value = Double.valueOf(0.0D);
        Iterator var2 = itemlist.iterator();

        while (var2.hasNext()) {
            ItemStack item = (ItemStack) var2.next();
            if (item != null && item.hasItemMeta() && item.getItemMeta().hasLore() && NewCustomCuiLian.customCuilianManager.ItemList.contains(item.getType()) && getLevelByItem(item) != NewCustomCuiLian.customCuilianManager.NULLLevel && getListStringByType(item.getType()).contains("bloodSuck")) {
                value = Double.valueOf(value.doubleValue() + ((Double) getLevelByItem(item).attribute.bloodSuck.get(getType(item.getType()))).doubleValue());
            }
        }

        return value;
    }

    public static Double getDefense(List itemlist) {
        Double value = Double.valueOf(0.0D);
        Iterator var2 = itemlist.iterator();

        while (var2.hasNext()) {
            ItemStack item = (ItemStack) var2.next();
            if (item != null && item.hasItemMeta() && item.getItemMeta().hasLore() && NewCustomCuiLian.customCuilianManager.ItemList.contains(item.getType()) && getLevelByItem(item) != NewCustomCuiLian.customCuilianManager.NULLLevel && getListStringByType(item.getType()).contains("defense")) {
                value = Double.valueOf(value.doubleValue() + ((Double) getLevelByItem(item).attribute.defense.get(getType(item.getType()))).doubleValue());
            }
        }

        return value;
    }

    public static Double getReboundDamage(List itemlist) {
        Double value = Double.valueOf(0.0D);
        Iterator var2 = itemlist.iterator();

        while (var2.hasNext()) {
            ItemStack item = (ItemStack) var2.next();
            if (item != null && item.hasItemMeta() && item.getItemMeta().hasLore() && NewCustomCuiLian.customCuilianManager.ItemList.contains(item.getType()) && getLevelByItem(item) != NewCustomCuiLian.customCuilianManager.NULLLevel && getListStringByType(item.getType()).contains("reboundDamage")) {
                value = Double.valueOf(value.doubleValue() + ((Double) getLevelByItem(item).attribute.reboundDamage.get(getType(item.getType()))).doubleValue());
            }
        }

        return value;
    }

    public static List addAll(ItemStack i1, ItemStack i6, ItemStack i2, ItemStack i3, ItemStack i4, ItemStack i5) {
        ArrayList item = new ArrayList();
        if (i1 != null && i1.hasItemMeta() && i1.getItemMeta().hasLore() && NewCustomCuiLian.customCuilianManager.ItemList.contains(i1.getType()) && getLevelByItem(i1) != NewCustomCuiLian.customCuilianManager.NULLLevel && getCuiLianTypeForLocal(i1.getType()).contains("hand")) {
            item.add(i1);
        }

        if (NewCustomCuiLian.ServerVersion.equalsIgnoreCase("1.9+") && i6 != null && i6.hasItemMeta() && i6.getItemMeta().hasLore() && NewCustomCuiLian.customCuilianManager.ItemList.contains(i6.getType()) && getLevelByItem(i6) != NewCustomCuiLian.customCuilianManager.NULLLevel && getCuiLianTypeForLocal(i6.getType()).contains("hand")) {
            item.add(i6);
        }

        if (i2 != null && i2.hasItemMeta() && i2.getItemMeta().hasLore() && NewCustomCuiLian.customCuilianManager.ItemList.contains(i2.getType()) && getLevelByItem(i2) != NewCustomCuiLian.customCuilianManager.NULLLevel && getCuiLianTypeForLocal(i2.getType()).contains("bag")) {
            item.add(i2);
        }

        if (i3 != null && i3.hasItemMeta() && i3.getItemMeta().hasLore() && NewCustomCuiLian.customCuilianManager.ItemList.contains(i3.getType()) && getLevelByItem(i3) != NewCustomCuiLian.customCuilianManager.NULLLevel && getCuiLianTypeForLocal(i3.getType()).contains("bag")) {
            item.add(i3);
        }

        if (i4 != null && i4.hasItemMeta() && i4.getItemMeta().hasLore() && NewCustomCuiLian.customCuilianManager.ItemList.contains(i4.getType()) && getLevelByItem(i4) != NewCustomCuiLian.customCuilianManager.NULLLevel && getCuiLianTypeForLocal(i4.getType()).contains("bag")) {
            item.add(i4);
        }

        if (i5 != null && i5.hasItemMeta() && i5.getItemMeta().hasLore() && NewCustomCuiLian.customCuilianManager.ItemList.contains(i5.getType()) && getLevelByItem(i5) != NewCustomCuiLian.customCuilianManager.NULLLevel && getCuiLianTypeForLocal(i5.getType()).contains("bag")) {
            item.add(i5);
        }

        return item;
    }

    public static Level getLevelByString(List s) {
        Iterator var1 = NewCustomCuiLian.customCuilianManager.customCuilianLevelList.iterator();

        Level l;
        do {
            if (!var1.hasNext()) {
                return NewCustomCuiLian.customCuilianManager.NULLLevel;
            }

            l = (Level) var1.next();
        } while (!l.levelString.equals(s));

        return l;
    }

    public static Level getLevelByItem(ItemStack i) {
        if (i != null && i.hasItemMeta() && i.getItemMeta().hasLore()) {
            Iterator var1 = NewCustomCuiLian.customCuilianManager.customCuilianLevelList.iterator();

            while (var1.hasNext()) {
                Level Key = (Level) var1.next();
                if (i.getItemMeta().getLore().containsAll(Key.levelString)) {
                    return Key;
                }
            }
        }

        return NewCustomCuiLian.customCuilianManager.NULLLevel;
    }

    public static Level getLevelByLoreList(List lore) {
        if (lore != null && !lore.isEmpty()) {
            Iterator var1 = NewCustomCuiLian.customCuilianManager.customCuilianLevelList.iterator();

            while (var1.hasNext()) {
                Level Key = (Level) var1.next();
                if (lore.containsAll(Key.levelString)) {
                    return Key;
                }
            }
        }

        return NewCustomCuiLian.customCuilianManager.NULLLevel;
    }

    public static Level getLevelByInteger(int s) {
        return s > -1 && s <= NewCustomCuiLian.Max ? (Level) NewCustomCuiLian.customCuilianManager.customCuilianLevelList.get(s) : NewCustomCuiLian.customCuilianManager.NULLLevel;
    }

    public static boolean isStoneMapItemMetaHasItemMeta(ItemMeta meta) {
        if (meta != null) {
            Iterator var1 = NewCustomCuiLian.customCuilianManager.customCuilianStoneList.iterator();

            while (var1.hasNext()) {
                Stone s = (Stone) var1.next();
                if (s.cuiLianStone.hasItemMeta() && s.cuiLianStone.getItemMeta().equals(meta)) {
                    return true;
                }
            }
        }

        return false;
    }

    public static boolean isQuenchingProtectRuneMapItemMetaHasItemMeta(ItemMeta meta) {
        if (meta != null) {
            Iterator var1 = NewCustomCuiLian.customCuilianManager.customCuilianLevelList.iterator();

            while (var1.hasNext()) {
                Level s = (Level) var1.next();
                if (s.quenchingProtectRune != null && s.quenchingProtectRune.hasItemMeta() && s.quenchingProtectRune.getItemMeta().equals(meta)) {
                    return true;
                }
            }
        }

        return false;
    }

    public static HashMap getHashMap(String s1, YamlConfiguration config, int i, String s2) {
        HashMap hashmap = new HashMap();
        if (config.get(i + "." + s1 + "." + s2) != null) {
            Double value = Double.valueOf(config.getDouble(i + "." + s1 + "." + s2));
            hashmap.put(s1, value);
        } else {
            hashmap.put(s1, Double.valueOf(0.0D));
        }

        return hashmap;
    }

    public static List removeDurability(List items) {
        Iterator var1 = items.iterator();

        while (var1.hasNext()) {
            ItemStack item = (ItemStack) var1.next();
            if (!item.getItemMeta().spigot().isUnbreakable()) {
                short nj = item.getDurability();
                if (nj - 1 > 0) {
                    item.setDurability((short) (nj - 1));
                } else {
                    item.setType(Material.AIR);
                }
            }
        }

        return items;
    }
}
