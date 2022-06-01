/*
 * Decompiled with CFR 0_133.
 * 
 * Could not load the following classes:
 *  org.bukkit.ChatColor
 *  org.bukkit.configuration.file.FileConfiguration
 *  org.bukkit.entity.Item
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.entity.ItemMergeEvent
 *  org.bukkit.event.entity.ItemSpawnEvent
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.plugin.java.JavaPlugin
 */
package su.nightexpress.divineitems.modules.itemhints;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Item;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.ItemMergeEvent;
import org.bukkit.event.entity.ItemSpawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import su.nightexpress.divineitems.DivineItems;
import su.nightexpress.divineitems.DivineListener;
import su.nightexpress.divineitems.Module;
import su.nightexpress.divineitems.config.Config;
import su.nightexpress.divineitems.config.ConfigManager;
import su.nightexpress.divineitems.config.MyConfig;
import su.nightexpress.divineitems.modules.ModuleManager;

public class ItemHintsManager
extends DivineListener<DivineItems>
implements Module {
    private DivineItems plugin;
    private MyConfig settingsCfg;
    private boolean e;
    private boolean s_forAll;
    private boolean s_glow;
    private String s_formatOne;
    private String s_formatMany;
    private HashMap<String, Boolean> s_for;
    private List<String> s_blackList;
    private List<String> s_gBlackList;
    private final String n = this.name().toLowerCase().replace(" ", "_");

    public ItemHintsManager(DivineItems divineItems) {
        super(divineItems);
        this.plugin = divineItems;
        this.e = this.plugin.getCM().getCFG().isModuleEnabled(this.name());
    }

    @Override
    public void loadConfig() {
        this.settingsCfg = new MyConfig(this.plugin, "/modules/" + this.n, "settings.yml");
        this.s_for = new HashMap();
        this.s_blackList = new ArrayList<String>();
        FileConfiguration fileConfiguration = this.settingsCfg.getConfig();
        this.s_forAll = fileConfiguration.getBoolean("ForAllItems");
        this.s_glow = fileConfiguration.getBoolean("Glow");
        this.s_formatOne = ChatColor.translateAlternateColorCodes((char)'&', (String)fileConfiguration.getString("FormatOne"));
        this.s_formatMany = ChatColor.translateAlternateColorCodes((char)'&', (String)fileConfiguration.getString("FormatMany"));
        for (Module module : this.plugin.getMM().getModules()) {
            if (!module.isActive() || !module.isDropable()) continue;
            if (!fileConfiguration.contains("EnabledFor." + module.name())) {
                fileConfiguration.set("EnabledFor." + module.name(), (Object)true);
            }
            boolean bl = fileConfiguration.getBoolean("EnabledFor." + module.name());
            this.s_for.put(module.name(), bl);
        }
        if (!fileConfiguration.contains("ItemBlackList")) {
            fileConfiguration.set("ItemBlackList", Arrays.asList("BEDROCK", "BARRIER"));
        }
        if (!fileConfiguration.contains("ItemGlowBlackList")) {
            fileConfiguration.set("ItemGlowBlackList", Arrays.asList("BEDROCK", "BARRIER"));
        }
        this.s_blackList = fileConfiguration.getStringList("ItemBlackList");
        this.s_gBlackList = fileConfiguration.getStringList("ItemGlowBlackList");
        this.settingsCfg.save();
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
        return "Item Hints";
    }

    @Override
    public String version() {
        return "1.0.1";
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

    public boolean isForAll() {
        return this.s_forAll;
    }

    public boolean makeGlow() {
        return this.s_glow;
    }

    public String getNameFormatOne() {
        return this.s_formatOne;
    }

    public String getNameFormatMany() {
        return this.s_formatMany;
    }

    @EventHandler(ignoreCancelled=true)
    public void onItemSpawn(ItemSpawnEvent itemSpawnEvent) {
        Item item = itemSpawnEvent.getEntity();
        ItemStack itemStack = item.getItemStack();
        this.setItemHint(item, itemStack, 0, this.getNameFormatOne());
    }

    @EventHandler
    public void onItemMerge(ItemMergeEvent itemMergeEvent) {
        Item item = itemMergeEvent.getEntity();
        Item item2 = itemMergeEvent.getTarget();
        this.setItemHint(item2, item2.getItemStack(), item.getItemStack().getAmount(), this.getNameFormatMany());
    }

    /*
     * Exception decompiling
     */
    private void setItemHint(Item var1_1, ItemStack var2_2, int var3_3, String var4_4) {
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
}

