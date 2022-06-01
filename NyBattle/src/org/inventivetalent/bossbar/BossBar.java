package org.inventivetalent.bossbar;

import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class BossBar {

    String msg = "";
    List<Player> ps = new ArrayList<>();

    public BossBar(List<Player> asList, String name) {
        this.msg = name;
        asList.forEach(p -> addPlayer(p));
    }

    public void setProgress(float v) {
        ps.forEach(p -> p.sendMessage(this.msg));
    }

    public void addPlayer(Player p) {
        p.sendMessage(this.msg);
        ps.add(p);
    }

    public void setMessage(String replace) {
        msg = replace;
        ps.forEach(p -> p.sendMessage(this.msg));
    }

    public void removePlayer(Player p) {
        this.ps.remove(p);
    }

    public List<Player> getPlayers() {
        return this.ps;
    }
}
