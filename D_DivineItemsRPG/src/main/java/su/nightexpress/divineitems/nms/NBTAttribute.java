/*
 * Decompiled with CFR 0_133.
 */
package su.nightexpress.divineitems.nms;

public enum NBTAttribute {
    armor("armor"),
    armorToughness("armorToughness"),
    attackDamage("attackDamage"),
    attackSpeed("attackSpeed"),
    movementSpeed("movementSpeed"),
    maxHealth("maxHealth"),
    knockbackResistance("knockbackResistance");
    
    private String s;

    private NBTAttribute(String string2, int n2, String string3) {
        this.s = string2;
    }

    public String att() {
        return this.s;
    }
}

