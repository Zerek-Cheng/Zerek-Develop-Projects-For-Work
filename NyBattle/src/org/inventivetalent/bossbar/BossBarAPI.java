package org.inventivetalent.bossbar;

import org.bukkit.entity.Player;

import java.util.List;

public class BossBarAPI {
    public static BossBar addBar(List<Player> asList, String name, Color white, Style notched10, float v) {
        return new BossBar(asList,name);
    }

    public enum Color {
        YELLOW, WHITE
    }

    public enum Style {PROGRESS, NOTCHED_10}
}
