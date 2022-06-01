/*
 * Decompiled with CFR 0_133.
 * 
 * Could not load the following classes:
 *  net.md_5.bungee.api.ChatColor
 */
package su.nightexpress.divineitems.attributes;

import net.md_5.bungee.api.ChatColor;
import su.nightexpress.divineitems.attributes.StatSettings;

public enum ItemStat {
    DIRECT_DAMAGE("Direct Damage", "&f\u25b8", "&f", "&6\u23cf&f", 100.0, ItemType.WEAPON, true, false, null),
    AOE_DAMAGE("AoE Damage", "&3\u25b8", "&f", "&6\u23cf&f", -1.0, ItemType.WEAPON, true, false, null),
    PVP_DAMAGE("PvP Damage", "&b\u25b8", "&f", "&6\u23cf&f", -1.0, ItemType.WEAPON, true, true, null),
    PVE_DAMAGE("PvE Damage", "&b\u25b8", "&f", "&6\u23cf&f", -1.0, ItemType.WEAPON, true, true, null),
    DODGE_RATE("Dodge Rate", "&6\u25b8", "&f", "&6\u23cf&f", -1.0, ItemType.ARMOR, true, true, null),
    ACCURACY_RATE("Accuracy Rate", "&6\u25b8", "&f", "&6\u23cf&f", -1.0, ItemType.WEAPON, true, true, null),
    BLOCK_RATE("Block Rate", "&6\u25b8", "&f", "&6\u23cf&f", -1.0, ItemType.ARMOR, true, true, null),
    BLOCK_DAMAGE("Block Damage", "&6\u25b8", "&f", "&6\u23cf&f", -1.0, ItemType.ARMOR, true, true, null),
    LOOT_RATE("Loot Rate", "&e\u25b8", "&f", "&6\u23cf&f", 250.0, ItemType.BOTH, true, true, null),
    BURN_RATE("Chance to Burn", "&c\u25b8", "&f", "&6\u23cf&f", 100.0, ItemType.WEAPON, true, false, null),
    PVP_DEFENSE("PvP Defense", "&b\u25b8", "&f", "&6\u23cf&f", -1.0, ItemType.ARMOR, true, true, null),
    PVE_DEFENSE("PvE Defense", "&b\u25b8", "&f", "&6\u23cf&f", -1.0, ItemType.ARMOR, true, true, null),
    CRITICAL_RATE("Critical Rate", "&e\u25b8", "&f", "&6\u23cf&f", 75.0, ItemType.WEAPON, true, true, null),
    CRITICAL_DAMAGE("Critical Damage", "&e\u25b8", "&f", "&6\u23cf&f", 3.0, ItemType.WEAPON, false, false, null),
    DURABILITY("Durability", "&f\u25b8", "&f", "&6\u23cf&f", -1.0, ItemType.BOTH, false, false, null),
    DURABILITY_UNBREAK("Unbreakable", "&f\u25b8", "&f", "&6\u23cf&f", -1.0, ItemType.BOTH, false, false, null),
    MOVEMENT_SPEED("Movement speed", "&3\u25b8", "&f", "&6\u23cf&f", -1.0, ItemType.BOTH, true, true, null),
    PENETRATION("Armor Penetration", "&c\u25b8", "&f", "&6\u23cf&f", 45.0, ItemType.WEAPON, true, true, null),
    ATTACK_SPEED("Attack Speed", "&3\u25b8", "&f", "&6\u23cf&f", -1.0, ItemType.WEAPON, true, true, null),
    VAMPIRISM("Vampirism", "&c\u25b8", "&f", "&6\u23cf&f", 45.0, ItemType.WEAPON, true, true, null),
    MAX_HEALTH("Max Health", "&3\u25b8", "&f", "&6\u23cf&f", 30.0, ItemType.BOTH, false, true, null),
    BLEED_RATE("Chance to Open Wounds", "&c\u25b8", "&f", "&6\u23cf&f", 75.0, ItemType.WEAPON, true, true, null),
    DISARM_RATE("Chance to Disarm", "&c\u25b8", "&f", "&6\u23cf&f", 25.0, ItemType.WEAPON, true, true, null),
    RANGE("Range", "&6\u25b8", "&f", "&6\u23cf&f", 7.0, ItemType.WEAPON, false, false, null);
    
    private String name;
    private String prefix;
    private String value;
    private String bonus;
    private double cap;
    private ItemType type;
    private boolean p;
    private boolean plus;
    private StatSettings settings;

    /*
     * Exception decompiling
     */
    private ItemStat(String var3_2, int var4_3, String var5_4, String var6_5, String var7_6, String var8_7, double var9_8, ItemType varnull, boolean var12_10, boolean var13_11, StatSettings var14_12) {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // java.lang.IllegalStateException
        // org.benf.cfr.reader.bytecode.analysis.variables.VariableFactory.localVariable(VariableFactory.java:59)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op02WithProcessedDataAndRefs.mkRetrieve(Op02WithProcessedDataAndRefs.java:890)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op02WithProcessedDataAndRefs.createStatement(Op02WithProcessedDataAndRefs.java:938)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op02WithProcessedDataAndRefs$11.call(Op02WithProcessedDataAndRefs.java:1983)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op02WithProcessedDataAndRefs$11.call(Op02WithProcessedDataAndRefs.java:1980)
        // org.benf.cfr.reader.util.graph.AbstractGraphVisitorFI.process(AbstractGraphVisitorFI.java:63)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op02WithProcessedDataAndRefs.convertToOp03List(Op02WithProcessedDataAndRefs.java:1992)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:368)
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

    public String getName() {
        return ChatColor.translateAlternateColorCodes((char)'&', (String)this.name);
    }

    public void setName(String string) {
        this.name = string;
    }

    public String getPrefix() {
        return ChatColor.translateAlternateColorCodes((char)'&', (String)this.prefix);
    }

    public void setPrefix(String string) {
        this.prefix = string;
    }

    public String getValue() {
        return ChatColor.translateAlternateColorCodes((char)'&', (String)this.value);
    }

    public void setValue(String string) {
        this.value = string;
    }

    public String getBonus() {
        return ChatColor.translateAlternateColorCodes((char)'&', (String)this.bonus);
    }

    public void setBonus(String string) {
        this.bonus = string;
    }

    public double getCapability() {
        return this.cap;
    }

    public void setCapability(double d) {
        this.cap = d;
    }

    public ItemType getType() {
        return this.type;
    }

    public void setType(ItemType itemType) {
        this.type = itemType;
    }

    public boolean isPercent() {
        return this.p;
    }

    public boolean isPlus() {
        return this.plus;
    }

    public StatSettings getSettings() {
        return this.settings;
    }

    public void setSettings(StatSettings statSettings) {
        this.settings = statSettings;
    }

    public static enum ItemType {
        ARMOR,
        WEAPON,
        BOTH;
        

        private ItemType(String string2, int n2) {
        }
    }

}

