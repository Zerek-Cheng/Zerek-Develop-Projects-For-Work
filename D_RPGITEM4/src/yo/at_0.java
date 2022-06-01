/*
 * Decompiled with CFR 0_133.
 * 
 * Could not load the following classes:
 *  org.bukkit.entity.Player
 */
package yo;

import java.util.HashMap;
import org.bukkit.entity.Player;
import think.rpgitems.item.RPGItem;

public class at_0 {
    Object a;
    static HashMap<String, at_0> b;

    public static void a() {
        b = new HashMap();
    }

    public static at_0 a(Player player, RPGItem item, String name) {
        return b.get(player.getName() + "." + item.getID() + "." + name);
    }

    public at_0(Player player, RPGItem item, String name, Object value) {
        this.a = value;
        b.put(player.getName() + "." + item.getID() + "." + name, this);
    }

    public void a(Object value) {
        this.a = value;
    }

    public boolean b() {
        return (Boolean)this.a;
    }

    public byte c() {
        return (Byte)this.a;
    }

    public double d() {
        return (Double)this.a;
    }

    public float e() {
        return ((Float)this.a).floatValue();
    }

    public int f() {
        return (Integer)this.a;
    }

    public long g() {
        return (Long)this.a;
    }

    public short h() {
        return (Short)this.a;
    }

    public String i() {
        return (String)this.a;
    }

    public Object j() {
        return this.a;
    }
}

