/*
 * Decompiled with CFR 0_133.
 * 
 * Could not load the following classes:
 *  org.bukkit.Server
 *  org.bukkit.command.ConsoleCommandSender
 *  org.bukkit.event.Listener
 *  org.bukkit.plugin.Plugin
 *  org.bukkit.plugin.PluginDescriptionFile
 *  org.bukkit.plugin.PluginManager
 */
package su.nightexpress.divineitems.hooks;

import org.bukkit.Server;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import su.nightexpress.divineitems.DivineItems;
import su.nightexpress.divineitems.config.Config;
import su.nightexpress.divineitems.config.ConfigManager;
import su.nightexpress.divineitems.hooks.ClassesHook;
import su.nightexpress.divineitems.hooks.Hook;
import su.nightexpress.divineitems.hooks.LevelsHook;
import su.nightexpress.divineitems.hooks.external.HologramsHook;
import su.nightexpress.divineitems.hooks.external.MythicMobsHook;
import su.nightexpress.divineitems.hooks.external.RPGInvHook;
import su.nightexpress.divineitems.hooks.external.VaultHook;
import su.nightexpress.divineitems.hooks.external.WorldGuardHook;
import su.nightexpress.divineitems.hooks.external.citizens.CitizensHook;
import su.nightexpress.divineitems.hooks.placeholders.PlaceholderAPI;
import su.nightexpress.divineitems.listeners.MythicListener;
import su.nightexpress.divineitems.listeners.SkillAPIListener;

public class HookManager {
    private DivineItems plugin;
    private ClassesHook ch;
    private LevelsHook lh;
    private HologramsHook hh;
    private MythicMobsHook mmh;
    private CitizensHook npc;
    private WorldGuardHook wg;
    private RPGInvHook rpginv;
    private VaultHook vault;
    private Hook pl_class;
    private Hook pl_level;
    private static /* synthetic */ int[] $SWITCH_TABLE$su$nightexpress$divineitems$hooks$Hook;

    public HookManager(DivineItems divineItems) {
        this.plugin = divineItems;
        this.ch = new ClassesHook(divineItems, this);
        this.lh = new LevelsHook(this);
    }

    public void setup() {
        PluginManager pluginManager = this.plugin.getPluginManager();
        this.pl_class = this.plugin.getCM().getCFG().getClassPlugin();
        this.pl_level = this.plugin.getCM().getCFG().getLevelPlugin();
        Hook[] arrhook = Hook.values();
        int n = arrhook.length;
        int n2 = 0;
        while (n2 < n) {
            block15 : {
                Hook hook;
                Plugin plugin;
                block17 : {
                    block16 : {
                        hook = arrhook[n2];
                        plugin = pluginManager.getPlugin(hook.getPluginName());
                        if (plugin == null || !plugin.isEnabled() || hook.isEnabled()) break block15;
                        if (hook != Hook.VAULT) break block16;
                        hook.enable();
                        this.vault = new VaultHook(this.plugin);
                        this.vault.setupEconomy();
                        break block15;
                    }
                    if (hook.isClassPlugin() && this.pl_class == hook) {
                        if (hook == Hook.SKILL_API) {
                            this.plugin.getPluginManager().registerEvents((Listener)new SkillAPIListener(this.plugin), (Plugin)this.plugin);
                        }
                        hook.enable();
                    }
                    if (!hook.isLevelPlugin()) break block17;
                    if (this.pl_level != hook) break block15;
                    hook.enable();
                }
                hook.enable();
                switch (HookManager.$SWITCH_TABLE$su$nightexpress$divineitems$hooks$Hook()[hook.ordinal()]) {
                    case 7: {
                        this.hh = new HologramsHook(this.plugin);
                        break;
                    }
                    case 8: {
                        this.mmh = new MythicMobsHook();
                        this.plugin.getPluginManager().registerEvents((Listener)new MythicListener(this.plugin), (Plugin)this.plugin);
                        break;
                    }
                    case 6: {
                        if (plugin.getDescription().getVersion().startsWith("7")) {
                            hook.disable();
                            break;
                        }
                        this.wg = new WorldGuardHook(this.plugin);
                        break;
                    }
                    case 1: {
                        this.npc = new CitizensHook();
                        this.npc.registerTraits();
                        break;
                    }
                    case 11: {
                        PlaceholderAPI.registerPlaceholderAPI();
                        break;
                    }
                    case 12: {
                        this.rpginv = new RPGInvHook(this.plugin);
                        break;
                    }
                }
            }
            ++n2;
        }
        this.sendStatus();
    }

    private void sendStatus() {
        this.plugin.getServer().getConsoleSender().sendMessage("\u00a73---------[ \u00a7bHooks Initializing \u00a73]---------");
        Hook[] arrhook = Hook.values();
        int n = arrhook.length;
        int n2 = 0;
        while (n2 < n) {
            Hook hook = arrhook[n2];
            if (hook != Hook.NONE) {
                this.plugin.getServer().getConsoleSender().sendMessage("\u00a77> \u00a7f" + hook.getPluginName() + ": " + this.getColorStatus(hook.isEnabled()));
            }
            ++n2;
        }
    }

    private String getColorStatus(boolean bl) {
        if (bl) {
            return "\u00a7aSuccess!";
        }
        return "\u00a7cNot found / Error.";
    }

    public void disable() {
        Hook[] arrhook = Hook.values();
        int n = arrhook.length;
        int n2 = 0;
        while (n2 < n) {
            Hook hook = arrhook[n2];
            if (hook.isEnabled() && hook != Hook.MYTHIC_MOBS) {
                if (hook == Hook.CITIZENS) {
                    this.npc.unregisterTraits();
                }
                hook.disable();
            }
            ++n2;
        }
    }

    public ClassesHook getClassesHook() {
        return this.ch;
    }

    public LevelsHook getLevelsHook() {
        return this.lh;
    }

    public HologramsHook getHoloHook() {
        return this.hh;
    }

    public MythicMobsHook getMythicHook() {
        return this.mmh;
    }

    public CitizensHook getCitizens() {
        return this.npc;
    }

    public WorldGuardHook getWorldGuard() {
        return this.wg;
    }

    public RPGInvHook getRPGInventory() {
        return this.rpginv;
    }

    public VaultHook getVault() {
        return this.vault;
    }

    public Hook getLevelPlugin() {
        return this.pl_level;
    }

    public Hook getClassPlugin() {
        return this.pl_class;
    }

    static /* synthetic */ int[] $SWITCH_TABLE$su$nightexpress$divineitems$hooks$Hook() {
        int[] arrn;
        int[] arrn2 = $SWITCH_TABLE$su$nightexpress$divineitems$hooks$Hook;
        if (arrn2 != null) {
            return arrn2;
        }
        arrn = new int[Hook.values().length];
        try {
            arrn[Hook.BATTLE_LEVELS.ordinal()] = 2;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Hook.CITIZENS.ordinal()] = 1;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Hook.DIVINE_CLASSES.ordinal()] = 3;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Hook.HEROES.ordinal()] = 9;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Hook.HOLOGRAPHIC_DISPLAYS.ordinal()] = 7;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Hook.MCMMO.ordinal()] = 13;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Hook.MYTHIC_MOBS.ordinal()] = 8;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Hook.NONE.ordinal()] = 14;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Hook.PLACEHOLDER_API.ordinal()] = 11;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Hook.RACES_OF_THANA.ordinal()] = 16;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Hook.RPG_INVENTORY.ordinal()] = 12;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Hook.RPGme.ordinal()] = 15;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Hook.SKILLS.ordinal()] = 5;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Hook.SKILL_API.ordinal()] = 4;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Hook.VAULT.ordinal()] = 10;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            arrn[Hook.WORLD_GUARD.ordinal()] = 6;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        $SWITCH_TABLE$su$nightexpress$divineitems$hooks$Hook = arrn;
        return $SWITCH_TABLE$su$nightexpress$divineitems$hooks$Hook;
    }
}

