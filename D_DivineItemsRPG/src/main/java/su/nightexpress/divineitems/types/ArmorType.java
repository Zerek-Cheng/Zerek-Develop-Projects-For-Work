/*
 * Decompiled with CFR 0_133.
 */
package su.nightexpress.divineitems.types;

import java.util.List;

public class ArmorType {
    private String id;
    private String prefix;
    private String name;
    private String value;
    private boolean percent;
    private List<String> bds;
    private List<String> bdt;
    private String formula;

    public ArmorType(String string, String string2, String string3, String string4, boolean bl, List<String> list, List<String> list2, String string5) {
        this.setId(string);
        this.setPrefix(string2);
        this.setName(string3);
        this.setValue(string4);
        this.setPercent(bl);
        this.setBlockDamageSources(list);
        this.setBlockDamageTypes(list2);
        this.setFormula(string5);
    }

    public String getId() {
        return this.id;
    }

    public void setId(String string) {
        this.id = string.toLowerCase();
    }

    public String getPrefix() {
        return this.prefix;
    }

    public void setPrefix(String string) {
        this.prefix = string;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String string) {
        this.name = string;
    }

    public String getValue() {
        return this.value;
    }

    public void setValue(String string) {
        this.value = string;
    }

    public boolean isPercent() {
        return this.percent;
    }

    public void setPercent(boolean bl) {
        this.percent = bl;
    }

    public List<String> getBlockDamageSources() {
        return this.bds;
    }

    public void setBlockDamageSources(List<String> list) {
        this.bds = list;
    }

    public List<String> getBlockDamageTypes() {
        return this.bdt;
    }

    public void setBlockDamageTypes(List<String> list) {
        this.bdt = list;
    }

    public String getFormula() {
        return this.formula;
    }

    public void setFormula(String string) {
        this.formula = string;
    }
}

