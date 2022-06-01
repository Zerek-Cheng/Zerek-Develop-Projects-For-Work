/*
 * Decompiled with CFR 0_133.
 * 
 * Could not load the following classes:
 *  org.bukkit.Bukkit
 */
package su.nightexpress.divineitems.nms;

import org.bukkit.Bukkit;
import su.nightexpress.divineitems.nms.NMS;
import su.nightexpress.divineitems.nms.V1_11_R1;
import su.nightexpress.divineitems.nms.V1_12_R1;
import su.nightexpress.divineitems.nms.V1_13_R1;
import su.nightexpress.divineitems.nms.V1_13_R2;
import su.nightexpress.divineitems.nms.V1_9_R1;
import su.nightexpress.divineitems.nms.V1_9_R2;

public class VersionUtils {
    private Version version = Version.getCurrent();
    private NMS nms;

    public boolean setup() {
        if (this.setNMS()) {
            Bukkit.getConsoleSender().sendMessage("\u00a77> \u00a7fServer version: \u00a7a" + this.getVersion().getVersion() + " / OK!");
            return true;
        }
        return false;
    }

    public boolean setNMS() {
        String string = this.getVersion().getVersion();
        if (string.equals("v1_9_R1")) {
            this.nms = new V1_9_R1();
        } else if (string.equals("v1_9_R2")) {
            this.nms = new V1_9_R2();
        } else if (string.equals("v1_10_R1")) {
            this.nms = new V1_9_R1();
        } else if (string.equals("v1_11_R1")) {
            this.nms = new V1_11_R1();
        } else if (string.equals("v1_12_R1")) {
            this.nms = new V1_12_R1();
        } else if (string.equals("v1_13_R1")) {
            this.nms = new V1_13_R1();
        } else if (string.equals("v1_13_R2")) {
            this.nms = new V1_13_R2();
        }
        if (this.nms != null) {
            return true;
        }
        return false;
    }

    public NMS getNMS() {
        return this.nms;
    }

    public Version getVersion() {
        return this.version;
    }

    public Class<?> getNmsClass(String string) {
        return Class.forName("net.minecraft.server." + this.version.getVersion() + "." + string);
    }

    public static enum Version {
        v1_9_R1("v1_9_R1", 10),
        v1_9_R2("v1_9_R2", 10),
        v1_10_R1("v1_10_R1", 11),
        v1_11_R1("v1_11_R1", 12),
        v1_12_R1("v1_12_R1", 13),
        v1_13_R1("v1_13_R1", 14),
        v1_13_R2("v1_13_R2", 15);
        
        private int n;
        private String s;

        private Version(String string2, int n2, String string3, int n3) {
            this.n = n2;
            this.s = string2;
        }

        public String getVersion() {
            return this.s;
        }

        public int getValue() {
            return this.n;
        }

        public static Version getCurrent() {
            String[] arrstring = Bukkit.getServer().getClass().getPackage().getName().split("\\.");
            String string = arrstring[arrstring.length - 1];
            Version[] arrversion = Version.values();
            int n = arrversion.length;
            int n2 = 0;
            while (n2 < n) {
                Version version = arrversion[n2];
                if (version.name().equalsIgnoreCase(string)) {
                    return version;
                }
                ++n2;
            }
            return null;
        }

        public boolean isLower(Version version) {
            if (this.getValue() < version.getValue()) {
                return true;
            }
            return false;
        }

        public boolean isHigher(Version version) {
            if (this.getValue() > version.getValue()) {
                return true;
            }
            return false;
        }
    }

}

