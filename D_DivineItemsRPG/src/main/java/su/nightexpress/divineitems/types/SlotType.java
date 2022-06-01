/*
 * Decompiled with CFR 0_133.
 */
package su.nightexpress.divineitems.types;

import su.nightexpress.divineitems.Module;

public enum SlotType {
    GEM(null, 0, null, "&2\u1690\u1691\u1692\u1693\u1694\u168d\u168e\u168f &a&lGems &2\u168f\u168e\u168d\u1694\u1693\u1692\u1691\u1690", "&a\u25a1 (Slot) Gem", "&a\u25a3 Gem: &f"),
    RUNE(null, 0, null, "&3\u1690\u1691\u1692\u1693\u1694\u168d\u168e\u168f &b&lRunes &3\u168f\u168e\u168d\u1694\u1693\u1692\u1691\u1690", "&b\u25c7 (Slot) Rune", "&b\u25c8 Rune: &f"),
    ENCHANT(null, 0, null, "&4\u1690\u1691\u1692\u1693\u1694\u168d\u168e\u168f &c&lEnchants &4\u168f\u168e\u168d\u1694\u1693\u1692\u1691\u1690", "&c\u25cb (Slot) Enchant", "&c\u25c9 Enchant: &f"),
    ABILITY(null, 0, null, "&5\u1691\u1692\u1693\u1694\u168d\u168e\u168f &d&lAbilities &5\u168f\u168e\u168d\u1694\u1693\u1692\u1691\u1690", "&d\u266f (Slot) Ability", "&d\u26a1 Ability: &f");

    private Module m;
    private String head;
    private String empty;
    private String filled;

    private SlotType(String string2, int n2, Module module, String string3, String string4, String string5) {
        this.setModule((Module) ((Object) string2));
        this.setHeader(String.valueOf(n2));
        this.setEmpty((String) ((Object) module));
        this.setFilled(string3);
    }

    public Module getModule() {
        return this.m;
    }

    public void setModule(Module module) {
        this.m = module;
    }

    public String getHeader() {
        return this.head;
    }

    public void setHeader(String string) {
        this.head = string;
    }

    public String getEmpty() {
        return this.empty;
    }

    public void setEmpty(String string) {
        this.empty = string;
    }

    public String getFilled() {
        return this.filled;
    }

    public void setFilled(String string) {
        this.filled = string;
    }
}

