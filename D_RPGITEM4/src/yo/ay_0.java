/*
 * Decompiled with CFR 0_133.
 * 
 * Could not load the following classes:
 *  org.bukkit.Bukkit
 *  org.bukkit.entity.Player
 */
package yo;

import java.util.ArrayList;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import yo.aa_1;
import yo.ac_1;
import yo.ao_0;

class ay_0
extends aa_1 {
    ay_0() {
    }

    @Override
    public void a(String a2) {
    }

    @Override
    public Object a(String in, String locale) {
        Player player = Bukkit.getPlayer((String)in);
        if (player == null) {
            return new ac_1(String.format(ao_0.a("message.error.player", locale), in));
        }
        return player;
    }

    @Override
    public List<String> b(String in) {
        List players = Bukkit.matchPlayer((String)in);
        ArrayList<String> out = new ArrayList<String>();
        for (Player player : players) {
            out.add(player.getName());
        }
        return out;
    }

    @Override
    public String c(String locale) {
        return ao_0.a("command.info.player", locale);
    }

    @Override
    public Class<?> b() {
        return Player.class;
    }
}

