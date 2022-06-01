/*
 * Decompiled with CFR 0_133.
 */
package su.nightexpress.divineitems;

public enum DivinePerms {
    REPAIR_ANVL("divineitems.repair.anvil");
    
    private String node;

    private DivinePerms(String string2, int n2, String string3) {
        this.node = string2;
    }

    public String node() {
        return this.node;
    }
}

