/*
 * Decompiled with CFR 0_133.
 * 
 * Could not load the following classes:
 *  net.md_5.bungee.api.ChatColor
 *  org.bukkit.Location
 *  org.bukkit.Sound
 *  org.bukkit.configuration.ConfigurationSection
 *  org.bukkit.configuration.file.FileConfiguration
 *  org.bukkit.entity.Entity
 *  org.bukkit.entity.LivingEntity
 *  org.bukkit.entity.Player
 *  org.bukkit.entity.Projectile
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.EventPriority
 *  org.bukkit.event.entity.EntityDamageByEntityEvent
 *  org.bukkit.event.entity.EntityDamageEvent
 *  org.bukkit.metadata.FixedMetadataValue
 *  org.bukkit.metadata.MetadataValue
 *  org.bukkit.plugin.Plugin
 *  org.bukkit.plugin.java.JavaPlugin
 *  org.bukkit.projectiles.ProjectileSource
 */
package su.nightexpress.divineitems.modules.combatlog;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.projectiles.ProjectileSource;
import su.nightexpress.divineitems.DivineItems;
import su.nightexpress.divineitems.DivineListener;
import su.nightexpress.divineitems.Module;
import su.nightexpress.divineitems.config.Config;
import su.nightexpress.divineitems.config.ConfigManager;
import su.nightexpress.divineitems.config.Lang;
import su.nightexpress.divineitems.config.MyConfig;
import su.nightexpress.divineitems.hooks.Hook;
import su.nightexpress.divineitems.hooks.HookManager;
import su.nightexpress.divineitems.hooks.external.HologramsHook;
import su.nightexpress.divineitems.utils.ActionTitle;
import su.nightexpress.divineitems.utils.ErrorLog;
import su.nightexpress.divineitems.utils.Utils;

public class CombatLogManager
extends DivineListener<DivineItems>
implements Module {
    private DivineItems plugin;
    private MyConfig config;
    private boolean e;
    private boolean combat_out_chat;
    private boolean combat_out_action;
    private boolean combat_msg_default;
    private boolean combat_msg_critical;
    private boolean combat_msg_dodge;
    private boolean combat_msg_block;
    private boolean combat_msg_zero;
    private boolean combat_hd_e;
    private boolean combat_sound_default;
    private boolean combat_sound_critical;
    private boolean combat_sound_dodge;
    private boolean combat_sound_block;
    private Sound combat_sound_default_value;
    private Sound combat_sound_critical_value;
    private Sound combat_sound_dodge_value;
    private Sound combat_sound_block_value;
    private String f_crit;
    private String f_block;
    private String f_dodge;
    private List<String> f_total;
    private HashMap<String, String> f_dt;
    private HashMap<String, String> f_ds;
    private final String META_CRIT = "DI_CRIT";
    private final String META_BLOCK = "DI_BLOCK";
    private final String META_DODGE = "DI_DODGE";
    private final String META_DT = "DDT_";
    private final String META_DS = "DDS_";
    private final String n = this.name().toLowerCase().replace(" ", "_");

    public CombatLogManager(DivineItems divineItems) {
        super(divineItems);
        this.plugin = divineItems;
        this.e = this.plugin.getCM().getCFG().isModuleEnabled(this.name());
    }

    @Override
    public void loadConfig() {
        String string;
        this.config = new MyConfig(this.plugin, "/modules/" + this.n, "settings.yml");
        FileConfiguration fileConfiguration = this.config.getConfig();
        if (!fileConfiguration.contains("Messages.IgnoreZeroDamage")) {
            fileConfiguration.set("Messages.IgnoreZeroDamage", (Object)false);
        }
        if (!fileConfiguration.contains("Indicators.Format")) {
            fileConfiguration.set("Indicators.Format.Critical", (Object)"&8[&c&lCritical!&8]");
            fileConfiguration.set("Indicators.Format.Block", (Object)"&8[&e&lBlocked!&8]");
            fileConfiguration.set("Indicators.Format.Dodge", (Object)"&8[&b&lDodge!&8]");
            fileConfiguration.set("Indicators.Format.Total", Arrays.asList("%crit%", "%block%", "%dodge%", "%dmg%"));
            fileConfiguration.set("Indicators.Format.DamageTypes.Physical", (Object)"&c-%dmg%");
            fileConfiguration.set("Indicators.Format.DamageTypes.Magical", (Object)"&d-%dmg%");
            fileConfiguration.set("Indicators.Format.DamageTypes.Fire", (Object)"&4-%dmg%");
            fileConfiguration.set("Indicators.Format.DamageTypes.Poison", (Object)"&a-%dmg%");
            fileConfiguration.set("Indicators.Format.DamageTypes.Water", (Object)"&9-%dmg%");
            fileConfiguration.set("Indicators.Format.DamageTypes.Wind", (Object)"&7-%dmg%");
            fileConfiguration.set("Indicators.Format.DamageSources.default", (Object)"&c-%dmg%");
            fileConfiguration.set("Indicators.Format.DamageSources.POISON", (Object)"&a-%dmg%");
        }
        this.combat_out_chat = fileConfiguration.getBoolean("Messages.Chat");
        this.combat_out_action = fileConfiguration.getBoolean("Messages.ActionBar");
        this.combat_msg_default = fileConfiguration.getBoolean("Messages.Default");
        this.combat_msg_critical = fileConfiguration.getBoolean("Messages.Critical");
        this.combat_msg_dodge = fileConfiguration.getBoolean("Messages.Dodge");
        this.combat_msg_block = fileConfiguration.getBoolean("Messages.Block");
        this.combat_msg_zero = fileConfiguration.getBoolean("Messages.IgnoreZeroDamage");
        this.combat_hd_e = fileConfiguration.getBoolean("Indicators.Enabled");
        this.combat_sound_default = fileConfiguration.getBoolean("Sounds.Default.Enabled");
        this.combat_sound_critical = fileConfiguration.getBoolean("Sounds.Critical.Enabled");
        this.combat_sound_dodge = fileConfiguration.getBoolean("Sounds.Dodge.Enabled");
        this.combat_sound_block = fileConfiguration.getBoolean("Sounds.Block.Enabled");
        this.combat_sound_default_value = Sound.ENTITY_ZOMBIE_ATTACK_IRON_DOOR;
        try {
            this.combat_sound_default_value = Sound.valueOf((String)fileConfiguration.getString("Sounds.Default.Value"));
        }
        catch (IllegalArgumentException illegalArgumentException) {
            ErrorLog.sendError(this, "Sounds.Default.Value", "Invalid Sound value!", true);
            fileConfiguration.set("Sounds.Default.Value", (Object)"ENTITY_ZOMBIE_ATTACK_IRON_DOOR");
        }
        this.combat_sound_critical_value = Sound.ENTITY_GENERIC_EXPLODE;
        try {
            this.combat_sound_critical_value = Sound.valueOf((String)fileConfiguration.getString("Sounds.Critical.Value"));
        }
        catch (IllegalArgumentException illegalArgumentException) {
            ErrorLog.sendError(this, "Sounds.Critical.Value", "Invalid Sound value!", true);
            fileConfiguration.set("Sounds.Critical.Value", (Object)"ENTITY_GENERIC_EXPLODE");
        }
        this.combat_sound_dodge_value = Sound.ENTITY_COW_MILK;
        try {
            this.combat_sound_dodge_value = Sound.valueOf((String)fileConfiguration.getString("Sounds.Dodge.Value"));
        }
        catch (IllegalArgumentException illegalArgumentException) {
            ErrorLog.sendError(this, "Sounds.Dodge.Value", "Invalid Sound value!", true);
            fileConfiguration.set("Sounds.Block.Value", (Object)"ENTITY_COW_MILK");
        }
        this.combat_sound_block_value = Sound.BLOCK_ANVIL_PLACE;
        try {
            this.combat_sound_block_value = Sound.valueOf((String)fileConfiguration.getString("Sounds.Block.Value"));
        }
        catch (IllegalArgumentException illegalArgumentException) {
            ErrorLog.sendError(this, "Sounds.Block.Value", "Invalid Sound value!", true);
            fileConfiguration.set("Sounds.Block.Value", (Object)"BLOCK_ANVIL_PLACE");
        }
        this.f_crit = ChatColor.translateAlternateColorCodes((char)'&', (String)fileConfiguration.getString("Indicators.Format.Critical"));
        this.f_block = ChatColor.translateAlternateColorCodes((char)'&', (String)fileConfiguration.getString("Indicators.Format.Block"));
        this.f_dodge = ChatColor.translateAlternateColorCodes((char)'&', (String)fileConfiguration.getString("Indicators.Format.Dodge"));
        this.f_total = fileConfiguration.getStringList("Indicators.Format.Total");
        this.f_dt = new HashMap();
        if (fileConfiguration.contains("Indicators.Format.DamageTypes")) {
            for (String string2 : fileConfiguration.getConfigurationSection("Indicators.Format.DamageTypes").getKeys(false)) {
                string = ChatColor.translateAlternateColorCodes((char)'&', (String)fileConfiguration.getString("Indicators.Format.DamageTypes." + string2));
                this.f_dt.put(string2.toLowerCase(), string);
            }
        }
        this.f_ds = new HashMap();
        if (fileConfiguration.contains("Indicators.Format.DamageSources")) {
            for (String string2 : fileConfiguration.getConfigurationSection("Indicators.Format.DamageSources").getKeys(false)) {
                string = ChatColor.translateAlternateColorCodes((char)'&', (String)fileConfiguration.getString("Indicators.Format.DamageSources." + string2));
                this.f_ds.put(string2.toUpperCase(), string);
            }
        }
        this.config.save();
    }

    @Override
    public boolean isActive() {
        return this.e;
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
    public String name() {
        return "Combat Log";
    }

    @Override
    public String version() {
        return "1.0";
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
            this.e = false;
            this.unregisterListeners();
        }
    }

    @Override
    public void reload() {
        this.unload();
        this.loadConfig();
        this.enable();
    }

    public void sendDamageMsg(double d, String string, String string2, Entity entity, Entity entity2) {
        Player player;
        if (d <= 0.0 && this.combat_msg_zero) {
            return;
        }
        d = Utils.round3(d);
        if (entity instanceof Player) {
            player = (Player)entity;
            if (this.combat_msg_default) {
                if (this.combat_out_chat) {
                    player.sendMessage(Lang.CombatLog_Get.toMsg().replace("%s", "" + d).replace("%e", string2));
                }
                if (this.combat_out_action) {
                    ActionTitle.sendActionBar(player, Lang.CombatLog_Get.toMsg().replace("%s", "" + d).replace("%e", string2));
                }
            }
            if (this.combat_sound_default) {
                player.playSound(player.getLocation(), this.combat_sound_default_value, 1.0f, 1.0f);
            }
        }
        if (entity2 instanceof Projectile && (player = (Projectile)entity2).getShooter() != null && player.getShooter() instanceof Player) {
            entity2 = (Entity)player.getShooter();
        }
        if (entity2 instanceof Player) {
            player = (Player)entity2;
            if (this.combat_msg_default) {
                if (this.combat_out_chat) {
                    player.sendMessage(Lang.CombatLog_Deal.toMsg().replace("%s", "" + d).replace("%e", string));
                }
                if (this.combat_out_action) {
                    ActionTitle.sendActionBar(player, Lang.CombatLog_Deal.toMsg().replace("%s", "" + d).replace("%e", string));
                }
            }
            if (this.combat_sound_default) {
                player.playSound(player.getLocation(), this.combat_sound_default_value, 1.0f, 1.0f);
            }
        }
    }

    public void sendCritDmgMsg(double d, String string, String string2, Entity entity, Entity entity2) {
        Player player;
        if (d <= 0.0 && this.combat_msg_zero) {
            return;
        }
        d = Utils.round3(d);
        if (entity instanceof Player) {
            player = (Player)entity;
            if (this.combat_msg_critical) {
                if (this.combat_out_chat) {
                    player.sendMessage(Lang.CombatLog_GetCrit.toMsg().replace("%s", "" + d).replace("%e", string2));
                }
                if (this.combat_out_action) {
                    ActionTitle.sendActionBar(player, Lang.CombatLog_GetCrit.toMsg().replace("%s", "" + d).replace("%e", string2));
                }
            }
            if (this.combat_sound_critical) {
                player.playSound(player.getLocation(), this.combat_sound_critical_value, 1.0f, 1.0f);
            }
        }
        if (entity2 instanceof Projectile && (player = (Projectile)entity2).getShooter() != null && player.getShooter() instanceof Player) {
            entity2 = (Entity)player.getShooter();
        }
        if (entity2 instanceof Player) {
            player = (Player)entity2;
            if (this.combat_msg_critical) {
                if (this.combat_out_chat) {
                    player.sendMessage(Lang.CombatLog_DealCrit.toMsg().replace("%s", "" + d).replace("%e", string));
                }
                if (this.combat_out_action) {
                    ActionTitle.sendActionBar(player, Lang.CombatLog_DealCrit.toMsg().replace("%s", "" + d).replace("%e", string));
                }
            }
            if (this.combat_sound_critical) {
                player.playSound(player.getLocation(), this.combat_sound_critical_value, 1.0f, 1.0f);
            }
        }
    }

    public void sendDodgeMsg(String string, String string2, Entity entity, Entity entity2) {
        Player player;
        if (entity instanceof Player) {
            player = (Player)entity;
            if (this.combat_msg_dodge) {
                if (this.combat_out_chat) {
                    player.sendMessage(Lang.CombatLog_Dodge.toMsg().replace("%e", string2));
                }
                if (this.combat_out_action) {
                    ActionTitle.sendActionBar(player, Lang.CombatLog_Dodge.toMsg().replace("%e", string2));
                }
            }
            if (this.combat_sound_dodge) {
                player.playSound(player.getLocation(), this.combat_sound_dodge_value, 1.0f, 1.0f);
            }
        }
        if (entity2 instanceof Projectile && (player = (Projectile)entity2).getShooter() != null && player.getShooter() instanceof Player) {
            entity2 = (Entity)player.getShooter();
        }
        if (entity2 instanceof Player) {
            player = (Player)entity2;
            if (this.combat_msg_dodge) {
                if (this.combat_out_chat) {
                    player.sendMessage(Lang.CombatLog_DodgeEnemy.toMsg().replace("%e", string));
                }
                if (this.combat_out_action) {
                    ActionTitle.sendActionBar(player, Lang.CombatLog_DodgeEnemy.toMsg().replace("%e", string));
                }
            }
            if (this.combat_sound_dodge) {
                player.playSound(player.getLocation(), this.combat_sound_dodge_value, 1.0f, 1.0f);
            }
        }
    }

    public void sendBlockMsg(double d, String string, String string2, Entity entity, Entity entity2) {
        Player player;
        d = Utils.round3(d);
        if (entity instanceof Player) {
            player = (Player)entity;
            if (this.combat_msg_block) {
                if (this.combat_out_chat) {
                    player.sendMessage(Lang.CombatLog_Block.toMsg().replace("%d", "" + Utils.round3(d)).replace("%e", string2));
                }
                if (this.combat_out_action) {
                    ActionTitle.sendActionBar(player, Lang.CombatLog_Block.toMsg().replace("%d", "" + Utils.round3(d)).replace("%e", string2));
                }
            }
            if (this.combat_sound_block) {
                player.playSound(player.getLocation(), this.combat_sound_block_value, 1.0f, 1.0f);
            }
        }
        if (entity2 instanceof Projectile && (player = (Projectile)entity2).getShooter() != null && player.getShooter() instanceof Player) {
            entity2 = (Entity)player.getShooter();
        }
        if (entity2 instanceof Player) {
            player = (Player)entity2;
            if (this.combat_msg_block) {
                if (this.combat_out_chat) {
                    player.sendMessage(Lang.CombatLog_BlockEnemy.toMsg().replace("%d", "" + Utils.round3(d)).replace("%e", string));
                }
                if (this.combat_out_action) {
                    ActionTitle.sendActionBar(player, Lang.CombatLog_BlockEnemy.toMsg().replace("%d", "" + Utils.round3(d)).replace("%e", string));
                }
            }
            if (this.combat_sound_block) {
                player.playSound(player.getLocation(), this.combat_sound_block_value, 1.0f, 1.0f);
            }
        }
    }

    public String getDamageTypeFormat(String string) {
        if (this.f_dt.containsKey(string = string.toLowerCase())) {
            return this.f_dt.get(string);
        }
        return "\u00a7cError # Invalid Type!";
    }

    public String getDamageTypeSource(String string) {
        if (this.f_ds.containsKey(string = string.toUpperCase())) {
            return this.f_ds.get(string);
        }
        return "\u00a7cError # Invalid Type!";
    }

    public void setDTMeta(LivingEntity livingEntity, String string, double d) {
        livingEntity.setMetadata("DDT_" + string, (MetadataValue)new FixedMetadataValue((Plugin)this.plugin, (Object)d));
    }

    public void setDSMeta(LivingEntity livingEntity, String string, double d) {
        if (!this.f_ds.containsKey(string = string.toUpperCase())) {
            string = "DEFAULT";
        }
        livingEntity.setMetadata("DDS_" + string, (MetadataValue)new FixedMetadataValue((Plugin)this.plugin, (Object)d));
    }

    public void setCritMeta(LivingEntity livingEntity) {
        livingEntity.setMetadata("DI_CRIT", (MetadataValue)new FixedMetadataValue((Plugin)this.plugin, (Object)"SU"));
    }

    public void setBlockMeta(LivingEntity livingEntity, double d) {
        livingEntity.setMetadata("DI_BLOCK", (MetadataValue)new FixedMetadataValue((Plugin)this.plugin, (Object)d));
    }

    public void setDodgeMeta(LivingEntity livingEntity) {
        livingEntity.setMetadata("DI_DODGE", (MetadataValue)new FixedMetadataValue((Plugin)this.plugin, (Object)"SU"));
    }

    public void removeAllMeta(LivingEntity livingEntity) {
        livingEntity.removeMetadata("DI_BLOCK", (Plugin)this.plugin);
        livingEntity.removeMetadata("DI_CRIT", (Plugin)this.plugin);
        livingEntity.removeMetadata("DI_DODGE", (Plugin)this.plugin);
        for (String string : this.f_dt.keySet()) {
            livingEntity.removeMetadata("DDT_" + string, (Plugin)this.plugin);
        }
        for (String string : this.f_ds.keySet()) {
            livingEntity.removeMetadata("DDS_" + string, (Plugin)this.plugin);
        }
    }

    @EventHandler(priority=EventPriority.HIGHEST)
    public void onDamageIndicator(EntityDamageEvent entityDamageEvent) {
        String string3;
        Object object;
        EntityDamageByEntityEvent entityDamageByEntityEvent;
        Entity entity = entityDamageEvent.getEntity();
        if (entityDamageEvent.isCancelled() && !entity.hasMetadata("DI_DODGE")) {
            return;
        }
        if (entity.hasMetadata("DI_DODGE") && !entityDamageEvent.isCancelled()) {
            entityDamageEvent.setCancelled(true);
        }
        double d = entityDamageEvent.getFinalDamage();
        double d2 = 0.0;
        if (d <= 0.0 && this.combat_msg_zero) {
            return;
        }
        if (entityDamageEvent instanceof EntityDamageByEntityEvent) {
            entityDamageByEntityEvent = (EntityDamageByEntityEvent)entityDamageEvent;
            object = entityDamageByEntityEvent.getDamager();
            string3 = Utils.getEntityName(entity);
            String string2 = Utils.getEntityName((Entity)object);
            if (entity.hasMetadata("DI_CRIT")) {
                this.sendCritDmgMsg(d, string3, string2, entity, (Entity)object);
            } else if (entity.hasMetadata("DI_DODGE")) {
                this.sendDodgeMsg(string3, string2, entity, (Entity)object);
            } else if (entity.hasMetadata("DI_BLOCK")) {
                d2 = ((MetadataValue)entity.getMetadata("DI_BLOCK").get(0)).asDouble();
                this.sendBlockMsg(d2, string3, string2, entity, (Entity)object);
            } else {
                this.sendDamageMsg(d, string3, string2, entity, (Entity)object);
            }
        }
        if (!(entity instanceof LivingEntity)) {
            return;
        }
        entityDamageByEntityEvent = (LivingEntity)entity;
        if (!Hook.HOLOGRAPHIC_DISPLAYS.isEnabled() || !this.combat_hd_e || this.plugin.getHM().getHoloHook().isHologram(entity)) {
            this.removeAllMeta((LivingEntity)entityDamageByEntityEvent);
            return;
        }
        object = new ArrayList();
        for (String string3 : this.f_total) {
            double d3;
            if (string3.equalsIgnoreCase("%dodge%") && entityDamageByEntityEvent.hasMetadata("DI_DODGE")) {
                object.add(this.f_dodge);
                break;
            }
            if (string3.equalsIgnoreCase("%crit%") && entityDamageByEntityEvent.hasMetadata("DI_CRIT")) {
                object.add(this.f_crit);
                continue;
            }
            if (string3.equalsIgnoreCase("%block%") && entityDamageByEntityEvent.hasMetadata("DI_BLOCK")) {
                d2 = ((MetadataValue)entity.getMetadata("DI_BLOCK").get(0)).asDouble();
                object.add(this.f_block);
                continue;
            }
            if (!string3.equalsIgnoreCase("%dmg%")) continue;
            for (String string4 : this.f_dt.keySet()) {
                if (!entityDamageByEntityEvent.hasMetadata("DDT_" + string4)) continue;
                d3 = ((MetadataValue)entityDamageByEntityEvent.getMetadata("DDT_" + string4).get(0)).asDouble();
                if (d2 > 0.0) {
                    d3 /= d2;
                }
                if (d3 <= 0.0) continue;
                object.add(this.getDamageTypeFormat(string4).replace("%dmg%", String.valueOf(Utils.round3(d3))));
            }
            for (String string4 : this.f_ds.keySet()) {
                if (!entityDamageByEntityEvent.hasMetadata("DDS_" + string4)) continue;
                d3 = ((MetadataValue)entityDamageByEntityEvent.getMetadata("DDS_" + string4).get(0)).asDouble();
                if (d2 > 0.0) {
                    d3 /= d2;
                }
                if (d3 <= 0.0) continue;
                object.add(this.getDamageTypeSource(string4).replace("%dmg%", String.valueOf(Utils.round3(d3))));
            }
        }
        string3 = entityDamageByEntityEvent.getEyeLocation().clone().add(0.0, 1.25, 0.0);
        this.plugin.getHM().getHoloHook().createIndicator((Location)string3, (List<String>)object);
        this.removeAllMeta((LivingEntity)entityDamageByEntityEvent);
    }
}

