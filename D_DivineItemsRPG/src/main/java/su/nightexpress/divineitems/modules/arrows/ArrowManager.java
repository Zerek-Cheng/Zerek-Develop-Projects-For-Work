/*
 * Decompiled with CFR 0_133.
 * 
 * Could not load the following classes:
 *  org.bukkit.ChatColor
 *  org.bukkit.Location
 *  org.bukkit.Material
 *  org.bukkit.Server
 *  org.bukkit.configuration.ConfigurationSection
 *  org.bukkit.configuration.file.FileConfiguration
 *  org.bukkit.entity.Entity
 *  org.bukkit.entity.LivingEntity
 *  org.bukkit.entity.Player
 *  org.bukkit.entity.Projectile
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.EventPriority
 *  org.bukkit.event.entity.EntityShootBowEvent
 *  org.bukkit.event.entity.ProjectileHitEvent
 *  org.bukkit.inventory.ItemFlag
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.inventory.PlayerInventory
 *  org.bukkit.inventory.meta.ItemMeta
 *  org.bukkit.metadata.FixedMetadataValue
 *  org.bukkit.metadata.MetadataValue
 *  org.bukkit.plugin.Plugin
 *  org.bukkit.plugin.java.JavaPlugin
 *  org.bukkit.projectiles.ProjectileSource
 *  org.bukkit.scheduler.BukkitScheduler
 *  org.bukkit.util.Vector
 */
package su.nightexpress.divineitems.modules.arrows;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.Set;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.projectiles.ProjectileSource;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.util.Vector;
import su.nightexpress.divineitems.DivineItems;
import su.nightexpress.divineitems.DivineListener;
import su.nightexpress.divineitems.Module;
import su.nightexpress.divineitems.api.DivineItemsAPI;
import su.nightexpress.divineitems.api.ItemAPI;
import su.nightexpress.divineitems.attributes.ItemStat;
import su.nightexpress.divineitems.cmds.CommandBase;
import su.nightexpress.divineitems.cmds.MainCommand;
import su.nightexpress.divineitems.cmds.list.ArrowsCommand;
import su.nightexpress.divineitems.config.Config;
import su.nightexpress.divineitems.config.ConfigManager;
import su.nightexpress.divineitems.config.MyConfig;
import su.nightexpress.divineitems.nbt.NBTItem;
import su.nightexpress.divineitems.types.AmmoType;
import su.nightexpress.divineitems.utils.ErrorLog;

public class ArrowManager
extends DivineListener<DivineItems>
implements Module {
    private DivineItems plugin;
    private boolean e;
    private Random r;
    private MyConfig arrowsCfg;
    private HashMap<String, DivineArrow> ars;
    private List<Projectile> pjs;
    private int taskId;
    private final String n = this.name().toLowerCase().replace(" ", "_");
    private final String label = this.n.replace("_", "");
    private final String NBT_KEY_ARROW = "DIVINE_ARROW_ID";
    private final String NBT_KEY_LAUNCH = "DIVINE_ARROW_LAUNCHER";

    public ArrowManager(DivineItems divineItems) {
        super(divineItems);
        this.plugin = divineItems;
        this.e = this.plugin.getCM().getCFG().isModuleEnabled(this.name());
        this.r = new Random();
        this.pjs = new ArrayList<Projectile>();
    }

    @Override
    public void loadConfig() {
        this.ars = new HashMap();
        this.arrowsCfg = new MyConfig(this.plugin, "/modules/" + this.n, "arrows.yml");
        this.setup();
    }

    @Override
    public boolean isActive() {
        return this.e;
    }

    @Override
    public boolean isDropable() {
        return true;
    }

    @Override
    public boolean isResolvable() {
        return true;
    }

    @Override
    public String name() {
        return "Arrows";
    }

    @Override
    public String version() {
        return "1.0";
    }

    @Override
    public void enable() {
        if (this.isActive()) {
            this.plugin.getCommander().registerCmd(this.label, new ArrowsCommand(this.plugin));
            this.loadConfig();
            this.registerListeners();
            this.startTask();
        }
    }

    @Override
    public void unload() {
        if (this.isActive()) {
            this.plugin.getCommander().unregisterCmd(this.label);
            this.ars.clear();
            this.e = false;
            this.unregisterListeners();
            this.stopTask();
        }
    }

    @Override
    public void reload() {
        this.unload();
        this.enable();
    }

    private void setup() {
        FileConfiguration fileConfiguration = this.arrowsCfg.getConfig();
        for (String string : fileConfiguration.getConfigurationSection("Arrows").getKeys(false)) {
            Object object;
            Object object22;
            Object object32;
            String string2 = "Arrows." + string + ".";
            String string3 = ChatColor.translateAlternateColorCodes((char)'&', (String)fileConfiguration.getString(String.valueOf(string2) + "Name"));
            ArrayList<String> arrayList = new ArrayList<String>();
            for (Object object22 : fileConfiguration.getStringList(String.valueOf(string2) + "Lore")) {
                arrayList.add(ChatColor.translateAlternateColorCodes((char)'&', (String)object22));
            }
            object22 = ArrowType.valueOf(fileConfiguration.getString(String.valueOf(string2) + "Type").toUpperCase());
            HashMap hashMap = new HashMap();
            for (Object object32 : fileConfiguration.getConfigurationSection(String.valueOf(string2) + "Attributes").getKeys(false)) {
                object = String.valueOf(string2) + "Attributes" + "." + (String)object32 + ".";
                ItemStat itemStat = null;
                try {
                    itemStat = ItemStat.valueOf(object32.toUpperCase());
                }
                catch (IllegalArgumentException illegalArgumentException) {
                    ErrorLog.sendError(this, (String)object, "Invalid Attribute!", false);
                    continue;
                }
                double d = fileConfiguration.getDouble(String.valueOf(object) + "Value");
                AttributeAction attributeAction = AttributeAction.valueOf(fileConfiguration.getString(String.valueOf(object) + "Action").toUpperCase());
                ArrowAttribute arrowAttribute = new ArrowAttribute(itemStat, d, attributeAction);
                hashMap.put(itemStat, arrowAttribute);
            }
            object32 = fileConfiguration.getStringList(String.valueOf(string2) + "OnFlyActions");
            List list = fileConfiguration.getStringList(String.valueOf(string2) + "OnHitActions");
            object = new DivineArrow(string, string3, arrayList, (ArrowType)((Object)object22), hashMap, (List<String>)object32, list);
            this.ars.put(string.toLowerCase(), (DivineArrow)object);
        }
    }

    private void startTask() {
        this.taskId = this.plugin.getServer().getScheduler().scheduleSyncRepeatingTask((Plugin)this.plugin, new Runnable(){

            @Override
            public void run() {
                ArrowManager.this.runFlyActions();
            }
        }, 10L, 1L);
    }

    private void stopTask() {
        this.plugin.getServer().getScheduler().cancelTask(this.taskId);
    }

    public boolean isDivineArrow(ItemStack itemStack) {
        if (itemStack == null || itemStack.getType() == Material.AIR) {
            return false;
        }
        return new NBTItem(itemStack).hasKey("DIVINE_ARROW_ID");
    }

    public String getArrowId(ItemStack itemStack) {
        NBTItem nBTItem = new NBTItem(itemStack);
        String string = nBTItem.getString("DIVINE_ARROW_ID");
        return string;
    }

    public List<String> getArrowNames() {
        ArrayList<String> arrayList = new ArrayList<String>();
        for (DivineArrow divineArrow : this.getArrows()) {
            arrayList.add(divineArrow.getId());
        }
        return arrayList;
    }

    public DivineArrow getArrowById(String string) {
        if (string.equalsIgnoreCase("random")) {
            return new ArrayList<DivineArrow>(this.getArrows()).get(this.r.nextInt(this.getArrows().size()));
        }
        return this.ars.get(string.toLowerCase());
    }

    public Collection<DivineArrow> getArrows() {
        return this.ars.values();
    }

    public ItemStack getFirstArrow(Player player, AmmoType ammoType) {
        ItemStack itemStack = player.getInventory().getItemInOffHand();
        if (itemStack != null && this.isDivineArrow(itemStack)) {
            return itemStack;
        }
        int n = player.getInventory().first(Material.ARROW);
        if (n >= 0) {
            return player.getInventory().getItem(n);
        }
        return null;
    }

    private void markArrow(String string, Projectile projectile, LivingEntity livingEntity) {
        projectile.setMetadata("DIVINE_ARROW_ID", (MetadataValue)new FixedMetadataValue((Plugin)this.plugin, (Object)string));
        projectile.setMetadata("DIVINE_ARROW_LAUNCHER", (MetadataValue)new FixedMetadataValue((Plugin)this.plugin, (Object)livingEntity));
    }

    public void runFlyActions() {
        ArrayList<Projectile> arrayList = new ArrayList<Projectile>(this.pjs);
        for (Projectile projectile : arrayList) {
            if (projectile.isOnGround() || !projectile.isValid()) {
                this.pjs.remove((Object)projectile);
                continue;
            }
            String string = ((MetadataValue)projectile.getMetadata("DIVINE_ARROW_ID").get(0)).asString();
            DivineArrow divineArrow = this.getArrowById(string);
            if (divineArrow == null || divineArrow.getFlyActions() == null) {
                return;
            }
            DivineItemsAPI.executeActions((Entity)projectile, divineArrow.getFlyActions(), null);
        }
    }

    @EventHandler(priority=EventPriority.HIGHEST, ignoreCancelled=true)
    public void onProj(EntityShootBowEvent entityShootBowEvent) {
        if (!(entityShootBowEvent.getProjectile() instanceof Projectile)) {
            return;
        }
        Projectile projectile = (Projectile)entityShootBowEvent.getProjectile();
        if (!(entityShootBowEvent.getEntity() instanceof Player)) {
            return;
        }
        Player player = (Player)entityShootBowEvent.getEntity();
        ItemStack itemStack = this.getFirstArrow(player, ItemAPI.getAmmoType(entityShootBowEvent.getBow()));
        if (itemStack == null || !this.isDivineArrow(itemStack)) {
            return;
        }
        String string = this.getArrowId(itemStack);
        this.markArrow(string, projectile, (LivingEntity)player);
        this.pjs.add(projectile);
    }

    @EventHandler(priority=EventPriority.HIGHEST, ignoreCancelled=true)
    public void onHit(ProjectileHitEvent projectileHitEvent) {
        Object object;
        Projectile projectile = projectileHitEvent.getEntity();
        if (projectile.getShooter() == null || !(projectile.getShooter() instanceof Player)) {
            return;
        }
        if (!projectile.hasMetadata("DIVINE_ARROW_ID")) {
            return;
        }
        String string = ((MetadataValue)projectile.getMetadata("DIVINE_ARROW_ID").get(0)).asString();
        Entity entity = projectileHitEvent.getHitEntity();
        if (entity != null) {
            object = entity.getLocation();
            Location location = projectile.getLocation();
            Vector vector = object.toVector();
            Vector vector2 = location.toVector();
            Vector vector3 = vector2.subtract(vector);
            vector3.normalize();
            vector3.multiply(2);
            object.setDirection(vector3);
            projectile.teleport((Location)object);
            projectile.setVelocity(vector3);
            projectile.setMetadata("DI_TARGET", (MetadataValue)new FixedMetadataValue((Plugin)this.plugin, (Object)entity));
        }
        object = this.getArrowById(string);
        DivineItemsAPI.executeActions((Entity)projectile, object.getHitActions(), null);
    }

    public class ArrowAttribute {
        private ItemStat att;
        private double val;
        private AttributeAction act;

        public ArrowAttribute(ItemStat itemStat, double d, AttributeAction attributeAction) {
            this.setAttribute(itemStat);
            this.setValue(d);
            this.setAction(attributeAction);
        }

        public ItemStat getAttribute() {
            return this.att;
        }

        public void setAttribute(ItemStat itemStat) {
            this.att = itemStat;
        }

        public double getValue() {
            return this.val;
        }

        public void setValue(double d) {
            this.val = d;
        }

        public AttributeAction getAction() {
            return this.act;
        }

        public void setAction(AttributeAction attributeAction) {
            this.act = attributeAction;
        }
    }

    public static enum ArrowType {
        NORMAL,
        TIPPED,
        SPECTRAL;
        

        private ArrowType(String string2, int n2) {
        }
    }

    public static enum AttributeAction {
        PLUS,
        MINUS;
        

        private AttributeAction(String string2, int n2) {
        }
    }

    public class DivineArrow {
        private String id;
        private String name;
        private List<String> lore;
        private ArrowType type;
        private HashMap<ItemStat, ArrowAttribute> att;
        private List<String> fly;
        private List<String> hit;
        private static /* synthetic */ int[] $SWITCH_TABLE$su$nightexpress$divineitems$modules$arrows$ArrowManager$ArrowType;

        public DivineArrow(String string, String string2, List<String> list, ArrowType arrowType, HashMap<ItemStat, ArrowAttribute> hashMap, List<String> list2, List<String> list3) {
            this.setId(string);
            this.setName(string2);
            this.setLore(list);
            this.setType(arrowType);
            this.setAttributes(hashMap);
            this.setFlyActions(list2);
            this.setHitActions(list3);
        }

        public String getId() {
            return this.id;
        }

        public void setId(String string) {
            this.id = string;
        }

        public String getName() {
            return this.name;
        }

        public void setName(String string) {
            this.name = string;
        }

        public List<String> getLore() {
            return this.lore;
        }

        public void setLore(List<String> list) {
            this.lore = list;
        }

        public ArrowType getType() {
            return this.type;
        }

        public void setType(ArrowType arrowType) {
            this.type = arrowType;
        }

        public HashMap<ItemStat, ArrowAttribute> getAttributes() {
            return this.att;
        }

        public void setAttributes(HashMap<ItemStat, ArrowAttribute> hashMap) {
            this.att = hashMap;
        }

        public List<String> getFlyActions() {
            return this.fly;
        }

        public void setFlyActions(List<String> list) {
            this.fly = list;
        }

        public List<String> getHitActions() {
            return this.hit;
        }

        public void setHitActions(List<String> list) {
            this.hit = list;
        }

        public ItemStack create() {
            ItemStack itemStack = new ItemStack(Material.ARROW);
            switch (DivineArrow.$SWITCH_TABLE$su$nightexpress$divineitems$modules$arrows$ArrowManager$ArrowType()[this.type.ordinal()]) {
                case 1: {
                    break;
                }
                case 3: {
                    itemStack.setType(Material.SPECTRAL_ARROW);
                    break;
                }
                case 2: {
                    itemStack.setType(Material.TIPPED_ARROW);
                }
            }
            ItemMeta itemMeta = itemStack.getItemMeta();
            itemMeta.setDisplayName(this.name);
            itemMeta.setLore(this.lore);
            itemMeta.addItemFlags(ItemFlag.values());
            itemStack.setItemMeta(itemMeta);
            NBTItem nBTItem = new NBTItem(itemStack);
            nBTItem.setString("DIVINE_ARROW_ID", this.id);
            itemStack = nBTItem.getItem();
            return itemStack;
        }

        static /* synthetic */ int[] $SWITCH_TABLE$su$nightexpress$divineitems$modules$arrows$ArrowManager$ArrowType() {
            int[] arrn;
            int[] arrn2 = $SWITCH_TABLE$su$nightexpress$divineitems$modules$arrows$ArrowManager$ArrowType;
            if (arrn2 != null) {
                return arrn2;
            }
            arrn = new int[ArrowType.values().length];
            try {
                arrn[ArrowType.NORMAL.ordinal()] = 1;
            }
            catch (NoSuchFieldError noSuchFieldError) {}
            try {
                arrn[ArrowType.SPECTRAL.ordinal()] = 3;
            }
            catch (NoSuchFieldError noSuchFieldError) {}
            try {
                arrn[ArrowType.TIPPED.ordinal()] = 2;
            }
            catch (NoSuchFieldError noSuchFieldError) {}
            $SWITCH_TABLE$su$nightexpress$divineitems$modules$arrows$ArrowManager$ArrowType = arrn;
            return $SWITCH_TABLE$su$nightexpress$divineitems$modules$arrows$ArrowManager$ArrowType;
        }
    }

}

