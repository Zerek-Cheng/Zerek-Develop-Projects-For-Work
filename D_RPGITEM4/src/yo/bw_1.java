/*
 * Decompiled with CFR 0_133.
 * 
 * Could not load the following classes:
 *  org.bukkit.configuration.ConfigurationSection
 */
package yo;

import java.util.ArrayList;
import java.util.List;
import org.bukkit.configuration.ConfigurationSection;

public class bw_1 {
    private String a;
    private List<Integer> b = new ArrayList<Integer>();

    public bw_1(String name) {
        this.a = name;
    }

    public bw_1(ConfigurationSection s) {
        this.a = s.getString("name");
        this.b = s.getIntegerList("items");
    }

    public void a(ConfigurationSection s) {
        s.set("name", (Object)this.a);
        s.set("items", this.b);
    }

    public String a() {
        return this.a;
    }
}

