/*
 * Decompiled with CFR 0_133.
 * 
 * Could not load the following classes:
 *  org.bukkit.Server
 *  org.bukkit.command.ConsoleCommandSender
 */
package su.nightexpress.divineitems.modules;

import java.util.ArrayList;
import java.util.List;
import org.bukkit.Server;
import org.bukkit.command.ConsoleCommandSender;
import su.nightexpress.divineitems.DivineItems;
import su.nightexpress.divineitems.Module;
import su.nightexpress.divineitems.modules.ability.AbilityManager;
import su.nightexpress.divineitems.modules.abyssdust.AbyssDustManager;
import su.nightexpress.divineitems.modules.arrows.ArrowManager;
import su.nightexpress.divineitems.modules.buffs.BuffManager;
import su.nightexpress.divineitems.modules.combatlog.CombatLogManager;
import su.nightexpress.divineitems.modules.consumes.ConsumeManager;
import su.nightexpress.divineitems.modules.customitems.CustomItemsManager;
import su.nightexpress.divineitems.modules.drops.DropManager;
import su.nightexpress.divineitems.modules.enchant.EnchantManager;
import su.nightexpress.divineitems.modules.gems.GemManager;
import su.nightexpress.divineitems.modules.identify.IdentifyManager;
import su.nightexpress.divineitems.modules.itemhints.ItemHintsManager;
import su.nightexpress.divineitems.modules.magicdust.MagicDustManager;
import su.nightexpress.divineitems.modules.notifications.NotificationsManager;
import su.nightexpress.divineitems.modules.repair.RepairManager;
import su.nightexpress.divineitems.modules.resolve.ResolveManager;
import su.nightexpress.divineitems.modules.runes.RuneManager;
import su.nightexpress.divineitems.modules.scrolls.ScrollManager;
import su.nightexpress.divineitems.modules.sets.SetManager;
import su.nightexpress.divineitems.modules.soulbound.SoulboundManager;
import su.nightexpress.divineitems.modules.tiers.TierManager;

public class ModuleManager {
    private DivineItems plugin;
    private List<Module> modules;
    private GemManager gems;
    private EnchantManager enchants;
    private RuneManager runes;
    private AbilityManager abilities;
    private RepairManager repair;
    private IdentifyManager iden;
    private ScrollManager scrolls;
    private ItemHintsManager ihm;
    private CustomItemsManager ci;
    private ResolveManager resolve;
    private TierManager tiers;
    private AbyssDustManager agem;
    private MagicDustManager dust;
    private ConsumeManager cons;
    private SoulboundManager soul;
    private NotificationsManager note;
    private BuffManager buffs;
    private DropManager drop;
    private CombatLogManager combat;
    private SetManager sets;
    private ArrowManager arrows;

    public ModuleManager(DivineItems divineItems) {
        this.plugin = divineItems;
        this.modules = new ArrayList<Module>();
    }

    public void initialize() {
        this.gems = new GemManager(this.plugin);
        this.enchants = new EnchantManager(this.plugin);
        this.runes = new RuneManager(this.plugin);
        this.abilities = new AbilityManager(this.plugin);
        this.scrolls = new ScrollManager(this.plugin);
        this.repair = new RepairManager(this.plugin);
        this.iden = new IdentifyManager(this.plugin);
        this.ihm = new ItemHintsManager(this.plugin);
        this.ci = new CustomItemsManager(this.plugin);
        this.resolve = new ResolveManager(this.plugin);
        this.tiers = new TierManager(this.plugin);
        this.agem = new AbyssDustManager(this.plugin);
        this.dust = new MagicDustManager(this.plugin);
        this.cons = new ConsumeManager(this.plugin);
        this.soul = new SoulboundManager(this.plugin);
        this.note = new NotificationsManager(this.plugin);
        this.buffs = new BuffManager(this.plugin);
        this.drop = new DropManager(this.plugin);
        this.combat = new CombatLogManager(this.plugin);
        this.sets = new SetManager(this.plugin);
        this.arrows = new ArrowManager(this.plugin);
        this.register(this.gems);
        this.register(this.enchants);
        this.register(this.runes);
        this.register(this.abilities);
        this.register(this.scrolls);
        this.register(this.repair);
        this.register(this.iden);
        this.register(this.ci);
        this.register(this.tiers);
        this.register(this.agem);
        this.register(this.dust);
        this.register(this.cons);
        this.register(this.soul);
        this.register(this.note);
        this.register(this.buffs);
        this.register(this.drop);
        this.register(this.combat);
        this.register(this.sets);
        this.register(this.arrows);
        this.register(this.resolve);
        this.register(this.ihm);
        this.sendStatus();
    }

    public void sendStatus() {
        this.plugin.getServer().getConsoleSender().sendMessage("\u00a76---------[ \u00a7eModules Initializing \u00a76]---------");
        for (Module module : this.modules) {
            this.plugin.getServer().getConsoleSender().sendMessage("\u00a77> \u00a7f" + module.name() + "\u00a77 v" + module.version() + ": " + this.getColorStatus(module.isActive()));
        }
    }

    public void disable() {
        for (Module module : this.modules) {
            module.unload();
            module = null;
        }
        this.modules.clear();
    }

    public void cfgrel() {
        for (Module module : this.modules) {
            module.loadConfig();
        }
    }

    public void register(Module module) {
        module.enable();
        this.modules.add(module);
    }

    public <T extends Module> T getModule(Class<T> class_) {
        for (Module module : this.modules) {
            if (!class_.isAssignableFrom(module.getClass())) continue;
            return (T)module;
        }
        return null;
    }

    public List<Module> getModules() {
        return this.modules;
    }

    public List<String> getModuleLabels() {
        ArrayList<String> arrayList = new ArrayList<String>();
        for (Module module : this.getModules()) {
            if (!module.isActive()) continue;
            String string = module.name().toLowerCase().replace(" ", "_");
            String string2 = string.replace("_", "");
            arrayList.add(string2);
        }
        return arrayList;
    }

    public String getColorStatus(boolean bl) {
        if (bl) {
            return "\u00a7aOK!";
        }
        return "\u00a7cOFF";
    }

    public GemManager getGemManager() {
        return this.gems;
    }

    public EnchantManager getEnchantManager() {
        return this.enchants;
    }

    public RuneManager getRuneManager() {
        return this.runes;
    }

    public AbilityManager getAbilityManager() {
        return this.abilities;
    }

    public RepairManager getRepairManager() {
        return this.repair;
    }

    public IdentifyManager getIdentifyManager() {
        return this.iden;
    }

    public ScrollManager getScrollManager() {
        return this.scrolls;
    }

    public ItemHintsManager getItemHintsManager() {
        return this.ihm;
    }

    public CustomItemsManager getCustomItemsManager() {
        return this.ci;
    }

    public ResolveManager getResolveManager() {
        return this.resolve;
    }

    public TierManager getTierManager() {
        return this.tiers;
    }

    public AbyssDustManager getAbyssDustManager() {
        return this.agem;
    }

    public MagicDustManager getMagicDustManager() {
        return this.dust;
    }

    public SoulboundManager getSoulboundManager() {
        return this.soul;
    }

    public NotificationsManager getNotificationsManager() {
        return this.note;
    }

    public BuffManager getBuffManager() {
        return this.buffs;
    }

    public DropManager getDropManager() {
        return this.drop;
    }

    public CombatLogManager getCombatLogManager() {
        return this.combat;
    }

    public SetManager getSetManager() {
        return this.sets;
    }

    public ArrowManager getArrowManager() {
        return this.arrows;
    }

    public ConsumeManager getConsumeManager() {
        return this.cons;
    }
}

