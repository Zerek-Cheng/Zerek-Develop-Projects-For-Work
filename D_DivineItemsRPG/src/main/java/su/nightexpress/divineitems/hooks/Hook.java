/*
 * Decompiled with CFR 0_133.
 */
package su.nightexpress.divineitems.hooks;

public enum Hook {
    CITIZENS("Citizens", false, false),
    BATTLE_LEVELS("BattleLevels", false, true),
    DIVINE_CLASSES("DivineClassesRPG", true, true),
    SKILL_API("SkillAPI", true, true),
    SKILLS("Skills", true, true),
    WORLD_GUARD("WorldGuard", false, false),
    HOLOGRAPHIC_DISPLAYS("HolographicDisplays", false, false),
    MYTHIC_MOBS("MythicMobs", false, false),
    HEROES("Heroes", true, true),
    VAULT("Vault", true, false),
    PLACEHOLDER_API("PlaceholderAPI", false, false),
    RPG_INVENTORY("RPGInventory", false, false),
    MCMMO("mcMMO", false, true),
    NONE("None", false, true),
    RPGme("RPGme", false, true),
    RACES_OF_THANA("RacesOfThana", true, false);
    
    private String plugin;
    private boolean can_cls;
    private boolean can_lvl;
    private boolean e = false;

    private Hook(String string2, int n2, String string3, boolean bl, boolean bl2) {
        this.plugin = string2;
        this.can_cls = n2;
        this.can_lvl = string3;
    }

    public boolean isEnabled() {
        return this.e;
    }

    public void enable() {
        this.e = true;
    }

    public void disable() {
        this.e = false;
    }

    public String getPluginName() {
        return this.plugin;
    }

    public boolean isClassPlugin() {
        return this.can_cls;
    }

    public boolean isLevelPlugin() {
        return this.can_lvl;
    }
}

