/*
 * Decompiled with CFR 0_133.
 */
package su.nightexpress.divineitems.types;

import java.util.HashMap;
import java.util.List;

public class DamageType {
    private String id;
    private boolean def;
    private String prefix;
    private String name;
    private String value;
    private List<String> actions;
    private HashMap<String, Double> biome;

    public DamageType(String string, boolean bl, String string2, String string3, String string4, List<String> list, HashMap<String, Double> hashMap) {
        this.setId(string);
        this.setDefault(bl);
        this.setPrefix(string2);
        this.setName(string3);
        this.setValue(string4);
        this.setActions(list);
        this.setBiomeDamageModifiers(hashMap);
    }

    public String getId() {
        return this.id;
    }

    public void setId(String string) {
        this.id = string.toLowerCase();
    }

    public boolean isDefault() {
        return this.def;
    }

    public void setDefault(boolean bl) {
        this.def = bl;
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

    public List<String> getActions() {
        return this.actions;
    }

    public void setActions(List<String> list) {
        this.actions = list;
    }

    public HashMap<String, Double> getBiomeDamageModifiers() {
        return this.biome;
    }

    public void setBiomeDamageModifiers(HashMap<String, Double> hashMap) {
        this.biome = hashMap;
    }

    public double getDamageModifierByBiome(String string) {
        if (this.biome.containsKey(string.toUpperCase())) {
            return this.biome.get(string.toUpperCase());
        }
        return 1.0;
    }
}

