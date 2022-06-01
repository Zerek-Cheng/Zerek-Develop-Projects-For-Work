/*
 * Decompiled with CFR 0_133.
 * 
 * Could not load the following classes:
 *  org.bukkit.Bukkit
 *  org.bukkit.ChatColor
 *  org.bukkit.Location
 *  org.bukkit.Material
 *  org.bukkit.World
 *  org.bukkit.configuration.file.FileConfiguration
 *  org.bukkit.entity.Item
 *  org.bukkit.entity.LivingEntity
 *  org.bukkit.entity.Player
 *  org.bukkit.inventory.EntityEquipment
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.inventory.PlayerInventory
 *  org.bukkit.inventory.meta.ItemMeta
 */
package Test;

import Test.LoreAttributes;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Item;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;

public class LoreManager {
    private LoreAttributes plugin;
    private Pattern healthRegex;
    private Pattern negHealthRegex;
    private Pattern regenRegex;
    private Pattern attackSpeedRegex;
    private Pattern damageValueRegex;
    private Pattern negitiveDamageValueRegex;
    private Pattern damageRangeRegex;
    private Pattern dodgeRegex;
    private Pattern critChanceRegex;
    private Pattern critDamageRegex;
    private Pattern lifestealRegex;
    private Pattern armorRegex;
    private Pattern restrictionRegex;
    private Pattern levelRegex;
    private HashMap<String, Timestamp> attackLog;
    private boolean attackSpeedEnabled;
    private Random generator;

    public LoreManager(LoreAttributes plugin) {
        this.plugin = plugin;
        this.generator = new Random();
        this.attackSpeedEnabled = false;
        if (LoreAttributes.config.getBoolean("lore.attack-speed.enabled")) {
            this.attackSpeedEnabled = true;
            this.attackLog = new HashMap();
        }
        this.healthRegex = Pattern.compile("[+](\\d+)[ ](" + LoreAttributes.config.getString("lore.health.keyword").toLowerCase() + ")");
        this.negHealthRegex = Pattern.compile("[-](\\d+)[ ](" + LoreAttributes.config.getString("lore.health.keyword").toLowerCase() + ")");
        this.regenRegex = Pattern.compile("[+](\\d+)[ ](" + LoreAttributes.config.getString("lore.regen.keyword").toLowerCase() + ")");
        this.attackSpeedRegex = Pattern.compile("[+](\\d+)[ ](" + LoreAttributes.config.getString("lore.attack-speed.keyword").toLowerCase() + ")");
        this.damageValueRegex = Pattern.compile("[+](\\d+)[ ](" + LoreAttributes.config.getString("lore.damage.keyword").toLowerCase() + ")");
        this.negitiveDamageValueRegex = Pattern.compile("[-](\\d+)[ ](" + LoreAttributes.config.getString("lore.damage.keyword").toLowerCase() + ")");
        this.damageRangeRegex = Pattern.compile("(\\d+)(-)(\\d+)[ ](" + LoreAttributes.config.getString("lore.damage.keyword").toLowerCase() + ")");
        this.dodgeRegex = Pattern.compile("[+](\\d+)[%][ ](" + LoreAttributes.config.getString("lore.dodge.keyword").toLowerCase() + ")");
        this.critChanceRegex = Pattern.compile("[+](\\d+)[%][ ](" + LoreAttributes.config.getString("lore.critical-chance.keyword").toLowerCase() + ")");
        this.critDamageRegex = Pattern.compile("[+](\\d+)[ ](" + LoreAttributes.config.getString("lore.critical-damage.keyword").toLowerCase() + ")");
        this.lifestealRegex = Pattern.compile("[+](\\d+)[ ](" + LoreAttributes.config.getString("lore.life-steal.keyword").toLowerCase() + ")");
        this.armorRegex = Pattern.compile("[+](\\d+)[ ](" + LoreAttributes.config.getString("lore.armor.keyword").toLowerCase() + ")");
        this.restrictionRegex = Pattern.compile("(" + LoreAttributes.config.getString("lore.restriction.keyword").toLowerCase() + ": )(\\w*)");
        this.levelRegex = Pattern.compile("level (\\d+)()");
    }

    public void disable() {
        this.attackSpeedEnabled = false;
        if (this.attackLog != null) {
            this.attackLog.clear();
        }
    }

    public void handleArmorRestriction(Player player) {
        if (!this.canUse(player, player.getInventory().getBoots())) {
            if (player.getInventory().firstEmpty() >= 0) {
                player.getInventory().addItem(new ItemStack[]{player.getInventory().getBoots()});
            } else {
                player.getWorld().dropItem(player.getLocation(), player.getInventory().getBoots());
            }
            player.getInventory().setBoots(new ItemStack(0));
        }
        if (!this.canUse(player, player.getInventory().getChestplate())) {
            if (player.getInventory().firstEmpty() >= 0) {
                player.getInventory().addItem(new ItemStack[]{player.getInventory().getChestplate()});
            } else {
                player.getWorld().dropItem(player.getLocation(), player.getInventory().getChestplate());
            }
            player.getInventory().setChestplate(new ItemStack(0));
        }
        if (!this.canUse(player, player.getInventory().getHelmet())) {
            if (player.getInventory().firstEmpty() >= 0) {
                player.getInventory().addItem(new ItemStack[]{player.getInventory().getHelmet()});
            } else {
                player.getWorld().dropItem(player.getLocation(), player.getInventory().getHelmet());
            }
            player.getInventory().setHelmet(new ItemStack(0));
        }
        if (!this.canUse(player, player.getInventory().getLeggings())) {
            if (player.getInventory().firstEmpty() >= 0) {
                player.getInventory().addItem(new ItemStack[]{player.getInventory().getLeggings()});
            } else {
                player.getWorld().dropItem(player.getLocation(), player.getInventory().getLeggings());
            }
            player.getInventory().setLeggings(new ItemStack(0));
        }
        this.applyHpBonus((LivingEntity)player);
    }

    public boolean canUse(Player player, ItemStack item) {
        if (item != null && item.hasItemMeta() && item.getItemMeta().hasLore()) {
            List lore = item.getItemMeta().getLore();
            String allLore = lore.toString().toLowerCase();
            Matcher valueMatcher = this.levelRegex.matcher(allLore);
            if (valueMatcher.find() && player.getLevel() < Integer.valueOf(valueMatcher.group(1))) {
                player.sendMessage("Item was not able to be equipped.");
                return false;
            }
            valueMatcher = this.restrictionRegex.matcher(allLore);
            if (valueMatcher.find()) {
                if (player.hasPermission("loreattributes." + valueMatcher.group(2))) {
                    return true;
                }
                if (LoreAttributes.config.getBoolean("lore.restriction.display-message")) {
                    player.sendMessage(LoreAttributes.config.getString("lore.restriction.message").replace("%itemname%", item.getType().toString()));
                }
                return false;
            }
        }
        return true;
    }

    public int getDodgeBonus(LivingEntity entity) {
        List lore;
        String allLore;
        Matcher valueMatcher;
        ItemStack item;
        Integer dodgeBonus = 0;
        ItemStack[] arritemStack = entity.getEquipment().getArmorContents();
        int n = arritemStack.length;
        int n2 = 0;
        while (n2 < n) {
            Matcher valueMatcher2;
            List lore2;
            String allLore2;
            item = arritemStack[n2];
            if (item != null && item.hasItemMeta() && item.getItemMeta().hasLore() && (valueMatcher2 = this.dodgeRegex.matcher(allLore2 = (lore2 = item.getItemMeta().getLore()).toString().toLowerCase())).find()) {
                dodgeBonus = dodgeBonus + Integer.valueOf(valueMatcher2.group(1));
            }
            ++n2;
        }
        item = entity.getEquipment().getItemInHand();
        if (item != null && item.hasItemMeta() && item.getItemMeta().hasLore() && (valueMatcher = this.dodgeRegex.matcher(allLore = (lore = item.getItemMeta().getLore()).toString().toLowerCase())).find()) {
            dodgeBonus = dodgeBonus + Integer.valueOf(valueMatcher.group(1));
        }
        return dodgeBonus;
    }

    public boolean dodgedAttack(LivingEntity entity) {
        if (!entity.isValid()) {
            return false;
        }
        Integer chance = this.getDodgeBonus(entity);
        Integer roll = this.generator.nextInt(100) + 1;
        if (chance >= roll) {
            return true;
        }
        return false;
    }

    private int getCritChance(LivingEntity entity) {
        List lore;
        String allLore;
        Matcher valueMatcher;
        ItemStack item;
        Integer chance = 0;
        ItemStack[] arritemStack = entity.getEquipment().getArmorContents();
        int n = arritemStack.length;
        int n2 = 0;
        while (n2 < n) {
            Matcher valueMatcher2;
            List lore2;
            String allLore2;
            item = arritemStack[n2];
            if (item != null && item.hasItemMeta() && item.getItemMeta().hasLore() && (valueMatcher2 = this.critChanceRegex.matcher(allLore2 = (lore2 = item.getItemMeta().getLore()).toString().toLowerCase())).find()) {
                chance = chance + Integer.valueOf(valueMatcher2.group(1));
            }
            ++n2;
        }
        item = entity.getEquipment().getItemInHand();
        if (item != null && item.hasItemMeta() && item.getItemMeta().hasLore() && (valueMatcher = this.critChanceRegex.matcher(allLore = (lore = item.getItemMeta().getLore()).toString().toLowerCase())).find()) {
            chance = chance + Integer.valueOf(valueMatcher.group(1));
        }
        return chance;
    }

    private boolean critAttack(LivingEntity entity) {
        if (!entity.isValid()) {
            return false;
        }
        Integer chance = this.getCritChance(entity);
        Integer roll = this.generator.nextInt(100) + 1;
        if (chance >= roll) {
            return true;
        }
        return false;
    }

    public int getArmorBonus(LivingEntity entity) {
        List lore;
        String allLore;
        Matcher valueMatcher;
        ItemStack item;
        Integer armor = 0;
        ItemStack[] arritemStack = entity.getEquipment().getArmorContents();
        int n = arritemStack.length;
        int n2 = 0;
        while (n2 < n) {
            Matcher valueMatcher2;
            List lore2;
            String allLore2;
            item = arritemStack[n2];
            if (item != null && item.hasItemMeta() && item.getItemMeta().hasLore() && (valueMatcher2 = this.armorRegex.matcher(allLore2 = (lore2 = item.getItemMeta().getLore()).toString().toLowerCase())).find()) {
                armor = armor + Integer.valueOf(valueMatcher2.group(1));
            }
            ++n2;
        }
        item = entity.getEquipment().getItemInHand();
        if (item != null && item.hasItemMeta() && item.getItemMeta().hasLore() && (valueMatcher = this.armorRegex.matcher(allLore = (lore = item.getItemMeta().getLore()).toString().toLowerCase())).find()) {
            armor = armor + Integer.valueOf(valueMatcher.group(1));
        }
        return armor;
    }

    public int getLifeSteal(LivingEntity entity) {
        List lore;
        String allLore;
        Matcher valueMatcher;
        ItemStack item;
        Integer steal = 0;
        ItemStack[] arritemStack = entity.getEquipment().getArmorContents();
        int n = arritemStack.length;
        int n2 = 0;
        while (n2 < n) {
            Matcher valueMatcher2;
            List lore2;
            String allLore2;
            item = arritemStack[n2];
            if (item != null && item.hasItemMeta() && item.getItemMeta().hasLore() && (valueMatcher2 = this.lifestealRegex.matcher(allLore2 = (lore2 = item.getItemMeta().getLore()).toString().toLowerCase())).find()) {
                steal = steal + Integer.valueOf(valueMatcher2.group(1));
            }
            ++n2;
        }
        item = entity.getEquipment().getItemInHand();
        if (item != null && item.hasItemMeta() && item.getItemMeta().hasLore() && (valueMatcher = this.lifestealRegex.matcher(allLore = (lore = item.getItemMeta().getLore()).toString().toLowerCase())).find()) {
            steal = steal + Integer.valueOf(valueMatcher.group(1));
        }
        return steal;
    }

    public int getCritDamage(LivingEntity entity) {
        List lore;
        String allLore;
        Matcher valueMatcher;
        ItemStack item;
        if (!this.critAttack(entity)) {
            return 0;
        }
        Integer damage = 0;
        ItemStack[] arritemStack = entity.getEquipment().getArmorContents();
        int n = arritemStack.length;
        int n2 = 0;
        while (n2 < n) {
            Matcher valueMatcher2;
            List lore2;
            String allLore2;
            item = arritemStack[n2];
            if (item != null && item.hasItemMeta() && item.getItemMeta().hasLore() && (valueMatcher2 = this.critDamageRegex.matcher(allLore2 = (lore2 = item.getItemMeta().getLore()).toString().toLowerCase())).find()) {
                damage = damage + Integer.valueOf(valueMatcher2.group(1));
            }
            ++n2;
        }
        item = entity.getEquipment().getItemInHand();
        if (item != null && item.hasItemMeta() && item.getItemMeta().hasLore() && (valueMatcher = this.critDamageRegex.matcher(allLore = (lore = item.getItemMeta().getLore()).toString().toLowerCase())).find()) {
            damage = damage + Integer.valueOf(valueMatcher.group(1));
        }
        return damage;
    }

    private double getAttackCooldown(Player player) {
        if (!this.attackSpeedEnabled) {
            return 0.0;
        }
        return LoreAttributes.config.getDouble("lore.attack-speed.base-delay") * 0.1 - this.getAttackSpeed(player) * 0.1;
    }

    public void addAttackCooldown(String playerName) {
        if (!this.attackSpeedEnabled) {
            return;
        }
        Timestamp able = new Timestamp((long)((double)new Date().getTime() + this.getAttackCooldown(Bukkit.getPlayerExact((String)playerName)) * 1000.0));
        this.attackLog.put(playerName, able);
    }

    public boolean canAttack(String playerName) {
        if (!this.attackSpeedEnabled) {
            return true;
        }
        if (!this.attackLog.containsKey(playerName)) {
            return true;
        }
        Date now = new Date();
        if (now.after(this.attackLog.get(playerName))) {
            return true;
        }
        return false;
    }

    private double getAttackSpeed(Player player) {
        List lore;
        ItemStack item;
        String allLore;
        Matcher valueMatcher;
        if (player == null) {
            return 1.0;
        }
        double speed = 1.0;
        ItemStack[] arritemStack = player.getEquipment().getArmorContents();
        int n = arritemStack.length;
        int n2 = 0;
        while (n2 < n) {
            Matcher valueMatcher2;
            String allLore2;
            List lore2;
            item = arritemStack[n2];
            if (item != null && item.hasItemMeta() && item.getItemMeta().hasLore() && (valueMatcher2 = this.attackSpeedRegex.matcher(allLore2 = (lore2 = item.getItemMeta().getLore()).toString().toLowerCase())).find()) {
                speed += (double)Integer.valueOf(valueMatcher2.group(1)).intValue();
            }
            ++n2;
        }
        item = player.getEquipment().getItemInHand();
        if (item != null && item.hasItemMeta() && item.getItemMeta().hasLore() && (valueMatcher = this.attackSpeedRegex.matcher(allLore = (lore = item.getItemMeta().getLore()).toString().toLowerCase())).find()) {
            speed += (double)Integer.valueOf(valueMatcher.group(1)).intValue();
        }
        return speed;
    }

    public void applyHpBonus(LivingEntity entity) {
        if (!entity.isValid()) {
            return;
        }
        Integer hpToAdd = this.getHpBonus(entity);
        if (entity instanceof Player) {
            if (entity.getHealth() > (double)(this.getBaseHealth((Player)entity) + hpToAdd)) {
                entity.setHealth((double)(this.getBaseHealth((Player)entity) + hpToAdd));
            }
            entity.setMaxHealth((double)(this.getBaseHealth((Player)entity) + hpToAdd));
        }
    }

    public int getHpBonus(LivingEntity entity) {
        Integer hpToAdd = 0;
        ItemStack[] arritemStack = entity.getEquipment().getArmorContents();
        int n = arritemStack.length;
        int n2 = 0;
        while (n2 < n) {
            ItemStack item = arritemStack[n2];
            if (item != null && item.hasItemMeta() && item.getItemMeta().hasLore()) {
                List lore = item.getItemMeta().getLore();
                String allLore = lore.toString().toLowerCase();
                Matcher negmatcher = this.negHealthRegex.matcher(allLore);
                Matcher matcher = this.healthRegex.matcher(allLore);
                if (matcher.find()) {
                    hpToAdd = hpToAdd + Integer.valueOf(matcher.group(1));
                }
                if (negmatcher.find()) {
                    hpToAdd = hpToAdd - Integer.valueOf(negmatcher.group(1));
                }
                if (hpToAdd < 0) {
                    hpToAdd = 0;
                }
            }
            ++n2;
        }
        return hpToAdd;
    }

    public int getBaseHealth(Player player) {
        int hp = LoreAttributes.config.getInt("lore.health.base-health");
        return hp;
    }

    public int getRegenBonus(LivingEntity entity) {
        if (!entity.isValid()) {
            return 0;
        }
        Integer regenBonus = 0;
        ItemStack[] arritemStack = entity.getEquipment().getArmorContents();
        int n = arritemStack.length;
        int n2 = 0;
        while (n2 < n) {
            List lore;
            String allLore;
            Matcher matcher;
            ItemStack item = arritemStack[n2];
            if (item != null && item.hasItemMeta() && item.getItemMeta().hasLore() && (matcher = this.regenRegex.matcher(allLore = (lore = item.getItemMeta().getLore()).toString().toLowerCase())).find()) {
                regenBonus = regenBonus + Integer.valueOf(matcher.group(1));
            }
            ++n2;
        }
        return regenBonus;
    }

    public int getDamageBonus(LivingEntity entity) {
        ItemStack item;
        if (!entity.isValid()) {
            return 0;
        }
        Integer damageMin = 0;
        Integer damageMax = 0;
        Integer damageBonus = 0;
        ItemStack[] arritemStack = entity.getEquipment().getArmorContents();
        int n = arritemStack.length;
        int n2 = 0;
        while (n2 < n) {
            item = arritemStack[n2];
            if (item != null && item.hasItemMeta() && item.getItemMeta().hasLore()) {
                List lore = item.getItemMeta().getLore();
                String allLore = lore.toString().toLowerCase();
                Matcher rangeMatcher = this.damageRangeRegex.matcher(allLore);
                Matcher valueMatcher = this.damageValueRegex.matcher(allLore);
                if (rangeMatcher.find()) {
                    damageMin = damageMin + Integer.valueOf(rangeMatcher.group(1));
                    damageMax = damageMax + Integer.valueOf(rangeMatcher.group(3));
                }
                if (valueMatcher.find()) {
                    damageBonus = damageBonus + Integer.valueOf(valueMatcher.group(1));
                }
            }
            ++n2;
        }
        item = entity.getEquipment().getItemInHand();
        if (item != null && item.hasItemMeta() && item.getItemMeta().hasLore()) {
            List lore = item.getItemMeta().getLore();
            String allLore = lore.toString().toLowerCase();
            Matcher negValueMatcher = this.negitiveDamageValueRegex.matcher(allLore);
            Matcher rangeMatcher = this.damageRangeRegex.matcher(allLore);
            Matcher valueMatcher = this.damageValueRegex.matcher(allLore);
            if (rangeMatcher.find()) {
                damageMin = damageMin + Integer.valueOf(rangeMatcher.group(1));
                damageMax = damageMax + Integer.valueOf(rangeMatcher.group(3));
            }
            if (valueMatcher.find()) {
                damageBonus = damageBonus + Integer.valueOf(valueMatcher.group(1));
                damageBonus = damageBonus - Integer.valueOf(negValueMatcher.group(1));
            }
        }
        if (damageMax < 1) {
            damageMax = 1;
        }
        if (damageMin < 1) {
            damageMin = 1;
        }
        return (int)Math.round(Math.random() * (double)(damageMax - damageMin) + (double)damageMin.intValue() + (double)damageBonus.intValue() + (double)this.getCritDamage(entity));
    }

    public boolean useRangeOfDamage(LivingEntity entity) {
        List lore;
        Matcher rangeMatcher;
        String allLore;
        ItemStack item;
        if (!entity.isValid()) {
            return false;
        }
        ItemStack[] arritemStack = entity.getEquipment().getArmorContents();
        int n = arritemStack.length;
        int n2 = 0;
        while (n2 < n) {
            Matcher rangeMatcher2;
            List lore2;
            String allLore2;
            item = arritemStack[n2];
            if (item != null && item.hasItemMeta() && item.getItemMeta().hasLore() && (rangeMatcher2 = this.damageRangeRegex.matcher(allLore2 = (lore2 = item.getItemMeta().getLore()).toString().toLowerCase())).find()) {
                return true;
            }
            ++n2;
        }
        item = entity.getEquipment().getItemInHand();
        if (item != null && item.hasItemMeta() && item.getItemMeta().hasLore() && (rangeMatcher = this.damageRangeRegex.matcher(allLore = (lore = item.getItemMeta().getLore()).toString().toLowerCase())).find()) {
            return true;
        }
        return false;
    }

    private int getPermissionsHealth(Player player) {
        int hp = LoreAttributes.config.getInt("lore.health.base-health");
        try {
            hp = LoreAttributes.config.getInt("lore.health.base-health");
        }
        catch (Exception e) {
            return hp;
        }
        return hp;
    }

    public void displayLoreStats(Player sender) {
        HashSet<String> message = new HashSet<String>();
        if (this.getHpBonus((LivingEntity)sender) != 0) {
            message.add((Object)ChatColor.GRAY + LoreAttributes.config.getString("lore.health.keyword") + ": " + (Object)ChatColor.WHITE + this.getHpBonus((LivingEntity)sender));
        }
        if (this.getRegenBonus((LivingEntity)sender) != 0) {
            message.add((Object)ChatColor.GRAY + LoreAttributes.config.getString("lore.regen.keyword") + ": " + (Object)ChatColor.WHITE + this.getRegenBonus((LivingEntity)sender));
        }
        if (LoreAttributes.config.getBoolean("lore.attack-speed.enabled")) {
            message.add((Object)ChatColor.GRAY + LoreAttributes.config.getString("lore.attack-speed.keyword") + ": " + (Object)ChatColor.WHITE + this.getAttackSpeed(sender));
        }
        if (this.getDamageBonus((LivingEntity)sender) != 0) {
            message.add((Object)ChatColor.GRAY + LoreAttributes.config.getString("lore.damage.keyword") + ": " + (Object)ChatColor.WHITE + this.getDamageBonus((LivingEntity)sender));
        }
        if (this.getDodgeBonus((LivingEntity)sender) != 0) {
            message.add((Object)ChatColor.GRAY + LoreAttributes.config.getString("lore.dodge.keyword") + ": " + (Object)ChatColor.WHITE + this.getDodgeBonus((LivingEntity)sender) + "%");
        }
        if (this.getCritChance((LivingEntity)sender) != 0) {
            message.add((Object)ChatColor.GRAY + LoreAttributes.config.getString("lore.critical-chance.keyword") + ": " + (Object)ChatColor.WHITE + this.getCritChance((LivingEntity)sender) + "%");
        }
        if (this.getCritDamage((LivingEntity)sender) != 0) {
            message.add((Object)ChatColor.GRAY + LoreAttributes.config.getString("lore.critical-damage.keyword") + ": " + (Object)ChatColor.WHITE + this.getCritDamage((LivingEntity)sender));
        }
        if (this.getLifeSteal((LivingEntity)sender) != 0) {
            message.add((Object)ChatColor.GRAY + LoreAttributes.config.getString("lore.life-steal.keyword") + ": " + (Object)ChatColor.WHITE + this.getLifeSteal((LivingEntity)sender));
        }
        if (this.getArmorBonus((LivingEntity)sender) != 0) {
            message.add((Object)ChatColor.GRAY + LoreAttributes.config.getString("lore.armor.keyword") + ": " + (Object)ChatColor.WHITE + this.getArmorBonus((LivingEntity)sender));
        }
        String newMessage = "";
        for (String toSend : message) {
            if ((newMessage = String.valueOf(newMessage) + "     " + toSend).length() <= 40) continue;
            sender.sendMessage(newMessage);
            newMessage = "";
        }
        if (newMessage.length() > 0) {
            sender.sendMessage(newMessage);
        }
        message.clear();
    }
}

