package org.bukkit.boss;

import java.util.List;
import org.bukkit.entity.Player;

public abstract interface BossBar
{
    public abstract String getTitle();

    public abstract void setTitle(String paramString);

    public abstract BarColor getColor();

    public abstract void setColor(BarColor paramBarColor);

    public abstract BarStyle getStyle();

    public abstract void setStyle(BarStyle paramBarStyle);

    public abstract void removeFlag(BarFlag paramBarFlag);

    public abstract void addFlag(BarFlag paramBarFlag);

    public abstract boolean hasFlag(BarFlag paramBarFlag);

    public abstract void setProgress(double paramDouble);

    public abstract double getProgress();

    public abstract void addPlayer(Player paramPlayer);

    public abstract void removePlayer(Player paramPlayer);

    public abstract void removeAll();

    public abstract List<Player> getPlayers();

    public abstract void setVisible(boolean paramBoolean);

    public abstract boolean isVisible();

    @Deprecated
    public abstract void show();

    @Deprecated
    public abstract void hide();
}
