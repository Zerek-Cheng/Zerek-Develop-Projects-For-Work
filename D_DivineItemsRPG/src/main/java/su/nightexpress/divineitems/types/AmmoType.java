/*
 * Decompiled with CFR 0_133.
 */
package su.nightexpress.divineitems.types;

public enum AmmoType {
    ARROW(true, "&f\u27b6", "Arrow"),
    SNOWBALL(true, "&9\u2746&f", "Snowball"),
    EGG(true, "&c\u26ab&f", "Egg"),
    FIREBALL(true, "&c\u2604&f", "Fireball"),
    WITHER_SKULL(true, "&8\u2622&f", "Wither Skull"),
    SHULKER_BULLET(true, "&d\u2726&f", "Shulker Bullet"),
    LLAMA_SPIT(true, "&e\u2614&f", "Llama Spit"),
    ENDER_PEARL(true, "&b\u25c9", "Ender Peral"),
    EXP_POTION(true, "&e\u2618", "Exp Potion");
    
    private boolean enabled;
    private String prefix;
    private String name;

    private AmmoType(boolean string2,String string3, String string4) {
        this.setEnabled((boolean)string2);
        this.setPrefix((String)string3);
        this.setName((String)string4);
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

    public boolean isEnabled() {
        return this.enabled;
    }

    public void setEnabled(boolean bl) {
        this.enabled = bl;
    }
}

