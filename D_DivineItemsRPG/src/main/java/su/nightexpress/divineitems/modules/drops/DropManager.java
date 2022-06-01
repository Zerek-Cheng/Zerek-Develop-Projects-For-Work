/*
 * Decompiled with CFR 0_133.
 * 
 * Could not load the following classes:
 *  org.bukkit.Location
 *  org.bukkit.Material
 *  org.bukkit.World
 *  org.bukkit.block.Biome
 *  org.bukkit.block.Block
 *  org.bukkit.configuration.ConfigurationSection
 *  org.bukkit.configuration.file.YamlConfiguration
 *  org.bukkit.entity.Animals
 *  org.bukkit.entity.ArmorStand
 *  org.bukkit.entity.Entity
 *  org.bukkit.entity.EntityType
 *  org.bukkit.entity.LivingEntity
 *  org.bukkit.entity.Monster
 *  org.bukkit.entity.Player
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.EventPriority
 *  org.bukkit.event.entity.CreatureSpawnEvent
 *  org.bukkit.event.entity.CreatureSpawnEvent$SpawnReason
 *  org.bukkit.event.entity.EntityDeathEvent
 *  org.bukkit.inventory.EntityEquipment
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.metadata.FixedMetadataValue
 *  org.bukkit.metadata.MetadataValue
 *  org.bukkit.plugin.Plugin
 *  org.bukkit.scheduler.BukkitRunnable
 *  org.bukkit.scheduler.BukkitTask
 */
package su.nightexpress.divineitems.modules.drops;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Random;
import java.util.Set;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.block.Block;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Animals;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import su.nightexpress.divineitems.DivineItems;
import su.nightexpress.divineitems.DivineListener;
import su.nightexpress.divineitems.Module;
import su.nightexpress.divineitems.api.EntityAPI;
import su.nightexpress.divineitems.attributes.ItemStat;
import su.nightexpress.divineitems.config.Config;
import su.nightexpress.divineitems.config.ConfigManager;
import su.nightexpress.divineitems.hooks.Hook;
import su.nightexpress.divineitems.hooks.HookManager;
import su.nightexpress.divineitems.hooks.LevelsHook;
import su.nightexpress.divineitems.hooks.external.MythicMobsHook;
import su.nightexpress.divineitems.hooks.external.WorldGuardHook;
import su.nightexpress.divineitems.hooks.external.citizens.CitizensHook;
import su.nightexpress.divineitems.modules.ModuleManager;
import su.nightexpress.divineitems.modules.tiers.TierManager;
import su.nightexpress.divineitems.utils.ItemUtils;
import su.nightexpress.divineitems.utils.Utils;

public class DropManager
extends DivineListener<DivineItems>
implements Module {
    private DivineItems plugin;
    private boolean e;
    private HashMap<Module, Drops> drops;
    private final String nodrop = "NO_DROP:";

    public DropManager(DivineItems divineItems) {
        super(divineItems);
        this.plugin = divineItems;
        this.drops = new HashMap();
        this.e = divineItems.getCM().getCFG().isModuleEnabled(this.name());
    }

    @Override
    public void loadConfig() {
        for (Module module : this.plugin.getMM().getModules()) {
            Object object3;
            Object object22;
            File file;
            if (!module.isDropable() || !module.isActive() || !(file = new File(this.plugin.getDataFolder() + "/modules/drops/", String.valueOf(module.name().toLowerCase().replace(" ", "_")) + ".yml")).exists()) continue;
            YamlConfiguration yamlConfiguration = YamlConfiguration.loadConfiguration((File)file);
            if (!yamlConfiguration.contains("General.RollOnce")) {
                yamlConfiguration.set("General.RollOnce", (Object)false);
            }
            double d = yamlConfiguration.getDouble("General.Chance");
            boolean bl = yamlConfiguration.getBoolean("General.RollOnce");
            LinkedHashMap<String, Double> linkedHashMap = new LinkedHashMap<String, Double>();
            for (Object object22 : yamlConfiguration.getConfigurationSection("General.Multipliers").getKeys(false)) {
                double d2 = yamlConfiguration.getDouble("Global.Multipliers." + (String)object22);
                linkedHashMap.put((String)object22, d2);
            }
            object22 = new HashSet(yamlConfiguration.getStringList("General.Worlds"));
            boolean bl2 = yamlConfiguration.getBoolean("General.NoDropInRegions.Reverse");
            HashSet<String> hashSet = new HashSet<String>(yamlConfiguration.getStringList("General.NoDropInRegions.List"));
            boolean bl3 = yamlConfiguration.getBoolean("General.BiomesWhitelist.Reverse");
            HashSet<String> hashSet2 = new HashSet<String>(yamlConfiguration.getStringList("General.BiomesWhitelist.List"));
            HashSet<String> hashSet3 = new HashSet<String>(yamlConfiguration.getStringList("General.EntityTypes"));
            HashSet<String> hashSet4 = new HashSet<String>(yamlConfiguration.getStringList("General.MythicMobs"));
            HashSet<String> hashSet5 = new HashSet<String>(yamlConfiguration.getStringList("General.PreventFrom"));
            boolean bl4 = yamlConfiguration.getBoolean("General.DropLevelPenalty.Enabled", false);
            int n = yamlConfiguration.getInt("General.DropLevelPenalty.Variance");
            DropGlobalSettings dropGlobalSettings = new DropGlobalSettings(d, bl, linkedHashMap, (Set<String>)object22, bl2, hashSet, bl3, hashSet2, hashSet3, hashSet4, hashSet5, bl4, n);
            HashMap<String, DropItemSettings> hashMap = new HashMap<String, DropItemSettings>();
            HashMap hashMap2 = new HashMap();
            if (yamlConfiguration.contains("Items")) {
                for (Object object3 : yamlConfiguration.getConfigurationSection("Items").getKeys(false)) {
                    Object object4;
                    HashSet<String> hashSet6;
                    String string = "Items." + (String)object3 + ".";
                    double d3 = yamlConfiguration.getDouble(String.valueOf(string) + "Chance");
                    HashMap<String, String> hashMap3 = new HashMap<String, String>();
                    if (yamlConfiguration.contains(String.valueOf(string) + "Worlds")) {
                        for (String string2 : yamlConfiguration.getConfigurationSection(String.valueOf(string) + "Worlds").getKeys(false)) {
                            object4 = yamlConfiguration.getString(String.valueOf(string) + "Worlds." + string2 + ".levels");
                            hashMap3.put(string2, (String)object4);
                        }
                    }
                    boolean bl5 = yamlConfiguration.getBoolean(String.valueOf(string) + "NoDropInRegions.Reverse");
                    HashMap hashMap4 = new HashMap();
                    Object object5 = object4 = yamlConfiguration.getConfigurationSection(String.valueOf(string) + "NoDropInRegions.List").getKeys(false).toArray();
                    int n2 = ((Object[])object5).length;
                    int n3 = 0;
                    while (n3 < n2) {
                        hashSet6 = object5[n3];
                        String string3 = hashSet6.toString();
                        String string4 = yamlConfiguration.getString(String.valueOf(string) + "NoDropInRegions.List." + string3 + ".levels");
                        hashMap4.put(string3, string4);
                        ++n3;
                    }
                    hashSet6 = new HashSet<String>(yamlConfiguration.getStringList(String.valueOf(string) + "EntityTypes"));
                    HashSet<String> hashSet7 = new HashSet<String>(yamlConfiguration.getStringList(String.valueOf(string) + "MythicMobs"));
                    HashSet<String> hashSet8 = new HashSet<String>(yamlConfiguration.getStringList(String.valueOf(string) + "PreventFrom"));
                    object5 = new DropItemSettings(d3, hashMap3, bl5, hashMap4, hashSet6, hashSet7, hashSet8);
                    hashMap.put(object3.toLowerCase(), (DropItemSettings)object5);
                    hashMap2.put(object3, d3);
                }
            }
            hashMap2 = (HashMap)Utils.sortByValue(hashMap2);
            object3 = new Drops(dropGlobalSettings, hashMap, hashMap2);
            this.drops.put(module, (Drops)object3);
            try {
                yamlConfiguration.save(file);
            }
            catch (IOException iOException) {
                iOException.printStackTrace();
            }
        }
    }

    @Override
    public boolean isDropable() {
        return false;
    }

    @Override
    public boolean isResolvable() {
        return false;
    }

    @Override
    public boolean isActive() {
        return this.e;
    }

    @Override
    public String name() {
        return "Drops";
    }

    @Override
    public String version() {
        return "2.0";
    }

    @Override
    public void enable() {
        if (this.isActive()) {
            this.loadConfig();
            this.registerListeners();
        }
    }

    @Override
    public void unload() {
        if (this.isActive()) {
            this.drops.clear();
            this.e = false;
            this.unregisterListeners();
        }
    }

    @Override
    public void reload() {
        this.unload();
        this.enable();
    }

    private boolean checkRegion(DropGlobalSettings dropGlobalSettings, LivingEntity livingEntity) {
        if (!Hook.WORLD_GUARD.isEnabled()) {
            return true;
        }
        if (dropGlobalSettings.isRegionReversed()) {
            for (String string : dropGlobalSettings.getRegions()) {
                if (!this.plugin.getHM().getWorldGuard().isInRegion(livingEntity, string)) continue;
                return true;
            }
            return false;
        }
        for (String string : dropGlobalSettings.getRegions()) {
            if (!this.plugin.getHM().getWorldGuard().isInRegion(livingEntity, string)) continue;
            return false;
        }
        return true;
    }

    private boolean checkRegion(DropItemSettings dropItemSettings, LivingEntity livingEntity) {
        if (!Hook.WORLD_GUARD.isEnabled()) {
            return true;
        }
        if (dropItemSettings.isRegionReversed()) {
            for (String string : dropItemSettings.getRegions().keySet()) {
                if (!this.plugin.getHM().getWorldGuard().isInRegion(livingEntity, string)) continue;
                return true;
            }
            return false;
        }
        for (String string : dropItemSettings.getRegions().keySet()) {
            if (!this.plugin.getHM().getWorldGuard().isInRegion(livingEntity, string)) continue;
            return false;
        }
        return true;
    }

    private boolean checkBiome(DropGlobalSettings dropGlobalSettings, LivingEntity livingEntity) {
        String string = livingEntity.getLocation().getBlock().getBiome().name();
        if (dropGlobalSettings.isBiomesReversed()) {
            for (String string2 : dropGlobalSettings.getBiomes()) {
                if (!string2.equalsIgnoreCase(string) && !string2.equalsIgnoreCase("ANY")) continue;
                return false;
            }
            return true;
        }
        for (String string3 : dropGlobalSettings.getBiomes()) {
            if (!string3.equalsIgnoreCase(string) && !string3.equalsIgnoreCase("ANY")) continue;
            return true;
        }
        return false;
    }

    private boolean checkEntity(DropGlobalSettings dropGlobalSettings, LivingEntity livingEntity, String string) {
        if (livingEntity.hasMetadata("NO_DROP:" + string)) {
            return false;
        }
        if (Hook.MYTHIC_MOBS.isEnabled() && this.plugin.getHM().getMythicHook().isMythicMob((Entity)livingEntity)) {
            String string2 = this.plugin.getHM().getMythicHook().getMythicNameByEntity((Entity)livingEntity);
            if (dropGlobalSettings.getMythics().contains("ALL")) {
                return true;
            }
            if (dropGlobalSettings.getMythics().contains(string2)) {
                return true;
            }
        } else {
            if (dropGlobalSettings.getEntities() != null && dropGlobalSettings.getEntities().contains("ALL")) {
                return true;
            }
            if (livingEntity instanceof Animals && dropGlobalSettings.getEntities().contains("PASSIVE")) {
                return true;
            }
            if (livingEntity instanceof Monster && dropGlobalSettings.getEntities().contains("HOSTILE")) {
                return true;
            }
            if (dropGlobalSettings.getEntities().contains(livingEntity.getType().name())) {
                return true;
            }
        }
        return false;
    }

    private boolean checkEntity(DropItemSettings dropItemSettings, LivingEntity livingEntity, String string) {
        if (livingEntity.hasMetadata("NO_DROP:" + string)) {
            return false;
        }
        if (Hook.MYTHIC_MOBS.isEnabled() && this.plugin.getHM().getMythicHook().isMythicMob((Entity)livingEntity)) {
            String string2 = this.plugin.getHM().getMythicHook().getMythicNameByEntity((Entity)livingEntity);
            if (dropItemSettings.getMythics().contains("ALL")) {
                return true;
            }
            if (dropItemSettings.getMythics().contains(string2)) {
                return true;
            }
        } else {
            if (dropItemSettings.getEntities() != null && dropItemSettings.getEntities().contains("ALL")) {
                return true;
            }
            if (livingEntity instanceof Animals && dropItemSettings.getEntities().contains("PASSIVE")) {
                return true;
            }
            if (livingEntity instanceof Monster && dropItemSettings.getEntities().contains("HOSTILE")) {
                return true;
            }
            if (dropItemSettings.getEntities().contains(livingEntity.getType().name())) {
                return true;
            }
        }
        return false;
    }

    /*
     * Exception decompiling
     */
    public ItemStack getDropItem(Player var1_1, String var2_2, String var3_3, int var4_4) {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.CannotPerformDecode: reachable test BLOCK was exited and re-entered.
        // org.benf.cfr.reader.bytecode.analysis.opgraph.op3rewriters.Misc.getFarthestReachableInRange(Misc.java:143)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.op3rewriters.SwitchReplacer.examineSwitchContiguity(SwitchReplacer.java:385)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.op3rewriters.SwitchReplacer.replaceRawSwitches(SwitchReplacer.java:65)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:416)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:196)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:141)
        // org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:95)
        // org.benf.cfr.reader.entities.Method.analyse(Method.java:379)
        // org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:867)
        // org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:768)
        // org.benf.cfr.reader.Main.doJar(Main.java:140)
        // org.benf.cfr.reader.Main.main(Main.java:241)
        throw new IllegalStateException("Decompilation failed");
    }

    private boolean checkPenalty(Player player, LivingEntity livingEntity, DropGlobalSettings dropGlobalSettings) {
        if (Hook.MYTHIC_MOBS.isEnabled() && this.plugin.getHM().getMythicHook().isMythicMob((Entity)livingEntity) && dropGlobalSettings.isLevelPenalty()) {
            int n = dropGlobalSettings.getPenaltyVariance();
            int n2 = this.plugin.getHM().getMythicHook().getLevel((Entity)livingEntity);
            int n3 = this.plugin.getHM().getLevelsHook().getPlayerLevel(player);
            if (n3 > n2 && n3 - n2 >= n) {
                return false;
            }
        }
        return true;
    }

    public List<ItemStack> methodRoll(Player player, LivingEntity livingEntity, boolean bl) {
        ArrayList<ItemStack> arrayList = new ArrayList<ItemStack>();
        for (Module module : this.drops.keySet()) {
            DropGlobalSettings dropGlobalSettings;
            Drops drops;
            double d;
            if (bl && !module.name().equals("Tiers") || !this.checkPenalty(player, livingEntity, dropGlobalSettings = (drops = this.drops.get(module)).getGlobalSettings())) continue;
            double d2 = Utils.getRandDouble(0.0, 100.0);
            double d3 = EntityAPI.getItemStat((LivingEntity)player, ItemStat.LOOT_RATE) / 100.0;
            d2 = this.getMultiplier(player, d2, module, dropGlobalSettings);
            if ((d2 *= 1.0 - d3) == 0.0 || d2 > dropGlobalSettings.getRate() || dropGlobalSettings.getRate() <= 0.0 || !dropGlobalSettings.getWorlds().contains(player.getWorld().getName()) || !this.checkEntity(dropGlobalSettings, livingEntity, module.name()) || !this.checkBiome(dropGlobalSettings, livingEntity) || !this.checkRegion(dropGlobalSettings, (LivingEntity)player)) continue;
            double d4 = 0.0;
            Object object = drops.getItemRates().values().iterator();
            while (object.hasNext()) {
                d4 = d = object.next().doubleValue();
            }
            d = Utils.getRandDouble(0.0, d4);
            d = Math.min(d * (1.0 - d3), d4);
            object = new ArrayList();
            ArrayList<String> arrayList2 = new ArrayList<String>();
            double d5 = 0.0;
            for (String string : drops.getItemRates().keySet()) {
                if (d <= 0.0 || d > drops.getItemRates().get(string)) continue;
                d5 = drops.getItemRates().get(string);
                break;
            }
            for (String string : drops.getItemRates().keySet()) {
                if (drops.getItemRates().get(string) != d5) continue;
                object.add(string);
            }
            if (object.isEmpty()) continue;
            if (dropGlobalSettings.isRollOnce()) {
                String string;
                string = (String)object.get(new Random().nextInt(object.size()));
                arrayList2.add(string);
            } else {
                arrayList2.addAll((Collection<String>)object);
            }
            for (String string : arrayList2) {
                String string2;
                DropItemSettings dropItemSettings = drops.getItemSettings(string);
                if (dropItemSettings == null || !this.checkEntity(dropItemSettings, livingEntity, module.name())) continue;
                String string3 = player.getWorld().getName();
                if (!dropItemSettings.getWorlds().containsKey(string3) || !this.checkRegion(dropItemSettings, (LivingEntity)player)) continue;
                int n = 0;
                if (Hook.WORLD_GUARD.isEnabled() && dropItemSettings.getRegions().containsKey(this.plugin.getHM().getWorldGuard().getRegion((LivingEntity)player))) {
                    string2 = this.plugin.getHM().getWorldGuard().getRegion((LivingEntity)player);
                    String string4 = dropItemSettings.getRegions().get(string2);
                    n = this.getItemLevel(player, livingEntity, string4);
                } else {
                    n = this.getItemLevel(player, livingEntity, dropItemSettings.getWorlds().get(string3));
                }
                string2 = this.getDropItem(player, string, module.name(), n);
                if (string2 == null || string2.getType() == Material.AIR) continue;
                arrayList.add((ItemStack)string2);
            }
        }
        return arrayList;
    }

    private int getItemLevel(Player player, LivingEntity livingEntity, String string) {
        double d = 0.0;
        double d2 = 0.0;
        int n = this.plugin.getHM().getLevelsHook().getPlayerLevel(player);
        int n2 = 0;
        if (Hook.MYTHIC_MOBS.isEnabled() && this.plugin.getHM().getMythicHook().isMythicMob((Entity)livingEntity)) {
            n2 = this.plugin.getHM().getMythicHook().getLevel((Entity)livingEntity);
        }
        if (!(string = string.replace("%mob.lvl%", String.valueOf(n2)).replace("%player.lvl%", String.valueOf(n))).contains("<") && !string.contains(">")) {
            String[] arrstring = string.split("-");
            d = Integer.parseInt(arrstring[0]);
            d2 = arrstring.length == 2 ? (double)Integer.parseInt(arrstring[1]) : d;
            return Utils.randInt((int)d, (int)d2);
        }
        if (string.startsWith("<") && string.endsWith(">")) {
            int n3 = string.indexOf("<");
            int n4 = string.indexOf(">");
            String string2 = string.substring(n3 + 1, n4);
            d = ItemUtils.calc(string2);
            n3 = string.lastIndexOf("<");
            n4 = string.lastIndexOf(">");
            String string3 = string.substring(n3 + 1, n4);
            d2 = ItemUtils.calc(string3);
        } else if (string.startsWith("<") && !string.endsWith(">")) {
            int n5 = string.indexOf("<");
            int n6 = string.indexOf(">");
            String string4 = string.substring(n5 + 1, n6);
            d = ItemUtils.calc(string4);
            String string5 = string.substring(n6 + 2);
            d2 = Integer.parseInt(string5);
        } else if (!string.startsWith("<") && string.endsWith(">")) {
            int n7 = string.lastIndexOf("<");
            int n8 = string.lastIndexOf(">");
            String string6 = string.substring(n7 + 1, n8);
            d2 = ItemUtils.calc(string6);
            String string7 = string.substring(0, n7 - 1);
            d = Integer.parseInt(string7);
        }
        return Utils.randInt((int)d, (int)d2);
    }

    private double getMultiplier(Player player, double d, Module module, DropGlobalSettings dropGlobalSettings) {
        double d2 = 1.0;
        String string = "ditems.drop." + module.name().toLowerCase().replace(" ", "_") + ".";
        for (String string2 : dropGlobalSettings.getMultipliers().keySet()) {
            if (!player.hasPermission(String.valueOf(string) + string2)) continue;
            double d3 = dropGlobalSettings.getMultipliers().get(string2);
            d2 = d3 > 0.0 ? d3 - 1.0 : d3 + 1.0;
        }
        return d * d2;
    }

    private void equip(LivingEntity livingEntity, ItemStack itemStack) {
        if (itemStack.getType().name().endsWith("_HELMET")) {
            livingEntity.getEquipment().setHelmet(itemStack);
            livingEntity.getEquipment().setHelmetDropChance(1.0f);
        } else if (itemStack.getType().name().endsWith("_CHESTPLATE")) {
            livingEntity.getEquipment().setChestplate(itemStack);
            livingEntity.getEquipment().setChestplateDropChance(1.0f);
        } else if (itemStack.getType().name().endsWith("_LEGGINGS")) {
            livingEntity.getEquipment().setLeggings(itemStack);
            livingEntity.getEquipment().setLeggingsDropChance(1.0f);
        } else if (itemStack.getType().name().endsWith("_BOOTS")) {
            livingEntity.getEquipment().setBoots(itemStack);
            livingEntity.getEquipment().setBootsDropChance(1.0f);
        } else if (itemStack.getType().name().endsWith("SHIELD")) {
            livingEntity.getEquipment().setItemInOffHand(itemStack);
            livingEntity.getEquipment().setItemInOffHandDropChance(1.0f);
        } else {
            livingEntity.getEquipment().setItemInMainHand(itemStack);
            livingEntity.getEquipment().setItemInMainHandDropChance(1.0f);
        }
    }

    @EventHandler(priority=EventPriority.HIGH)
    public void onDrop(EntityDeathEvent entityDeathEvent) {
        Player player = entityDeathEvent.getEntity().getKiller();
        if (player == null) {
            return;
        }
        LivingEntity livingEntity = entityDeathEvent.getEntity();
        if (Hook.MYTHIC_MOBS.isEnabled() && this.plugin.getHM().getMythicHook().isMythicMob((Entity)livingEntity)) {
            return;
        }
        if (entityDeathEvent.getDrops() == null) {
            return;
        }
        entityDeathEvent.getDrops().addAll(this.methodRoll(player, livingEntity, false));
    }

    @EventHandler(priority=EventPriority.HIGHEST, ignoreCancelled=true)
    public void onSpawn(final CreatureSpawnEvent creatureSpawnEvent) {
        if (creatureSpawnEvent.getEntity().getEquipment() == null || creatureSpawnEvent.getEntity() instanceof ArmorStand) {
            return;
        }
        new BukkitRunnable(){

            public void run() {
                LivingEntity livingEntity = creatureSpawnEvent.getEntity();
                if (Hook.MYTHIC_MOBS.isEnabled() && DropManager.this.plugin.getHM().getMythicHook().isMythicMob((Entity)livingEntity)) {
                    return;
                }
                for (Module module : DropManager.this.drops.keySet()) {
                    Object object;
                    Drops drops = (Drops)DropManager.this.drops.get(module);
                    DropGlobalSettings dropGlobalSettings = drops.getGlobalSettings();
                    String string = creatureSpawnEvent.getSpawnReason().name();
                    if (dropGlobalSettings.getReasons().contains(string)) {
                        livingEntity.setMetadata("NO_DROP:" + module.name(), (MetadataValue)new FixedMetadataValue((Plugin)DropManager.this.plugin, (Object)"yeah"));
                        continue;
                    }
                    if (!module.name().equals("Tiers")) continue;
                    Player player = null;
                    ItemStack itemStack = livingEntity.getNearbyEntities(30.0, 5.0, 30.0).iterator();
                    while (itemStack.hasNext()) {
                        object = (Entity)itemStack.next();
                        if (!(object instanceof Player) || Hook.CITIZENS.isEnabled() && DropManager.this.plugin.getHM().getCitizens().isNPC((Entity)object)) continue;
                        player = (Player)object;
                        break;
                    }
                    if (player == null || !DropManager.this.checkPenalty(player, livingEntity, dropGlobalSettings) || (object = DropManager.this.methodRoll(player, livingEntity, true)).isEmpty() || (itemStack = (ItemStack)object.get(0)) == null) continue;
                    String string2 = DropManager.this.plugin.getMM().getTierManager().getTierId(itemStack);
                    TierManager.Tier tier = DropManager.this.plugin.getMM().getTierManager().getTierById(string2);
                    if (!tier.isEquipOnEntity()) continue;
                    DropManager.this.equip(livingEntity, itemStack);
                    livingEntity.setMetadata("NO_DROP:" + module.name(), (MetadataValue)new FixedMetadataValue((Plugin)DropManager.this.plugin, (Object)"yeah"));
                }
            }
        }.runTaskLater((Plugin)this.plugin, 1L);
    }

    public class DropGlobalSettings {
        private double rate;
        private boolean once;
        private LinkedHashMap<String, Double> mult;
        private Set<String> worlds;
        private boolean reg_reverse;
        private Set<String> reg_list;
        private boolean biome_reverse;
        private Set<String> biome_list;
        private Set<String> entity;
        private Set<String> mythic;
        private Set<String> reasons;
        private boolean lvl_penal;
        private int lvl_penalVar;

        public DropGlobalSettings(double d, boolean bl, LinkedHashMap<String, Double> linkedHashMap, Set<String> set, boolean bl2, Set<String> set2, boolean bl3, Set<String> set3, Set<String> set4, Set<String> set5, Set<String> set6, boolean bl4, int n) {
            this.setRate(d);
            this.setRollOnce(bl);
            this.setMultipliers(linkedHashMap);
            this.setWorlds(set);
            this.setRegionReversed(bl2);
            this.setRegions(set2);
            this.setBiomesReversed(bl3);
            this.setBiomes(set3);
            this.setEntities(set4);
            this.setMythics(set5);
            this.setReasons(set6);
            this.setLevelPenalty(bl4);
            this.setPenaltyVariance(n);
        }

        public double getRate() {
            return this.rate;
        }

        public void setRate(double d) {
            this.rate = d;
        }

        public boolean isRollOnce() {
            return this.once;
        }

        public void setRollOnce(boolean bl) {
            this.once = bl;
        }

        public LinkedHashMap<String, Double> getMultipliers() {
            return this.mult;
        }

        public void setMultipliers(LinkedHashMap<String, Double> linkedHashMap) {
            this.mult = linkedHashMap;
        }

        public Set<String> getWorlds() {
            return this.worlds;
        }

        public void setWorlds(Set<String> set) {
            this.worlds = set;
        }

        public boolean isRegionReversed() {
            return this.reg_reverse;
        }

        public void setRegionReversed(boolean bl) {
            this.reg_reverse = bl;
        }

        public Set<String> getRegions() {
            return this.reg_list;
        }

        public void setRegions(Set<String> set) {
            this.reg_list = set;
        }

        public boolean isBiomesReversed() {
            return this.biome_reverse;
        }

        public void setBiomesReversed(boolean bl) {
            this.biome_reverse = bl;
        }

        public Set<String> getBiomes() {
            return this.biome_list;
        }

        public void setBiomes(Set<String> set) {
            this.biome_list = set;
        }

        public Set<String> getEntities() {
            return this.entity;
        }

        public void setEntities(Set<String> set) {
            this.entity = set;
        }

        public Set<String> getMythics() {
            return this.mythic;
        }

        public void setMythics(Set<String> set) {
            this.mythic = set;
        }

        public Set<String> getReasons() {
            return this.reasons;
        }

        public void setReasons(Set<String> set) {
            this.reasons = set;
        }

        public boolean isLevelPenalty() {
            return this.lvl_penal;
        }

        public void setLevelPenalty(boolean bl) {
            this.lvl_penal = bl;
        }

        public int getPenaltyVariance() {
            return this.lvl_penalVar;
        }

        public void setPenaltyVariance(int n) {
            this.lvl_penalVar = n;
        }
    }

    public class DropItemSettings {
        private double rate;
        private HashMap<String, String> worlds;
        private boolean reg_reverse;
        private HashMap<String, String> reg_list;
        private Set<String> entity;
        private Set<String> mythic;
        private Set<String> reasons;

        public DropItemSettings(double d, HashMap<String, String> hashMap, boolean bl, HashMap<String, String> hashMap2, Set<String> set, Set<String> set2, Set<String> set3) {
            this.setRate(d);
            this.setWorlds(hashMap);
            this.setRegionReversed(bl);
            this.setRegions(hashMap2);
            this.setEntities(set);
            this.setMythics(set2);
            this.setReasons(set3);
        }

        public double getRate() {
            return this.rate;
        }

        public void setRate(double d) {
            this.rate = d;
        }

        public HashMap<String, String> getWorlds() {
            return this.worlds;
        }

        public void setWorlds(HashMap<String, String> hashMap) {
            this.worlds = hashMap;
        }

        public boolean isRegionReversed() {
            return this.reg_reverse;
        }

        public void setRegionReversed(boolean bl) {
            this.reg_reverse = bl;
        }

        public HashMap<String, String> getRegions() {
            return this.reg_list;
        }

        public void setRegions(HashMap<String, String> hashMap) {
            this.reg_list = hashMap;
        }

        public Set<String> getEntities() {
            return this.entity;
        }

        public void setEntities(Set<String> set) {
            this.entity = set;
        }

        public Set<String> getMythics() {
            return this.mythic;
        }

        public void setMythics(Set<String> set) {
            this.mythic = set;
        }

        public Set<String> getReasons() {
            return this.reasons;
        }

        public void setReasons(Set<String> set) {
            this.reasons = set;
        }
    }

    public class Drops {
        private DropGlobalSettings global;
        private HashMap<String, DropItemSettings> items;
        private HashMap<String, Double> rates;

        public Drops(DropGlobalSettings dropGlobalSettings, HashMap<String, DropItemSettings> hashMap, HashMap<String, Double> hashMap2) {
            this.global = dropGlobalSettings;
            this.items = hashMap;
            this.rates = hashMap2;
        }

        public DropGlobalSettings getGlobalSettings() {
            return this.global;
        }

        public HashMap<String, DropItemSettings> getItemsMap() {
            return this.items;
        }

        public DropItemSettings getItemSettings(String string) {
            return this.items.get(string.toLowerCase());
        }

        public HashMap<String, Double> getItemRates() {
            return this.rates;
        }
    }

}

