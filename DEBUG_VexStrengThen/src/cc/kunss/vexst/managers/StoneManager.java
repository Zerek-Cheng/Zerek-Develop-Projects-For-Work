/*
 * Decompiled with CFR 0_133.
 */
package cc.kunss.vexst.managers;

public class StoneManager {
    private String ShowName;
    private String lore;

    public StoneManager(String lore, String showName) {
        this.setLore(lore);
        this.setShowName(showName);
    }

    public String getLore() {
        return this.lore;
    }

    public void setLore(String lore) {
        this.lore = lore;
    }

    public String getShowName() {
        return this.ShowName;
    }

    public void setShowName(String showName) {
        this.ShowName = showName;
    }
}

