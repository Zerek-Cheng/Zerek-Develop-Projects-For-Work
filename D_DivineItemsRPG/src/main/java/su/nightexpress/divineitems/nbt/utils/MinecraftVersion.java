/*
 * Decompiled with CFR 0_133.
 * 
 * Could not load the following classes:
 *  org.bukkit.Bukkit
 */
package su.nightexpress.divineitems.nbt.utils;

import org.bukkit.Bukkit;

public enum MinecraftVersion {
    Unknown(Integer.MAX_VALUE),
    MC1_7_R4(174),
    MC1_8_R3(183),
    MC1_9_R1(191),
    MC1_9_R2(192),
    MC1_10_R1(1101),
    MC1_11_R1(1111),
    MC1_12_R1(1121),
    MC1_13_R1(1131),
    MC1_13_R2(1132);
    
    private static MinecraftVersion version;
    private static Boolean hasGsonSupport;
    private final int versionId;

    private MinecraftVersion(String string2, int n2, int n3) {
        this.versionId = (int)string2;
    }

    public int getVersionId() {
        return this.versionId;
    }

    public static MinecraftVersion getVersion() {
        if (version != null) {
            return version;
        }
        String string = Bukkit.getServer().getClass().getPackage().getName().replace(".", ",").split(",")[3];
        try {
            version = MinecraftVersion.valueOf(string.replace("v", "MC"));
        }
        catch (IllegalArgumentException illegalArgumentException) {
            version = Unknown;
        }
        return version;
    }

    public static boolean hasGsonSupport() {
        if (hasGsonSupport != null) {
            return hasGsonSupport;
        }
        try {
            hasGsonSupport = true;
        }
        catch (Exception exception) {
            hasGsonSupport = false;
        }
        return hasGsonSupport;
    }
}

