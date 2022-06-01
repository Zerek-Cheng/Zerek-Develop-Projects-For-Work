// 
// Decompiled by Procyon v0.5.30
// 

package yo;

import org.bukkit.configuration.ConfigurationSection;
import java.util.ArrayList;
import java.util.List;

public class bw
{
    private String a;
    private List<Integer> b;
    
    public bw(final String name) {
        this.b = new ArrayList<Integer>();
        this.a = name;
    }
    
    public bw(final ConfigurationSection s) {
        this.b = new ArrayList<Integer>();
        this.a = s.getString("name");
        this.b = (List<Integer>)s.getIntegerList("items");
    }
    
    public void a(final ConfigurationSection s) {
        s.set("name", (Object)this.a);
        s.set("items", (Object)this.b);
    }
    
    public String a() {
        return this.a;
    }
}
